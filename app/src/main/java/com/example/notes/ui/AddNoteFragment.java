package com.example.notes.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.notes.R;
import com.example.notes.domain.DBNoteRepository;
import com.example.notes.domain.RouterHolder;

public class AddNoteFragment extends Fragment {

    public static final String TAG = "NoteFragment";

    private DBNoteRepository repository;
    private EditText title, details;

    public static AddNoteFragment newInstance() {

        return new AddNoteFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        repository = new DBNoteRepository(requireContext());

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_note_add, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        title = view.findViewById(R.id.noteTitle);
        details = view.findViewById(R.id.noteDetails);
        Toolbar toolbar = view.findViewById(R.id.toolbar);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                String titleNote = title.getText().toString();
                String detailsNote = details.getText().toString();

                if (isFilled(titleNote, detailsNote)) {

                    repository.addNote(titleNote, detailsNote);

                    if (getActivity() instanceof RouterHolder) {
                        MainRouter router = ((RouterHolder) getActivity()).getMainRouter();

                        router.goBack();
                        return true;
                    }
                }

                return false;
            }
        });
    }

    private boolean isFilled(String titleNote, String detailsNote) {
        return !titleNote.isEmpty() && !detailsNote.isEmpty();
    }

}
