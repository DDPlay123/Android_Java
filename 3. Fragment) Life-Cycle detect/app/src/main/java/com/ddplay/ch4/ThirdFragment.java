package com.ddplay.ch4;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ThirdFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("Third", "onCreate");
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e("Third", "onCreateView");
        return inflater.inflate(R.layout.fragment_third, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e("Third", "onViewCreated");
    }
    @Override
    public void onStart() {
        super.onStart();
        Log.e("Third", "onStart");
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.e("Third", "onResume");
    }
    @Override
    public void onPause() {
        super.onPause();
        Log.e("Third", "onPause");
    }
    @Override
    public void onStop() {
        super.onStop();
        Log.e("Third", "onStop");
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e("Third", "onDestroyView");
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("Third", "onDestroy");
    }
    @Override
    public void onDetach() {
        super.onDetach();
        Log.e("Third", "onDetach");
    }
}
