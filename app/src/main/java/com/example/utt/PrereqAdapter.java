package com.example.utt;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PrereqAdapter extends RecyclerView.Adapter<PrereqAdapter.ViewHolder> {
    private List<CourseModel> courseList;
    private SelectPrereqs activity;

    public PrereqAdapter(SelectPrereqs activity){
        this.activity = activity;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.prereq_layout, parent, false);
        return new ViewHolder(itemView);
    }

    public void onBindViewHolder(ViewHolder holder, int position){
        CourseModel item = courseList.get(position);
        holder.courseTask.setText(item.getCourseCode());
        holder.courseTask.setChecked(toBoolean(item.getStatus()));
    }

    private boolean toBoolean(int n){
        return n!=0;
    }

    public int getItemCount(){
        return courseList.size();
    }

    public void setList(List<CourseModel> list){
        this.courseList = list;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        CheckBox courseTask;

        ViewHolder(View view){
            super(view);
            courseTask = view.findViewById(R.id.prereqCheckBox);
        }
    }
}
