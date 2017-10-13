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
    calendar = icalendar;
    id = UUID.randomUUID();
  }
}
