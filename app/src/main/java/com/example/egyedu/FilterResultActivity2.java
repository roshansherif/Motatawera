package com.example.egyedu;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.egyedu.databinding.ActivityFilterResult2Binding;
import com.example.egyedu.fragment.viewed.ViewedAdapter;
import com.example.egyedu.most_viewed.ViewedModel;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class FilterResultActivity2 extends AppCompatActivity {
    ActivityFilterResult2Binding binding;
    RecyclerView recyclerView;
    CardView CardView ;
    ViewedAdapter viewedAdapter;
    List<ViewedModel> viewModelList;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_filter_result2);
        recyclerView = binding.rvCours;
        firestore = FirebaseFirestore.getInstance();

        viewModelList = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));
        viewedAdapter = new ViewedAdapter(this, viewModelList);
        recyclerView.setAdapter(viewedAdapter);
        getData();
    }

    private void getData() {
        String price = getIntent().getStringExtra("price");
        String category = getIntent().getStringExtra("category");
        String city = getIntent().getStringExtra("city");
        Boolean isOnline = Boolean.parseBoolean(getIntent().getStringExtra("isOnline"));


        if (price.equals("0")) {

            firestore.collection("courses")
                    .whereEqualTo("city", city)
                    .whereEqualTo("category", category)
                    .whereEqualTo("price", 0)
                    .whereEqualTo("isOnline", isOnline)
                    .addSnapshotListener((value, error) -> {
                        if (error == null) {
                            if (value == null) {
                                Toast.makeText(this, error.getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                if (value.getDocuments ().isEmpty ()){
                                    new AlertDialog.Builder(this)
                                            .setTitle(" No courses yet ")
                                            .setPositiveButton("ok", (dialog, which) -> {
                                                //  startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                            })
                                            .create()
                                            .show();
                                }else
                                for (DocumentChange documentChange : value.getDocumentChanges()) {

                                    binding.progressView.setVisibility(View.GONE);
                                    viewedAdapter.setList(viewModelList);
                                    ViewedModel viewedModel = documentChange.getDocument().toObject(ViewedModel.class);
                                    viewModelList.add(viewedModel);

                                }
                            }
                        } else Toast.makeText(this, error.getMessage(), Toast.LENGTH_SHORT).show();

                    });


        } else if (price.equals("1")) {

            firestore.collection("courses")
                    .whereEqualTo("city", city)
                    .whereEqualTo("category", category)
                    .whereEqualTo("isOnline", isOnline)
                    .addSnapshotListener((value, error) -> {
                        if (error == null) {
                            if (value == null) {
                                Toast.makeText(this, error.getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                for (DocumentChange documentChange : value.getDocumentChanges()) {

                                    binding.progressView.setVisibility(View.GONE);
                                    viewedAdapter.setList(viewModelList);
                                    ViewedModel viewedModel = documentChange.getDocument().toObject(ViewedModel.class);
                                    viewModelList.add(viewedModel);
                                }
                            }
                        } else Toast.makeText(this, error.getMessage(), Toast.LENGTH_SHORT).show();

                    });


        }
    }


}