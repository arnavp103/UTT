package com.example.utt;

import static com.example.utt.SecondFragment.yearList;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class timelineCourseAdapter extends RecyclerView.Adapter<timelineCourseAdapter.timetableViewHolder> {

    private final Context context;
    private final ArrayList<TimelineCourseModel> courseModelArrayList;

    public timelineCourseAdapter(Context context, ArrayList<TimelineCourseModel> courseModelArrayList) {
        this.context = context;
        this.courseModelArrayList = courseModelArrayList;
    }

    @NonNull
    @Override
    public timelineCourseAdapter.timetableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.timeline_recycler_view,
                parent, false);
        return new timetableViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull timelineCourseAdapter.timetableViewHolder holder, int position) {
        TimelineCourseModel model = courseModelArrayList.get(position);
        // Allow card to expand when clicked
        holder.year.setOnClickListener(view1 -> {
            boolean expanded = !model.isExpanded();
            model.setExpanded(expanded);
            notifyItemChanged(position);

            holder.timelineBind(model);
        });
        holder.timelineBind(model);
    }

    @Override
    public int getItemCount() {
        // Show number of card items (year) in timetable
        return courseModelArrayList.size();
    }

    // Initializing views
    public static class timetableViewHolder extends RecyclerView.ViewHolder {
        private final TextView year;
        private final TextView winterHeader;
        private final TextView summerHeader;
        private final TextView fallHeader;
        private final TextView winterCourses;
        private final TextView summerCourses;
        private final TextView fallCourses;
        private final LinearLayout detailsLayout;


        public timetableViewHolder(@NonNull View itemView) {
            super(itemView);
            year = itemView.findViewById(R.id.idYear);
            winterHeader = itemView.findViewById(R.id.winterTextHeader);
            summerHeader = itemView.findViewById(R.id.summerHeader);
            fallHeader = itemView.findViewById(R.id.fallHeader);
            winterCourses = itemView.findViewById(R.id.winterCourses);
            summerCourses = itemView.findViewById(R.id.summerCourses);
            fallCourses = itemView.findViewById(R.id.fallCourses);
            detailsLayout = itemView.findViewById(R.id.linLayoutYear);
        }

        void timelineBind(TimelineCourseModel model){
            // Get the state
            boolean expanded = model.isExpanded();
            // Set the visibility based on state
            detailsLayout.setVisibility(expanded ? View.VISIBLE : View.GONE);

            // Set title of each card (Year)
            year.setText(model.getYear());
            year.setTypeface(null, Typeface.BOLD);

            winterCourses.setText(model.getWinterCourses());
            summerCourses.setText(model.getSummerCourses());
            fallCourses.setText(model.getFallCourses());
//            courseName.setText(model.getCourseName());
//            courseCode.setText(model.getCourseCode());

            // Use this when there are no courses offered that term
//            for(String yearIndex: yearList) {
//                if(yearIndex.equals(model.getYear())) {
                    if (model.getWinterCourses().isEmpty()) {
                        ((ViewGroup) detailsLayout).removeView(winterCourses);
                        ((ViewGroup) detailsLayout).removeView(winterHeader);
                    }
                    if (model.getFallCourses().isEmpty()) {
                        ((ViewGroup) detailsLayout).removeView(fallCourses);
                        ((ViewGroup) detailsLayout).removeView(fallHeader);
                    }
                    if (model.getSummerCourses().isEmpty()) {
//                if (winterCourses.getParent() != null || summerCourses.getParent() != null ||
//                fallCourses.getParent() != null) {
                        ((ViewGroup) detailsLayout).removeView(summerCourses);
                        ((ViewGroup) detailsLayout).removeView(summerHeader);
                    }
//                }
//            }
        }
    }

}
