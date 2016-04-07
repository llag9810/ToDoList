package com.zhu.todolist;

import android.app.TabActivity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TabHost;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static com.zhu.todolist.DbHelper.getInstance;

public class MainActivity extends AppCompatActivity {

    public ArrayList<HashMap<String, Object>> listData;
    ListView listView1;
    private DbHelper helper = null;
    private SQLiteDatabase db = null;
    private SimpleCursorAdapter adapter = null;
    Cursor cursor = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

/*
        for(int i = 1; i < 30; i++) {

            HashMap<String, Object> test = new HashMap<>();
            test.put("title", "Test" + i);
            test.put("deadline", "YYYY-MM-DD");
            listData.add(test);
        }
*/

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EventEditActivity.class);
                startActivity(intent);
            }
        });

        db = getInstance(this);
        cursor = db.rawQuery("SELECT * from eventData", null);

        listView1 = (ListView)findViewById(R.id.listView1);
        adapter = new SimpleCursorAdapter(MainActivity.this,
                R.layout.item,
                cursor,
                new String[] {"title", "createDate", "deadline", "priority", "isFinished"},
                new int[] {R.id.titleText, R.id.dateText, R.id.deadlineText, R.id.priorityText,
                R.id.isFinishedText},
                0);
        listView1.setAdapter(adapter);

        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                cursor.moveToPosition(position);
                String[] detail = {
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6)};
               intent.putExtra("detail", detail);
                startActivity(intent);
            }
        });

/*        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });*/

    }

    class RefreshListTask extends AsyncTask<Void, Void, Cursor> {
        @Override
        protected Cursor doInBackground(Void... params) {
            return db.rawQuery("SELECT * from eventData", null);
        }

        @Override
        protected void onPostExecute(Cursor c) {
            super.onPostExecute(c);
            adapter.changeCursor(c);
            cursor.close();
            cursor = c;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        new RefreshListTask().execute();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cursor.close();
        db.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_sort_by_deadline) {
            Cursor c = db.rawQuery("SELECT * from eventData order by deadline", null);
            adapter.changeCursor(c);
            cursor.close();
            cursor = c;
/*            Collections.sort(listData, new Comparator<HashMap<String, Object>>() {
                @Override
                public int compare(HashMap<String, Object> lhs, HashMap<String, Object> rhs) {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        return format.parse((String)lhs.get("deadline")).
                                compareTo(format.parse((String) rhs.get("deadline")));
                    } catch (ParseException e) {
                        e.printStackTrace();
                        return 0;
                    }
                }
            });*/
            SimpleCursorAdapter adapter = (SimpleCursorAdapter)listView1.getAdapter();
            adapter.notifyDataSetChanged();
            return true;
        } else if (id == R.id.action_sort_by_priority) {
            Cursor c = db.rawQuery("SELECT * from eventData order by priority", null);
            adapter.changeCursor(c);
            cursor.close();
            cursor = c;
/*            Collections.sort(listData, new Comparator<HashMap<String, Object>>() {
                @Override
                public int compare(HashMap<String, Object> lhs, HashMap<String, Object> rhs) {
                    Integer a = Integer.parseInt((String)lhs.get("priority"));
                    Integer b = Integer.parseInt((String)rhs.get("priority"));
                    return b.compareTo(a);
                }
            });*/
            SimpleCursorAdapter adapter = (SimpleCursorAdapter)listView1.getAdapter();
            adapter.notifyDataSetChanged();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}