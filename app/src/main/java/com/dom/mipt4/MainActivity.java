package com.dom.mipt4;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.dom.mipt4.database.NoteDao;
import com.dom.mipt4.database.NotesDatabase;
import com.dom.mipt4.elements.NoteElement;
import com.dom.mipt4.objects.Note;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadNotes();
        loadMenu();
    }

    private void loadMenu() {

        NavigationBarView menu = findViewById(R.id.bottomMenu);
        menu.setOnItemSelectedListener((event) -> {

            Intent intent;
            if (event.getItemId() == R.id.addBtn) intent = new Intent(this, AddNoteActivity.class);
            else intent = new Intent(this, DeleteNoteActivity.class);

            startActivity(intent);
            return true;
        });
    }

    public void loadNotes() {
        NoteDao dao = NotesDatabase.getDatabase(this).noteDao();

        try {
            List<Note> notes = new ArrayList<>(dao.getAllNotes().blockingFirst());
            writeNotesUi(notes);
        } catch (NullPointerException | AssertionError | NoSuchElementException ex) {
            Log.d("NOTES", "EMPTY");
            writeNotesUi(null);
        }
    }

    private void writeNotesUi(List<Note> notes) {
        if (notes == null || notes.isEmpty()) {
            findViewById(R.id.messageBox).setVisibility(View.VISIBLE);
            findViewById(R.id.notesListContainer).setVisibility(View.INVISIBLE);
            return;
        }
        LinearLayout noteListElem = findViewById(R.id.notes);
        for (Note note: notes) {
            NoteElement elem = new NoteElement(this, note);
            noteListElem.addView(elem);

        }
    }


}