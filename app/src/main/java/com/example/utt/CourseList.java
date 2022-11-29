package com.example.utt;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.utt.algorithm.model.Course;

import java.util.List;

public class CourseList extends ArrayAdapter<CourseTest> {

    private Activity context;
    private List<CourseTest> courseList;

    public CourseList(Activity context, List<CourseTest> courseList){
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
        CourseTest course = courseList.get(position);
        textViewName.setText(course.getCode());
        textViewSeason.setText(course.getName());

        return listViewItem;
    }
}
