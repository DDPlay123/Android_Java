package com.ddplay.ch4;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FirstFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("First", "onCreate");
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e("First", "onCreateView");
        return inflater.inflate(R.layout.fragment_first, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e("First", "onViewCreated");
    }
    @Override
    public void onStart() {
        super.onStart();
        Log.e("First", "onStart");
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.e("First", "onResume");
    }
    @Override
    public void onPause() {
        super.onPause();
        Log.e("First", "onPause");
    }
    @Override
    public void onStop() {
        super.onStop();
        Log.e("First", "onStop");
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e("First", "onDestroyView");
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("First", "onDestroy");
    }
    @Override
    public void onDetach() {
        super.onDetach();
        Log.e("First", "onDetach");
    }
}
