package com.powerrangers.todo;

import java.io.Serializable;
import java.util.Calendar;
import java.util.UUID;

public class Task implements Serializable {

  public String taskName;
  public String taskDesc;
  public Calendar calendar;
  public UUID id;

//Constructors
  public Task(String iname, Calendar icalendar, String idescription) {
    taskDesc = idescription;
    taskName = iname;
    calendar = Calendar.getInstance();  // ensures it is a deep copy
    calendar.setTime(icalendar.getTime());
    id = UUID.randomUUID();
  }

  public Task(String iname, Calendar icalendar) {
    taskName = iname;
    calendar = Calendar.getInstance();  // ensures it is a deep copy
    calendar.setTime(icalendar.getTime());
    id = UUID.randomUUID();
  }
  public Task() {
  }
// Proper Functions
  @Override
  public boolean equals(Object other) {
    Task t2 = (Task) other;
<<<<<<< HEAD
<<<<<<< refs/remotes/origin/group
    return this.id == t2.id && this.name == t2.name && this.calendar.equals(t2.calendar);
=======
=======
>>>>>>> andrewBranch
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
<<<<<<< HEAD
>>>>>>> Issues with updateTask
=======
>>>>>>> andrewBranch
  }

  public void setTaskDesc(String taskDesc) {
    this.taskDesc = taskDesc;
  }

  public Calendar getCalendar() {
    return calendar;
  }

  public void setCalendar(Calendar calendar) {
    this.calendar = calendar;
  }
  public UUID getId() {
    return id;
  }
  public void setId(UUID id) {
    this.id = id;
  }

};