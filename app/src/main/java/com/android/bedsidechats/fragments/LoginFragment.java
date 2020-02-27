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
import android.widget.EditText;
import android.widget.Toast;

import com.android.bedsidechats.R;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginFragment extends Fragment implements View.OnClickListener {
    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mDatabase;
    private static String TAG = "LOGIN_FGMT";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v;
        Activity activity = getActivity();

        if (activity != null)
        {
            int rotation = getActivity().getWindowManager().getDefaultDisplay().getRotation();
            if (rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270) {
                v = inflater.inflate(R.layout.activity_login, container, false);
            } else {
                v = inflater.inflate(R.layout.activity_login, container, false);
            }
        }
        else{
            v = inflater.inflate(R.layout.activity_login, container, false);
        }
        v = inflater.inflate(R.layout.activity_login, container, false);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseFirestore.getInstance();
        mEmailEditText = v.findViewById(R.id.email_editText_login_port);
        mPasswordEditText = v.findViewById(R.id.password_editText_login_port);

        Button loginButton = v.findViewById(R.id.login_button_login_port);
        if (loginButton != null) {
            loginButton.setOnClickListener(this);
        }

        Button forgotPasswordButton = v.findViewById(R.id.forgotPassword_button_login_port);
        if (forgotPasswordButton != null) {
            forgotPasswordButton.setOnClickListener(this);
        }

        Button signUpButton = v.findViewById(R.id.signup_button_login_port);
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
                case R.id.login_button_login_port:
                    checkLogin();
                    break;
                case R.id.forgotPassword_button_login_port:
                    Log.d(TAG, "Forgot Password");
                    forgotPasswordSendEmail();
                    // TODO: GIVE NEW PASSWORD? LINK TO PAGE WHERE THEY CHOOSE NEW PASSWORD?
                    break;
                case R.id.signup_button_login_port:
                    FragmentManager fragmentManager = getFragmentManager();
                    Fragment fragment = new SignupFragment();
                    if (fragmentManager != null) {
                        fragmentManager.beginTransaction()
                                .replace(R.id.fragment_container, fragment)
                                .addToBackStack("login_fragment")
                                .commit();
                    }
                    break;
            }
        }
    }

    private void forgotPasswordSendEmail() {
    }

    private void checkLogin() {
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
        final String email = mEmailEditText.getText().toString();
        String password = mPasswordEditText.getText().toString();
        final Activity activity = getActivity();
        if (activity != null) {
            // check if fields are empty
            if (email.equals("") || password.equals("")) {
                Toast.makeText(activity.getApplicationContext(), "One or more fields are blank!",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                                getUsername(email); // get username which will be set in shared preferences
                            } else {
                                // If sign in fails, display a message to the user.
                                //Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(activity, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            // TODO handle errors
        }
    }

    private void getUsername(final String email) {
        DocumentReference userRef = mDatabase.collection("patients").document(email);
        userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String username = document.getString("username");
                        Log.d(TAG, "username: " + username);
                        setSharedPreferences(email, username); // store email & username in shared preferences

                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "Get failed with ", task.getException());
                }
            }
        });
    }

    private void setSharedPreferences(String email, String username) {
        Activity activity = getActivity();
        if (activity != null) {
            SharedPreferences.Editor editor = activity.getSharedPreferences("LOGIN",
                    Context.MODE_PRIVATE).edit();
            editor.putString("email", email);
            editor.putString("username", username);
            editor.apply();
            Log.d(TAG, "in setSharedPreferences");
        }

        transferToHome(email, username);
    }

    private void transferToHome(String email, String username) {
        Activity activity = getActivity();
        if (activity != null) {
            // load HomeFragment
            FragmentManager fragmentManager = getFragmentManager();
            Fragment fragment = new HomeFragment();
            Bundle args = new Bundle();
            args.putString("Username", username);
            args.putString("Email", email);
            fragment.setArguments(args);
            if (fragmentManager != null) {
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .addToBackStack("login_fragment")
                        .commit();
            }
        }
    }
}