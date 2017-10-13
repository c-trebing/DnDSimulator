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

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CreateTaskActivity extends AppCompatActivity
    implements DatePickerFragment.DatePickedListener,
               TimePickerFragment.TimePickedListener {
    // UI references.
    private EditText nameInput;
    private EditText dateInput;
    private EditText timeInput;
    private Calendar aggregateCalendar;

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

        aggregateCalendar = Calendar.getInstance();

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
    public void returnDate (Calendar calendar) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, MMM d");
        String formattedDate = sdf.format(calendar.getTime());

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        aggregateCalendar.set(Calendar.YEAR, year);
        aggregateCalendar.set(Calendar.MONTH, month);
        aggregateCalendar.set(Calendar.DAY_OF_MONTH, day);

        dateInput.setText(formattedDate);
    }

    @Override
    public void returnTime (Calendar calendar) {
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        aggregateCalendar.set(Calendar.HOUR_OF_DAY, hour);
        aggregateCalendar.set(Calendar.MINUTE, minute);

        timeInput.setText(String.format("%02d:%02d", hour, minute));
    }

    public void submitCreateTask (View view) {
        String name = nameInput.getText().toString();
        String date = dateInput.getText().toString();
        String time = timeInput.getText().toString();

        if (validateInput(name, date, time)) {
            Task task = new Task(name, aggregateCalendar);
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("CREATED_TASK", task);
            setResult(200, intent);
            finish();
        }
    }
}
