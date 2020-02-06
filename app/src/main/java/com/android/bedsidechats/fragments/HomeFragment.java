package com.android.bedsidechats.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.bedsidechats.R;
//import com.google.android.gms.common.api.ApiException;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.AuthCredential;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.GoogleAuthProvider;
//import com.google.firebase.firestore.DocumentReference;
//import com.google.firebase.firestore.DocumentSnapshot;
//import com.google.firebase.firestore.FirebaseFirestore;

public class HomeFragment extends Fragment implements View.OnClickListener {
    //private FirebaseAuth mAuth;
    //private FirebaseFirestore mDatabase;
    private static String TAG = "LOGIN_FGMT";
    private static final int RC_SIGN_IN = 9001;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v;

        // TODO handle rotation
        v = inflater.inflate(R.layout.activity_home, container, false);

        //mAuth = FirebaseAuth.getInstance();
        //mDatabase = FirebaseFirestore.getInstance();

        Button cardsButton = v.findViewById(R.id.cards_button_home_port);
        if (cardsButton != null) {
            cardsButton.setOnClickListener(this);
        }
        Button favoritesButton = v.findViewById(R.id.favorites_button_home_port);
        if (favoritesButton != null) {
            favoritesButton.setOnClickListener(this);
        }

        Button providerButton = v.findViewById(R.id.provider_button_home_port);
        if (providerButton != null) {
            providerButton.setOnClickListener(this);
        }

        return v;
    }
    // DOUBLE FOR TWO ORIENTATIONS OR CHECK ORIENTATION? B/C NEW NAMING SCHEME
    @Override
    public void onClick(View view) {
        Activity activity = getActivity();

        if (activity != null) {
            switch (view.getId()) {
                case R.id.cards_button_home_port:
//                    FragmentManager fragmentManager = getFragmentManager();
//                    Fragment fragment = new CardsFragment();
//                    if (fragmentManager != null) {
//                        fragmentManager.beginTransaction()
//                                .replace(R.id.fragment_container, fragment)
//                                .addToBackStack("home_fragment")
//                                .commit();
//                    }
                    break;
                case R.id.favorites_button_home_port:
//                    fragmentManager = getFragmentManager();
//                    fragment = new FavoritesFragment();
                    //OR CARD FRAGMENT WITH FAVORITES PASSED IN
//                    if (fragmentManager != null) {
//                        fragmentManager.beginTransaction()
//                                .replace(R.id.fragment_container, fragment)
//                                .addToBackStack("home_fragment")
//                                .commit();
//                    }
                    break;
                case R.id.provider_button_home_port:
//                    fragmentManager = getFragmentManager();
//                    fragment = new ProviderFragment();
//                    if (fragmentManager != null) {
//                        fragmentManager.beginTransaction()
//                                .replace(R.id.fragment_container, fragment)
//                                .addToBackStack("home_fragment")
//                                .commit();
//                    }
                    break;
            }
        }
    }

}