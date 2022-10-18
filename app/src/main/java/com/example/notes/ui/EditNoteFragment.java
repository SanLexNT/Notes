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
import com.example.notes.domain.Note;
import com.example.notes.domain.RouterHolder;

public class EditNoteFragment extends Fragment {

    private static final String KEY = "KEY_NOTE";
    private EditText editTextTitle, editTextDetails;
    private DBNoteRepository repository;

    public static final String TAG = "EditNoteFragment";

    public static EditNoteFragment newInstance(Note note) {

        EditNoteFragment fragment = new EditNoteFragment();

        Bundle args = new Bundle();
        args.putParcelable(KEY, note);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        repository = new DBNoteRepository(requireContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note_edit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editTextTitle = view.findViewById(R.id.Title);
        editTextDetails = view.findViewById(R.id.Details);
        Toolbar toolbar = view.findViewById(R.id.toolbar);

        Note note = getArguments().getParcelable(KEY);

        editTextTitle.setText(note.getTitle());
        editTextDetails.setText(note.getDetail());

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if (item.getItemId() == R.id.item_done) {

                    String title = editTextTitle.getText().toString();
                    String details = editTextDetails.getText().toString();

                    if (isFilled(title, details)) {

                        repository.editNote(note, title, details);

                        if (getActivity() instanceof RouterHolder) {

                            MainRouter router = ((RouterHolder) getActivity()).getMainRouter();

                            router.goBack();

                            return true;
                        }

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
