package com.example.utt;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CourseList extends ArrayAdapter<Course> {

    private Activity context;
    private List<Course> courseList;

    public CourseList(Activity context, List<Course> courseList){
        super(context, R.layout.list_layout, courseList);
        this.context = context;
        this.courseList = courseList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_layout, null, true);
        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        TextView textViewSeason = (TextView) listViewItem.findViewById(R.id.textViewSeason);
        Course course = courseList.get(position);
        textViewName.setText(course.getName());
        textViewSeason.setText(course.getSeason());

        return listViewItem;
    }
}
