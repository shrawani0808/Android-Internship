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
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SecondFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SecondFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    public static final String TAG = "FRAGMENT_LIFECYCLE_SecondFragement";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SecondFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SecondFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SecondFragment newInstance(String param1, String param2) {
        SecondFragment fragment = new SecondFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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

        Bundle bundle = getArguments();
        if(bundle != null){
            String name = bundle.getString("name");
            Log.d(TAG,"Received name:"+name);
            Toast.makeText(requireContext(), "Received Name:"+name, Toast.LENGTH_SHORT).show();
        }
        Log.d(TAG,"onCreate Called");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG,"onCreateView Called!");
        // Inflate the layout for requireContext() fragment
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
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