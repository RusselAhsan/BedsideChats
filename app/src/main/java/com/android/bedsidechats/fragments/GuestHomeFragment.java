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

import java.util.TreeMap;

public class GuestHomeFragment extends Fragment implements View.OnClickListener {
    private String mSavedCards = "";
    private String mCategory = "";
    private String mProviderChoice = "";
    private String mLanguageChoice = "";
    private TreeMap<String, String> mSavedQuestions;
    private TreeMap<String, String> mSavedNotes;
    private TextView lastDeck;
    private static String TAG = "GUEST_HOME_FGMT";
    private static final int RC_SIGN_IN = 9001;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v;
        Activity activity = getActivity();

        if (activity != null)
        {
            int rotation = getActivity().getWindowManager().getDefaultDisplay().getRotation();
            if (rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270) {
                v = inflater.inflate(R.layout.activity_guest_home, container, false);
            } else {
                v = inflater.inflate(R.layout.activity_guest_home, container, false);
            }
        }
        else{
            v = inflater.inflate(R.layout.activity_guest_home, container, false);
        }
        v = inflater.inflate(R.layout.activity_guest_home, container, false);

        mCategory = getArguments().getString("Category") != null ? getArguments().getString("Category") : "";
        mProviderChoice = getArguments().getString("Provider") != null ? getArguments().getString("Provider") : "";
        mLanguageChoice = getArguments().getString("Language") != null ? getArguments().getString("Language") : "";
        mSavedQuestions = getArguments().getSerializable("Questions") != null ? (TreeMap) getArguments().getSerializable("Questions") : new TreeMap<>();
        mSavedNotes = getArguments().getSerializable("Notes") != null ? (TreeMap) getArguments().getSerializable("Notes") : new TreeMap<>();

        Button cardsButton = v.findViewById(R.id.cards_button_guestHome_port);
        if (cardsButton != null) {
            cardsButton.setOnClickListener(this);
        }
        Button savedButton = v.findViewById(R.id.saved_button_guestHome_port);
        if (savedButton != null) {
            savedButton.setOnClickListener(this);
        }

        Button providerButton = v.findViewById(R.id.provider_button_guestHome_port);
        if (providerButton != null) {
            providerButton.setOnClickListener(this);
        }

        lastDeck = v.findViewById(R.id.lastDeck_textView_guestHome_port);
        lastDeck.setText(lastDeck.getText().toString() + " " + mProviderChoice);

        return v;
    }

    @Override
    public void onClick(View view) {
        Activity activity = getActivity();

        if (activity != null) {
            switch (view.getId()) {
                case R.id.cards_button_guestHome_port:
                    if(mProviderChoice == ""){
                        Toast.makeText(activity, R.string.no_recent_deck,
                                Toast.LENGTH_SHORT).show();
                    }else {
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        Fragment fragment = new CardsFragment();
                        Bundle args = new Bundle();
                        args.putString("Language", mLanguageChoice);
                        args.putString("Provider", mProviderChoice);
                        args.putString("Category", mCategory);
                        fragment.setArguments(args);
                        if (fragmentManager != null) {
                            fragmentManager.beginTransaction()
                                    .replace(R.id.fragment_container, fragment)
                                    .addToBackStack("guestHome_fragment")
                                    .commit();
                        }
                    }
                    break;
                case R.id.saved_button_guestHome_port:
                    if(mSavedQuestions.size() == 0){
                        Toast.makeText(activity, R.string.no_saved_questions,
                                Toast.LENGTH_SHORT).show();
                    }else {
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        Fragment fragment = new GuestSavedFragment();
                        //OR CARD FRAGMENT WITH FAVORITES PASSED IN
                        Bundle args = new Bundle();
                        args.putString("Language", mLanguageChoice);
                        args.putString("Provider", mProviderChoice);
                        args.putString("Category", mCategory);
                        args.putSerializable("Questions", mSavedQuestions);
                        args.putSerializable("Notes", mSavedNotes);
                        fragment.setArguments(args);
                        if (fragmentManager != null) {
                            fragmentManager.beginTransaction()
                                    .replace(R.id.fragment_container, fragment)
                                    .addToBackStack("guestHome_fragment")
                                    .commit();
                        }
                    }
                    break;
                case R.id.provider_button_guestHome_port:
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    Fragment fragment = new ProviderFragment();
                    Bundle args = new Bundle();
                    args.putString("Language", mLanguageChoice);
                    fragment.setArguments(args);
                    if (fragmentManager != null) {
                        fragmentManager.beginTransaction()
                                .replace(R.id.fragment_container, fragment)
                                .addToBackStack("guestHome_fragment")
                                .commit();
                    }
                    break;
            }
        }
    }

}