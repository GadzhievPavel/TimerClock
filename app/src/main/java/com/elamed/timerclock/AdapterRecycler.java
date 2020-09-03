package com.elamed.timerclock;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;
import java.util.List;

public class AdapterRecycler extends RecyclerView.Adapter<AdapterRecycler.MyViewHolder> {

    private List<Long> times;
    private LayoutInflater layoutInflater;

    public AdapterRecycler(@NonNull Context context, List<Long> times) {
        this.times = times;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.fragment_clock,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        long time = times.get(position);
        Date date = new Date(time);
        holder.textView.setText(date.toString());
    }

    @Override
    public int getItemCount() {
        return times.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        Switch aSwitch;
        MyViewHolder(View view){
            super(view);
            textView = view.findViewById(R.id.clock_text);
            aSwitch = view.findViewById(R.id.sw);
        }
    }
}
