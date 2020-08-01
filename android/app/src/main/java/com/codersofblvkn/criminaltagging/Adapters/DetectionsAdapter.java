package com.codersofblvkn.criminaltagging.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.codersofblvkn.criminaltagging.R;
import com.codersofblvkn.criminaltagging.Utils.Detection;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DetectionsAdapter extends RecyclerView.Adapter<DetectionsAdapter.DetectionsViewHolder> {

    final List<Detection> list;
    final OnItemClickListener listener;
    public DetectionsAdapter(List<Detection> list,OnItemClickListener listener)
    {
        this.list=list;
        this.listener=listener;
    }


    @NonNull
    @Override
    public DetectionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DetectionsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.detection_item, parent, false), listener);
    }

    @Override
    public void onBindViewHolder(@NonNull DetectionsViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class DetectionsViewHolder extends RecyclerView.ViewHolder {

        final TextView tv1;
        final TextView tv2;
        final ImageView avatar;
        final OnItemClickListener listener;
        public DetectionsViewHolder(@NonNull View itemView,OnItemClickListener listener) {
            super(itemView);
            tv1=itemView.findViewById(R.id.textView2);
            tv2=itemView.findViewById(R.id.textView3);
            avatar=itemView.findViewById(R.id.avatarList);
            this.listener=listener;
        }

        public void bind(Detection detection) {


            String disp_ID= itemView.getContext().getString(R.string.detectionid)+Integer.toString(detection.getId());

            tv1.setText(disp_ID);
//            tv2.setText(detection.getLatitude()+" "+detection.getLongitude());
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher_round);
            Glide.with(itemView.getContext()).load(detection.getImg()).apply(options).into(avatar);
            DateFormat simple = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
            Date result=new Date(detection.getTimestamp());
            tv2.setText(simple.format(result));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(detection);
                }
            });
        }
    }
}
