package com.android.bedsidechats.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.android.bedsidechats.R;

public class GuestFragment extends Fragment implements View.OnClickListener {
    private String mLanguage;
    private String mProvider;
    private static String TAG = "GUEST_FGMT";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v;
        Activity activity = getActivity();

        if (activity != null)
        {
            int rotation = getActivity().getWindowManager().getDefaultDisplay().getRotation();
            if (rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270) {
                v = inflater.inflate(R.layout.activity_guest, container, false);
            } else {
                v = inflater.inflate(R.layout.activity_guest, container, false);
            }
        }
        else{
            v = inflater.inflate(R.layout.activity_instructions, container, false);
        }
        v = inflater.inflate(R.layout.activity_instructions, container, false);

        mProvider =  getArguments().getString("Provider");
        mLanguage = getArguments().getString("Language");

        Button continueButton = v.findViewById(R.id.continueGuest_button_guest_port);
        if (continueButton != null) {
            continueButton.setOnClickListener(this);
        }
        Button loginButton = v.findViewById(R.id.login_button_guest_port);
        if (loginButton != null) {
            loginButton.setOnClickListener(this);
        }
        Button signupButton = v.findViewById(R.id.signup_button_guest_port);
        if (signupButton != null) {
            signupButton.setOnClickListener(this);
        }
        return v;
    }

    @Override
    public void onClick(View view) {
        Activity activity = getActivity();

        if (activity != null) {
            switch (view.getId()) {
                case R.id.continueGuest_button_guest_port:
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    Fragment fragment = new HomeFragment();
                    Bundle args = new Bundle();
                    args.putString("Language", mLanguage);
                    args.putString("Provider", mProvider);
                    fragment.setArguments(args);
                    if (fragmentManager != null) {
                        fragmentManager.beginTransaction()
                                .replace(R.id.fragment_container, fragment)
                                .addToBackStack("provider_fragment")
                                .commit();
                    }
                    break;
                case R.id.login_button_guest_port:
                    fragmentManager = getActivity().getSupportFragmentManager();
                    fragment = new LoginFragment();
                    args = new Bundle();
                    args.putString("Language", mLanguage);
                    args.putString("Provider", mProvider);
                    fragment.setArguments(args);
                    if (fragmentManager != null) {
                        fragmentManager.beginTransaction()
                                .replace(R.id.fragment_container, fragment)
                                .addToBackStack("provider_fragment")
                                .commit();
                    }
                    break;
                case R.id.signup_button_guest_port:
                    fragmentManager = getActivity().getSupportFragmentManager();
                    fragment = new SignupFragment();
                    args = new Bundle();
                    args.putString("Language", mLanguage);
                    args.putString("Provider", mProvider);
                    fragment.setArguments(args);
                    if (fragmentManager != null) {
                        fragmentManager.beginTransaction()
                                .replace(R.id.fragment_container, fragment)
                                .addToBackStack("provider_fragment")
                                .commit();
                    }
                    break;
            }
        }
    }

}
