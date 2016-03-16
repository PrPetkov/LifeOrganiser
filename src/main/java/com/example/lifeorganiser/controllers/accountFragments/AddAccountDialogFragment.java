package com.example.lifeorganiser.src.controllers.accountFragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lifeorganiser.R;
import com.example.lifeorganiser.src.Models.Exceptions.UserManagerException;
import com.example.lifeorganiser.src.Models.user.UserManager;


public class AddAccountDialogFragment extends DialogFragment {

    private UserManager userManager;

    private EditText accountNameEditText;
    private EditText accountAmountEditText;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        this.userManager = UserManager.getInstance();

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.fragment_add_account_dialog, null);

        this.accountNameEditText = (EditText) v.findViewById(R.id.addAccountName);
        this.accountAmountEditText = (EditText) v.findViewById(R.id.addAccountAmount);

        dialogBuilder.setView(v);

        dialogBuilder.setPositiveButton("save", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = AddAccountDialogFragment.this.accountNameEditText.getText().toString();
                double amount = Double.parseDouble(AddAccountDialogFragment.this.accountAmountEditText.getText().toString());

                try {
                    AddAccountDialogFragment.this.userManager.addDebitAccounts(name, amount);
                } catch (UserManagerException e) {
                    Toast.makeText(getActivity().getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

                AccountsFragment accountsFragment = new AccountsFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentLayout, accountsFragment);
                transaction.commit();
            }
        });

        dialogBuilder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });

        return dialogBuilder.create();
    }
}
