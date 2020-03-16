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
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.bedsidechats.R;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class HomeFragment extends Fragment implements View.OnClickListener {
    private String mLanguage = "";
    private String mProvider = "";
    private String mUsername = "";
    private String mSavedCards = "";
    private FirebaseAuth mAuth;
    private TextView lastDeck;
    private FirebaseFirestore mDatabase;
    private static String TAG = "LOGIN_FGMT";
    private static final int RC_SIGN_IN = 9001;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v;
        Activity activity = getActivity();

        if (activity != null)
        {
            int rotation = getActivity().getWindowManager().getDefaultDisplay().getRotation();
            if (rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270) {
                v = inflater.inflate(R.layout.activity_home, container, false);
            } else {
                v = inflater.inflate(R.layout.activity_home, container, false);
            }
        }
        else{
            v = inflater.inflate(R.layout.activity_home, container, false);
        }
        v = inflater.inflate(R.layout.activity_home, container, false);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseFirestore.getInstance();

        Button cardsButton = v.findViewById(R.id.cards_button_home_port);
        if (cardsButton != null) {
            cardsButton.setOnClickListener(this);
        }
        Button savedButton = v.findViewById(R.id.saved_button_home_port);
        if (savedButton != null) {
            savedButton.setOnClickListener(this);
        }

        Button providerButton = v.findViewById(R.id.provider_button_home_port);
        if (providerButton != null) {
            providerButton.setOnClickListener(this);
        }

        String email = getArguments().getString("Email");
        mUsername = getArguments().getString("Username");
        getUserPreferences(email, v);

        return v;
    }
    // DOUBLE FOR TWO ORIENTATIONS OR CHECK ORIENTATION? B/C NEW NAMING SCHEME
    @Override
    public void onClick(View view) {
        Activity activity = getActivity();

        if (activity != null) {
            switch (view.getId()) {
                case R.id.cards_button_home_port:
                    if(mProvider == ""){
                        Toast.makeText(activity, "You have no recently selected deck!",
                                Toast.LENGTH_SHORT).show();
                    }else {
                        FragmentManager fragmentManager = getFragmentManager();
                        Fragment fragment = new CardsFragment();
                        Bundle args = new Bundle();
                        args.putString("Language", mLanguage);
                        args.putString("Provider", mProvider);
                        fragment.setArguments(args);
                        if (fragmentManager != null) {
                            fragmentManager.beginTransaction()
                                    .replace(R.id.fragment_container, fragment)
                                    .addToBackStack("home_fragment")
                                    .commit();
                        }
                    }
                    break;
                case R.id.saved_button_home_port:
                    if(mProvider == ""){
                        Toast.makeText(activity, "You have no saved questions!",
                                Toast.LENGTH_SHORT).show();
                    }else {
                        FragmentManager fragmentManager = getFragmentManager();
                        Fragment fragment = new SavedFragment();
                        //OR CARD FRAGMENT WITH FAVORITES PASSED IN
                        Bundle args = new Bundle();
                        args.putString("Username", mUsername);
                        args.putString("Language", mLanguage);
                        args.putString("Provider", mProvider);
                        args.putString("Saved_Cards", mSavedCards);
                        fragment.setArguments(args);
                        if (fragmentManager != null) {
                            fragmentManager.beginTransaction()
                                    .replace(R.id.fragment_container, fragment)
                                    .addToBackStack("home_fragment")
                                    .commit();
                        }
                    }
                    break;
                case R.id.provider_button_home_port:
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    Fragment fragment = new ProviderFragment();
                    Bundle args = new Bundle();
                    args.putString("Username", mUsername);
                    args.putString("Language", mLanguage);
                    fragment.setArguments(args);
                    if (fragmentManager != null) {
                        fragmentManager.beginTransaction()
                                .replace(R.id.fragment_container, fragment)
                                .addToBackStack("home_fragment")
                                .commit();
                    }
                    break;
            }
        }
    }

    public void getUserPreferences(String email, View v){
        mDatabase.collection("patients").document(email).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            mLanguage = (task.getResult().getString("language") != null ? task.getResult().getString("language") : "English");
                            mProvider = (task.getResult().getString("recent_deck") != null ? task.getResult().getString("recent_deck") : "");
                            mSavedCards = (task.getResult().getString("saved_cards") != null ? task.getResult().getString("saved_cards") : "");
                            lastDeck = v.findViewById(R.id.lastDeck_textView_home_port);
                            if(mProvider != "") {
                                lastDeck.setText(lastDeck.getText().toString() + " " + mProvider);
                            }
                            else{
                                lastDeck.setText("No recently used deck");
                            }
                        } else {
                            Log.d(TAG, "Error getting data: ", task.getException());
                        }
                    }
                });
    }
}