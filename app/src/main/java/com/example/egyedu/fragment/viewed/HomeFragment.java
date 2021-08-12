package com.example.egyedu.fragment.viewed;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.egyedu.R;
import com.example.egyedu.databinding.FragmentHomeBinding;
import com.example.egyedu.most_viewed.ViewedModel;
import com.example.egyedu.most_viewed.ViewedModel2;
import com.example.egyedu.most_viewed.ViewedModel3;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    FragmentHomeBinding binding;
    RecyclerView recyclerView1;
    RecyclerView recyclerView2;
    RecyclerView recyclerView3;
    RecyclerView recyclerView4;
    ViewedAdapter viewedAdapter;
    ViewedAdapter2 viewedAdapter2;
    ViewedAdapter3 viewedAdapter3;
    List<ViewedModel> viewModelList;
    List<ViewedModel2> viewModelList2;
    List<ViewedModel3> viewModelList3;
    FirebaseFirestore firestore;

    int x;

    @Override
    public View onCreateView ( LayoutInflater inflater , ViewGroup container ,
                               Bundle savedInstanceState ) {

        if (container != null) {
            container.removeAllViews ();
        }
        binding = DataBindingUtil.inflate ( inflater , R.layout.fragment_home , container , false );
        recyclerView1 = binding.rvCours;
        recyclerView2 = binding.rvCours2;
        recyclerView3 = binding.rvCours3;
        recyclerView4 = binding.rvCours4;
        return binding.getRoot ();
    }

    @Override
    public void onViewCreated ( @NonNull View view , @Nullable Bundle savedInstanceState ) {
        super.onViewCreated ( view , savedInstanceState );
        firestore = FirebaseFirestore.getInstance ();
        viewModelList = new ArrayList<> ();
        viewModelList2 = new ArrayList<> ();
        viewModelList3 = new ArrayList<> ();
        recyclerView1.setHasFixedSize ( true );
        recyclerView1.setLayoutManager ( new LinearLayoutManager ( getActivity () , LinearLayoutManager.HORIZONTAL , false ) );
        recyclerView2.setHasFixedSize ( true );
        recyclerView2.setLayoutManager ( new LinearLayoutManager ( getActivity () , LinearLayoutManager.HORIZONTAL , true ) );
        recyclerView3.setHasFixedSize ( true );
        recyclerView3.setLayoutManager ( new LinearLayoutManager ( getActivity () , LinearLayoutManager.HORIZONTAL , true ) );
        recyclerView4.setHasFixedSize ( true );
        recyclerView4.setLayoutManager ( new LinearLayoutManager ( getActivity () , LinearLayoutManager.HORIZONTAL , true ) );
        viewedAdapter3 = new ViewedAdapter3 ( getActivity () , viewModelList3 );
        viewedAdapter2 = new ViewedAdapter2 ( getActivity () , viewModelList2 );
        viewedAdapter = new ViewedAdapter ( getActivity () , viewModelList );
        viewedAdapter = new ViewedAdapter ( getActivity () , viewModelList );
        recyclerView1.setAdapter ( viewedAdapter2 );
        recyclerView2.setAdapter ( viewedAdapter );
        recyclerView3.setAdapter ( viewedAdapter );
        recyclerView4.setAdapter ( viewedAdapter3 );
        viewedAdapter.notifyDataSetChanged ();
        viewedAdapter2.notifyDataSetChanged ();
        viewedAdapter3.notifyDataSetChanged ();
        getData ();
    }
    private void getData () {
        firestore.collection ( "mostviewed").addSnapshotListener ( ( value , error ) -> {
            if (error == null) {
                if (value == null) {
                    Toast.makeText ( getActivity () , error.getMessage () , Toast.LENGTH_SHORT ).show ();
                } else {
                    for (DocumentChange documentChange : value.getDocumentChanges ()) {
                        binding.progressView.setVisibility ( View.GONE );
                        viewedAdapter2.setList ( viewModelList2 );
                        viewedAdapter3.setList ( viewModelList3 );
                        ViewedModel2 viewedModel2 = documentChange.getDocument ().toObject ( ViewedModel2.class );
                        viewModelList2.add ( viewedModel2 );
                        ViewedModel3 viewedModel3 = documentChange.getDocument ().toObject ( ViewedModel3.class );
                        viewModelList3.add ( viewedModel3 );
                    }
                }
            } else {
                Toast.makeText ( getActivity () , error.getMessage () , Toast.LENGTH_SHORT ).show ();
            }
        } );
        firestore.collection ( "courses" ).addSnapshotListener ( ( value , error ) -> {
            if (error == null) {
                if (value == null) {
                    Toast.makeText ( getActivity () , error.getMessage () , Toast.LENGTH_SHORT ).show ();
                } else {
                    for (DocumentChange documentChange : value.getDocumentChanges ()) {
                        binding.progressView.setVisibility ( View.GONE );
                        viewedAdapter.setList ( viewModelList );

                        ViewedModel viewedModel = documentChange.getDocument ().toObject ( ViewedModel.class );
                        viewModelList.add ( viewedModel );
                    }
                }
            } else {
                Toast.makeText ( getActivity () , error.getMessage () , Toast.LENGTH_SHORT ).show ();
            }
        } );
    }

    private void setData () {

    }
}