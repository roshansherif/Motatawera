package com.example.egyedu.fragment.viewed;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.egyedu.R;
import com.example.egyedu.ShowCoursDataActivity;
import com.example.egyedu.databinding.CoursesLayoutBinding;
import com.example.egyedu.most_viewed.ViewedModel;

import java.util.List;

public class ViewedAdapter extends RecyclerView.Adapter<ViewedAdapter.ViewHolder> {
    Context context;
    List<ViewedModel> viewedModelst;


    public ViewedAdapter ( Context context , List<ViewedModel> viewedModelst ) {
        this.context = context;
        this.viewedModelst = viewedModelst;
    }


    @NonNull
    @Override
    public ViewedAdapter.ViewHolder onCreateViewHolder ( @NonNull ViewGroup parent , int viewType ) {

        CoursesLayoutBinding coursesLayoutBinding = DataBindingUtil.inflate ( LayoutInflater.from ( parent.getContext () ) ,
                R.layout.courses_layout , parent , false );
        return new ViewedAdapter.ViewHolder ( coursesLayoutBinding );
    }

    @Override
    public void onBindViewHolder ( @NonNull ViewedAdapter.ViewHolder holder , int position ) {
        Glide.with ( context )
                .load ( viewedModelst.get ( position ).getImage () )
                .placeholder ( R.drawable.ic_booking )
                .into ( holder.coursesLayoutBinding.coursImage );
        if (viewedModelst.get ( position ).getPrice () == 0) {
            holder.coursesLayoutBinding.price.setText ( "Free" );
        }
        if (viewedModelst.get ( position ).getPrice () == 0) {
            holder.coursesLayoutBinding.price.setText ( "Free" );
        } else {
            holder.coursesLayoutBinding.price.setText ( viewedModelst.get ( position ).getPrice () + "  L.E" );
        }
        holder.coursesLayoutBinding.centerName.setText ( viewedModelst.get ( position ).getCenter_name () );
        holder.coursesLayoutBinding.courseName.setText ( viewedModelst.get ( position ).getCourse_name () );
        holder.coursesLayoutBinding.courseInfo.setText ( viewedModelst.get ( position ).getInfo () );
        holder.coursesLayoutBinding.btnrelative.setOnClickListener ( v -> {

            Intent intent = new Intent ( context , ShowCoursDataActivity.class );
            intent.putExtra ( "price" , viewedModelst.get ( position ).getPrice () );
            intent.putExtra ( "id" , viewedModelst.get ( position ).getId () );
            intent.putExtra ( "image" , viewedModelst.get ( position ).getImage () );
            intent.putExtra ( "info" , viewedModelst.get ( position ).getInfo () );
            intent.putExtra ( "cat" , viewedModelst.get ( position ).getCategory () );
            intent.putExtra ( "centerName" , viewedModelst.get ( position ).getCenter_name () );
            intent.putExtra ( "courseName" , viewedModelst.get ( position ).getCourse_name () );
            intent.putExtra ( "date" , viewedModelst.get ( position ).getDate () );
            intent.putExtra ( "isOnline" , viewedModelst.get ( position ).isOnline () );
            //  Toast.makeText ( context , String.valueOf ( viewedModelst.get (position).getPrice () ) , Toast.LENGTH_SHORT ).show ();
            context.startActivity ( intent );

        } );
    }

    @Override
    public int getItemCount () {
        return viewedModelst.size ();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CoursesLayoutBinding coursesLayoutBinding;

        public ViewHolder ( @NonNull CoursesLayoutBinding coursesLayoutBinding ) {
            super ( coursesLayoutBinding.getRoot () );
            this.coursesLayoutBinding = coursesLayoutBinding;
        }
    }

    public void setList ( List<ViewedModel> viewedModelst ) {
        this.viewedModelst = viewedModelst;
        notifyDataSetChanged ();
    }
}
