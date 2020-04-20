package com.android.bedsidechats.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.bedsidechats.R;
import com.android.bedsidechats.data.DeckCategoryAdapter;
import com.android.bedsidechats.data.LanguageAdapter;
import com.android.bedsidechats.data.ProviderAdapter;
import com.android.bedsidechats.data.SavedCardAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ProviderFragment extends Fragment implements View.OnClickListener {
    public String mLanguage = "English";
    private String mUsername = "";
    private String mEmail = "";
    private FirebaseFirestore mDatabase;
    //public ArrayList<String> providerOptions;
    //public RecyclerView mProviders;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private static String TAG = "PRVDR_FGMT";

    DeckCategoryAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    private ArrayList<String> mDecks;
    //private HashMap<String, String> mDecks;

    Button expand, collapse;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v;
        Activity activity = getActivity();

        if (activity != null)
        {
            int rotation = getActivity().getWindowManager().getDefaultDisplay().getRotation();
            if (rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270) {
                v = inflater.inflate(R.layout.fragment_deck_categories, container, false);
            } else {
                v = inflater.inflate(R.layout.fragment_deck_categories, container, false);
            }
        }
        else{
            v = inflater.inflate(R.layout.fragment_deck_categories, container, false);
        }
        v = inflater.inflate(R.layout.fragment_deck_categories, container, false);

        mDatabase = FirebaseFirestore.getInstance();
        //providerOptions =  new ArrayList<>();

        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        mLanguage =  getArguments().getString("Language") != null ? getArguments().getString("Language") : "English";
        mEmail = getArguments().getString("Email") != null ? getArguments().getString("Email") : "";
        mUsername = getArguments().getString("Username") != null ? getArguments().getString("Username") : "";

        expListView = (ExpandableListView) v.findViewById(R.id.deck_categories_list);

        expand = (Button) v.findViewById(R.id.expand_button);
        collapse = (Button) v.findViewById(R.id.collapse_button);

        expand.setOnClickListener(this);
        collapse.setOnClickListener(this);

        //mProviders = v.findViewById(R.id.providers_list);
       // if (mProviders != null){
            mLinearLayoutManager =  new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        //    mProviders.setLayoutManager(mLinearLayoutManager);
            //getListOfProviders(mLanguage);
            getCategoriesAndDecks(mLanguage);
        //}


        return v;
    }

    @Override
    public void onClick(View view) {
        Activity activity = getActivity();

        if (activity != null) {
            switch (view.getId()) {
                case R.id.expand_button:
                    expandAll();
                    break;
                case R.id.collapse_button:
                    collapseAll();
                    break;
                default:
                    break;
            }
        }
    }

//    public void getListOfProviders(String language){
//        mDatabase.collection("languages").document(language).collection("decks").get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                providerOptions.add(document.getId());
//                                mAdapter = new ProviderAdapter(getActivity(), providerOptions, getActivity().getSupportFragmentManager(), mLanguage, mEmail, mUsername);
//                                mProviders.setAdapter(mAdapter);
//                                Log.d(TAG, document.getId() + " => " + document.getData());
//                            }
//                        } else {
//                            Log.d(TAG, "Error getting documents: ", task.getException());
//                        }
//                    }
//                });
//    }

        public void getCategoriesAndDecks(String language){
        mDatabase.collection("languages").document(language).collection("categories").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot category : task.getResult()) {
                                String deck_category = category.getId();
                                listDataHeader.add(deck_category.toUpperCase());

                                mDatabase.collection("languages").document(language).collection("categories").document(deck_category).collection("decks").get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            mDecks = new ArrayList<>();
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                mDecks.add(document.getId().toUpperCase());
                                            }
                                            listDataChild.put(deck_category.toUpperCase(), mDecks);
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                            }
                            listAdapter = new DeckCategoryAdapter(getContext(), listDataHeader, listDataChild, mEmail, mUsername, mLanguage, getActivity().getSupportFragmentManager());

                            // setting list adapter
                            expListView.setAdapter(listAdapter);
                        } else {
                            Log.d(TAG, "Error getting categories: ", task.getException());
                        }
                    }
                });
    }

//    public int getProviderListSize(){
//        return providerOptions.size();
//    }

    public String getLanguage(){
        return mLanguage;
    }

//    public RecyclerView getRecyclerView(){
//        return mProviders;
//    }

//    public ArrayList<String> getProviderList(){
//        return providerOptions;
//    }

    //method to expand all groups
    private void expandAll() {
        int count = listAdapter.getGroupCount();
        for (int i = 0; i < count; i++){
            expListView.expandGroup(i);
        }
    }

    //method to collapse all groups
    private void collapseAll() {
        int count = listAdapter.getGroupCount();
        for (int i = 0; i < count; i++){
            expListView.collapseGroup(i);
        }
    }
}
