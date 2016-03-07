package com.example.lifeorganiser.src.controllers;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lifeorganiser.R;
import com.example.lifeorganiser.src.Models.user.UserManager;

public class AccountViewFragment extends Fragment {

    private UserManager userManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.userManager = UserManager.getInstance();

        View v = inflater.inflate(R.layout.fragment_account_view, container, false);

        return v;
    }

}
