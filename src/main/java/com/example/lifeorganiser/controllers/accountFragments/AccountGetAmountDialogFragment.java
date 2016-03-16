package com.example.lifeorganiser.src.controllers.accountFragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.lifeorganiser.R;

public class AccountGetAmountDialogFragment extends DialogFragment {

    private EditText amountEditText;

    @Override
    public Dialog onCreateDialog(Bundle savedInstance) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View v = inflater.inflate(R.layout.fragment_account_get_amount_dialog, null);
        this.amountEditText = (EditText) v.findViewById(R.id.getAmountDialogAmount);

        dialogBuilder.setView(v);

        dialogBuilder.setPositiveButton("save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                double amount = Double.parseDouble(AccountGetAmountDialogFragment.this.amountEditText.getText().toString());

                Intent data = new Intent();
                data.putExtra("amount", amount);

                getTargetFragment().onActivityResult(getTargetRequestCode(), 1, data);
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
