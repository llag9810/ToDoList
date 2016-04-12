package com.zhu.todolist;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends ListFragment {

    private SimpleCursorAdapter adapter;
    private Cursor c;
    private SQLiteDatabase db;

    public BlankFragment() {
        super();
    }

    public static BlankFragment newInstance(int position) {
        BlankFragment fragment = new BlankFragment();

        Bundle args = new Bundle();
        args.putInt("position", position);
        fragment.setArguments(args);

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blank, container, false);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int position = getArguments().getInt("position");
        db = DbHelper.getInstance(getActivity());

        adapter = new android.widget.SimpleCursorAdapter(getActivity(),
                R.layout.item,
                c,
                new String[] {"title", "createDate", "deadline", "priority", "isFinished"},
                new int[] {R.id.titleText, R.id.dateText, R.id.deadlineText, R.id.priorityText,
                        R.id.isFinishedText},
                0);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent intent = new Intent(getActivity(),DetailActivity.class);
        c.moveToPosition(position);
        String[] detail = {
                c.getString(0),
                c.getString(1),
                c.getString(2),
                c.getString(3),
                c.getString(4),
                c.getString(5),
                c.getString(6)};
        intent.putExtra("detail", detail);
        startActivity(intent);
    }

    class RefreshListTask extends AsyncTask<Void, Void, Cursor> {
        @Override
        protected Cursor doInBackground(Void... params) {
            int position = getArguments().getInt("position");
            switch (position) {
                case 0: return db.rawQuery("SELECT * from eventData", null);
                case 1: return db.rawQuery("SELECT * from eventData WHERE isFinished=0", null);
                case 2: return db.rawQuery("SELECT * from eventData WHERE isFinished=1", null);
                default: return null;
            }
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            adapter.changeCursor(cursor);
            c = cursor;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        new RefreshListTask().execute();
    }
}
