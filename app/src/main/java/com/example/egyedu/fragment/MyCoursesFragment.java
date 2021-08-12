package com.example.egyedu.fragment;

import android.content.Intent;
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

import com.example.egyedu.MyCourses.MyCoursesAdapter;
import com.example.egyedu.MyCourses.MyCoursesModel;
import com.example.egyedu.MyCoursesActivity;
import com.example.egyedu.R;
import com.example.egyedu.databinding.FragmentMyCoursesBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;


public class MyCoursesFragment extends Fragment {
    FragmentMyCoursesBinding binding;
    RecyclerView recyclerMycorses;
    MyCoursesAdapter myCoursesAdapter;
    List<MyCoursesModel> myCoursesModelList;
    FirebaseFirestore firestore;
    FirebaseAuth firebaseAuth;


    @Nullable
    @Override
    public View onCreateView ( @NonNull LayoutInflater inflater , @Nullable ViewGroup container , @Nullable Bundle savedInstanceState ) {
        if (container != null) {
            container.removeAllViews ();
        }
        binding = DataBindingUtil.inflate ( inflater , R.layout.fragment_my_courses , container , false );
        return binding.getRoot ();
    }

    @Override
    public void onViewCreated ( @NonNull View view , @Nullable Bundle savedInstanceState ) {
        super.onViewCreated ( view , savedInstanceState );
        recyclerMycorses = binding.recyclemycourses;
        myCoursesModelList = new ArrayList<> ();
        firestore = FirebaseFirestore.getInstance ();
        firebaseAuth = FirebaseAuth.getInstance ();
        recyclerMycorses.setHasFixedSize ( true );
        recyclerMycorses.setLayoutManager ( new LinearLayoutManager ( getActivity () ) );
        myCoursesAdapter = new MyCoursesAdapter ( myCoursesModelList , getActivity () );
        recyclerMycorses.setAdapter ( myCoursesAdapter );

        binding.recyclemycourses.setOnClickListener(v -> {
            startActivity (new Intent (getActivity (), MyCoursesActivity.class) );
        });

        getcorseData ();
    }

    private void getcorseData () {
        String Uid = firebaseAuth.getUid ();
        firestore.collection ( getString ( R.string.user ) )
                .document ( Uid )
                .collection ( getString ( R.string.mycourses ) )
                .addSnapshotListener ( ( value , error ) -> {
                    if (error == null) {
                        if (value == null) {
                            Toast.makeText ( getActivity () , error.getMessage () , Toast.LENGTH_SHORT ).show ();
                        } else {
                            for (DocumentChange documentChange : value.getDocumentChanges ()) {
                                MyCoursesModel model =documentChange.getDocument ().toObject ( MyCoursesModel.class );
                                myCoursesModelList.add ( model );
                                myCoursesAdapter.notifyDataSetChanged ();
                            }
                        }
                    }
                } );
    }
}