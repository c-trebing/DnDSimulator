package com.powerrangers.todo;

import java.util.ArrayList;
import java.util.UUID;


public class Group {
    public String group_name;
    public String group_owner;  // change to User type
    public UUID group_id;
    public ArrayList<UUID> group_members;  // will be changed to some collection of User type
    public ArrayList<Task> group_tasks;  // will be changed to some collection of Task type

    public Group (String name, String owner){
        group_name = name;
        group_owner = owner;
        group_id  = UUID.randomUUID();
    }

    public boolean addTask(Task t){ // future: add to firebase
        if( group_tasks.size() == 0){
            group_tasks.add(t);
            return true;
        }
        for(int i=0; i < group_tasks.size(); i++){
            if(group_tasks.get(i).id == t.id){
                return false;
            }
        }
        group_tasks.add(t);
        return true;
    }

    public boolean editTask(Task t){ // future: add to firebase
        for(int i=0; i < group_tasks.size(); i++){

        }
        return false;
    }


    public boolean addMember(UUID id){ // future: add to firebase
        if(group_members.contains(id)){
            return false;
        }
        else{
            group_members.add(id);
            return true;
        }
    }

    //  more getters and setters below
}

