package com.example.notes.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.notes.R;
import com.example.notes.domain.RouterHolder;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity implements RouterHolder {

    private static final String FRAGMENT_KEY = "FRAGMENT_KEY";
    private MainRouter router;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        router = new MainRouter(getSupportFragmentManager());
        router.showNoteList();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_menu);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.item_note) {
                    router.showNoteList();
                }
                if (item.getItemId() == R.id.item_info) {
                    router.showInfo();
                }
                return true;
            }
        });
    }

    @Override
    public MainRouter getMainRouter() {
        return router;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        String tag = router.getFragmentTag();

        outState.putString(FRAGMENT_KEY, tag);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState.getString(FRAGMENT_KEY) != null) {

            String tag = savedInstanceState.getString(FRAGMENT_KEY);

            router.showFragment(tag);
        }
    }
}