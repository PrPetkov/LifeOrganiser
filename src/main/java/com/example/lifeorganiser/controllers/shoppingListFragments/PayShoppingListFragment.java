package com.example.lifeorganiser.src.controllers.shoppingListFragments;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.example.lifeorganiser.R;
import com.example.lifeorganiser.src.Models.Exceptions.IllegalAmountException;
import com.example.lifeorganiser.src.Models.accounts.Account;
import com.example.lifeorganiser.src.Models.accounts.DebitAccount;
import com.example.lifeorganiser.src.Models.events.PaymentEvent;
import com.example.lifeorganiser.src.Models.events.ShoppingList;
import com.example.lifeorganiser.src.Models.events.ShoppingListEntry;
import com.example.lifeorganiser.src.Models.user.UserManager;
import com.example.lifeorganiser.src.controllers.activities.MainActivity;

import java.util.ArrayList;
import java.util.Calendar;


public class PayShoppingListFragment extends Fragment {

    private UserManager userManager;

    private TextView shoppingListName;
    private RadioGroup radioGroup;
    private RadioButton totalRadio;
    private RadioButton selectedRadio;
    private RadioButton otherRadio;
    private TextView totalPriceText;
    private TextView selectedPriceText;
    private EditText otherPriceEditText;
    private Spinner accountPicker;
    private Button payButton;
    private Button cancelButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.userManager = UserManager.getInstance();

        final ShoppingList shoppingList = (ShoppingList) getArguments().get("shoppingList");

        View v =  inflater.inflate(R.layout.fragment_pay_shopping_list, container, false);

        this.shoppingListName = (TextView) v.findViewById(R.id.payShoppingListName);
        this.radioGroup = (RadioGroup) v.findViewById(R.id.payShoppingListRadioGroup);
        this.totalRadio = (RadioButton) v.findViewById(R.id.payShoppingListTotalRadio);
        this.selectedRadio = (RadioButton) v.findViewById(R.id.payShoppingListSelectedRadio);
        this.otherRadio = (RadioButton) v.findViewById(R.id.payShoppingListOtherRadio);
        this.totalPriceText = (TextView) v.findViewById(R.id.payShoppingListTotalText);
        this.selectedPriceText = (TextView) v.findViewById(R.id.payShoppingListSelectedText);
        this.otherPriceEditText = (EditText) v.findViewById(R.id.payShoppingListOtherEditText);
        this.accountPicker = (Spinner) v.findViewById(R.id.payShoppingListAccountPicker);
        this.payButton = (Button) v.findViewById(R.id.payShoppingListPayButton);
        this.cancelButton = (Button) v.findViewById(R.id.payShoppingListCancelButton);

        this.shoppingListName.setText(shoppingList.getName());
        this.totalPriceText.setText(shoppingList.getAmountOfAllEntries() +"");
        this.selectedPriceText.setText(shoppingList.getAmountOfAllTakenEnties() + "");

        ArrayList<String> accountStrings = new ArrayList<>();

        for (Account account : this.userManager.getDebitAccounts()){
            accountStrings.add(account.getAccountName());
        }

        SpinnerAdapter adapter = new ArrayAdapter<String>(getActivity(), R.layout.simple_listview_item, accountStrings);

        this.accountPicker.setAdapter(adapter);

        this.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        this.payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder description = new StringBuilder();

                for (ShoppingListEntry entry : shoppingList.getEntries()){
                    description.append(entry.getName());
                    description.append(" ");
                    description.append(entry.getPrice());
                    description.append((entry.isTaken()) ? ", marked as taken" : ", marked as not taken");
                }

                description.append(System.getProperty("line.separator"));

                double price = 0;

                switch (PayShoppingListFragment.this.radioGroup.getCheckedRadioButtonId()) {
                    case R.id.payShoppingListTotalRadio:
                        price = shoppingList.getAmountOfAllEntries();
                        break;
                    case R.id.payShoppingListSelectedRadio:
                        price = shoppingList.getAmountOfAllTakenEnties();
                        break;
                    case R.id.payShoppingListOtherRadio:
                        price = Double.parseDouble(PayShoppingListFragment.this.otherPriceEditText.getText().toString());
                        break;
                }

                int accountPos = PayShoppingListFragment.this.accountPicker.getSelectedItemPosition();
                DebitAccount account = PayShoppingListFragment.this.userManager.getDebitAccounts().get(accountPos);

                try {
                    PaymentEvent paymentEvent = new PaymentEvent(shoppingList.getName(), description.toString(), price, false, true, Calendar.getInstance());
                    PayShoppingListFragment.this.userManager.pay(account, paymentEvent);
                } catch (IllegalAmountException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        return v;
    }

}
