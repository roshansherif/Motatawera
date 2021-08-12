package com.example.egyedu.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.egyedu.Favourite.FavoriteAdapter;
import com.example.egyedu.Favourite.FavouriteModel;
import com.example.egyedu.R;
import com.example.egyedu.databinding.FragmentFavoriteBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;


public class FavoriteFragment extends Fragment {
    FragmentFavoriteBinding binding;
    RecyclerView recyclerFavourite;
    FavoriteAdapter favoriteAdapter;
    List<FavouriteModel> favouriteModelList;
    FirebaseFirestore firestore;
    FirebaseAuth firebaseAuth;


    @Nullable
    @Override
    public View onCreateView ( @NonNull LayoutInflater inflater , @Nullable ViewGroup container , @Nullable Bundle savedInstanceState ) {
        if (container != null) {
            container.removeAllViews ();
        }
        binding = DataBindingUtil.inflate ( inflater , R.layout.fragment_favorite , container , false );
        return binding.getRoot ();


    }

    @Override
    public void onViewCreated ( @NonNull View view , @Nullable Bundle savedInstanceState ) {
        super.onViewCreated ( view , savedInstanceState );
        recyclerFavourite = binding.recyclefavorite;
        favouriteModelList = new ArrayList<> ();
        firestore = FirebaseFirestore.getInstance ();
        firebaseAuth = FirebaseAuth.getInstance ();
        recyclerFavourite.setHasFixedSize ( true );
        recyclerFavourite.setLayoutManager ( new LinearLayoutManager ( getActivity () ) );
        favoriteAdapter = new FavoriteAdapter ( favouriteModelList , getActivity () );
        recyclerFavourite.setAdapter ( favoriteAdapter );

        getFavData ();


    }

    private void getFavData () {
        String Uid = firebaseAuth.getUid ();
        firestore.collection ( getString ( R.string.user ) )
                .document ( Uid )
                .collection ( getString ( R.string.favorite ))
                .addSnapshotListener ( ( value , error ) -> {
                    if (error == null) {
                        if (value == null) {
                            Toast.makeText ( getActivity () , error.getMessage () , Toast.LENGTH_SHORT ).show ();
                        } else {
                            if (value.getDocuments ().isEmpty ()){
                                new AlertDialog.Builder(getActivity ())
                                        .setTitle(getString ( R.string.No_favourites_yet ))
                                        .setPositiveButton(getString ( R.string.ok ), (dialog, which) -> {
                                            //  startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                        })
                                        .create()
                                        .show();
                            }else
                            for (DocumentChange documentChange : value.getDocumentChanges ()) {
                              FavouriteModel model =documentChange.getDocument ().toObject ( FavouriteModel.class );
                              favouriteModelList.add ( model );
                              favoriteAdapter.notifyDataSetChanged ();


                            }
                        }
                    }
                } );

    }
   private void AlertDialog(){
        new AlertDialog.Builder ( getActivity () )
                .setTitle(getString ( R.string.msg ))
                .setMessage(getString ( R.string.no ))
                .setPositiveButton(getString ( R.string.ok ), (dialog, which) -> {
                }).create().show();
    }

}