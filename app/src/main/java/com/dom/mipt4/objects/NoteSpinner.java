package com.dom.mipt4.objects;

import androidx.annotation.NonNull;
import org.jetbrains.annotations.NotNull;

public class NoteSpinner {

    public int id;
    public String name;

    public NoteSpinner(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @NonNull
    @NotNull
    @Override
    public String toString() {
        return name;
    }
}
