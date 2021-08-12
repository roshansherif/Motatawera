package com.example.egyedu.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.egyedu.FilterResultActivity2;
import com.example.egyedu.R;
import com.example.egyedu.databinding.FragmentFilterBinding;


public class FilterFragment extends Fragment {

    FragmentFilterBinding binding;

    String isOnline;
    String category, city;
    String price;

    public FilterFragment() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_filter, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        binding.showCourses.setOnClickListener(v -> {

                    validation();

                }
        );

    }

    private void validation() {

        category = binding.category.getSelectedItem().toString();
        if (binding.rbgiza.isChecked()) {
            city = getString ( R.string.giza );
        }
        if (binding.rdcairo.isChecked()) {
            city = getString ( R.string.cairo );
        }
        if (!binding.rdcairo.isChecked() && !binding.rbgiza.isChecked()) {
            city = getString ( R.string.out_of_range );
        }
        if (binding.price.isChecked()) {
            price ="0";
        }
        if (!binding.price.isChecked()) {
            price = "1";
        }

        if (binding.rdoffline.isChecked()) {
            isOnline = getString ( R.string.False );
        }
        if (binding.rbonline.isChecked()) {
            isOnline = getString ( R.string.True );
        }

        gotoactivity();
    }

    private void gotoactivity() {

        Intent intent = new Intent(getActivity(), FilterResultActivity2.class);
        intent.putExtra(getString ( R.string.category ), binding.category.getSelectedItem().toString());
        intent.putExtra(getString ( R.string.price ), price);
        intent.putExtra(getString ( R.string.city ), city);
        intent.putExtra(getString ( R.string.Isonline ), isOnline);
        startActivity(intent);
    }
}