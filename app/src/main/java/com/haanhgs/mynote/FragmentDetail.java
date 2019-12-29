package com.haanhgs.mynote;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentDetail extends DialogFragment {

    @BindView(R.id.etTitle)
    EditText etTitle;
    @BindView(R.id.etDetail)
    EditText etDetail;
    @BindView(R.id.cbImportant)
    CheckBox cbImportant;
    @BindView(R.id.cbTodo)
    CheckBox cbTodo;
    @BindView(R.id.cbIdea)
    CheckBox cbIdea;
    @BindView(R.id.bnSave)
    Button bnSave;
    @BindView(R.id.bnCancel)
    Button bnCancel;

    private Note note;
    private boolean isNewNote = true;
    private Context context;

    public void setNote(Note note) {
        this.note = note;
    }

    private void updateViews(){
        if (note == null){
            note = new Note();
        }else {
            isNewNote = false;
            etTitle.setText(note.getTitle());
            etDetail.setText(note.getDetail());
            cbTodo.setChecked(note.isTodo());
            cbIdea.setChecked(note.isIdea());
            cbImportant.setChecked(note.isImportant());
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, view);
        updateViews();
        return view;
    }

    private void updateNote(){
        note.setTitle(etTitle.getText().toString());
        note.setDetail(etDetail.getText().toString());
        note.setIdea(cbIdea.isChecked());
        note.setImportant(cbImportant.isChecked());
        note.setTodo(cbTodo.isChecked());
    }

    private void handleButtonSave(View view){
        updateNote();
        if (isNewNote){
            ((MainActivity)context).addNote(note);
        }else {
            ((MainActivity)context).notifyChange();
        }
        dismiss();
    }

    @OnClick({R.id.bnSave, R.id.bnCancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bnSave:
                handleButtonSave(view);
                break;
            case R.id.bnCancel:
                dismiss();
                break;
        }
    }
}
