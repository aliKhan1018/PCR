package com.mak.pcr.ui.faculty.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.mak.pcr.R;

public class HomeFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

       View root = inflater.inflate(R.layout.fragment_faculty_home, container, false);


        return root;
    }

}
