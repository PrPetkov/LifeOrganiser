package com.example.lifeorganiser.src.controllers.shoppingListFragments;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.lifeorganiser.R;


public class ShoppingListGetNameDialogFragment extends DialogFragment {

    EditText nameEditText;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.fragment_shopping_list_get_name_dialog, null);

        this.nameEditText = (EditText) v.findViewById(R.id.shoppingListGetName);

        dialogBuilder.setView(v);

        dialogBuilder.setPositiveButton("save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = ShoppingListGetNameDialogFragment.this.nameEditText.getText().toString();

                Intent intent = new Intent();
                intent.putExtra("name", name);

                getTargetFragment().onActivityResult(getTargetRequestCode(), 1, intent);

                ShoppingListFragment fragment = new ShoppingListFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentLayout, fragment);
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
