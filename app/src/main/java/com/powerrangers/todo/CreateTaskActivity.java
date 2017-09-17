package com.powerrangers.todo;

import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class CreateTaskActivity extends AppCompatActivity
    implements DatePickerFragment.TheListener {
    // UI references.
    private EditText nameInput;
    private EditText dateInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        nameInput = (EditText) findViewById(R.id.create_task_name_input);
        dateInput = (EditText) findViewById(R.id.create_task_date_input);

        dateInput.setOnClickListener (new View.OnClickListener() {
          @Override
          public void onClick (View arg0) {
            DialogFragment picker = new DatePickerFragment();
            picker.show(getFragmentManager(), "datePicker");
          }
        });
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void returnDate (String date) {
        dateInput.setText(date);
    }

    public void submitCreateTask (View view) {
        String name = nameInput.getText().toString();
        String date = dateInput.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // validation
        if (TextUtils.isEmpty(name)) {
            nameInput.setError(getString(R.string.error_field_required));
            focusView = nameInput;
            cancel = true;
        }
        else if (TextUtils.isEmpty(date)) {
            dateInput.setError(getString(R.string.error_field_required));
            focusView = dateInput;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("CREATE_TASK_NAME", name);
            intent.putExtra("CREATE_TASK_DATE", date);
            setResult(200, intent);
            finish();
        }
    }
}
