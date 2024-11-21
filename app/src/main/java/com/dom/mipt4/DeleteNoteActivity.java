package com.dom.mipt4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.dom.mipt4.database.NoteDao;
import com.dom.mipt4.database.NotesDatabase;
import com.dom.mipt4.objects.Note;
import com.dom.mipt4.objects.NoteSpinner;

import java.util.List;
import java.util.stream.Collectors;

public class DeleteNoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_note);

        ImageButton backBtn = findViewById(R.id.closeBtn);
        backBtn.setOnClickListener((event) -> backToMainActivity());

        Button deleteBtn = findViewById(R.id.deleteBtn);

        Spinner notesListElem = findViewById(R.id.notesListToDelete);
        setupSpinnerEvents(notesListElem, deleteBtn);
        setupSpinnerAdapter(notesListElem);

        deleteBtn.setOnClickListener((event) -> deleteBtnAction(notesListElem));
    }

    private void deleteBtnAction(Spinner spinner) {
        NoteSpinner selected = (NoteSpinner) spinner.getSelectedItem();

        NoteDao dao = NotesDatabase.getDatabase(this).noteDao();
        NotesDatabase.databaseActionsExecutor.execute(() -> {
            dao.delete(selected.id).blockingAwait();
        });

        Toast.makeText(this, getString(R.string.valid_title), Toast.LENGTH_SHORT).show();
        backToMainActivity();
    }

    private void setupSpinnerAdapter(Spinner spinner) {
        NoteDao dao = NotesDatabase.getDatabase(this).noteDao();
        List<Note> notes = dao.getAllNotes().blockingFirst();

        //Kam spinnerio elemente saugoti ir tekstus? Uztenka ID ir Pavadinimo.
        List<NoteSpinner> notePairs = notes.stream()
                .map(note -> new NoteSpinner(note.getId(), note.getName()))
                .collect(Collectors.toList());

        ArrayAdapter<NoteSpinner> adapter = new ArrayAdapter<>(this,
                androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item,
                notePairs
        );

        spinner.setAdapter(adapter);
    }

    private void setupSpinnerEvents(Spinner spinner, Button btn) {
        btn.setEnabled(false);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                btn.setEnabled(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                btn.setEnabled(false);
            }
        });
    }

    private void backToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}