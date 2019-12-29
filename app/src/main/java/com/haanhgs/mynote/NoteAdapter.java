package com.haanhgs.mynote;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    private final List<Note> noteList;

    public NoteAdapter(List<Note> noteList) {
        this.noteList = noteList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recycler_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindViews(noteList.get(position));
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cbRvImportant)
        CheckBox cbRvImportant;
        @BindView(R.id.cbRvTodo)
        CheckBox cbRvTodo;
        @BindView(R.id.cbRvIdea)
        CheckBox cbRvIdea;
        @BindView(R.id.tvRvTitle)
        TextView tvRvTitle;
        @BindView(R.id.tvRvDetail)
        TextView tvRvDetail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(view -> {
                Note note = noteList.get(ViewHolder.this.getAdapterPosition());
                ((MainActivity)itemView.getContext()).editNote(note);
            });
        }

        private void bindViews(Note note) {
            tvRvTitle.setText(note.getTitle());
            tvRvDetail.setText(note.getDetail());
            cbRvImportant.setChecked(note.isImportant());
            cbRvTodo.setChecked(note.isTodo());
            cbRvIdea.setChecked(note.isIdea());
        }
    }
}
