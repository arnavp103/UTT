package com.example.utt;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import com.example.utt.models.Course;

import java.util.List;

public class PrereqAdapter extends RecyclerView.Adapter<PrereqAdapter.ViewHolder> {
    private List<CourseModel> courseList;
    private SelectPrereqs activity;
    private ArrayList<Course> prereqList;

    public PrereqAdapter(SelectPrereqs activity){
        this.activity = activity;
    }

    @NonNull
    public PrereqAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.prereq_layout, parent, false);
        return new ViewHolder(itemView);
    }

    public void onBindViewHolder(ViewHolder holder, int position){
        CourseModel item = courseList.get(position);
        prereqList = AdminHome.courses;

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
        activity.emptyResultView.setVisibility(View.GONE);
        this.courseList = list;
//        notifyDataSetChanged();
    }

    public void filterList(ArrayList<CourseModel> filterList) {
        activity.emptyResultView.setVisibility(View.GONE);
        courseList = filterList;
        notifyDataSetChanged();
    }

    public void emptyList() {
        filterList(new ArrayList<>());
        activity.emptyResultView.setVisibility(View.VISIBLE);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        CheckBox courseTask;

        ViewHolder(View view){
            super(view);
            courseTask = view.findViewById(R.id.prereqCheckBox);
        }
    }
}
