package com.example.lifeorganiser.src.controllers.todoFragments;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.lifeorganiser.R;
import com.example.lifeorganiser.src.Models.events.TODOEvent;
import com.example.lifeorganiser.src.Models.user.UserManager;


public class AddTODODialogFragment extends DialogFragment {

    private UserManager userManager = UserManager.getInstance();

    private EditText todoNameEditText;
    private EditText todoDescriptionEditText;



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());

        Bundle bundle = getArguments();
        final TODOEvent.Type type = (TODOEvent.Type) bundle.get("type");

        final LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.fragment_add_tododialog, null);

        this.todoNameEditText = (EditText) v.findViewById(R.id.addTODOName);
        this.todoDescriptionEditText = (EditText) v.findViewById(R.id.addTODODescription);

        if (bundle.containsKey("name")){
            this.todoNameEditText.setText(bundle.getString("name"));
        }

        if (bundle.containsKey("description")){
            this.todoDescriptionEditText.setText(bundle.getString("description"));
        }

        dialogBuilder.setView(v);

        dialogBuilder.setPositiveButton("save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                TODOEvent todo = new TODOEvent(AddTODODialogFragment.this.todoNameEditText.getText().toString(),
                        AddTODODialogFragment.this.todoDescriptionEditText.getText().toString());

                AddTODODialogFragment.this.userManager.addTodo(type, todo);

                TODOFragment todoFragment = new TODOFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentLayout, todoFragment);
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
