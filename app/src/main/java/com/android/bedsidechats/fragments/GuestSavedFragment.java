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
import androidx.fragment.app.FragmentManager;

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

public class GuestSavedFragment extends Fragment implements View.OnClickListener {
    private String mLanguageChoice = "";
    private String mProviderChoice = "";
    private String mCategory = "";
    private String mUsername = "";
    private String mEmail = "";
    private String mSavedCards = "";
    SavedCardAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    private TreeMap<String, String> mSavedQuestions;
    private TreeMap<String, String> mSavedNotes;

    Button expand, collapse, done;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v;

        // TODO handle rotation
        v = inflater.inflate(R.layout.fragment_saved_cards, container, false);

        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        mSavedQuestions = new TreeMap<>();
        mSavedNotes = new TreeMap<>();

        mLanguageChoice = getArguments() != null ? getArguments().getString("Language") : "";
        mProviderChoice = getArguments().getString("Provider");
        mCategory = getArguments().getString("Category");
        mUsername = getArguments().getString("Username");
        mEmail = getArguments().getString("Email") != null ? getArguments().getString("Email") : "";
        mSavedQuestions = getArguments().getSerializable("Questions") != null ? (TreeMap) getArguments().getSerializable("Questions") : new TreeMap<>();
        mSavedNotes = getArguments().getSerializable("Notes") != null ? (TreeMap) getArguments().getSerializable("Notes") : new TreeMap<>();
        mSavedCards = getArguments().getString("Saved_Cards");

        // get the listview
        expListView = v.findViewById(R.id.saved_cards_list);

        expand = v.findViewById(R.id.expand_button);
        collapse = v.findViewById(R.id.collapse_button);
        done = v.findViewById(R.id.done_button);

        expand.setOnClickListener(this);
        collapse.setOnClickListener(this);
        done.setOnClickListener(this);

        // preparing list data
        prepareGuestData();

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
                case R.id.done_button:
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    Fragment fragment = new GuestHomeFragment();
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
                                .addToBackStack("guestSaved_fragment")
                                .commit();
                    }
                    break;
            }
        }
    }

    /*
     * Preparing the guest saved card and notes data
     */
    private void prepareGuestData() {
        for (Map.Entry<String, String> questions : mSavedQuestions.entrySet()) {
            listDataHeader.add(questions.getKey().toUpperCase() + ": " + questions.getValue());
            if (mSavedNotes != null) {
                List<String> child = new ArrayList<String>();
                child.add(mSavedNotes.get("n" + questions.getKey().substring(1)) != null ? mSavedNotes.get("n" + questions.getKey().substring(1)) : getString(R.string.no_saved_notes));
                listDataChild.put(questions.getKey().toUpperCase() + ": " + questions.getValue(), child);
            }
        }
        listAdapter = new SavedCardAdapter(getContext(), listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);
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
