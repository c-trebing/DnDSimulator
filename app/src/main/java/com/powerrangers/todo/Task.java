package com.powerrangers.todo;

import java.io.Serializable;
import java.util.Calendar;

public class Task implements Serializable {
  public String name;
  public String date;
  public Calendar calendar;

  public Task (String iname, String idate) {
    name = iname;
    date = idate;
    calendar = Calendar.getInstance();
  }
}
