package com.example.utt;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import com.example.utt.database.DatabaseHandler;
import com.example.utt.models.Course;
import com.example.utt.models.CourseEventListener;
import com.example.utt.models.firebase.datamodel.CourseDataModel;

import java.util.ArrayList;
import java.util.List;

public class CourseList extends ArrayAdapter<Course> implements Filterable {
    Filter myFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String filterString = constraint.toString().toLowerCase();
            FilterResults filterResults = new FilterResults();
            ArrayList<Course> tempList=new ArrayList<Course>();
            //constraint is the result from text you want to filter against.
            //objects is your data set you will filter from
            if(constraint != null && fullList!=null) {
                int length=fullList.size();
                int i=0;
                while(i<length){
                    Course item = fullList.get(i);
                    //do whatever you wanna do here
                    //adding result set output array
                    if (item.getName().toLowerCase().contains(filterString) || item.getCode().toLowerCase().contains(filterString)) {
                        tempList.add(item);
                    }

                    Log.d("FILETRING:", item.toString());

                    i++;
                }
                //following two lines is very important
                //as publish result can only take FilterResults objects
                filterResults.values = tempList;
                filterResults.count = tempList.size();
            }

            if (filterString.isEmpty()) {filterResults.values = fullList; filterResults.count = fullList.size();}
            return filterResults;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence contraint, FilterResults results) {
//            courseList = (ArrayList<Course>) results.values;

            courseList.clear();
            for (Course c : (ArrayList<Course>)results.values) courseList.add(c);
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    };

    @Override
    public Filter getFilter() {
        return myFilter;
    }

    private Activity context;
    private List<Course> courseList;
    private List<Course> fullList;

    public CourseList(Activity context, List<Course> courseList){
        super(context, R.layout.list_layout, courseList);
        this.context = context;
        this.courseList = courseList;
        this.fullList = new ArrayList<Course>(courseList);
    }

    public void updateFullList() {
        this.fullList = new ArrayList<>(courseList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_layout, null, true);
        TextView textViewCode = (TextView) listViewItem.findViewById(R.id.textViewCode);
        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        TextView textViewSeason = (TextView) listViewItem.findViewById(R.id.textViewSeason);
        listViewItem.setVisibility(View.VISIBLE);
        Course course = courseList.get(position);
        textViewCode.setText(course.getCode());
        textViewName.setText(course.getName());
        String sessionOutput = "Offered: ";
        for (int i = 0; i < course.getSessionOffering().size(); i++) {
            sessionOutput += course.getSessionOffering().get(i).toString();
            if ((i + 1) != course.getSessionOffering().size()) {
                sessionOutput += ", ";
            }
        }

        textViewSeason.setText(sessionOutput);

        String prereqs = "Prerequisites: ";
        if (course.getPrerequisites() != null){
            for (int i = 0; i < course.getPrerequisites().size(); i++) {
                if (course.getPrerequisites().size() <= i) continue;
                if (course.getPrerequisites().get(i).getName().equals("Missing")){
                    continue;
                }

                prereqs += course.getPrerequisites().get(i).getCode();

                if (i != course.getPrerequisites().size() - 1) {
                    prereqs += ", ";
                }
            }
        }

        ((TextView) listViewItem.findViewById(R.id.coursePrerequisites)).setText(prereqs);

        ImageView edit = listViewItem.findViewById(R.id.deleteCourse);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyDelete(CourseDataModel.getCourseByCode(course.getCode()));
                notifyDataSetChanged();
            }
        });

        listViewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyModify(course, view);
            }
        });

        return listViewItem;
    }

    private void verifyDelete(CourseDataModel course) {
        new AlertDialog.Builder(this.getContext())
                .setTitle("Delete Course")
                .setMessage("Are you sure you want to delete this course?")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("Remove", course.getKey() + "->" + course);
                        DatabaseHandler.removeCourse(course);
                    }
                })
                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton("Cancel", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void verifyModify(Course course, View view) {
        new AlertDialog.Builder(this.getContext())
                .setTitle("Modify Course")
                .setMessage("Do you want to modify this course?")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Bundle bundle = new Bundle();
                        bundle.putString("CourseCode", course.getCode());
                        Navigation.findNavController(view).navigate(R.id.action_firstFragment_to_fragment_modify_course, bundle);
                    }
                })
                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton("Cancel", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

}
