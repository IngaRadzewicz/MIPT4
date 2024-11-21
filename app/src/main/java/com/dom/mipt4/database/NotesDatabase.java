package com.dom.mipt4.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.dom.mipt4.objects.Note;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Note.class}, version = 1, exportSchema = false)
public abstract class NotesDatabase extends RoomDatabase {
    public abstract NoteDao noteDao();

    private static volatile NotesDatabase INSTANCE;
    public static final ExecutorService databaseActionsExecutor = Executors.newFixedThreadPool(2);

    public static NotesDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (NotesDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), NotesDatabase.class, "notes_db").build();
                }
            }
        }
        return INSTANCE;
    }

}
