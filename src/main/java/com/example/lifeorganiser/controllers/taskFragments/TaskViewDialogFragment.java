package com.example.lifeorganiser.src.controllers.taskFragments;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lifeorganiser.R;
import com.example.lifeorganiser.src.Models.events.DatedEvent;
import com.example.lifeorganiser.src.Models.events.Event;

import java.util.Calendar;

public class TaskViewDialogFragment extends DialogFragment {

    private TextView name;
    private TextView dateTime;
    private TextView description;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());

        final LayoutInflater inflater = getActivity().getLayoutInflater();

        View v = inflater.inflate(R.layout.fragment_task_view, null);

        this.name = (TextView) v.findViewById(R.id.taskViewTaskNameText);
        this.dateTime = (TextView) v.findViewById(R.id.taskViewTaskTimeText);
        this.description = (TextView) v.findViewById(R.id.taskViewTaskDescriptionText);

        Bundle taskBundle = this.getArguments();
        DatedEvent selectedEvent = (DatedEvent) taskBundle.get("event");
        Calendar date = selectedEvent.getDateTime();

        String dateString = String.format("Date: %s %s %s ", date.get(Calendar.DAY_OF_WEEK), date.get(Calendar.MONTH), date.get(Calendar.YEAR));
        dateString = dateString.concat(System.getProperty("line.separator"));
        dateString = dateString + String.format("Time: %s %s", date.get(Calendar.HOUR_OF_DAY), date.get(Calendar.MINUTE));

        this.name.setText(selectedEvent.getTitle().toString());
        this.dateTime.setText(dateString);
        this.description.setText(selectedEvent.getDescription().toString());

        dialogBuilder.setView(v);

        dialogBuilder.setPositiveButton(R.string.edit, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        dialogBuilder.setNegativeButton("close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });

        return dialogBuilder.create();
    }
}
