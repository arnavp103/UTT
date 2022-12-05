package com.example.utt;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.utt.models.Course;

import java.util.ArrayList;
import java.util.List;

public class PrereqAdapter extends RecyclerView.Adapter<PrereqAdapter.ViewHolder> {
    private List<CourseModel> courseList;
    private SelectPrereqs activity;
    private ArrayList<Course> prereqList;

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
        prereqList = FirstFragment.courses;//new ArrayList<>();

        if (prereqList.contains(item.getCourse())) item.setStatus(1);
        holder.courseTask.setText(item.getCourseCode());
        holder.courseTask.setChecked(toBoolean(item.getStatus()));
        holder.courseTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
//                if((CheckBox) view)
                if(holder.courseTask.isChecked()){
                    prereqList.add(item.getCourse());
                }
                else {
                    prereqList.remove(item.getCourse());
                }
            }
        });
    }

    public ArrayList<Course> getSelectedCourses() { return prereqList; }

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
