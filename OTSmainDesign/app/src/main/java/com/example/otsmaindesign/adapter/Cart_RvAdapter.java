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

public class Cart_RvAdapter extends RecyclerView.Adapter<Cart_RvAdapter.RVViewHolderClass> {
    private Cart_RvAdapter.OnItemClickListener mListener;
    ArrayList<ModelClass> modelClassList;

    public String getImageName(int position) {
        return modelClassList.get(position).getImageName();
    }

    public String getTotalAmt(int position) {
        return modelClassList.get(position).getTotalPrice();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(Cart_RvAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    public Cart_RvAdapter(ArrayList<ModelClass> objectModelClassList) {
        modelClassList = objectModelClassList;
    }


    @NonNull
    @Override
    public RVViewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.cart_single_raw, parent, false);
        return new Cart_RvAdapter.RVViewHolderClass(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RVViewHolderClass holder, int position) {
        ModelClass modelClass = modelClassList.get(position);
        holder.fdName.setText(modelClass.getImageName());
        holder.imageView.setImageBitmap(modelClass.getImage());
        holder.total_amt.setText("Total Amount :  " + modelClass.getTotalPrice());
    }



    @Override
    public int getItemCount() {
        try {
            return modelClassList.size();
        } catch (Exception e) {
        }
        return 0;
    }

    public class RVViewHolderClass extends RecyclerView.ViewHolder {
        TextView fdName;
        ImageView imageView;
        TextView total_amt;

        public RVViewHolderClass(@NonNull View itemView, final Cart_RvAdapter.OnItemClickListener listener) {
            super(itemView);

            imageView = itemView.findViewById(R.id.food_image);
            fdName = itemView.findViewById(R.id.food_detsTV);
            total_amt = itemView.findViewById(R.id.total_amt);

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
