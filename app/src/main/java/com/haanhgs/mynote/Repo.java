package com.haanhgs.mynote;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class Repo {

    public static Note createNote(JSONObject jsonObject)throws JSONException{
        Note note = new Note();
        note.setTitle(jsonObject.getString("title"));
        note.setDetail(jsonObject.getString("detail"));
        note.setIdea(jsonObject.getBoolean("idea"));
        note.setImportant(jsonObject.getBoolean("important"));
        note.setTodo(jsonObject.getBoolean("todo"));
        return note;
    }

    public static JSONObject toJson(Note note)throws JSONException{
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("title", note.getTitle());
        jsonObject.put("detail", note.getDetail());
        jsonObject.put("important", note.isImportant());
        jsonObject.put("todo", note.isTodo());
        jsonObject.put("idea", note.isIdea());
        return jsonObject;
    }

    public static void saveJson(List<Note> noteList, Context ct)throws JSONException, IOException{
        JSONArray array = new JSONArray();
        for (Note note:noteList) array.put(toJson(note));
        Writer writer = null;
        try{
            OutputStream out = ct.openFileOutput("save.json", Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(out);
            writer.write(array.toString());
        }finally {
            if (writer != null) writer.close();
        }
    }

    public static List<Note> loadJson(Context ct)throws JSONException, IOException{
        List<Note> noteList = new ArrayList<>();
        BufferedReader reader = null;
        File file = new File(ct.getFilesDir(), "save.json");
        if (file.exists()){
            try{
                InputStream in = ct.openFileInput("save.json");
                reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder builder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) builder.append(line);
                JSONArray array = (JSONArray) new JSONTokener(builder.toString()).nextValue();
                for (int i = 0; i < array.length(); i++){
                    noteList.add(createNote(array.getJSONObject(i)));
                }
            }finally {
                if (reader != null) reader.close();
            }
        }
        return noteList;
    }

}
