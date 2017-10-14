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

public class EditTaskActivity extends AppCompatActivity
    implements DatePickerFragment.DatePickedListener,
               TimePickerFragment.TimePickedListener {

    private Task oldTask;
    private Task newTask;

    // UI references.
    protected EditText nameInput;
    protected EditText dateInput;
    protected EditText timeInput;

    protected boolean validateInput (String name, String date, String time) {
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
        setContentView(R.layout.activity_edit_task);

        Intent intent = getIntent();
        oldTask = (Task) intent.getSerializableExtra("EDITED_TASK");
        newTask = new Task(oldTask.name, oldTask.calendar);

        nameInput = (EditText) findViewById(R.id.edit_task_name_input);
        dateInput = (EditText) findViewById(R.id.edit_task_date_input);
        timeInput = (EditText) findViewById(R.id.edit_task_time_input);

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMM d");
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
        String formattedDate = dateFormat.format(oldTask.calendar.getTime());
        String formattedTime = timeFormat.format(oldTask.calendar.getTime());

        int hour = oldTask.calendar.get(Calendar.HOUR_OF_DAY);
        int minute = oldTask.calendar.get(Calendar.MINUTE);

        nameInput.setText(oldTask.name);
        timeInput.setText(formattedTime);
        dateInput.setText(formattedDate);

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

        newTask.calendar.set(Calendar.YEAR, year);
        newTask.calendar.set(Calendar.MONTH, month);
        newTask.calendar.set(Calendar.DAY_OF_MONTH, day);

        dateInput.setText(formattedDate);
    }

    @Override
    public void returnTime (Calendar calendar) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
        String formattedTime = timeFormat.format(calendar.getTime());

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        newTask.calendar.set(Calendar.HOUR_OF_DAY, hour);
        newTask.calendar.set(Calendar.MINUTE, minute);

        timeInput.setText(formattedTime);
    }

    public void submitEditTask (View view) {
        String name = nameInput.getText().toString();
        String date = dateInput.getText().toString();
        String time = timeInput.getText().toString();

        if (validateInput(name, date, time)) {
            newTask.name = name;
            newTask.id = oldTask.id;
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("OLD_TASK", oldTask);
            intent.putExtra("NEW_TASK", newTask);
            setResult(201, intent);
            finish();
        }
    }
}
