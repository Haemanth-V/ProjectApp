package com.example.projectapp.view.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.projectapp.R;
import com.example.projectapp.databinding.ActivitySetUserInfoBinding;
import com.example.projectapp.model.user.Users;
import com.example.projectapp.view.MainActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class SetUserInfo extends AppCompatActivity {

    private ActivitySetUserInfoBinding binding;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_set_user_info);
        dialog = new ProgressDialog(this);
        initButtonClick();
    }

    private void initButtonClick() {
        binding.buttonUserInfoNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(binding.editTextName.getText().toString())){
                    Toast.makeText(getApplicationContext(), "Please input username", Toast.LENGTH_LONG).show();
                }else {
                    doUpdate();
                }
            }
        });
        binding.imageViewSetProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //pickImage();
                Toast.makeText(getApplicationContext(), "This function is not ready to use", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void doUpdate() {
        dialog.setMessage("Please wait...");
        dialog.show();
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            String userID = user.getUid();
            Users users = new Users(userID,
                    binding.editTextName.getText().toString(),
                    user.getPhoneNumber(),
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "");
            firestore.collection("Users").document(user.getUid())
                    .set(users)
                    //.update("username", binding.editTextName.getText().toString())
                    .addOnSuccessListener(
                    new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(),"Update Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }
                    }
            ).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Update Failed : "+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            dialog.dismiss();
            Toast.makeText(getApplicationContext(),"You need to login first", Toast.LENGTH_SHORT).show();
        }
    }
}