package com.haanhgs.mynote;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.fbrMain)
    FloatingActionButton fbrMain;
    @BindView(R.id.rvMainList)
    RecyclerView rvMainList;

    private List<Note> noteList;
    private NoteAdapter adapter;

    //////////////////
    //public methods//
    //////////////////

    public void editNote(Note note) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentDetail fragment = (FragmentDetail) manager.findFragmentByTag("edit");
        if (fragment == null) {
            FragmentDetail noteDetail = new FragmentDetail();
            noteDetail.setNote(note);
            noteDetail.show(manager, "edit");
        }
    }

    public void addNote(Note note) {
        noteList.add(0, note);
        adapter.notifyDataSetChanged();
    }

    public void notifyChange() {
        adapter.notifyDataSetChanged();
    }

    private void createNote(){
        FragmentManager manager = getSupportFragmentManager();
        FragmentDetail fragment = (FragmentDetail) manager.findFragmentByTag("new");
        if (fragment == null) {
            FragmentDetail noteDetail = new FragmentDetail();
            noteDetail.show(manager, "new");
        }
    }

    @OnClick(R.id.fbrMain)
    public void onViewClicked() {
        createNote();
    }

    ///////////////////
    //private methods//
    //////////////////

    private void loadJson(){
        try{
            noteList = Repo.loadJson(this);
        }catch (Exception e){
            noteList = new ArrayList<>();
        }
    }

    private void initRecyclerView(){
        loadJson();
        adapter = new NoteAdapter(noteList);
        rvMainList.setLayoutManager(new LinearLayoutManager(this));
        rvMainList.setAdapter(adapter);
    }

    private void moveItem(RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target){
        int from = viewHolder.getAdapterPosition();
        int to = target.getAdapterPosition();
        Collections.swap(noteList, from, to);
        adapter.notifyItemMoved(from, to);
    }

    private void deleteItem(RecyclerView.ViewHolder viewHolder){
        noteList.remove(viewHolder.getAdapterPosition());
        adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
    }

    private void enableSwipe(){
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP|ItemTouchHelper.DOWN|ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT,
                ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT){
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                moveItem(viewHolder, target);
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                deleteItem(viewHolder);
            }
        });
        helper.attachToRecyclerView(rvMainList);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initRecyclerView();
        enableSwipe();
    }

    private void saveJson(){
        try{
            Repo.saveJson(noteList, this);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveJson();
    }


    /////////////////
    //menu methods  //
    /////////////////

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void openSettings(){
        startActivity(new Intent(this, SettingsActivity.class));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mniSettings){
            openSettings();
        }
        return super.onOptionsItemSelected(item);
    }
}
