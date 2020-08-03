package com.codersofblvkn.criminaltagging.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codersofblvkn.criminaltagging.R;
import com.codersofblvkn.criminaltagging.Utils.Violence;

import java.util.List;

public class ViolenceAdapter extends RecyclerView.Adapter<ViolenceAdapter.ViolenceViewHolder> {

    List<Violence> list;
    OnViolenceClickListener listener;
    public ViolenceAdapter(List<Violence> list,OnViolenceClickListener listener)
    {
        this.list=list;
        this.listener=listener;
    }

    public ViolenceAdapter() {
    }


    @NonNull
    @Override
    public ViolenceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViolenceViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.violence_item, parent, false), listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViolenceViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViolenceViewHolder extends RecyclerView.ViewHolder {

        final TextView tv1;
        final TextView tv2;
        final ImageView avatar;
        final OnViolenceClickListener listener;
        public ViolenceViewHolder(@NonNull View itemView, OnViolenceClickListener listener) {
            super(itemView);
            tv1=itemView.findViewById(R.id.textView2);
            tv2=itemView.findViewById(R.id.textView3);
            avatar=itemView.findViewById(R.id.avatarList);
            this.listener=listener;
        }

        public void bind(Violence violence) {


            String disp_ID= itemView.getContext().getString(R.string.violenceid)+ violence.getId();
            tv1.setText(disp_ID);
            tv2.setText(violence.getTimestamp().substring(0,20));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(violence);
                }
            });
        }
    }

}
