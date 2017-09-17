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
        EditText editText = (EditText) findViewById(R.id.create_task_name_input);
        String name = editText.getText().toString();

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("CREATE_TASK_NAME", name);
        setResult(200, intent);
        finish();
    }
}
