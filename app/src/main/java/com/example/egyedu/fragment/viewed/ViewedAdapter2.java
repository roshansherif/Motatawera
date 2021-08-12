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
import com.example.egyedu.databinding.Courses2LayoutBinding;
import com.example.egyedu.most_viewed.ViewedModel2;

import java.util.List;

public class ViewedAdapter2 extends RecyclerView.Adapter<ViewedAdapter2.ViewHolder> {


    Context context;
    List<ViewedModel2> viewedModelst2;


    public ViewedAdapter2 ( Context context , List<ViewedModel2> viewedModelst2 ) {
        this.context = context;
        this.viewedModelst2 = viewedModelst2;
    }


    @NonNull
    @Override
    public ViewedAdapter2.ViewHolder onCreateViewHolder ( @NonNull ViewGroup parent , int viewType ) {

        Courses2LayoutBinding courses2LayoutBinding = DataBindingUtil.inflate ( LayoutInflater.from ( parent.getContext () ) ,
                R.layout.courses2_layout , parent , false
                                                                              );
        return new ViewedAdapter2.ViewHolder ( courses2LayoutBinding );
    }

    @Override
    public void onBindViewHolder ( @NonNull ViewedAdapter2.ViewHolder holder , int position ) {


        Glide.with ( context )
                .load ( viewedModelst2.get ( position ).getImage () )
                .placeholder ( R.drawable.ic_gutir )
                .into ( holder.courses2LayoutBinding.imgview );
        holder.courses2LayoutBinding.name.setText ( viewedModelst2.get ( position ).getName () );
        holder.courses2LayoutBinding.textinfo.setText ( viewedModelst2.get ( position ).getInfo () );
        holder.courses2LayoutBinding.layout2.setOnClickListener ( v -> {
            Intent intent = new Intent ( context , ShowCoursDataActivity.class );

            intent.putExtra ( "name" , viewedModelst2.get ( position ).getName () );
            intent.putExtra ( "id" , viewedModelst2.get ( position ).getId () );
            intent.putExtra ( "image" , viewedModelst2.get ( position ).getImage () );
            intent.putExtra ( "info" , viewedModelst2.get ( position ).getInfo () );

            context.startActivity ( intent );

        } );


    }

    @Override
    public int getItemCount () {
        return viewedModelst2.size ();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Courses2LayoutBinding courses2LayoutBinding;

        public ViewHolder ( @NonNull Courses2LayoutBinding courses2LayoutBinding ) {
            super ( courses2LayoutBinding.getRoot () );
            this.courses2LayoutBinding = courses2LayoutBinding;
        }


    }

    public void setList ( List<ViewedModel2> viewedModelst2 ) {
        this.viewedModelst2 = viewedModelst2;
        notifyDataSetChanged ();
    }
}
