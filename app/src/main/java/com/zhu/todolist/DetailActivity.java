package com.zhu.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    TextView detailTitle;
    TextView detailContent;
    TextView detailCreateDate;
    TextView detailDeadline;
    TextView detailPriority;
    TextView detailIsFinished;
    String[] detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        detail = intent.getStringArrayExtra("detail");

        detailTitle = (TextView)findViewById(R.id.detail_title);
        detailContent = (TextView)findViewById(R.id.detail_content);
        detailCreateDate = (TextView)findViewById(R.id.detail_createDate);
        detailDeadline = (TextView)findViewById(R.id.detail_deadline);
        detailPriority = (TextView)findViewById(R.id.detail_priority);
        detailIsFinished = (TextView)findViewById(R.id.detail_isFinished);
        detailTitle.setKeyListener(null);
        detailContent.setKeyListener(null);
        detailCreateDate.setKeyListener(null);
        detailDeadline.setKeyListener(null);
        detailPriority.setKeyListener(null);
        detailIsFinished.setKeyListener(null);

        detailTitle.setText(detail[1]);
        detailContent.setText(detail[2]);
        detailCreateDate.setText(detail[3]);
        detailDeadline.setText(detail[4]);
        detailPriority.setText(detail[5]);
        detailIsFinished.setText(detail[6]);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailActivity.this, EventUpdateActivity.class);
                intent.putExtra("detail", detail);
                startActivity(intent);
            }
        });
    }
}
