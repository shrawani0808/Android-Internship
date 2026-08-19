package com.example.logindemo;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePasswordFragement extends Fragment {


    Button Btn_resetPass,Btn_resetEmail,Btn_DeleteUser;
    EditText Edt_changePass,Edt_changeEmail,Edt_CurrentPassword,Edt_deleteUser,Edt_passfordelete;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_change_password_fragement, container, false);

        View view = inflater.inflate(R.layout.fragment_change_password_fragement,container,false);
        Btn_resetPass=view.findViewById(R.id.Btn_resetPassword);
        Edt_changePass=view.findViewById(R.id.Edt_newPassword);
        Btn_resetEmail=view.findViewById(R.id.Btn_resetEmail);
        Edt_changeEmail=view.findViewById(R.id.Edt_newEmail);
        Edt_CurrentPassword=view.findViewById(R.id.Edt_currentPass);
        Edt_deleteUser=view.findViewById(R.id.Edt_deleteUser);
        Btn_DeleteUser=view.findViewById(R.id.Btn_deleteUser);
        Edt_passfordelete=view.findViewById(R.id.Edt_currentPassfordelete);


        Btn_resetPass.setOnClickListener(v -> {
            changePassword();
        });

        Btn_resetEmail.setOnClickListener(v -> {
            changeEmail();
        });


        Btn_DeleteUser.setOnClickListener(v -> {
            deleteUser();
        });

        return view;


    }

    private void changePassword(){
        String newPass = Edt_changePass.getText().toString().trim();
        if(newPass.isEmpty()){
            Edt_changePass.setError("Enter new Password First");
        }
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            user.updatePassword(newPass)
                    .addOnSuccessListener(unused -> {
                        Toast.makeText(requireContext(), "Password Changed Sucessfully", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(requireContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        }
    }

    private void changeEmail(){
        String newEmail = Edt_changeEmail.getText().toString().trim();
        String pass = Edt_CurrentPassword.getText().toString().trim();
        if(newEmail.isEmpty()){
            Edt_changeEmail.setError("Enter new Email First");
        }
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            AuthCredential authCredential = EmailAuthProvider.getCredential(user.getEmail(),pass);
            user.reauthenticate(authCredential)
                            .addOnSuccessListener(task -> {
                                        user.verifyBeforeUpdateEmail(newEmail)
                                                .addOnSuccessListener(unused -> {
                                                    Toast.makeText(requireContext(), "Email Reset link send", Toast.LENGTH_SHORT).show();
                                                })
                                                .addOnFailureListener(e -> {
                                                    Toast.makeText(requireContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                                });
                                })
                            .addOnFailureListener(e -> {
                                Toast.makeText(requireContext(), "Authentication Failed", Toast.LENGTH_SHORT).show();
                            });
        }
    }

    private void deleteUser(){
        String username = Edt_deleteUser.getText().toString().trim();
        String currentpass = Edt_passfordelete.getText().toString().trim();
        if(username.isEmpty()){
            Edt_deleteUser.setError("Enter User Name First");
        }
        FirebaseUser user =FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            AuthCredential authCredential = EmailAuthProvider.getCredential(user.getEmail(),currentpass);
            user.reauthenticate(authCredential)
                    .addOnSuccessListener(unused -> {
                        user.delete().addOnSuccessListener(unused1 -> {
                            Toast.makeText(requireContext(), "User Deleated", Toast.LENGTH_SHORT).show();

                        }).addOnFailureListener(e -> {
                            Toast.makeText(requireContext(), "Failed to Delete"+e.getMessage(), Toast.LENGTH_SHORT).show();
                        });

                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(requireContext(), "Failed Authentication"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        }
    }

}