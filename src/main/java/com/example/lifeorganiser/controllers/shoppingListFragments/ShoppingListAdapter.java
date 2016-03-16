package com.example.lifeorganiser.src.controllers.shoppingListFragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lifeorganiser.R;
import com.example.lifeorganiser.src.Models.events.ShoppingListEntry;
import com.example.lifeorganiser.src.Models.user.UserManager;

import java.util.List;


public class ShoppingListAdapter extends ArrayAdapter<ShoppingListEntry> {

    private class ViewHolder {
        private CheckBox checkBox;
        private EditText name;
        private EditText editText;

        public ViewHolder(View v) {
            this.checkBox = (CheckBox) v.findViewById(R.id.entryListViewCheckBox);
            this.name = (EditText) v.findViewById(R.id.entryListViewTextView);
            this.editText = (EditText) v.findViewById(R.id.entryListViewEditText);
        }
    }

    private UserManager userManager;

    private List<ShoppingListEntry> entries;

    public ShoppingListAdapter(Context context, List<ShoppingListEntry> objects) {
        super(context, R.layout.shopping_entrie_list_view, objects);

        this.userManager = UserManager.getInstance();
        this.entries = objects;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ShoppingListEntry entry = entries.get(position);

        View v;
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.shopping_entrie_list_view, parent, false);

            holder = new ViewHolder(v);
            v.setTag(holder);
        }else {
            v = convertView;
            holder = (ViewHolder) v.getTag();
        }

        final CheckBox isTakenCheckBox = holder.checkBox;
        final EditText name = holder.name;
        final EditText priceEditText = holder.editText;

        isTakenCheckBox.setChecked(entry.isTaken());

        isTakenCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShoppingListAdapter.this.userManager.updateShoppingListEntry(entry, entry.getName(), entry.getPrice(), isTakenCheckBox.isChecked());
            }
        });

        name.setText(entry.getName());
        name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    return;
                }

                String editName = name.getText().toString();

                if (!entry.getName().equals(editName)) {
                    ShoppingListAdapter.this.userManager.updateShoppingListEntry(entry, editName, entry.getPrice(), entry.isTaken());
                }
            }
        });

        priceEditText.setText(String.valueOf(entry.getPrice()));
        priceEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    return;
                }

                double newPrice = 0;

                if (!priceEditText.getText().toString().equals("")) {
                    newPrice = Double.parseDouble(priceEditText.getText().toString());
                }

                if (entry.getPrice() != newPrice) {
                    ShoppingListAdapter.this.userManager.updateShoppingListEntry(entry, entry.getName(), newPrice, entry.isTaken());
                }
            }
        });

        return v;
    }
}
