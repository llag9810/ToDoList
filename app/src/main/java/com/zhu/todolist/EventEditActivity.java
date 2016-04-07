package com.zhu.todolist;
import com.zhu.todolist.MainActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;




public class EventEditActivity extends AppCompatActivity {

    int priority;
    int isFinished;
    String[] detail;
    DbHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

/*        Intent intent = getIntent();
        detail = intent.getStringArrayExtra("detail");*/

        helper = new DbHelper(this);

        final EditText title = (EditText)findViewById(R.id.titleEdit);
        final EditText content = (EditText)findViewById(R.id.contentEdit);
        final EditText yearEdit = (EditText) findViewById(R.id.edit_year);
        final EditText monthEdit = (EditText) findViewById(R.id.edit_month);
        final EditText dayEdit = (EditText) findViewById(R.id.edit_day);
        final Button button = (Button) findViewById(R.id.edit_setTime_Button);
        final Spinner spinner1 = (Spinner)findViewById(R.id.spinner1);
        final Spinner spinner2 = (Spinner)findViewById(R.id.spinner2);

        if(detail != null) {
            String createYear = detail[4].substring(0, detail[3].indexOf("-"));
            String createMon = detail[4].substring(detail[3].indexOf("-") + 1, detail[3].lastIndexOf("-"));
            String createDay = detail[4].substring(detail[3].lastIndexOf("-") + 1, detail[3].length());
            title.setText(detail[1]);
            content.setText(detail[2]);
            yearEdit.setText(createYear);
            monthEdit.setText(createMon);
            dayEdit.setText(createDay);
            spinner1.setSelection(Integer.parseInt(detail[5]) - 1);
        }

        final DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                yearEdit.setText("" + year);
                monthEdit.setText("" + (monthOfYear + 1));
                dayEdit.setText("" + dayOfMonth);
            }
        };

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(EventEditActivity.this, listener,
                        Calendar.getInstance().get(Calendar.YEAR),
                        Calendar.getInstance().get(Calendar.MONTH),
                        Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                priority = position + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                isFinished = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = DbHelper.getInstance(EventEditActivity.this);
                DbHelper helper = new DbHelper(EventEditActivity.this);
                java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
                int year = Integer.parseInt(yearEdit.getText().toString());
                int month = Integer.parseInt(monthEdit.getText().toString()) - 1;
                int day = Integer.parseInt(dayEdit.getText().toString());

                Calendar cal = Calendar.getInstance();
                cal.set(year, month, day);

/*                db.execSQL("INSERT INTO eventData VALUES (NULL, ?, ?, ?, ?, ?, ?)",
                        new Object[]{
                                title.getText().toString(),
                                content.getText().toString(),
                                format.format(Calendar.getInstance().getTime()),
                                format.format(cal.getTime()),
                                priority,
                                isFinished
                        });*/
                helper.addData(
                        title.getText().toString(),
                        content.getText().toString(),
                        format.format(Calendar.getInstance().getTime()),
                        format.format(cal.getTime()),
                        priority,
                        isFinished
                );
                onBackPressed();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
