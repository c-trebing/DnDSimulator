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

import java.util.Calendar;

public class CreateTaskActivity extends AppCompatActivity
    implements DatePickerFragment.DatePickedListener,
               TimePickerFragment.TimePickedListener {
    // UI references.
    private EditText nameInput;
    private EditText dateInput;
    private EditText timeInput;
    private Task newTask;

    private boolean validateInput (String name, String date, String time) {
        if (TextUtils.isEmpty(name)) {
            nameInput.setError(getString(R.string.error_field_required));
            nameInput.requestFocus();
        }
        else if (TextUtils.isEmpty(date)) {
            dateInput.setError(getString(R.string.error_field_required));
            dateInput.requestFocus();
        }
        else if (TextUtils.isEmpty(time)) {
            timeInput.setError(getString(R.string.error_field_required));
            timeInput.requestFocus();
        }
        else { return true; }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        newTask = new Task("temp name", "temp date");

        nameInput = (EditText) findViewById(R.id.create_task_name_input);
        dateInput = (EditText) findViewById(R.id.create_task_date_input);
        timeInput = (EditText) findViewById(R.id.create_task_time_input);

        dateInput.setOnClickListener (new View.OnClickListener() {
          @Override
          public void onClick (View arg0) {
            DialogFragment picker = new DatePickerFragment();
            picker.show(getFragmentManager(), "datePicker");
          }
        });

        timeInput.setOnClickListener (new View.OnClickListener() {
          @Override
          public void onClick (View arg0) {
            DialogFragment picker = new TimePickerFragment();
            picker.show(getFragmentManager(), "timePicker");
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

    @Override
    public void returnTime (Calendar calendar) {
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        newTask.calendar.set(Calendar.HOUR_OF_DAY, hour);
        newTask.calendar.set(Calendar.MINUTE, minute);

        timeInput.setText(String.format("%02d:%02d", hour, minute));
    }

    public void submitCreateTask (View view) {
        String name = nameInput.getText().toString();
        String date = dateInput.getText().toString();
        String time = timeInput.getText().toString();

        if (validateInput(name, date, time)) {
            Task task = new Task(name, date);
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("CREATED_TASK", task);
            setResult(200, intent);
            finish();
        }
    }
}
