package com.example.lifeorganiser.src.controllers.taskFragments;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.lifeorganiser.R;
import com.example.lifeorganiser.src.Models.Exceptions.IllegalAmountException;
import com.example.lifeorganiser.src.Models.accounts.DebitAccount;
import com.example.lifeorganiser.src.Models.events.NotificationEvent;
import com.example.lifeorganiser.src.Models.events.PaymentEvent;
import com.example.lifeorganiser.src.Models.user.UserManager;
import com.example.lifeorganiser.src.controllers.todayFragments.TodayFragment;

import java.util.ArrayList;
import java.util.Calendar;

public class AddTaskDialog extends DialogFragment {

    private UserManager userManager = UserManager.getInstance();

    private EditText eventName;
    private EditText eventDescription;
    private EditText eventAmount;
    private DatePicker datePicker;
    private TimePicker timePicker;
    private CheckBox isPayable;
    private RadioGroup radioGroup;
    private RadioButton incomeRadio;
    private RadioButton expensesRadio;
    private TextView howMuchText;
    private Spinner spinner;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        final LayoutInflater inflater = getActivity().getLayoutInflater();

        View v = inflater.inflate(R.layout.fragment_add_task_dialog, null);

        this.eventName = (EditText) v.findViewById(R.id.addTaskEventName);
        this.eventDescription = (EditText) v.findViewById(R.id.addTaskDescription);
        this.eventAmount = (EditText) v.findViewById(R.id.addTaskAmount);
        this.datePicker = (DatePicker) v.findViewById(R.id.addTaskDatePicker);
        this.timePicker = (TimePicker) v.findViewById(R.id.addTaskTimePicker);
        this.isPayable = (CheckBox) v.findViewById(R.id.addTaskIsPayable);
        this.radioGroup = (RadioGroup) v.findViewById(R.id.addTaskRadioGroup);
        this.incomeRadio = (RadioButton) v.findViewById(R.id.incomeRadio);
        this.expensesRadio = (RadioButton) v.findViewById(R.id.expensesRadio);
        this.howMuchText = (TextView) v.findViewById(R.id.addTaskHowMuch);
        this.spinner = (Spinner) v.findViewById(R.id.addTaskSpinner);

        this.incomeRadio.setChecked(true);

        final ArrayList<DebitAccount> accounts = this.userManager.getDebitAccounts();
        ArrayList<String> stringAccounts = new ArrayList<>();
        for (DebitAccount account : accounts){
            stringAccounts.add(account.getAccountName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.simple_listview_item, stringAccounts);
        this.spinner.setAdapter(adapter);

        this.isPayable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AddTaskDialog.this.isPayable.isChecked()){
                    AddTaskDialog.this.radioGroup.setVisibility(View.VISIBLE);
                    AddTaskDialog.this.eventAmount.setVisibility(View.VISIBLE);
                    AddTaskDialog.this.howMuchText.setVisibility(View.VISIBLE);
                    AddTaskDialog.this.spinner.setVisibility(View.VISIBLE);
                }else {
                    AddTaskDialog.this.radioGroup.setVisibility(View.GONE);
                    AddTaskDialog.this.eventAmount.setVisibility(View.GONE);
                    AddTaskDialog.this.howMuchText.setVisibility(View.GONE);
                    AddTaskDialog.this.spinner.setVisibility(View.GONE);
                }
            }
        });

        builder.setView(v);
        builder.setPositiveButton("save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                String eventName = AddTaskDialog.this.eventName.getText().toString();
                String eventDescription = AddTaskDialog.this.eventDescription.getText().toString();
                Calendar dateTime = Calendar.getInstance();
                int day = AddTaskDialog.this.datePicker.getDayOfMonth();
                int month = AddTaskDialog.this.datePicker.getMonth() + 1;
                int year = AddTaskDialog.this.datePicker.getYear();
//                        final int[] time = new int[2];
                //TODO remove deprecated methods!!!
                int hour = AddTaskDialog.this.timePicker.getCurrentHour();
                int minute = AddTaskDialog.this.timePicker.getCurrentMinute();

//                        AddTaskDialog.this.timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
//                            @Override
//                            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
//                                time[0] = hourOfDay;
//                                time[1] = minute;
//                                Toast.makeText(getActivity(), hourOfDay + " " + minute, Toast.LENGTH_SHORT).show();
//                            }
//                        });

                dateTime.set(year, month, day, hour, minute);

                if (AddTaskDialog.this.isPayable.isChecked()) {
                    double amount = Double.parseDouble(AddTaskDialog.this.eventAmount.getText().toString());

                    boolean isIncome = AddTaskDialog.this.incomeRadio.isChecked();

                    int accountPos = AddTaskDialog.this.spinner.getSelectedItemPosition();
                    int accountID = accounts.get(accountPos).getDbUid();

                    try {
                        PaymentEvent newEvent = new PaymentEvent(eventName, eventDescription, amount, isIncome, false, dateTime);
                        AddTaskDialog.this.userManager.addEvent(newEvent, accountID);
                    } catch (IllegalAmountException e) {
                        e.printStackTrace();
                    }
                } else {
                    AddTaskDialog.this.userManager.addEvent(new NotificationEvent(eventName, eventDescription, dateTime));
                }

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                TasksFragment fragment = new TasksFragment();
                transaction.replace(R.id.fragmentLayout, fragment);
                transaction.commit();
            }
        });

        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dismiss();
            }
        });

        return builder.create();
    }
}
