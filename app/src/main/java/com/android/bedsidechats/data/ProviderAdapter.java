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
import com.android.bedsidechats.fragments.InstructionsFragment;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import java.util.ArrayList;


public class ProviderAdapter extends RecyclerView.Adapter<ProviderAdapter.ProviderViewHolder> {
    private String TAG = "PRVDR_ADPTR";;
    private static FirebaseFirestore mDatabase;
    private ArrayList<String> mProviders;
    private String mLanguageChoice;
    private Activity mContext;
    private FragmentManager mFragmentManager;

    public ProviderAdapter(Activity context, ArrayList<String> providers, FragmentManager fragmentManager, String language) {
        mProviders = providers;
        mDatabase = FirebaseFirestore.getInstance();
        mLanguageChoice =  language;
        mContext = context;
        mFragmentManager = fragmentManager;
    }

    @Override
    public ProviderViewHolder onCreateViewHolder(ViewGroup group, int i){
        View view = LayoutInflater.from(group.getContext())
                .inflate(R.layout.provider_holder, group, false);

        return new ProviderViewHolder(view, mContext, mFragmentManager, mLanguageChoice);
    }

    @Override
    public void onBindViewHolder(ProviderViewHolder holder, int position) {
        Log.d(TAG, "inside onBindViewHolder");
        holder.mProviderButton.setText(mProviders.get(position));
    }

    @Override
    public int getItemCount() {
        return mProviders != null ? mProviders.size() : 0;
    }

    public class ProviderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        protected Button mProviderButton;
        private String TAG = "PRVDR_HLDR";
        public String mProviderChoice;
        public String mLanguageChoice;
        private Activity mContext;
        private FragmentManager mFragmentManager;

        public ProviderViewHolder(View itemView, Activity context, FragmentManager fragmentManager, String language) {
            super(itemView);
            mProviderButton = itemView.findViewById(R.id.provider_button_provider_port);
            mProviderButton.setOnClickListener(this);
            mLanguageChoice =  language;
            mContext = context;
            mFragmentManager = fragmentManager;
        }

        @Override
        public void onClick(View v){
            mProviderChoice = mProviderButton.getText().toString();
            chooseProvider(mProviderChoice);
            Fragment fragment = new InstructionsFragment();
            Bundle args = new Bundle();
            args.putString("Provider", mProviderChoice);
            args.putString("Language", mLanguageChoice);
            fragment.setArguments(args);
            if (mFragmentManager != null) {
                mFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .addToBackStack("provider_fragment")
                        .commit();
            }

        }

        public void chooseProvider(String provider){
            Log.d(TAG, "Provider selected: " + provider);
        }
    }

}
