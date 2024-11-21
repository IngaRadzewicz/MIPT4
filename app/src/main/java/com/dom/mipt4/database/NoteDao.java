package com.dom.mipt4.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.dom.mipt4.objects.Note;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;

import java.util.List;

@Dao
public interface NoteDao {

    @Query("SELECT * FROM note")
    Observable<List<Note>> getAllNotes();

    @Query("SELECT * FROM note WHERE note.id IS (:noteId)")
    Flowable<Note> loadById(int noteId);

    @Insert
    Completable insertAll(Note... notes);

    @Query("DELETE FROM note WHERE note.id IS (:noteId)")
    Completable delete(int noteId);

}
