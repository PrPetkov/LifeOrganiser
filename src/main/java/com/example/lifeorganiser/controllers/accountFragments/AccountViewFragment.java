package com.example.lifeorganiser.src.controllers.accountFragments;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lifeorganiser.R;
import com.example.lifeorganiser.src.Models.Exceptions.UserManagerException;
import com.example.lifeorganiser.src.Models.accounts.DebitAccount;
import com.example.lifeorganiser.src.Models.events.PaymentEvent;
import com.example.lifeorganiser.src.Models.user.UserManager;
import com.example.lifeorganiser.src.controllers.taskFragments.TaskViewDialogFragment;

import java.util.ArrayList;

public class AccountViewFragment extends Fragment {

    private static final int REQUEST_CODE_ADD = 1;
    private static final int REQUEST_CODE_WITHDRAW = 2;
    private UserManager userManager;
    private double amount;
    private DebitAccount account;
    private Bundle arguments;

    private TextView accountNameTextView;
    private TextView accountAmountTextView;
    private TextView addTextView;
    private TextView withdrowTextView;
    private ListView accountHistory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.userManager = UserManager.getInstance();

        this.arguments = getArguments();

        this.account = (DebitAccount) this.arguments.get("account");

        View v = inflater.inflate(R.layout.fragment_account_view, container, false);

        this.accountNameTextView = (TextView) v.findViewById(R.id.accountViewName);
        this.accountAmountTextView = (TextView) v.findViewById(R.id.accountViewAmount);
        this.addTextView = (TextView) v.findViewById(R.id.accountViewAdd);
        this.withdrowTextView = (TextView) v.findViewById(R.id.accountViewWithdraw);
        this.accountHistory = (ListView) v.findViewById(R.id.accountViewHistoryList);

        this.accountNameTextView.setText(account.getAccountName());
        this.accountAmountTextView.setText(String.valueOf(account.getAmount()));

        this.addTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                AccountGetAmountDialogFragment getAmount = new AccountGetAmountDialogFragment();
                getAmount.setTargetFragment(AccountViewFragment.this, REQUEST_CODE_ADD);

                getAmount.show(fm, "getAccount");

            }
        });

        this.withdrowTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                AccountGetAmountDialogFragment getAmount = new AccountGetAmountDialogFragment();
                getAmount.setTargetFragment(AccountViewFragment.this, REQUEST_CODE_WITHDRAW);
                getAmount.show(fm, "getAccount");
            }
        });

        final ArrayList<PaymentEvent> accountHistory = this.userManager.getAccountHistory(account.getDbUid());
        ArrayList<String> stringAccountHistory = new ArrayList<>();

        for (PaymentEvent event : accountHistory){
            String type = event.getIsIncome() ? "Income" : "Expenses";
            String text = type + ": " + event.getTitle() + " balance: " + event.getAmount();
            stringAccountHistory.add(text);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity().getApplicationContext(),
                R.layout.simple_listview_item,
                stringAccountHistory);
        this.accountHistory.setAdapter(adapter);

        this.accountHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentManager fm = getFragmentManager();
                PaymentEvent clickedEvent = accountHistory.get(position);

                Bundle bundle = new Bundle();
                bundle.putSerializable("event", clickedEvent);

                TaskViewDialogFragment taskView = new TaskViewDialogFragment();
                taskView.setArguments(bundle);
                taskView.show(fm, "Event");
            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        this.amount = data.getDoubleExtra("amount", 0);

        if (requestCode == REQUEST_CODE_ADD){
            try {
                this.userManager.addMoney(this.account, this.amount);
            } catch (UserManagerException e) {
                Toast.makeText(getActivity().getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else if(requestCode == REQUEST_CODE_WITHDRAW){
            try {
                this.userManager.withdrawMoney(this.account, this.amount);
            } catch (UserManagerException e) {
                Toast.makeText(getActivity().getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        AccountViewFragment fragment = new AccountViewFragment();
        fragment.setArguments(this.arguments);
        transaction.replace(R.id.fragmentLayout, fragment);
        transaction.commit();
    }
}
