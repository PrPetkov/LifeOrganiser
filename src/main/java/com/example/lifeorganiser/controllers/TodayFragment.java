package com.example.lifeorganiser.src.controllers;


import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lifeorganiser.R;
import com.example.lifeorganiser.src.Models.events.DatedEvent;
import com.example.lifeorganiser.src.Models.events.Event;
import com.example.lifeorganiser.src.Models.user.UserManager;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;


public class TodayFragment extends android.app.Fragment {

    private static final String ADD_TEXT = "add +";

    private UserManager userManager;

    private TextView usenameGreatingTextView;
    private ListView tasksListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        this.userManager = UserManager.getInstance();

        View v = inflater.inflate(R.layout.fragment_today, container, false);

        this.usenameGreatingTextView = (TextView) v.findViewById(R.id.todayActivityUsernameTextView);
        this.tasksListView = (ListView) v.findViewById(R.id.todayActivityListView);

        this.usenameGreatingTextView.setText(this.userManager.getUsername());

        final ArrayList<DatedEvent> events = new ArrayList<>();
        ArrayList<String> stringEvents = new ArrayList<>();
        for (DatedEvent event: this.userManager.getTasks()){
            if (event.getDateTime().get(Calendar.YEAR) == Calendar.getInstance().get(Calendar.YEAR) &&
                    event.getDateTime().get(Calendar.MONTH) == Calendar.getInstance().get(Calendar.MONTH)&&
                    event.getDateTime().get(Calendar.DAY_OF_MONTH) == Calendar.getInstance().get(Calendar.DAY_OF_MONTH)){
                events.add(event);
                stringEvents.add(event.getDateTime().get(Calendar.HOUR) + " " +
                        event.getDateTime().get(Calendar.MINUTE) + " " +
                        event.getTitle());
            }
        }

        stringEvents.add(TodayFragment.ADD_TEXT);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),
                R.layout.simple_listview_item,
                stringEvents);
        this.tasksListView.setAdapter(adapter);

        this.tasksListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO possible bug
                FragmentManager fm = getFragmentManager();
                if (((TextView) view).getText().equals(TodayFragment.ADD_TEXT)) {

                    AddTaskDialog dialogFragment = new AddTaskDialog ();
                    dialogFragment.show(fm, "Add task fragment");

                    return;
                }
                //TODO start task view

                Event clickedEvent = events.get(position);

                Bundle eventBundle = new Bundle();
                eventBundle.putSerializable("event", clickedEvent);
                DialogFragment taskView = new TaskViewDialogFragment();
                taskView.setArguments(eventBundle);
                taskView.show(fm, "Task view fragment");
            }
        });

        return v;
    }

}
