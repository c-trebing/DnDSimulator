package com.powerrangers.todo;

/**
 * Created by Brendan on 10/10/2017.
 */

public class Group {
    public String group_name;
    public String group_owner;
    public int group_id;  //possibly use to allow for same names??

    public Group (String name, String owner){
        group_name = name;
        group_owner = owner;
        group_id = 0;
    }

    // getters and setters below
}
