package com.example.androidandfragementlifecycle;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class FirstFragment extends Fragment {

    private static final String TAG = "FRAGMENT_LIFECYCLE_FirstFragment";

    Button CallSecondFragment;

    public FirstFragment() {
        // Required empty public constructor
    }

    public static FirstFragment newInstance(String param1, String param2) {
        FirstFragment fragment = new FirstFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        Log.d(TAG,"onAttach Called!");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate Called!");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG,"onCreateView Called!");
        // Inflate the layout for requireContext() fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
        CallSecondFragment=view.findViewById(R.id.CallSecondFragment);
        CallSecondFragment.setOnClickListener(v -> {
            SecondFragment fragment = new SecondFragment();
            Bundle bundle = new Bundle();
            bundle.putString("name","Shrawani");
            fragment.setArguments(bundle);
            Toast.makeText(requireContext(), "Name Sent Successfully", Toast.LENGTH_SHORT).show();
            requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragementContainer,fragment)
                    .addToBackStack(null)
                    .commit();
        });
        Log.d(TAG,"onViewCreated Called!");
    }

    @Override
    public void onStart(){
        super.onStart();
        Log.d(TAG,"onStart() method Called!!");
        Toast.makeText(requireContext(), "onStart() method Called!!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d(TAG,"onResumet() method Called!!");
        Toast.makeText(requireContext(), "onResume() method Called!!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.d(TAG,"onPause() method Called!!");
        Toast.makeText(requireContext(), "onPause() method Called!!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStop(){
        super.onStop();
        Log.d(TAG,"onStop() method Called!!");
        Toast.makeText(requireContext(), "onStop() method Called!!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        Log.d(TAG,"onDestroyView Called!");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy Called!");
    }

    @Override
    public void onDetach(){
        super.onDetach();
        Log.d(TAG,"onDetach Called!");
    }
}