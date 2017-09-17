package com.powerrangers.todo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class CreateTaskActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
    }

    public void submitCreateTask (View view) {
        EditText nameInput = (EditText) findViewById(R.id.create_task_name_input);
        EditText dayInput = (EditText) findViewById(R.id.create_task_day_input);
        String name = nameInput.getText().toString();
        String day = dayInput.getText().toString();

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("CREATE_TASK_NAME", name);
        intent.putExtra("CREATE_TASK_DAY", day);
        setResult(200, intent);
        finish();
    }
}
