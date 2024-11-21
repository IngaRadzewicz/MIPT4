package com.dom.mipt4.elements;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.dom.mipt4.AddNoteActivity;
import com.dom.mipt4.R;
import com.dom.mipt4.objects.Note;

@SuppressLint("ViewConstructor")
public class NoteElement extends ConstraintLayout {

    public NoteElement(Context context, Note note) {
        super(context);
        inflate(context, R.layout.note, this);

        ImageButton btn = findViewById(R.id.showBtn);
        TextView text = findViewById(R.id.addNoteName);
        text.setText(note.getName());

        Intent intent = new Intent(context, AddNoteActivity.class);
        intent.putExtra("noteId", note.getId());

        btn.setOnClickListener((event) -> {
            Log.w("INFO", "CLICKED " + note.getText());
            context.startActivity(intent);
        });

    }

}
