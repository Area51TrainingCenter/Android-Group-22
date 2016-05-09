package pe.area51.notepad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class SQLiteManager extends SQLiteOpenHelper {

    private static SQLiteManager instance;

    private final static String DATABASE_NAME = "notepad";
    private final static int DATABASE_VERSION = 1;

    public static SQLiteManager getInstance(final Context context) {
        if (instance == null) {
            instance = new SQLiteManager(context.getApplicationContext());
        }
        return instance;
    }

    private SQLiteManager(final Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String creationScript =
                "CREATE TABLE notes(_id INTEGER PRIMARY KEY, title TEXT, content TEXT, creationTimestamp INTEGER, modificationTimestamp INTEGER);";
        db.execSQL(creationScript);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public ArrayList<Note> getAllNotes() {
        final Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM notes", null);
        final ArrayList<Note> notes = new ArrayList<>();
        while (cursor.moveToNext()) {
            final long id = cursor.getLong(cursor.getColumnIndex("_id"));
            final String title = cursor.getString(cursor.getColumnIndex("title"));
            final String content = cursor.getString(cursor.getColumnIndex("content"));
            final long creationTimestamp = cursor.getLong(cursor.getColumnIndex("creationTimestamp"));
            final long modificationTimestamp = cursor.getLong(cursor.getColumnIndex("modificationTimestamp"));
            final Note note = new Note(id, title, content, creationTimestamp, modificationTimestamp);
            notes.add(note);
        }
        cursor.close();
        return notes;
    }

    public long insertNote(final Note note) {
        final ContentValues contentValues = new ContentValues();
        contentValues.put("title", note.getTitle());
        contentValues.put("content", note.getContent());
        contentValues.put("creationTimestamp", note.getCreationTimestamp());
        contentValues.put("modificationTimestamp", note.getModificationTimestamp());
        return getWritableDatabase().insert("notes", null, contentValues);
    }

}
