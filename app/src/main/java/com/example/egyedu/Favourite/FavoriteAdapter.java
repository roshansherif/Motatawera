package com.example.egyedu.Favourite;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.egyedu.R;
import com.example.egyedu.ShowCoursDataActivity;
import com.example.egyedu.databinding.FavoriteLayoutBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    private List<FavouriteModel> list;
    private Context context;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;
    private ImageView love;

    public FavoriteAdapter ( List<FavouriteModel> list , Context context ) {
        this.list = list;
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder ( @NonNull ViewGroup parent , int viewType ) {
        FavoriteLayoutBinding favoritelayoutBinding = DataBindingUtil.inflate ( LayoutInflater.from ( parent.getContext () ) ,
                R.layout.favorite_layout , parent , false );


        return new FavoriteAdapter.ViewHolder ( favoritelayoutBinding );

    }


    @Override
    public void onBindViewHolder ( @NonNull ViewHolder holder , int position ) {

        Glide.with ( context )
                .load ( list.get ( position ).getImage () )
                .placeholder ( R.drawable.ic_booking )
                .into ( holder.favoriteLayoutBinding.coursImage );
        if (list.get ( position ).getPrice () == 0) {
            holder.favoriteLayoutBinding.price.setText ( "Free" );
        }
        if (list.get ( position ).getPrice () == 0) {
            holder.favoriteLayoutBinding.price.setText ( "Free" );
        } else {
            holder.favoriteLayoutBinding.price.setText ( list.get ( position ).getPrice () + "  L.E" ); }
        holder.favoriteLayoutBinding.centerName.setText ( list.get ( position ).getCenter_name () );
        holder.favoriteLayoutBinding.date.setText ( list.get ( position ).getDate () );
        holder.favoriteLayoutBinding.courseName.setText ( list.get ( position ).getCourse_name () );
        holder.favoriteLayoutBinding.courseInfo.setText ( list.get ( position ).getInfo () );
        holder.favoriteLayoutBinding.btnrelative.setOnClickListener ( v -> {

            Intent intent = new Intent ( context , ShowCoursDataActivity.class );
            intent.putExtra ( "price" , list.get ( position ).getPrice () );
            intent.putExtra ( "id" , list.get ( position ).getId () );
            intent.putExtra ( "image" , list.get ( position ).getImage () );
            intent.putExtra ( "info" , list.get ( position ).getInfo () );
            intent.putExtra ( "cat" , list.get ( position ).getCategory () );
            intent.putExtra ( "centerName" , list.get ( position ).getCenter_name () );
            intent.putExtra ( "courseName" , list.get ( position ).getCourse_name () );
            intent.putExtra ( "date" , list.get ( position ).getDate () );

            context.startActivity ( intent );

        } );
        holder.favoriteLayoutBinding.love.setOnClickListener ( v -> {

            firebaseAuth=FirebaseAuth.getInstance ();
            firestore=FirebaseFirestore.getInstance ();
            String Uid = firebaseAuth.getUid ();
            firestore.collection ( "users" )
                    .document ( Uid )
                    .collection ( "favorite" )
                    .document ( list.get ( position ).getId () ).delete ()
                    .addOnCompleteListener ( task -> {
                        if (task.isSuccessful ()) {
                            Toast.makeText ( context , "Removed from favorit successful" , Toast.LENGTH_SHORT ).show ();

                            notifyDataSetChanged ();
                        } else {
                            Toast.makeText ( context , task.getException ().getMessage () , Toast.LENGTH_SHORT ).show ();
                        }
                    } );


        } );
    }


    @Override
    public int getItemCount () {
        return list.size ();
    }

    public void setList ( List<FavouriteModel> favouriteModelList ) {
        this.list = favouriteModelList;
        notifyDataSetChanged ();


    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public FavoriteLayoutBinding favoriteLayoutBinding;

        public ViewHolder ( @NonNull FavoriteLayoutBinding itemView ) {
            super ( itemView.getRoot () );
            this.favoriteLayoutBinding = itemView;


        }
    }
}
