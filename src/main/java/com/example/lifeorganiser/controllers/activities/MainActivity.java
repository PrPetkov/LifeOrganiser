package com.example.lifeorganiser.src.controllers.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.lifeorganiser.R;
import com.example.lifeorganiser.src.Models.events.DatedEvent;
import com.example.lifeorganiser.src.Models.user.UserManager;
import com.example.lifeorganiser.src.controllers.accountFragments.AccountsFragment;
import com.example.lifeorganiser.src.controllers.shoppingListFragments.ShoppingListFragment;
import com.example.lifeorganiser.src.controllers.todayFragments.CalendarFragment;
import com.example.lifeorganiser.src.controllers.todoFragments.TODOFragment;
import com.example.lifeorganiser.src.controllers.taskFragments.TasksFragment;
import com.example.lifeorganiser.src.controllers.todayFragments.TodayFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.TreeSet;

public class MainActivity extends AppCompatActivity {

    private UserManager userManager;

    private TextView todayTextView;
    private TextView todoTextView;
    private TextView calendarTextView;
    private TextView tasksTextView;
    private TextView accountsTextView;
    private TextView shoppingListTextView;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.userManager = UserManager.getInstance();

        if (!this.userManager.hasLoggedUser()){
            Intent intent = new Intent(MainActivity.this, LogInActivity.class);

            startActivity(intent);
        }

        this.todayTextView = (TextView) findViewById(R.id.todayNavigationTodayTextView);
        this.todoTextView = (TextView) findViewById(R.id.todayNavigationTODOTextView);
        this.calendarTextView = (TextView) findViewById(R.id.todayNavigationCalendarTextView);
        this.tasksTextView = (TextView) findViewById(R.id.todayNavigationTasksTextView);
        this.accountsTextView = (TextView) findViewById(R.id.todayNavigationAccountTextView);
        this.shoppingListTextView = (TextView) findViewById(R.id.todayNavigationShoppingTextView);
        this.toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        this.drawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, this.drawerLayout, this.toolbar, R.string.openDrawer, R.string.closeDrawer);
        this.drawerLayout.setDrawerListener(toggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        final FragmentManager fragmentManager = getFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        TodayFragment todayFragment = new TodayFragment();

        fragmentTransaction.add(R.id.fragmentLayout, todayFragment, "todayFragment");
        fragmentTransaction.commit();

        this.todayTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.drawerLayout.closeDrawers();

                TodayFragment todayFragment = new TodayFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                transaction.replace(R.id.fragmentLayout, todayFragment);
                transaction.addToBackStack(null);

                transaction.commit();
            }
        });

        this.tasksTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.drawerLayout.closeDrawers();

                TasksFragment tasksFragment = new TasksFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                transaction.replace(R.id.fragmentLayout, tasksFragment);
                transaction.addToBackStack(null);

                transaction.commit();
            }
        });

        this.todoTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.drawerLayout.closeDrawers();

                TODOFragment todoFragment = new TODOFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                transaction.replace(R.id.fragmentLayout, todoFragment);
                transaction.addToBackStack(null);

                transaction.commit();
            }
        });

        this.accountsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.drawerLayout.closeDrawers();

                AccountsFragment accountsFragment = new AccountsFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                transaction.replace(R.id.fragmentLayout, accountsFragment);
                transaction.addToBackStack(null);

                transaction.commit();
            }
        });

        this.shoppingListTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.drawerLayout.closeDrawers();

                ShoppingListFragment fragment = new ShoppingListFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                transaction.replace(R.id.fragmentLayout, fragment);
                transaction.addToBackStack(null);

                transaction.commit();
            }
        });

        this.calendarTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.drawerLayout.closeDrawers();

                CalendarFragment calendarFragment = new CalendarFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                transaction.replace(R.id.fragmentLayout, calendarFragment);
                transaction.addToBackStack(null);

                transaction.commit();
            }
        });
    }
}
