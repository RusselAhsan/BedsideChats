package com.android.bedsidechats.data;

import android.app.Activity;
import android.content.Intent;
import java.util.Locale;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.bedsidechats.R;
import com.android.bedsidechats.activities.MainActivity;
import com.android.bedsidechats.fragments.ProviderFragment;
import com.google.firebase.firestore.FirebaseFirestore;

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
            updateAppLanguage();
        }

        public void updateAppLanguage(){
            String locale =  mContext.getResources().getConfiguration().locale.toString();
            switch(mLanguageChoice){
                case "English":
                    if(locale.substring(0, 2).equals("en")){
                        swapToProviderScreen();
                    }
                    else {
                        setLocale("en");
                    }
                    break;
                case "Español":
                    if(locale.substring(0, 2).equals("es")){
                        swapToProviderScreen();
                    }
                    else {
                        setLocale("es");
                    }
                    break;
            }

            Log.d(TAG, "New Language: " + mLanguageChoice);
        }

        public void setLocale(String lang){
            Locale newLocale = new Locale(lang);
            Resources res = mContext.getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = res.getConfiguration();
            conf.locale = newLocale;
            res.updateConfiguration(conf, dm);
            Intent refresh = new Intent(mContext, MainActivity.class);
            refresh.putExtra("Language", mLanguageChoice);
            mContext.finish();
            mContext.startActivity(refresh);
        }

        public void swapToProviderScreen(){
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
    }

}
