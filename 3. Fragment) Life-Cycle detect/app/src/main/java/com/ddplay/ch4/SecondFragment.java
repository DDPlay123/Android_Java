package com.ddplay.ch4;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SecondFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("Second", "onCreate");
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e("Second", "onCreateView");
        return inflater.inflate(R.layout.fragment_second, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e("Second", "onViewCreated");
    }
    @Override
    public void onStart() {
        super.onStart();
        Log.e("Second", "onStart");
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.e("Second", "onResume");
    }
    @Override
    public void onPause() {
        super.onPause();
        Log.e("Second", "onPause");
    }
    @Override
    public void onStop() {
        super.onStop();
        Log.e("Second", "onStop");
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e("Second", "onDestroyView");
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("Second", "onDestroy");
    }
    @Override
    public void onDetach() {
        super.onDetach();
        Log.e("Second", "onDetach");
    }
}
