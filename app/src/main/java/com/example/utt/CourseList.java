//package com.example.utt;
//
//import android.app.Activity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//
//import com.example.utt.models.Course;
//
//import java.util.List;
//
//public class CourseList extends ArrayAdapter<Course> {
//
//    private Activity context;
//    private List<Course> courseList;
//
//    public CourseList(Activity context, List<Course> courseList){
//        super(context, R.layout.list_layout, courseList);
//        this.context = context;
//        this.courseList = courseList;
//    }
//
//    @NonNull
//    @Override
//    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        LayoutInflater inflater = context.getLayoutInflater();
//
//        View listViewItem = inflater.inflate(R.layout.list_layout, null, true);
//        TextView textViewCode = (TextView) listViewItem.findViewById(R.id.textViewCode);
//        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
//        TextView textViewSeason = (TextView) listViewItem.findViewById(R.id.textViewSeason);
//        Course course = courseList.get(position);
//        textViewCode.setText(course.getCode());
//        textViewName.setText(course.getName());
//        String sessionOutput = "";
//        for(int i = 0; i < course.getSessionOffering().size(); i++){
//            sessionOutput.concat(course.getSessionOffering().get(i).toString());
//            if((i+1) != course.getSessionOffering().size()){
//                sessionOutput.concat(", ");
//            }
//        }
//        textViewSeason.setText(sessionOutput);
//
//        return listViewItem;
//    }
//}
