package pe.area51.notepad;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {

    private ListView listView;

    private ListFragmentInterface listFragmentInterface;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_list, container, false);
        listView = (ListView) view.findViewById(R.id.listview_notes);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        listView.setAdapter(new NotesAdapter(getActivity(), getAllNotes()));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listFragmentInterface != null) {
                    final Note note = ((NotesAdapter) listView.getAdapter()).getItem(position);
                    listFragmentInterface.onNoteSelected(note);
                }
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_note:
                addNewNote();
                return true;
            default:
                return false;
        }
    }

    private ArrayList<Note> getAllNotes() {
        final Cursor cursor = getActivity().getContentResolver().query(NoteContract.URI, null, null, null, null);
        final ArrayList<Note> notes = new ArrayList<>();
        while (cursor.moveToNext()) {
            final long id = cursor.getLong(cursor.getColumnIndex(NoteContract.ID));
            final String title = cursor.getString(cursor.getColumnIndex(NoteContract.TITLE));
            final String content = cursor.getString(cursor.getColumnIndex(NoteContract.CONTENT));
            final long creationTimestamp = cursor.getLong(cursor.getColumnIndex(NoteContract.CREATION_TIMESTAMP));
            final long modificationTimestamp = cursor.getLong(cursor.getColumnIndex(NoteContract.MODIFICATION_TIMESTAMP));
            final Note note = new Note(id, title, content, creationTimestamp, modificationTimestamp);
            notes.add(note);
        }
        cursor.close();
        return notes;
    }

    private void addNewNote() {
        final String title = "Test Title";
        final String content = "Test Content";
        final long creationTimestamp = System.currentTimeMillis();
        final long modificationTimestamp = System.currentTimeMillis();
        final ContentValues contentValues = new ContentValues();
        contentValues.put(NoteContract.TITLE, title);
        contentValues.put(NoteContract.CONTENT, content);
        contentValues.put(NoteContract.CREATION_TIMESTAMP, creationTimestamp);
        contentValues.put(NoteContract.MODIFICATION_TIMESTAMP, modificationTimestamp);
        final Uri newNoteUri = getActivity()
                .getContentResolver().insert(NoteContract.URI, contentValues);
        final long id = ContentUris.parseId(newNoteUri);
        ((NotesAdapter) listView.getAdapter())
                .add(new Note(id, title, content, creationTimestamp, modificationTimestamp));
    }

    public void setListFragmentInterface(ListFragmentInterface listFragmentInterface) {
        this.listFragmentInterface = listFragmentInterface;
    }

    private static class NotesAdapter extends ArrayAdapter<Note> {

        public NotesAdapter(final Context context, final List<Note> notes) {
            super(context, 0, notes);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            /*
            Esta forma de generar elementos de lista no es eficiente, ya que no estamos reutilizando los elementos
	        de lista que salen de nuestro campo de visión ("convertView"), además que estamos ejecutando continuamente
	        el método "findViewById". Deberíamos utilizar por ejemplo el patrón ViewHolder para mejorar el rendimiento:
	        "http://developer.android.com/intl/es/training/improving-layouts/smooth-scrolling.html".
	        */
            final Note note = getItem(position);
            final LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            final View listElement = layoutInflater.inflate(R.layout.element_note, parent, false);
            ((TextView) listElement.findViewById(R.id.textview_title)).setText(note.getTitle());
            ((TextView) listElement.findViewById(R.id.textview_content)).setText(note.getContent());
            return listElement;
        }
    }

    public interface ListFragmentInterface {

        public void onNoteSelected(final Note note);

    }

}