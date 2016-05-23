package pe.area51.notepad;

import android.net.Uri;

/**
 * Created by Alumno on 09/05/2016.
 */
public class NoteContract {

    public static final Uri URI = Uri.parse("content://pe.area51.notepad.Content/note");

    public static final String ID = "_id";
    public static final String TITLE = "title";
    public static final String CONTENT = "content";
    public static final String CREATION_TIMESTAMP = "creationTimestamp";
    public static final String MODIFICATION_TIMESTAMP = "modificationTimestamp";

    public static final String MIME_ITEM = "vnd.android.cursor.item/pe.area51.notepad.Note";
    public static final String MIME_DIR = "vnd.android.cursor.dir/pe.area51.notepad.Note";

}
