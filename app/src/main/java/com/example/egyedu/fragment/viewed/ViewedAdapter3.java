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
import com.example.egyedu.databinding.Courses3LayoutBinding;
import com.example.egyedu.most_viewed.ViewedModel3;

import java.util.List;

public class ViewedAdapter3 extends RecyclerView.Adapter<ViewedAdapter3.ViewHolder> {


    Context context;
    List<ViewedModel3> viewedModelst3;


    public ViewedAdapter3 ( Context context , List<ViewedModel3> viewedModelst3 ) {
        this.context = context;
        this.viewedModelst3 = viewedModelst3;
    }


    @NonNull
    @Override
    public ViewedAdapter3.ViewHolder onCreateViewHolder ( @NonNull ViewGroup parent , int viewType ) {

        Courses3LayoutBinding courses3LayoutBinding = DataBindingUtil.inflate ( LayoutInflater.from ( parent.getContext () ) ,
                R.layout.courses3_layout , parent , false
                                                                                            );
        return new ViewedAdapter3.ViewHolder ( courses3LayoutBinding );
    }

    @Override
    public void onBindViewHolder ( @NonNull ViewedAdapter3.ViewHolder holder , int position ) {


        Glide.with ( context )
                .load ( viewedModelst3.get ( position ).getImage () )
                .placeholder ( R.drawable.ic_gutir )
                .into ( holder.courses3LayoutBinding.image3 );
        holder.courses3LayoutBinding.name3.setText ( viewedModelst3.get ( position ).getName () );
        holder.courses3LayoutBinding.info3.setText ( viewedModelst3.get ( position ).getInfo () );

        holder.courses3LayoutBinding.layout3.setOnClickListener ( v -> {

            Intent intent = new Intent ( context , ShowCoursDataActivity.class );
            context.startActivity ( intent );

        } );


    }

    @Override
    public int getItemCount () {
        return viewedModelst3.size ();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Courses3LayoutBinding courses3LayoutBinding;

        public ViewHolder ( @NonNull Courses3LayoutBinding courses3LayoutBinding ) {
            super ( courses3LayoutBinding.getRoot () );
            this.courses3LayoutBinding = courses3LayoutBinding;
        }


    }

    public void setList ( List<ViewedModel3> viewedModelst3 ) {
        this.viewedModelst3 = viewedModelst3;
        notifyDataSetChanged ();
    }
}
