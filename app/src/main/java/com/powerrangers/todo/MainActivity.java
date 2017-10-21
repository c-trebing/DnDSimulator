package com.powerrangers.todo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ArrayList<TaskActivity> tasks;

    MyExpandableListAdapter listAdaptor;
    ExpandableListView listView;
    List<String> listDataHeaders;
    HashMap<String, List<String>> listDataChildren;
    private static final String TAG = "Main_Activity";
    private FirebaseAuth mAuth;

    /*********test of firebase********/
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    /*********test of firebase********/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        tasks = new ArrayList<TaskActivity>();
        listDataHeaders = new ArrayList<String>();
        listDataChildren = new HashMap<String, List<String>>();

        listAdaptor = new MyExpandableListAdapter(this, listDataHeaders, listDataChildren);
        listView = (ExpandableListView) findViewById(R.id.task_list);
        listView.setAdapter(listAdaptor);
        mAuth = FirebaseAuth.getInstance();

        prepareMockData();
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
      if (resultCode == 200 && requestCode == 100) {
          TaskActivity task = (TaskActivity)intent.getSerializableExtra("CREATED_TASK");
        addTask(task);
        sortTasks();
        updateDisplayedTasks();

      }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
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

        } else if (id == R.id.nav_logout) {
            Log.d(TAG, "Logging out");
            mAuth.signOut();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivityForResult(intent, 100);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void addTask (TaskActivity task) {
        tasks.add( task );

        /** Update Firebase with new information upon addTask**/
        SimpleDateFormat taskFormat = new SimpleDateFormat("EEEE, MMM d @ hh:mm a  -  ");
        String header = taskFormat.format(task.calendar.getTime());
        DatabaseReference myRef = database.getReference(header);
        myRef.setValue(task.name);
    }

    private void sortTasks () {
      Collections.sort(tasks, new Comparator<TaskActivity>() {
        @Override
        public int compare(TaskActivity t1, TaskActivity t2) {
          return t1.calendar.getTime().compareTo(t2.calendar.getTime());
        }
      });
    }

    private void updateDisplayedTasks () {
      SimpleDateFormat taskHeaderFormat = new SimpleDateFormat("EEEE, MMM d");
      SimpleDateFormat taskEntryFormat = new SimpleDateFormat("hh:mm a  -  ");

      // Kinda naive to tear it down and rebuild it at every change,
      // but listAdaptors are funky to work with. It works.
      listDataHeaders.clear();
      listDataChildren.clear();

      for (TaskActivity task : tasks) {
        String header = taskHeaderFormat.format(task.calendar.getTime());
        String entry = taskEntryFormat.format(task.calendar.getTime()) + task.name;
        // if it doesnt exist, create it
        if (listDataHeaders.indexOf(header) == -1) {
          listDataHeaders.add(header);
          listDataChildren.put(header, new ArrayList<String>());
        }

        listDataChildren.get(header).add(entry);
        listAdaptor.setNewItems(listDataHeaders, listDataChildren);
      }
    }

    private void prepareMockData () {
      Calendar today = Calendar.getInstance();
      Calendar tomorrow = Calendar.getInstance();
      tomorrow.add(Calendar.DATE, 1);

      for (int i=1; i<20; i++) {
        addTask( new TaskActivity("problem " + i, tomorrow) );
      }
      addTask( new TaskActivity("panic about tomorrow", today) );

      sortTasks();
      updateDisplayedTasks();
    }
}
