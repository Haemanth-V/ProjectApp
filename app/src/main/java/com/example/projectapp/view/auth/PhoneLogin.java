package com.example.projectapp.view.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.projectapp.R;
import com.example.projectapp.databinding.ActivityPhoneLoginBinding;
import com.example.projectapp.model.user.Users;
import com.example.projectapp.view.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabItem;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.TimeUnit;

public class PhoneLogin extends AppCompatActivity {

    private static final String TAG = "PhoneLogin";
    private ActivityPhoneLoginBinding binding;
    private FirebaseAuth auth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    private PhoneAuthProvider.ForceResendingToken resendingToken;
    private String mVerificationId;
    private ProgressDialog progressDialog;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_phone_login);
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        firebaseUser = auth.getCurrentUser();
        if(firebaseUser != null){
            startActivity(new Intent(this, SetUserInfo.class));
        }
        progressDialog = new ProgressDialog(this);
        binding.buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.buttonNext.getText().toString().equals("Next")) {
                    progressDialog.setMessage("Please Wait");
                    progressDialog.show();
                    String phone = "+"+binding.editTextCountryCode.getText().toString() +
                            binding.editTextPhoneNumber.getText().toString();
                    startPhoneNumberVerification(phone);
                }
                else{
                    progressDialog.setMessage("Verifying ...");
                    progressDialog.show();
                    verifyPhoneNumberWithCode(mVerificationId, binding.editTextCode.getText().toString());
                }
            }
        });
        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                Log.d(TAG, "onVerificationCompleted: Complete");
                signInWithPhoneAuthCredential(phoneAuthCredential);
                progressDialog.dismiss();
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Log.d(TAG, "onVerificationFailed: "+e.getMessage());
            }
            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {

                Log.d(TAG, "onCodeSent:" + verificationId);


                mVerificationId = verificationId;
                resendingToken = token;
                binding.buttonNext.setText("Confirm");
                progressDialog.dismiss();
            }
        };
    }

    private void startPhoneNumberVerification(String phoneNumber) {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                callbacks);        // OnVerificationStateChangedCallbacks

    }

    private void verifyPhoneNumberWithCode(String verificationId, String code) {

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            progressDialog.dismiss();
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = task.getResult().getUser();
                            startActivity(new Intent(PhoneLogin.this, SetUserInfo.class));
                            /*if (user != null) {
                                String userID = user.getUid();
                                Users users = new Users(userID,
                                        "",
                                        user.getPhoneNumber(),
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "");
                                firestore.collection("Users").document("UserInfo").
                                        collection(userID).add(users).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        }
                                });
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"ERROR",Toast.LENGTH_SHORT).show();
                            }*/

                        }else {

                            progressDialog.dismiss();
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {

                                Log.d(TAG, "onComplete: Error Code");

                            }
                        }
                    }
                });
    }
}