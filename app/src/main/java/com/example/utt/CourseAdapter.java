//package com.example.utt;
//
//import android.content.Context;
//import android.graphics.Typeface;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.ArrayList;
//
//// Adapter for RecyclerView
//
//public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {
//
//    private final Context context;
//    private final ArrayList<CourseModel> courseModelArrayList;
//    boolean isExpanded = false;
//
//    // Constructor
//    public CourseAdapter(Context context, ArrayList<CourseModel> courseModelArrayList) {
//        this.context = context;
//        this.courseModelArrayList = courseModelArrayList;
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        // to inflate the layout for each item of recycler view.
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.courselist_card, parent, false);
//        return new ViewHolder(view);
//    }
//
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        // to set data to textview of each card layout
//        CourseModel model = courseModelArrayList.get(position);
//        holder.itemView.setOnClickListener(view1 -> {
//            boolean expanded = !model.isExpanded();
//            model.setExpanded(expanded);
//            notifyItemChanged(position);
//
//            holder.bind(model);
//        });
//
//        holder.bind(model);
//    }
//
//    @Override
//    public int getItemCount() {
//        // this method is used for showing number of card items in recycler view
//        return courseModelArrayList.size();
//    }
//
//    // View holder class for initializing of your views such as TextView
//    public static class ViewHolder extends RecyclerView.ViewHolder {
//        private final TextView courseCodeTV;
//        private final TextView courseNameTV;
//        private final TextView courseSessionTV;
//        private final TextView coursePrereqTV;
//        private final TextView coursePrereqHeader;
//        private final LinearLayout detailsLayout;
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            courseCodeTV = itemView.findViewById(R.id.idTVCourseCode);
//            courseNameTV = itemView.findViewById(R.id.idTVCourseName);
//
////            detailsLayout = new LinearLayout(itemView.getContext()) {
////                @Override
////                protected void onLayout(boolean changed, int l, int t, int r, int b) {
////                    final int count = getChildCount();
////                    for (int i = 0; i < count; i++) {
////                        View child = getChildAt(i);
////                        int width = child.getMeasuredWidth();
////                        int height = child.getMeasuredHeight();
////                        child.layout(width * i,
////                                height * i,
////                                width * (i + 1),
////                                height * (i + 1));
////                    }
////                }
////            };
//            detailsLayout = itemView.findViewById(R.id.bababa);
//            courseSessionTV = itemView.findViewById(R.id.idTVCourseSession);
//            coursePrereqTV = itemView.findViewById(R.id.idTVCoursePrereqs);
//            coursePrereqHeader = itemView.findViewById(R.id.idTVCoursePrereqsHeader);
//
//        }
//
//        // Reset parent and adding view to layout
////        private void addViewToLayout(TextView tv, LinearLayout layout) {
////            if(tv.getParent() != null) // {
////                ((ViewGroup)tv.getParent()).removeView(tv); // <- fix
//////            }
////            layout.addView(tv);
////        }
//
//        void bind(CourseModel model) {
//            // Get the state
//            boolean expanded = model.isExpanded();
//            // Set the visibility based on state
//            // detailsLayout.setVisibility(expanded ? View.VISIBLE : View.GONE);
//            detailsLayout.setVisibility(expanded ? View.VISIBLE : View.GONE);
//            courseCodeTV.setText(model.getCourseCode());
//            courseCodeTV.setTypeface(null, Typeface.BOLD);
//
//            courseNameTV.setText(model.getCourseName());
//            courseSessionTV.setText(model.getCourseSession());
//            coursePrereqTV.setText(model.getCoursePrereqs());
//
//            if (model.getCoursePrereqs().isEmpty()) {
//                if (coursePrereqTV.getParent() != null) {
//                    ((ViewGroup) detailsLayout).removeView(coursePrereqTV);
//                    ((ViewGroup) detailsLayout).removeView(coursePrereqHeader);
//                }
//            }
////            courseSessionTV.setVisibility(expanded ? View.VISIBLE : View.GONE);
////            coursePrereqTV.setVisibility(expanded ? View.VISIBLE : View.GONE);
//        }
//    }
//}
