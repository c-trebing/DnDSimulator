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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    List<Task> tasks = new ArrayList<Task>();

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

        prepareListData();
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
        String taskName = intent.getStringExtra("CREATE_TASK_NAME");
        String taskDay = intent.getStringExtra("CREATE_TASK_DAY");

        // if it doesnt exist, create it
        if (listDataHeaders.indexOf(taskDay) == -1) {
          listDataHeaders.add(taskDay);
          listDataChildren.put(taskDay, new ArrayList<String>());
        }

        listDataChildren.get(taskDay).add(taskName);
        listAdaptor.setNewItems(listDataHeaders, listDataChildren);
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


    private void prepareListData () {
      listDataHeaders = new ArrayList<String>();
      listDataChildren = new HashMap<String, List<String>>();

      // adding child data
      listDataHeaders.add("Today");
      listDataHeaders.add("Wednesday, November 12");
      listDataHeaders.add("Thursday, November 13");

      List<String> taskList1 = new ArrayList<String>();
      taskList1.add("task1");
      taskList1.add("task2");
      List<String> taskList2 = new ArrayList<String>();
      for (int i=0; i<30; i++) {
        taskList2.add("task" + (5+i));
      }
      List<String> taskList3 = new ArrayList<String>();
      taskList3.add("task3");
      taskList3.add("task4");

      listDataChildren.put("Today", taskList1);
      listDataChildren.put(listDataHeaders.get(1), taskList2);
      listDataChildren.put(listDataHeaders.get(2), taskList3);


      listAdaptor = new MyExpandableListAdapter(this, listDataHeaders, listDataChildren);
      listView = (ExpandableListView) findViewById(R.id.task_list);
      listView.setAdapter(listAdaptor);
    }
}
