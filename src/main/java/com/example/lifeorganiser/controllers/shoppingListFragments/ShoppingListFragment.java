package com.example.lifeorganiser.src.controllers.shoppingListFragments;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lifeorganiser.R;
import com.example.lifeorganiser.src.Models.Exceptions.UserManagerException;
import com.example.lifeorganiser.src.Models.events.ShoppingList;
import com.example.lifeorganiser.src.Models.user.UserManager;

import java.util.ArrayList;

public class ShoppingListFragment extends Fragment {

    private UserManager userManager;

    private ListView shoppingListsListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        this.userManager = UserManager.getInstance();

        View v = inflater.inflate(R.layout.fragment_shopping_list, container, false);

        this.shoppingListsListView = (ListView) v.findViewById(R.id.shoppingFragmentListView);

        final ArrayList<ShoppingList> shoppingLists = this.userManager.getAllShoppingLists();
        final ArrayList<String> stringList = new ArrayList<>();

        for (ShoppingList list : shoppingLists){
            double price = list.getAmountOfAllEntries();
            String tag = list.getName();
            if (price > 0){
                tag += " " + price;
            }

            stringList.add(tag);
        }

        stringList.add(getString(R.string.add));

        ArrayAdapter adapter = new ArrayAdapter(getActivity().getApplicationContext(), R.layout.simple_listview_item, stringList);

        this.shoppingListsListView.setAdapter(adapter);

        this.shoppingListsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentManager fm = getFragmentManager();

                if (position == stringList.size() - 1) {
                    ShoppingListGetNameDialogFragment dialog = new ShoppingListGetNameDialogFragment();
                    dialog.setTargetFragment(ShoppingListFragment.this, 1);
                    dialog.show(fm, "getName");
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("entries", shoppingLists.get(position));

                    ShoppingListViewFragment fragment = new ShoppingListViewFragment();
                    fragment.setArguments(bundle);

                    FragmentTransaction transaction = fm.beginTransaction();
                    transaction.replace(R.id.fragmentLayout, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        String name = data.getStringExtra("name");

        try {
            this.userManager.addShoppingList(name, false);
        } catch (UserManagerException e) {
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
