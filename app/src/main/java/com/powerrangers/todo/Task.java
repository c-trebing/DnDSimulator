package com.powerrangers.todo;

import java.io.Serializable;
import java.util.Calendar;
import java.util.UUID;

public class Task implements Serializable {
  public String name;
  public Calendar calendar;
  public UUID id;

  public Task (String iname, Calendar icalendar) {
    name = iname;
    calendar = Calendar.getInstance();  // ensures it is a deep copy
    calendar.setTime(icalendar.getTime());
    id = UUID.randomUUID();
  }

  @Override
  public boolean equals (Object other) {
    Task t2 = (Task) other;
    return this.id == t2.id && this.name == t2.name && this.calendar == t2.calendar;
  }
}
