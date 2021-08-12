package com.example.egyedu.MyCourses;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.egyedu.MyCoursesActivity;
import com.example.egyedu.R;
import com.example.egyedu.databinding.MycoursesLayoutBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class MyCoursesAdapter extends RecyclerView.Adapter<MyCoursesAdapter.ViewHolder> {

    private List<MyCoursesModel> list;
    private Context context;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;
    private ImageView love;


    public MyCoursesAdapter ( List<MyCoursesModel> list , Context context ) {
        this.list = list;
        this.context = context;

    }

    @NonNull
    @Override
    public MyCoursesAdapter.ViewHolder onCreateViewHolder ( @NonNull ViewGroup parent , int viewType ) {
        MycoursesLayoutBinding  mycourseslayoutBinding = DataBindingUtil.inflate ( LayoutInflater.from ( parent.getContext () ) ,
                R.layout.mycourses_layout , parent , false );


        return new MyCoursesAdapter.ViewHolder ( mycourseslayoutBinding );

    }


    @Override
    public void onBindViewHolder ( @NonNull MyCoursesAdapter.ViewHolder holder , int position ) {

        Glide.with ( context )
                .load ( list.get ( position ).getImage () )
                .placeholder ( R.drawable.ic_booking )
                .into ( holder.mycourseslayoutBinding.coursImage );
        holder.mycourseslayoutBinding.courseName.setText ( list.get ( position ).getCourse_name () );
        holder.mycourseslayoutBinding.courseInfo.setText ( list.get ( position ).getInfo () );
        holder.mycourseslayoutBinding.btnmycourse.setOnClickListener ( v -> {

            Intent intent = new Intent ( context , MyCoursesActivity.class );
//            intent.putExtra ( "price" , list.get ( position ).getPrice () );
//            intent.putExtra ( "id" , list.get ( position ).getId () );
//            intent.putExtra ( "image" , list.get ( position ).getImage () );
//            intent.putExtra ( "info" , list.get ( position ).getInfo () );
//            intent.putExtra ( "cat" , list.get ( position ).getCategory () );
//            intent.putExtra ( "centerName" , list.get ( position ).getCenter_name () );
//            intent.putExtra ( "courseName" , list.get ( position ).getCourse_name () );
//            intent.putExtra ( "date" , list.get ( position ).getDate () );

            context.startActivity ( intent );

        } );

    }
    @Override
    public int getItemCount () {
        return list.size ();
    }

    public void setList ( List<MyCoursesModel> myCoursesModelList ) {
        this.list = myCoursesModelList;
        notifyDataSetChanged ();


    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public MycoursesLayoutBinding mycourseslayoutBinding;

        public ViewHolder ( @NonNull MycoursesLayoutBinding itemView ) {
            super ( itemView.getRoot () );
            this.mycourseslayoutBinding = itemView;


        }
    }
}
