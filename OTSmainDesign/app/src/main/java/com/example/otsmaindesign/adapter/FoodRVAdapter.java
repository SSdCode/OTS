package com.example.otsmaindesign.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.otsmaindesign.models.ModelClass;
import com.example.otsmaindesign.R;

import java.util.ArrayList;

public class FoodRVAdapter extends RecyclerView.Adapter<FoodRVAdapter.RVViewHolderClass> {
    private FoodRVAdapter.OnItemClickListener mListener;
    ArrayList<ModelClass> modelClassList;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(FoodRVAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    public FoodRVAdapter(ArrayList<ModelClass> objectModelClassList) {
        modelClassList = objectModelClassList;
    }

    @NonNull
    @Override
    public RVViewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.singl_row, parent, false);
        return new FoodRVAdapter.RVViewHolderClass(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RVViewHolderClass holder, int position) {
        ModelClass modelClass = modelClassList.get(position);
        holder.fdName.setText(modelClass.getImageName());
        holder.imageView.setImageBitmap(modelClass.getImage());
        holder.spname.setText(modelClass.getspName());
    }

    public String result(int position) {
        return modelClassList.get(position).getImageName();
    }

    @Override
    public int getItemCount() {
        try {
            return modelClassList.size();
        } catch (Exception e) {
        }
        return 0;
    }

    public static class RVViewHolderClass extends RecyclerView.ViewHolder {
        TextView fdName;
        ImageView imageView;
        TextView spname;

        public RVViewHolderClass(@NonNull View itemView, final FoodRVAdapter.OnItemClickListener listener) {
            super(itemView);
            fdName = itemView.findViewById(R.id.shop_detsTV);
            imageView = itemView.findViewById(R.id.shop_image);
            spname = itemView.findViewById(R.id.sp_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
