package com.android.bedsidechats.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.bedsidechats.R;
//import com.android.bedsidechats.data.CardAdapter;
import com.android.bedsidechats.data.CardAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.TreeMap;

public class CardsFragment extends Fragment implements View.OnClickListener {
    private String mLanguageChoice = "";
    private String mProviderChoice = "";
    private FirebaseFirestore mDatabase;
    private HashMap<String, String> mQuestionList;
    private RecyclerView mCards;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private static String TAG = "CRD_FGMT";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v;

        // TODO handle rotation
        v = inflater.inflate(R.layout.activity_cards, container, false);

        mDatabase = FirebaseFirestore.getInstance();
        mQuestionList = new HashMap<>();

        mLanguageChoice = getArguments() != null ? getArguments().getString("Language") : "";
        mProviderChoice = getArguments().getString("Provider");
//        Button leftButton = v.findViewById(R.id.left_button_card_port);
//        if (leftButton != null) {
//            leftButton.setOnClickListener(this);
//        }
//
//        Button rightButton = v.findViewById(R.id.right_button_card_port);
//        if (rightButton != null) {
//            rightButton.setOnClickListener(this);
//        }

        mCards = v.findViewById(R.id.cards_list);
        if (mCards != null){
            mLinearLayoutManager =  new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            mCards.setLayoutManager(mLinearLayoutManager);
            getCardDeck();
        }

        return v;
    }

    @Override
    public void onClick(View view) {
        Activity activity = getActivity();

        if (activity != null) {
            switch (view.getId()) {
                case R.id.login_button:
                    FragmentManager fragmentManager = getFragmentManager();
                    Fragment fragment = new LoginFragment();
                    if (fragmentManager != null) {
                        fragmentManager.beginTransaction()
                                .replace(R.id.fragment_container, fragment)
                                .addToBackStack("language_fragment")
                                .commit();
                    }
                    break;
//                case R.id.english_button:
//                    mLanguage = "English";
//                    Log.d(TAG, "Language: " + mLanguage);
//                    fragmentManager = getFragmentManager();
//                    fragment = new ProviderFragment();
//                    Bundle args = new Bundle();
//                    args.putString("Language", mLanguage);
//                    fragment.setArguments(args);
//                    if (fragmentManager != null) {
//                        fragmentManager.beginTransaction()
//                                .replace(R.id.fragment_container, fragment)
//                                .addToBackStack("language_fragment")
//                                .commit();
//                    }
//                    break;
//                case R.id.spanish_button:
//                    mLanguage = "Spanish";
//                    Log.d(TAG, "Language: " + mLanguage);
//                    fragmentManager = getFragmentManager();
//                    fragment = new ProviderFragment();
//                    args = new Bundle();
//                    args.putString("Language", mLanguage);
//                    fragment.setArguments(args);
//                    if (fragmentManager != null) {
//                        fragmentManager.beginTransaction()
//                                .replace(R.id.fragment_container, fragment)
//                                .addToBackStack("language_fragment")
//                                .commit();
//                    }
//                    break;
            }
        }
    }

    public void getCardDeck(){
        mDatabase.collection("languages").document(mLanguageChoice).collection("decks").document(mProviderChoice).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            mQuestionList = (HashMap) task.getResult().getData();
                            mQuestionList.remove("instructions");
                            TreeMap<String, String> mQuestionMap = new TreeMap<>(mQuestionList);
                            Log.d(TAG, "" + mQuestionMap);
                            mAdapter = new CardAdapter(getActivity(), mQuestionMap, getFragmentManager(), mLanguageChoice, mProviderChoice);
                            mCards.setAdapter(mAdapter);
                            Log.d(TAG, task.getResult().getId() + " => " + task.getResult().getData());
                        }
                        else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}
