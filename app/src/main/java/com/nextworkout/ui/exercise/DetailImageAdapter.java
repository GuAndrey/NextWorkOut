package com.nextworkout.ui.exercise;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.nextworkout.R;
import com.nextworkout.models.Exercise;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;

public class DetailImageAdapter extends RecyclerView.Adapter<DetailImageAdapter.ItemHolder> {
    private Exercise exercise;
    private String path;
    private Drawable[] drawables;

    public DetailImageAdapter(Exercise exercise, Context context) {
        this.exercise = exercise;
        path = exercise.getPath();
        InputStream inputStream = null;
        String[] files;

        //Получаем список файлов
        try {
            files = context.getAssets().list("images/" + path);
        } catch (IOException e) {
            e.printStackTrace();
            files = new String[0];
        }

        drawables = new  Drawable[files.length];

        //Инициализируем drawables
        for (int i=0; i < files.length; i++){

            Log.d("Image", "" + files[i]);

            try {
                inputStream = context.getAssets().open("images/" + path + "/" + files[i]);
                drawables[i] = Drawable.createFromStream(inputStream, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try{
                if(inputStream!=null)
                    inputStream.close();
            }
            catch (IOException ex){
                ex.printStackTrace();
            }

        }


    }

    @NonNull
    @NotNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View content = (LayoutInflater.from(parent.getContext())).inflate(R.layout.view_pager, parent,false);

        return new ItemHolder(content);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ItemHolder holder, int position) {
        holder.imageView.setImageDrawable(drawables[position]);
    }

    @Override
    public int getItemCount() {
        return drawables.length;
    }

    static class ItemHolder extends RecyclerView.ViewHolder{

        ImageView imageView;

        public ItemHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_detail);
        }

    }
}
