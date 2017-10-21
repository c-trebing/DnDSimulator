package com.powerrangers.todo;

import java.io.Serializable;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class Task implements Serializable {

  public String taskName;
  public String taskDesc;
  public Calendar calendar;
  public String id;

//Constructors
  public Task(String iname, Calendar icalendar, String idescription, String newId) {
    taskDesc = idescription;
    taskName = iname;
    calendar = Calendar.getInstance();  // ensures it is a deep copy
    calendar.setTime(icalendar.getTime());
    id = newId;
  }

  public Task(String iname, Calendar icalendar,String newId) {
    taskName = iname;
    calendar = Calendar.getInstance();  // ensures it is a deep copy
    calendar.setTime(icalendar.getTime());
    id = newId;
    taskDesc = "none";
  }
  public Task() {
  }
// Proper Functions
  @Override
  public boolean equals(Object other) {
    Task t2 = (Task) other;
    return this.id == t2.id && this.taskName == t2.taskName && this.calendar == t2.calendar;
  }

  //Below is all the getters and setters
  public String getTaskName() {
    return taskName;
  }

  public void setTaskName(String taskName) {
    this.taskName = taskName;
  }

  public String getTaskDesc() {
    return taskDesc;
  }

  public void setTaskDesc(String taskDesc) {
    this.taskDesc = taskDesc;
  }

  public String getCalendar() {
    SimpleDateFormat taskFormat = new SimpleDateFormat("EEEE, MMM d @ hh:mm a  -  ", Locale.ENGLISH);
    String header = taskFormat.format(this.calendar.getTime());

    return header;
  }

  public void setCalendar(Calendar calendar) {
    this.calendar = calendar;
  }
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }

};
