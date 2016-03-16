package com.example.lifeorganiser.src.controllers.todayFragments;


import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import com.example.lifeorganiser.R;

import java.util.Calendar;

public class CalendarFragment extends Fragment {

    private CalendarView calendar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_calendar, container, false);

        this.calendar = (CalendarView) v.findViewById(R.id.calendarFragmentCalendar);

        this.calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                TodayFragment fragment = new TodayFragment();

                Bundle bundle = new Bundle();
                Calendar date = Calendar.getInstance();
                date.set(year, month, dayOfMonth);
                bundle.putSerializable("date", date);

                fragment.setArguments(bundle);
                transaction.replace(R.id.fragmentLayout, fragment);
                transaction.addToBackStack(null);

                transaction.commit();
            }
        });

        return v;
    }

}
