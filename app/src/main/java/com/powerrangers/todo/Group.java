package com.powerrangers.todo;

import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.UUID;


public class Group {
    public String group_name;
    public String group_owner;  // change to User type
    public UUID group_id;
    public ArrayList<UUID> group_members;
    public ArrayList<Task> group_tasks;

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
        if(group_tasks.contains(t)){
            group_tasks.remove(t);
            group_tasks.add(t);
            return true;
        }
        else{
            return false;
        }
    }

    public boolean deleteTask(Task t){ // future: remove from firebase
        if(group_tasks.contains(t)){
            group_tasks.remove(t);
            return true;
        }
        else{
            return false;
        }
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

    public boolean removeMember(UUID id){ // future: remove from firebase
        if(group_members.contains(id)){
            group_members.remove(id);
            return true;
        }
        else{
            return false;
        }
    }
}

