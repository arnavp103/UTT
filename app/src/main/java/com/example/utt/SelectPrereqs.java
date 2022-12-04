package com.example.utt;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceFragmentCompat;

import com.example.utt.databinding.FragmentFirstBinding;
import com.example.utt.databinding.FragmentRecyclerListBinding;
import com.example.utt.databinding.SelectPrereqsBinding;

public class SelectPrereqs extends Fragment {
    private SelectPrereqsBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = SelectPrereqsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}