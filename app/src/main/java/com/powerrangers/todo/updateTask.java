package com.powerrangers.todo;

/**
 * Created by Cerberus on 10/15/2017.
 */


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class updateTask extends AppCompatActivity {
    /*
    private static final String TAG = "vietDatabase";
    //adding firebase things
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Method is caled once with inital value and again
                //wheenver data is updated
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        })
    }

    // code below doesn't work yet
    private void showData(DataSnapshot dataSnapshot) {
        for(DataSnapshot ds: dataSnapshot.getChildren()){
            UserInfo uInfo = new UserInfo();
            uInfo.setName(ds.child(userID).getValue(UserInfo.class).getName()) //set name
            uInfo.setName(ds.child(userID).getValue(UserInfo.class).getName()) //set name
            uInfo.setName(ds.child(userID).getValue(UserInfo.class).getName()) //set name

        }
    }
    */
}
