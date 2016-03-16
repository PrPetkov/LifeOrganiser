package com.example.lifeorganiser.src.controllers.shoppingListFragments;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lifeorganiser.R;
import com.example.lifeorganiser.src.Models.events.ShoppingList;
import com.example.lifeorganiser.src.Models.user.UserManager;

public class ShoppingListViewFragment extends Fragment {

    private UserManager userManager;

    private TextView shoppingListName;
    private ListView enties;
    private TextView add;
    private LinearLayout totalLayout;
    private TextView sumTextView;
    private TextView selectedSumtextView;
    private Button calculate;
    private Button pay;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.userManager = UserManager.getInstance();
        final Bundle arguments = getArguments();
        final ShoppingList shoppingList = (ShoppingList) arguments.get("entries");

        View v = inflater.inflate(R.layout.fragment_shopping_list_view, container, false);

        this.shoppingListName = (TextView) v.findViewById(R.id.shoppingListViewName);
        this.enties = (ListView) v.findViewById(R.id.shoppingListViewListView);
        this.totalLayout = (LinearLayout) v.findViewById(R.id.shoppingListViewTotalLayout);
        this.sumTextView = (TextView) v.findViewById(R.id.shoppingListViewTotalSum);
        this.selectedSumtextView = (TextView) v.findViewById(R.id.shoppingListViewSelectedSum);
        this.calculate = (Button) v.findViewById(R.id.shoppingListViewCalcButton);
        this.pay = (Button) v.findViewById(R.id.shoppingListViewPayButton);
        this.add = (TextView) v.findViewById(R.id.shoppingListViewAdd);

        this.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShoppingListViewFragment.this.userManager.addShoppingListEntry(shoppingList, "", 0, false);

                ShoppingListViewFragment fragment = new ShoppingListViewFragment();
                fragment.setArguments(arguments);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentLayout, fragment);
                transaction.commit();
            }
        });

        this.calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShoppingListViewFragment.this.sumTextView.setText(shoppingList.getAmountOfAllEntries() + "");
                ShoppingListViewFragment.this.selectedSumtextView.setText(shoppingList.getAmountOfAllTakenEnties() + "");
                ShoppingListViewFragment.this.totalLayout.setVisibility(View.VISIBLE);
            }
        });

        this.pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                PayShoppingListFragment fragment = new PayShoppingListFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("shoppingList", shoppingList);
                fragment.setArguments(bundle);

                transaction.replace(R.id.fragmentLayout, fragment);
                transaction.addToBackStack(null);

                transaction.commit();
            }
        });

        this.shoppingListName.setText(shoppingList.getName());

        ShoppingListAdapter adapter = new ShoppingListAdapter(getActivity(), shoppingList.getEntries());
        this.enties.setAdapter(adapter);

        return v;
    }

}
