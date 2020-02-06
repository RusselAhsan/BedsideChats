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
import android.widget.EditText;
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

public class SignupFragment extends Fragment implements View.OnClickListener {
    private EditText mEmailEditText;
    private EditText mUsernameEditText;
    private EditText mPasswordEditText;
    //private FirebaseAuth mAuth;
    //private FirebaseFirestore mDatabase;
    private static String TAG = "LOGIN_FGMT";
    private static final int RC_SIGN_IN = 9001;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v;

        // TODO handle rotation
        v = inflater.inflate(R.layout.activity_sign_up, container, false);

        //mAuth = FirebaseAuth.getInstance();
        //mDatabase = FirebaseFirestore.getInstance();
        mEmailEditText = v.findViewById(R.id.email_editText);
        mUsernameEditText = v.findViewById(R.id.username_editText);
        mPasswordEditText = v.findViewById(R.id.password_editText);

        Button loginButton = v.findViewById(R.id.login_button);
        if (loginButton != null) {
            loginButton.setOnClickListener(this);
        }
        Button signUpButton = v.findViewById(R.id.signup_button);
        if (signUpButton != null) {
            signUpButton.setOnClickListener(this);
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
                                .addToBackStack("signup_fragment")
                                .commit();
                    }
                    break;
                case R.id.signup_button:
                    //checkSignup();
                    break;
            }
        }
    }

    private void checkSignup() {
//        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
//        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
//                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
//            Log.d(TAG, "mobile " + connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState());
//            Log.d(TAG, "wifi " + connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState());
//        }
//        else{
//            Toast.makeText(getActivity(), "No network connection found.", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        final String email = mUsernameEditText.getText().toString();
//        String password = mPasswordEditText.getText().toString();
//        final Activity activity = getActivity();
//        if (activity != null) {
//            // check if fields are empty
//            if (email.equals("") || password.equals("")) {
//                Toast.makeText(activity.getApplicationContext(), "One or more fields are blank!",
//                        Toast.LENGTH_SHORT).show();
//                return;
//            }
//            mAuth.signInWithEmailAndPassword(email, password)
//                    .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            if (task.isSuccessful()) {
//                                // Sign in success, update UI with the signed-in user's information
//                                Log.d(TAG, "signInWithEmail:success");
//                                getUsername(email); // get username which will set shared preferences
//                            } else {
//                                // If sign in fails, display a message to the user.
//                                //Log.w(TAG, "signInWithEmail:failure", task.getException());
//                                Toast.makeText(activity, "Authentication failed.",
//                                        Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
//        } else {
//            // TODO handle errors
//        }
    }
//
//
//    private void getUsername(final String email) {
//        DocumentReference userRef = mDatabase.collection("user_emails").document(email);
//        userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    DocumentSnapshot document = task.getResult();
//                    if (document.exists()) {
//                        String username = document.getString("username");
//                        Log.d(TAG, "username: " + username);
//                        setSharedPreferences(email, username); // store email & username in shared preferences
//
//                    } else {
//                        Log.d(TAG, "No such document");
//                    }
//                } else {
//                    Log.d(TAG, "get failed with ", task.getException());
//                }
//            }
//        });
//    }
//
//    private void setSharedPreferences(String email, String username) {
//        Activity activity = getActivity();
//        if (activity != null) {
//            SharedPreferences.Editor editor = activity.getSharedPreferences("LOGIN",
//                    Context.MODE_PRIVATE).edit();
//            editor.putString("email", email);
//            editor.putString("username", username);
//            editor.apply();
//            Log.d("TEST", "here");
//        }
//
//        startHomeActivity(username);
//    }
//
//    private void startHomeActivity(String username) {
//        Activity activity = getActivity();
//        if (activity != null) {
//            // start HomeActivity
//            Intent intent = new Intent(activity, HomeActivity.class);
//            Log.d(TAG, "inside startHomeActivity username: " + username);
//            intent.putExtra("username", username);
//            startActivity(intent);
//            activity.finish();
//        }
//    }
}