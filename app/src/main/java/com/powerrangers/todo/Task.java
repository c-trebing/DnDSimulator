package com.powerrangers.todo;

import java.io.Serializable;

public class Task implements Serializable {
  public String name;
  public String date;

  public Task (String iname, String idate) {
    name = iname;
    date = idate;
  }
}
