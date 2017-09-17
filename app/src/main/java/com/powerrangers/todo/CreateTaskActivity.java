package com.powerrangers.todo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

public class CreateTaskActivity extends AppCompatActivity {
    // UI references.
    private EditText nameInput;
    private EditText dayInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        nameInput = (EditText) findViewById(R.id.create_task_name_input);
        dayInput = (EditText) findViewById(R.id.create_task_day_input);
    }

    public void submitCreateTask (View view) {
        String name = nameInput.getText().toString();
        String day = dayInput.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // validation
        if (TextUtils.isEmpty(name)) {
            nameInput.setError(getString(R.string.error_field_required));
            focusView = nameInput;
            cancel = true;
        }
        else if (TextUtils.isEmpty(day)) {
            dayInput.setError(getString(R.string.error_field_required));
            focusView = dayInput;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("CREATE_TASK_NAME", name);
            intent.putExtra("CREATE_TASK_DAY", day);
            setResult(200, intent);
            finish();
        }
    }
}
