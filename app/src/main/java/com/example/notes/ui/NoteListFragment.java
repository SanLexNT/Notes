package com.example.notes.ui;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.R;
import com.example.notes.domain.DBNoteRepository;
import com.example.notes.domain.Note;
import com.example.notes.domain.RouterHolder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class NoteListFragment extends Fragment {

    public static final String TAG = "NoteListFragment";

    private NoteAdapter adapter;
    private DBNoteRepository repository;
    private List<Note> noteList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MainRouter router;

    private int removeIndex = -1;

    public static NoteListFragment newInstance(){
        return new NoteListFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(getActivity() instanceof RouterHolder){
            router = ((RouterHolder) getActivity()).getMainRouter();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        repository = new DBNoteRepository(requireContext());

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.notes_list);
        FloatingActionButton buttonAdd = view.findViewById(R.id.buttonAdd);

        int orientation = getResources().getConfiguration().orientation;

        if(orientation == Configuration.ORIENTATION_PORTRAIT){

            setLayoutManager(1);
        } else {
            setLayoutManager(2);
        }

        getData();

        DefaultItemAnimator animator = new DefaultItemAnimator();
        animator.setRemoveDuration(500L);
        recyclerView.setItemAnimator(animator);

        recyclerView.setAdapter(adapter);

        adapter.setOnNoteClickListener(new NoteAdapter.OnNoteClickListener() {
            @Override
            public void onClick(int position) {

                Note note = noteList.get(position);

                router.showEditNote(note);
            }
        });
        adapter.setOnNoteLongClickListener(new NoteAdapter.OnNoteLongClickListener() {
            @Override
            public void onLongClick(int position) {
                removeIndex = position;
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                router.showAddNote();
            }
        });

    }

    private void setLayoutManager(int column){
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), column));
    }

    private void getData(){
        noteList = repository.getNotes();

        adapter = new NoteAdapter(this, noteList);
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        getActivity().getMenuInflater().inflate(R.menu.note_context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.item_remove){
            removeNote(removeIndex);
            return true;
        }

        return super.onContextItemSelected(item);
    }
    private void removeNote(int position){

        Note note = noteList.get(position);

        repository.removeNote(note);
        adapter.notifyItemRemoved(position);

        getData();
    }
}
