package com.haanhgs.mynote;

import org.json.JSONException;
import org.json.JSONObject;

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


}
