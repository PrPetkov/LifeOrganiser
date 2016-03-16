package com.example.lifeorganiser.src.controllers.accountFragments;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.lifeorganiser.R;
import com.example.lifeorganiser.src.Models.accounts.DebitAccount;
import com.example.lifeorganiser.src.Models.user.UserManager;

import java.util.ArrayList;

public class AccountsFragment extends Fragment {

    private UserManager userManager;

    private ListView accountsView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.userManager = UserManager.getInstance();

        View v = inflater.inflate(R.layout.fragment_accounts, container, false);

        this.accountsView = (ListView) v.findViewById(R.id.accountsListView);

        final ArrayList<DebitAccount> debitAccounts = this.userManager.getDebitAccounts();
        final ArrayList<String> debitAccountStrings = new ArrayList<>();

        for (DebitAccount account : debitAccounts){
            debitAccountStrings.add(account.getAccountName() + " balance: " + account.getAmount());
        }

        debitAccountStrings.add(getString(R.string.add));

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity().getApplicationContext(),
                R.layout.simple_listview_item,
                debitAccountStrings);

        this.accountsView.setAdapter(adapter);

        this.accountsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentManager fm = getFragmentManager();

                if (position == debitAccountStrings.size() - 1) {
                    AddAccountDialogFragment addFragment = new AddAccountDialogFragment();
                    addFragment.show(fm, "addAccount");
                } else {
                    AccountViewFragment viewFragment = new AccountViewFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("account", debitAccounts.get(position));
                    viewFragment.setArguments(bundle);
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();

                    transaction.replace(R.id.fragmentLayout, viewFragment);
                    transaction.addToBackStack(null);

                    transaction.commit();
                }
            }
        });

        return v;
    }

}
