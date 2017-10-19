package com.powerrangers.todo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.UUID;


import static android.R.attr.data;
import static android.R.attr.format;

//importing firebase
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

public class MainActivity extends AppCompatActivity
  implements NavigationView.OnNavigationItemSelectedListener {

  ArrayList<Task> tasks;

  MyExpandableListAdapter listAdaptor;
  ExpandableListView listView;
  List<Calendar> listDataHeaders;
  HashMap<Calendar, List<Task>> listDataChildren;
  FirebaseDatabase database = FirebaseDatabase.getInstance();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    setupXmlElements();
    setupTaskDisplay();
    listAdaptor = new MyExpandableListAdapter(this, listDataHeaders, listDataChildren);
    listView = (ExpandableListView) findViewById(R.id.task_list);
    listView.setAdapter(listAdaptor);
    DatabaseReference myRef = database.getReference();
    myRef.child("Groups").child("groupeOne").child("Tasks").addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        showData(dataSnapshot);
      }

      @Override
      public void onCancelled(DatabaseError databaseError) {

      }
    });
//    prepareMockData();
  }
  private void showData(DataSnapshot dataSnapshot){
    for(DataSnapshot ds : dataSnapshot.getChildren()){
      Task diffTask = new Task();
      firebaseData newData = ds.getValue(firebaseData.class);

      String newCalend = newData.dueDate;
      SimpleDateFormat sdf = new SimpleDateFormat("EEEE, MMM d @ hh:mm a  -  ", Locale.ENGLISH);
      try {
        Log.d("~~~~~~~", "New Calend: " + newCalend + "new Name: " + newData.taskDesc );
        sdf.parse(newCalend);
      }catch (Exception e){
        Log.d("~~~~~~~", "Catch");
      }
      Calendar cal = sdf.getCalendar();
      diffTask.setTaskDesc(newData.taskDesc);
      diffTask.setTaskName(newData.taskName);
      diffTask.setCalendar(cal);
      String newUUID = newData.id;
      UUID newID = UUID.fromString(newUUID);
      diffTask.setId(newID);
      tasks.add( diffTask );
      updateDisplayedTasks(diffTask);
    }
  }

  @Override
  public void onResume () {
    super.onResume();

    // expand groups by default
    int count = listAdaptor.getGroupCount();
    for (int i=0; i<count; i++) {
      listView.expandGroup(i);
    }
  }

  @Override
  public void onActivityResult (int requestCode, int resultCode, Intent intent) {
    if (requestCode == 100) {
      if (resultCode == 200) {
        Task task = (Task)intent.getSerializableExtra("CREATED_TASK");
        addTask(task);
      }
    }
    if (requestCode == 101) {
      if (resultCode == 201) {
        Task oldTask = (Task) intent.getSerializableExtra("OLD_TASK");
        Task newTask = (Task) intent.getSerializableExtra("NEW_TASK");
        deleteTask(oldTask);
        addTask(newTask);
      }
      if (resultCode == 202) {
        Task oldTask = (Task) intent.getSerializableExtra("OLD_TASK");
        deleteTask(oldTask);
      }
    }
  }

  @Override
  public void onBackPressed() {
    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    if (drawer.isDrawerOpen(GravityCompat.START)) {
      drawer.closeDrawer(GravityCompat.START);
    }
    else {
      super.onBackPressed();
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      Intent intent = new Intent(this, SettingsActivity.class);
      startActivity(intent);
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @SuppressWarnings("StatementWithEmptyBody")
  @Override
  public boolean onNavigationItemSelected(MenuItem item) {
    // Handle navigation view item clicks here.
    int id = item.getItemId();

    if (id == R.id.nav_camera) {
        // Handle the camera action
    } else if (id == R.id.nav_gallery) {

    } else if (id == R.id.nav_slideshow) {

    } else if (id == R.id.nav_manage) {

    } else if (id == R.id.nav_share) {

    } else if (id == R.id.nav_send) {

    }

    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    drawer.closeDrawer(GravityCompat.START);
    return true;
  }

  private void setupTaskDisplay () {
    tasks = new ArrayList<Task>();
    listDataHeaders = new ArrayList<Calendar>();
    listDataChildren = new HashMap<Calendar, List<Task>>();

    listAdaptor = new MyExpandableListAdapter(this, listDataHeaders, listDataChildren);
    listView = (ExpandableListView) findViewById(R.id.task_list);
    listView.setAdapter(listAdaptor);
  }

  private void setupXmlElements () {
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.create_fab);
    fab.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // Snackbar.make(view, "Replace this text with a tray of buttons...", Snackbar.LENGTH_LONG)
            //         .setAction("Action", null).show();
            Context context = view.getContext();
            Intent intent = new Intent(context, CreateTaskActivity.class);
            startActivityForResult(intent, 100);
        }
    });

    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawer.setDrawerListener(toggle);
    toggle.syncState();

    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
    navigationView.setNavigationItemSelectedListener(this);

  }

  private int searchListByTaskId (List<Task> list, Task task) {
    for (int i=0; i<list.size(); i++) {
      if (list.get(i).id.equals(task.id)) { return i; }
    }
    return -1;
  }

  private void addTask (Task task) {
    tasks.add( task );
    updateDisplayedTasks(task);

    /** Update Firebase with new information upon addTask**/
    //so it's easier to read the calender
    SimpleDateFormat taskFormat = new SimpleDateFormat("EEEE, MMM d @ hh:mm a  -  ");
    String header = taskFormat.format(task.calendar.getTime());
    DatabaseReference myRef = database.getReference();

    //generates a unique key
    String uniqueID;
    uniqueID = myRef.push().getKey();
    myRef.child("Groups").child("groupeOne").child("Tasks").child(uniqueID).child("taskName").setValue(task.taskName);
    myRef.child("Groups").child("groupeOne").child("Tasks").child(uniqueID).child("dueDate").setValue(header);
    myRef.child("Groups").child("groupeOne").child("Tasks").child(uniqueID).child("taskDesc").setValue("none");
    myRef.child("Groups").child("groupeOne").child("Tasks").child(uniqueID).child("id").setValue(task.id.toString());

  }

  private void deleteTask (Task task) {
    // remove task from task list
    tasks.remove( searchListByTaskId(tasks, task) );

    Calendar header = removeTimeFromCalendar(task.calendar);
    List<Task> headerGroup = listDataChildren.get(header);

    // remove display header if this is the sole task
    if (headerGroup.size() == 1) {
      listDataHeaders.remove(header);
      listDataChildren.remove(header);
    }

    // remove task from display header group if group has other tasks
    else {
      headerGroup.remove( searchListByTaskId(headerGroup, task) );
    }

    listAdaptor.setNewItems(listDataHeaders, listDataChildren);
  }

  private void sortTasks (List<Task> taskList) {
    Collections.sort(taskList, new Comparator<Task>() {
      @Override
      public int compare(Task t1, Task t2) {
        return t1.calendar.getTime().compareTo(t2.calendar.getTime());
      }
    });
  }

  private Calendar removeTimeFromCalendar (Calendar c) {
    Calendar newDate = Calendar.getInstance();
    newDate.setTime(c.getTime());
    newDate.set(Calendar.HOUR_OF_DAY, 0);
    newDate.set(Calendar.MINUTE, 0);
    newDate.set(Calendar.SECOND, 0);
    newDate.set(Calendar.MILLISECOND, 0);
    return newDate;
  }

  private void updateDisplayedTasks (Task task) {
    Calendar header = removeTimeFromCalendar(task.calendar);
    DatabaseReference myRef = database.getReference();

      // if it doesnt exist, create it
    if (listDataHeaders.indexOf(header) == -1) {
      listDataHeaders.add(header);
      listDataChildren.put(header, new ArrayList<Task>());
      Collections.sort(listDataHeaders);
    }

    // update task if it already exists or create it if it doesnt
    List<Task> groupList = listDataChildren.get(header);
    boolean exists = false;
    for (Task t : groupList) {
      if (t.id == task.id) {
        exists = true;
        t = task;
      }
    }

    if (!exists) { groupList.add(task); }

    sortTasks(groupList);
    listAdaptor.setNewItems(listDataHeaders, listDataChildren);
  }

  private void prepareMockData () {
    Calendar today = Calendar.getInstance();
    Calendar tomorrow = Calendar.getInstance();
    tomorrow.add(Calendar.DATE, 1);

    for (int i=1; i<1; i++) {
      addTask( new Task("problem " + i, tomorrow) );
    }
    addTask( new Task("panic about tomorrow", today) );
  }
}
