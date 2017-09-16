package com.powerrangers.todo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class EditTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        Intent intent = getIntent();
        String message = intent.getStringExtra(MyExpandableListAdapter.EXTRA_MESSAGE);

        TextView textView = (TextView) findViewById(R.id.temp_text);
        textView.setTextSize(40);
        textView.setText(message);
    }
}
