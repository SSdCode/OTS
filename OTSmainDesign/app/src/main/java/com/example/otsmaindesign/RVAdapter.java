package com.example.otsmaindesign;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.RVViewHolderClass> {
    private OnItemClickListener mListener;
    ArrayList<ModelClass> modelClassList;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public RVAdapter(ArrayList<ModelClass> objectModelClassList) {
        modelClassList = objectModelClassList;
    }

    @NonNull
    @Override
    public RVViewHolderClass onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.singl_row, viewGroup, false);
        return new RVViewHolderClass(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RVViewHolderClass holder, int position) {
        ModelClass modelClass = modelClassList.get(position);
        holder.spName.setText(modelClass.getImageName());
//        holder.imageView.setImageBitmap(modelClass.getImage());
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
        TextView spName;
        ImageView imageView;

        public RVViewHolderClass(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            spName = itemView.findViewById(R.id.shop_detsTV);
            imageView = itemView.findViewById(R.id.shop_image);
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
