package com.android.bedsidechats.data;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.bedsidechats.R;
import com.android.bedsidechats.fragments.ProviderFragment;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import java.util.ArrayList;


public class LanguageAdapter extends RecyclerView.Adapter<LanguageAdapter.LanguageViewHolder> {
    private String TAG = "LANG_ADPTR";;
    private static FirebaseFirestore mDatabase;
    private ArrayList<String> mLanguages;
    private Activity mContext;
    private FragmentManager mFragmentManager;

    public LanguageAdapter(Activity context, ArrayList<String> languages, FragmentManager fragmentManager) {
        mLanguages = languages;
        mDatabase = FirebaseFirestore.getInstance();
        mContext = context;
        mFragmentManager = fragmentManager;
    }

    @Override
    public LanguageViewHolder onCreateViewHolder(ViewGroup group, int i){
        View view = LayoutInflater.from(group.getContext())
                .inflate(R.layout.language_holder, group, false);

        return new LanguageViewHolder(view, mContext, mFragmentManager);
    }

    @Override
    public void onBindViewHolder(LanguageViewHolder holder, int position) {
        Log.d(TAG, "inside onBindViewHolder");
        holder.mLanguageButton.setText(mLanguages.get(position));
    }

    @Override
    public int getItemCount() {
        return mLanguages != null ? mLanguages.size() : 0;
    }

    public class LanguageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        protected Button mLanguageButton;
        private String TAG = "LANG_HLDR";
        public String mLanguageChoice;
        private Activity mContext;
        private FragmentManager mFragmentManager;

        public LanguageViewHolder(View itemView, Activity context, FragmentManager fragmentManager) {
            super(itemView);
            mLanguageButton = itemView.findViewById(R.id.language_button_language_port);
            mLanguageButton.setOnClickListener(this);
            mContext = context;
            mFragmentManager = fragmentManager;
        }

        @Override
        public void onClick(View v){
            mLanguageChoice = mLanguageButton.getText().toString();
            updateAppLanguage(mLanguageChoice);
            Fragment fragment = new ProviderFragment();
            Bundle args = new Bundle();
            args.putString("Language", mLanguageChoice);
            fragment.setArguments(args);
            if (mFragmentManager != null) {
                        mFragmentManager.beginTransaction()
                                .replace(R.id.fragment_container, fragment)
                                .addToBackStack("language_fragment")
                                .commit();
                    }

        }

        public void updateAppLanguage(String string){
            Log.d(TAG, "New Language: " + string);
        }
    }

}
