package com.powerrangers.todo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;

import java.io.Serializable;

public class CreateGroupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        groupName = (EditText) findViewById(R.id.groupName);
        groupDescription = (EditText) findViewById(R.id.groupDescription);
        confirmButton = (Button) findViewById(R.id.confirmButton);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String name = groupName.getText().toString();
                String desc = groupDescription.getText().toString();

                if (validateInput(name, desc)) {
                    Group group = new Group(name, desc); // desc should be the group owner. Need a way to pull username...
                    Context context = v.getContext();
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.putExtra("CREATED_GROUP", group);
                    setResult(203, intent);
                    finish();
                }
            }
        });
    }

    // UI references
    public EditText groupName;
    private EditText groupDescription;
    private Button confirmButton;


    private boolean validateInput(String name, String description){
        if (TextUtils.isEmpty(name)){
            groupName.setError("This field is required.");
            groupName.requestFocus();
        }
        else if (TextUtils.isEmpty(description)){
            groupDescription.setError("This field is required.");
            groupDescription.requestFocus();
        }
        else {return true;}
        return false;
    }
}
