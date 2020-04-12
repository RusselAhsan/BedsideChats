package com.android.bedsidechats.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.android.bedsidechats.R;
import com.android.bedsidechats.data.SavedCardAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class SavedFragment extends Fragment implements View.OnClickListener {
    private String mLanguageChoice = "";
    private String mProviderChoice = "";
    private String mUsername = "";
    private String mEmail = "";
    private String mSavedCards = "";
    SavedCardAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    private FirebaseFirestore mDatabase;
    private HashMap<String, String> mQuestionList;

    Button expand, collapse;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v;

        // TODO handle rotation
        v = inflater.inflate(R.layout.fragment_saved_cards, container, false);

        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        mDatabase = FirebaseFirestore.getInstance();
        mQuestionList = new HashMap<>();

        mLanguageChoice = getArguments() != null ? getArguments().getString("Language") : "";
        mProviderChoice = getArguments().getString("Provider");
        mUsername = getArguments().getString("Username");
        mEmail = getArguments().getString("Email") != null ? getArguments().getString("Email") : "";
        mSavedCards = getArguments().getString("Saved_Cards");

        // get the listview
        expListView = (ExpandableListView) v.findViewById(R.id.saved_cards_list);

        expand = (Button) v.findViewById(R.id.expand_button);
        collapse = (Button) v.findViewById(R.id.collapse_button);

        expand.setOnClickListener(this);
        collapse.setOnClickListener(this);

        // preparing list data
        prepareListData();

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
            }
        }
    }

    /*
     * Preparing the list data
     */
    private void prepareListData() {

        mDatabase.collection("patients").document(mEmail)
                .collection("saved").document(mProviderChoice).collection("data")
                .document("questions").get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            mQuestionList = (HashMap) task.getResult().getData();
                            if(mQuestionList != null) {
                                TreeMap<String, String> mQuestionMap = new TreeMap<>(mQuestionList);
                                for (Map.Entry<String, String> questions : mQuestionMap.entrySet()) {
                                    listDataHeader.add(questions.getKey());
                                    List<String> child = new ArrayList<String>();
                                    child.add(questions.getValue());
                                    listDataChild.put(questions.getKey(), child);
                                }
                                listAdapter = new SavedCardAdapter(getContext(), listDataHeader, listDataChild);

                                // setting list adapter
                                expListView.setAdapter(listAdapter);
                            }
                        }
                    }
                });
    }

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
