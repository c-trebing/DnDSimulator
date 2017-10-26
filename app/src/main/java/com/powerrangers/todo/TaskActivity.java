package com.powerrangers.todo;

import java.io.Serializable;
import java.util.Calendar;

public class TaskActivity implements Serializable {
    public String name;
    public Calendar calendar;

    public TaskActivity (String iname, Calendar icalendar) {
        name = iname;
        calendar = icalendar;
    }
}
