package com.example.lifeorganiser.src.controllers.taskFragments;


import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


import com.example.lifeorganiser.R;
import com.example.lifeorganiser.src.Models.events.DatedEvent;
import com.example.lifeorganiser.src.Models.events.Event;
import com.example.lifeorganiser.src.Models.user.UserManager;

import java.util.ArrayList;
import java.util.Calendar;


public class TasksFragment extends android.app.Fragment {

    private static final String ADD_TEXT = "add +";

    private UserManager userManager;

    private ListView tasksListView;

    public TasksFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {

        this.userManager = UserManager.getInstance();

        View v = inflater.inflate(R.layout.fragment_tasks, container, false);

        this.tasksListView = (ListView) v.findViewById(R.id.tasksActivityListView);

        final ArrayList<DatedEvent> events = new ArrayList<>();
        ArrayList<String> stringEvents = new ArrayList<>();
        for (DatedEvent event: this.userManager.getTasks()){

            events.add(event);
            stringEvents.add(event.getDateTime().get(Calendar.HOUR) + " " +
                    event.getDateTime().get(Calendar.MINUTE) + " " +
                    event.getTitle());

        }

        stringEvents.add(TasksFragment.ADD_TEXT);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),
                R.layout.simple_listview_item,
                stringEvents);
        this.tasksListView.setAdapter(adapter);

        this.tasksListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentManager fm = getFragmentManager();

                if (((TextView) view).getText().equals(TasksFragment.ADD_TEXT)) {
                    AddTaskDialog dialogFragment = new AddTaskDialog();
                    dialogFragment.show(fm, "Sample Fragment");

                    return;
                }

                Event clickedEvent = events.get(position);

                Bundle eventBundle = new Bundle();
                eventBundle.putSerializable("event", clickedEvent);
                TaskViewDialogFragment taskView = new TaskViewDialogFragment();
                taskView.setArguments(eventBundle);
                taskView.show(fm, "Task view fragment");
            }
        });

        return v;
    }

}
