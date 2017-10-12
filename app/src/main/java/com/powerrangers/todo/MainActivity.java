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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ArrayList<Task> tasks;

    MyExpandableListAdapter listAdaptor;
    ExpandableListView listView;
    List<String> listDataHeaders;
    HashMap<String, List<String>> listDataChildren;

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

        tasks = new ArrayList<Task>();
        listDataHeaders = new ArrayList<String>();
        listDataChildren = new HashMap<String, List<String>>();

        listAdaptor = new MyExpandableListAdapter(this, listDataHeaders, listDataChildren);
        listView = (ExpandableListView) findViewById(R.id.task_list);
        listView.setAdapter(listAdaptor);

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
        Task task = (Task)intent.getSerializableExtra("CREATED_TASK");
        addTask(task);
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

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void addTask (Task task) {
      tasks.add( task );
    }

    private void sortTasks () {
      Collections.sort(tasks, new Comparator<Task>() {
        @Override
        public int compare(Task t1, Task t2) {
          return t2.calendar.getTime().compareTo(t1.calendar.getTime());
        }
      });
    }

    private void updateDisplayedTasks () {
      for (Task task : tasks) {
        // if it doesnt exist, create it
        if (listDataHeaders.indexOf(task.date) == -1) {
          listDataHeaders.add(0, task.date);
          listDataChildren.put(task.date, new ArrayList<String>());
        }

        listDataChildren.get(task.date).add(task.name);
        listAdaptor.setNewItems(listDataHeaders, listDataChildren);
      }
    }

    private void prepareMockData () {
      SimpleDateFormat sdf = new SimpleDateFormat("EEEE, MMM d");
      Date today = new Date();
      Date tomorrow = new Date(today.getTime() + (1000 * 60 * 60 * 24));
      addTask( new Task("panic about tomorrow", sdf.format(today)) );
      for (int i=1; i<100; i++) {
        addTask( new Task("problem " + i, sdf.format(tomorrow)) );
      }
    }
}
