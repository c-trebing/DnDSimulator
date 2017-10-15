package com.powerrangers.todo;

/**
 * Created by Cerberus on 10/15/2017.
 * desc: This is updated whenever the database has decided to add something new like groupNames
 * or taskDescription
 */

public class UserInfo {
    private String Name;
    private String Group;
    private String DueDate;

    public UserInfo(){
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getGroup() {
        return Group;
    }

    public void setGroup(String group) {
        Group = group;
    }

    public String getDueDate() {
        return DueDate;
    }

    public void setDueDate(String dueDate) {
        DueDate = dueDate;
    }
}
