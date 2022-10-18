package com.example.notes.ui;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.notes.R;
import com.example.notes.domain.Note;

public class MainRouter {

    private final FragmentManager fragmentManager;

    public MainRouter(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public void showInfo() {
        fragmentManager
                .beginTransaction()
                .replace(R.id.container, InfoFragment.newInstance())
                .commit();
    }

    public void showNoteList() {
        fragmentManager
                .beginTransaction()
                .replace(R.id.container, NoteListFragment.newInstance())
                .commit();
    }

    public void showAddNote() {
        fragmentManager
                .beginTransaction()
                .replace(R.id.container, AddNoteFragment.newInstance(), AddNoteFragment.TAG)
                .addToBackStack(AddNoteFragment.TAG)
                .commit();
    }

    public void showEditNote(Note note) {
        fragmentManager
                .beginTransaction()
                .replace(R.id.container, EditNoteFragment.newInstance(note), EditNoteFragment.TAG)
                .addToBackStack(EditNoteFragment.TAG)
                .commit();
    }

    public String getFragmentTag() {
        Fragment fragment = fragmentManager.findFragmentById(R.id.container);

        if (fragment != null) {
            return fragment.getTag();
        }
        return null;
    }

    public void goBack() {
        fragmentManager.popBackStack();
    }

    public void showFragment(String tag) {

        switch (tag) {
            case NoteListFragment.TAG:
                showNoteList();
                break;
            case InfoFragment.TAG:
                showInfo();
                break;
            case AddNoteFragment.TAG:
                showAddNote();
                break;

        }

    }

}
