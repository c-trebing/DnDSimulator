package com.powerrangers.todo;

import java.io.Serializable;
import java.util.Calendar;

public class Task implements Serializable {
  public String name;
  public Calendar calendar;

  public Task (String iname, Calendar icalendar) {
    name = iname;
    calendar = icalendar;
  }
}
