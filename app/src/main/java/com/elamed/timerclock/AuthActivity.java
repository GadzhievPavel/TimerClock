package com.elamed.timerclock;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class AuthActivity extends AppCompatActivity {

    private static final String TAG ="Auth" ;
    private EditText phoneNumberEditText;
    private EditText verificationCodeEditText;
    private Button enterCodeButton;
    private Button getVerificationCodeButton;
    private FirebaseAuth mAuth;
    private String codeSent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_activity);
        mAuth = FirebaseAuth.getInstance();
        bindView();
        setListeners();
    }

    private void bindView() {
        phoneNumberEditText = findViewById(R.id.edit);
        verificationCodeEditText = findViewById(R.id.editCode);
        enterCodeButton = findViewById(R.id.buttonSignIn);
        getVerificationCodeButton = findViewById(R.id.button);
    }

    private void setListeners() {
        enterCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String verificationCode = verificationCodeEditText.getText().toString();
                verifySignInCode(verificationCode);
            }
        });
        getVerificationCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = phoneNumberEditText.getText().toString();
                sendVerificationCode(phoneNumber);
            }
        });
    }

    private void verifySignInCode(String verificationCode) {
        PhoneAuthCredential credential = null;
        try {
            credential = PhoneAuthProvider.getCredential(codeSent, verificationCode);
            signInWithPhoneAuthCredential(credential);
        } catch (IllegalArgumentException e) {
            verificationCodeEditText.setError("Invalid code");
            phoneNumberEditText.requestFocus();
        }

    }

    private void sendVerificationCode(String phoneNumber) {

        if (phoneNumber.isEmpty()) {
            phoneNumberEditText.setError("Phone number is required");
            phoneNumberEditText.requestFocus();
            return;
        }

        if (phoneNumber.length() < 10) {
            phoneNumberEditText.setError("Please enter a valid phone");
            phoneNumberEditText.requestFocus();
            return;
        }

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                60,
                TimeUnit.SECONDS,
                this,
                mCallbacks);

    }


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            final FirebaseUser user = task.getResult().getUser();

                            updateUI(user);

                        } else {

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {

                            }
                        }
                    }
                });
    }


    private void updateUI(FirebaseUser user) {
        if (user != null) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }

    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

        }

        @Override
        public void onVerificationFailed(FirebaseException e) {

        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            codeSent = s;
        }
    };
}




