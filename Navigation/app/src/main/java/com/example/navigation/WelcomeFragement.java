package com.example.navigation;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.navigation.databinding.FragmentWelcomeBinding;

public class WelcomeFragement extends Fragment {

    private FragmentWelcomeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_welcome, container, false);
        binding = FragmentWelcomeBinding.inflate(inflater,container,false);

        binding.goToLogin.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_welcomeFragement_to_loginFragement);
        });

        binding.goToSignup.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_welcomeFragement_to_signupFragement);
        });

        return binding.getRoot();
    }
}