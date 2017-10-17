package com.powerrangers.todo;

public class Group {
    public String group_name;
    public String group_owner;  // change to User type
    public int group_id;  // possibly use to allow for same names
    public String[] group_members;  // will be changed to some collection of User type
    public Task[] group_tasks;  // will be changed to some collection of Task type

    public Group (String name, String owner){
        group_name = name;
        group_owner = owner;
        group_id = 0;
    }

    public boolean addTask(Task t){
        // add task once in a collection not an array
        return true;
    }
    //  more getters and setters below
}

