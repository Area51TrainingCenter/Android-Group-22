package pe.area51.notepad;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

public class NotepadContentProvider extends ContentProvider {

    private SQLiteManager sqLiteManager;

    private final static int NOTE_WITH_ID = 1;
    private final static int NOTE = 2;

    private final static UriMatcher URI_MATCHER;

    static {
        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        URI_MATCHER.addURI(NoteContract.URI.getAuthority(), NoteContract.URI.getPath(), NOTE);
        URI_MATCHER.addURI(NoteContract.URI.getAuthority(), NoteContract.URI.getPath() + "/#", NOTE_WITH_ID);
    }

    @Override
    public boolean onCreate() {
        sqLiteManager = SQLiteManager.getInstance(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        final int matchCode = URI_MATCHER.match(uri);
        if (matchCode != UriMatcher.NO_MATCH) {
            if (matchCode == NOTE_WITH_ID) {
                selection = "_id=" + uri.getLastPathSegment();
            }
            return sqLiteManager.getReadableDatabase()
                    .query("notes", projection, selection, selectionArgs, null, null, sortOrder);
        }
        return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (URI_MATCHER.match(uri)) {
            case NOTE:
                return NoteContract.MIME_DIR;
            case NOTE_WITH_ID:
                return NoteContract.MIME_ITEM;
            default:
            case UriMatcher.NO_MATCH:
                return null;
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final int matchCode = URI_MATCHER.match(uri);
        if (matchCode == NOTE) {
            final long id = sqLiteManager.getWritableDatabase().insert("notes", null, values);
            return id != -1 ? ContentUris.withAppendedId(uri, id) : null;
        }
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final int matchCode = URI_MATCHER.match(uri);
        if (matchCode != UriMatcher.NO_MATCH) {
            if (matchCode == NOTE_WITH_ID) {
                selection = "_id=" + uri.getLastPathSegment();
            }
            return sqLiteManager.getWritableDatabase()
                    .delete("notes", selection, selectionArgs);
        }
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final int matchCode = URI_MATCHER.match(uri);
        if (matchCode != UriMatcher.NO_MATCH) {
            if (matchCode == NOTE_WITH_ID) {
                selection = "_id=" + uri.getLastPathSegment();
            }
            return sqLiteManager.getWritableDatabase()
                    .update("notes", values, selection, selectionArgs);
        }
        return 0;
    }
}
