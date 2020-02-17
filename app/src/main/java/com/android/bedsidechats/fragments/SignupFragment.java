package com.android.bedsidechats.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.view.Surface;

import com.android.bedsidechats.R;
import com.android.bedsidechats.helpers.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.common.api.ApiException;
import com.google.firebase.auth.AuthCredential;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.HashMap;
import java.util.Map;


public class SignupFragment extends Fragment implements View.OnClickListener {
    private EditText mEmailEditText;
    private EditText mUsernameEditText;
    private EditText mPasswordEditText;
    private EditText mConfirmPassword;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mDatabase;
    private static String TAG = "SIGNUP_FGMT";
    private static final int RC_SIGN_IN = 9001;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v;
        Activity activity = getActivity();

        if (activity != null)
        {
            int rotation = getActivity().getWindowManager().getDefaultDisplay().getRotation();
            if (rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270) {
                v = inflater.inflate(R.layout.activity_sign_up, container, false);
            } else {
                v = inflater.inflate(R.layout.activity_sign_up, container, false);
            }
        }
        else{
            v = inflater.inflate(R.layout.activity_sign_up, container, false);
        }
        v = inflater.inflate(R.layout.activity_sign_up, container, false);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseFirestore.getInstance();

        mEmailEditText = v.findViewById(R.id.email_editText_signup_port);
        mUsernameEditText = v.findViewById(R.id.username_editText_signup_port);
        mPasswordEditText = v.findViewById(R.id.password_editText_signup_port);

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
                    validateUser();
                    break;
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            AppCompatActivity activity = (AppCompatActivity) getActivity();
            if (activity != null) {
                ActionBar actionBar = activity.getSupportActionBar();
                if (actionBar != null) {
                    actionBar.setSubtitle(getResources().getString(R.string.account));
                }
            }
        }
        catch (NullPointerException npe) {
            // TODO handle exception
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void validateUser() {
        final FragmentActivity activity = getActivity();
        final String email = mEmailEditText.getText().toString();
        final String username = mUsernameEditText.getText().toString();
        String password = mPasswordEditText.getText().toString();
        String passwordConfirm = mConfirmPassword.getText().toString();
        if (activity != null) {
            if (password.equals(passwordConfirm) && !email.equals("") &&
                    !password.equals("") && !username.equals("")) {
                Toast.makeText(activity.getApplicationContext(), "Loading... Please wait.",
                        Toast.LENGTH_LONG).show();

                User user = new User(email, username, password);
                //checkUniqueUsername(user);
                createFirebaseUser(user);


            } else if ((email.equals("")) || (password.equals("")) || (passwordConfirm.equals(""))) {
                Toast.makeText(activity.getApplicationContext(), "One or more entries are blank.",
                        Toast.LENGTH_SHORT).show();
            } else if (!password.equals(passwordConfirm)) {
                Toast.makeText(activity.getApplicationContext(), "Passwords do not match.",
                        Toast.LENGTH_SHORT).show();
            } else {
                // TODO handle errors
            }
        }
    }

    private void addUserToFirebase(User user) {
        Map<String, String> email = new HashMap<>();
        email.put("email", user.getEmail());
        mDatabase.collection("patients").document(user.getEmail())
                .set(user.getUsername())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "User successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing user", e);
                    }
                });

        Map<String, String> username = new HashMap<>();
        username.put("username", user.getUsername());
        mDatabase.collection("patients").document(user.getUsername())
                .set(username)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "User email successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing user email", e);
                    }
                });
    }

//    private void checkUniqueUsername(final User user) {
//        DocumentReference docRef = mDatabase.collection("patients")
//                .document(user.getEmail());
//
//        final FragmentActivity activity = getActivity();
//        if (activity == null) return;
//        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    DocumentSnapshot document = task.getResult();
//                    if (document.exists()) {
//                        Log.d(TAG, "username is not unique");
//                        Toast.makeText(activity.getApplicationContext(), "Username is taken!",
//                                Toast.LENGTH_LONG).show();
//                    } else {
//                        Log.d(TAG, "creating firebase user");
//                        createFirebaseUser(user);
//                    }
//                } else {
//                    Log.d(TAG, "get failed with ", task.getException());
//                }
//            }
//        });
//    }

    private void createFirebaseUser(final User user) {
        final FragmentActivity activity = getActivity();
        if (activity == null) return;
        mAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // if successful, user is signed in
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            Toast.makeText(activity.getApplicationContext(), "User Created!",
                                    Toast.LENGTH_SHORT).show();

                            addUserToFirebase(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(activity.getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}