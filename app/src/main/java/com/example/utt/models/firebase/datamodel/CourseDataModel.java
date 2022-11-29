package com.example.utt.models.firebase.datamodel;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.utt.algorithm.model.Term;
import com.example.utt.algorithm.model.YearlySession;
import com.example.utt.models.Course;
import com.example.utt.models.CourseEventListener;
import com.google.firebase.firestore.Exclude;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

// Accessed by DatabaseHandler.java and Course.java
public class CourseDataModel {
    private final static String TAG = "Course DataModel";
    // Global collection of listeners.
    private static List<CourseEventListener> listeners;

    // Course-specific collection of listeners
    private List<CourseEventListener> myListeners;

    private static Map<String, CourseDataModel> courses;
    private static Map<String, String> coursesID_CODE; // Key ID -> Code

    // The Course object that this CourseDataModel is associated with.
    @Exclude
    private Course courseObject;

    @Exclude
    protected String key;

    // Will be converted into Database Fields
    protected String code;
    private String name;
    private List<Boolean> sessionOffering;
    private List<String> prerequisites;

    // Constructors
    // Empty Constructor, required for API: Attaches a listener
    public CourseDataModel() {
        if (courses == null) {coursesID_CODE = new HashMap<>(); courses = new HashMap<>();}
        if (myListeners == null) { myListeners = new ArrayList<>(); }
        prerequisites = new ArrayList<>();
        sessionOffering = List.of(false, false, false);
    }

    // public CourseDataModel(String name, String code, Map<Integer, Boolean> sessions, List<String> prerequisites) {
    public CourseDataModel(String name, String code, List<Boolean> sessions, List<String> prerequisites) {
        this();
        setCode(code);
        this.name = name;
        this.sessionOffering = sessions;
        this.prerequisites = prerequisites;
    }

    // Static Methods
    public static Map<String, Course> getCourses() {
        Map<String, Course> result = new HashMap<>();
        for (Map.Entry<String, CourseDataModel> courseDM : courses.entrySet()) {
            result.put(courseDM.getKey(), courseDM.getValue().getCourseObject());
        }
        return result;
    }

    @Exclude
    public Course getCourseObject() { return courseObject; }

    public void setCourseObject(Course course) {
        courseObject = course;
    }

    // Called on persistent listeners updating course.
    public void updateCourseObject() {
        Course newContent = generateCourseObject();
        courseObject.setCourseCode(newContent.getCode());
        courseObject.setName(newContent.getName());
        courseObject.setPrerequisites(newContent.getPrerequisites());
        courseObject.setSessionOffering(newContent.getSessionOffering());
    }

    public static CourseDataModel getCourseDataModel(String courseCode) {
        return courses.get(courseCode);
    }

    public static Course getCourse(String course) {
        if (getCourseDataModel(course) == null) return null;
        return getCourseDataModel(course).getCourseObject();
    }

    // This does not check to see if a course code exists
    public static void updateCourse(CourseDataModel course, String key) {
        // Fetch the course with the given key:
        Log.i(TAG, "Modified Information: " + course.toString());
        String keyRef = coursesID_CODE.get(key);

        if (courses.containsKey(keyRef)) {
            CourseDataModel oldCourse = Objects.requireNonNull(courses.get(keyRef));
            course.transferCourseListeners(Objects.requireNonNull(courses.get(keyRef)).myListeners);

            // Transfer child
            course.setCourseObject(oldCourse.getCourseObject());
            course.updateCourseObject();

            // Call that courses local listeners
            oldCourse.callCourseChangedListeners(course);

            Log.d(TAG, "Updated Courses ID_CODE: " + coursesID_CODE.get(key));

            courses.remove(keyRef); // We can keep a reference to this later but idk what you'll do with it
            courses.put(course.code, course);
            coursesID_CODE.put(key, course.code); // Update code
            // courses.put(keyRef, course); // We then get a duplicate of the old course
            Log.d(TAG,"New Value: " + coursesID_CODE.get(key));
        } else {
            Log.d(TAG, "Failed to find key: " + course.code);
        }

        if (listeners != null) for (CourseEventListener obj : listeners) obj.onCourseChanged(course.getCourseObject());
    }

    public static void removeCourse(CourseDataModel course) {
        Log.i(TAG, "Removed Course: " + course.toString() + course.getKey());
        // Call that courses local listeners
        String keyRef = coursesID_CODE.get(course.key);
        Log.d(TAG, courses.toString() + "\n\t with key reference (" + keyRef + ")");
        CourseDataModel oldCourse = Objects.requireNonNull(courses.get(keyRef));
        oldCourse.callCourseRemovedListeners();

        // Remove the references
//        courses.remove(course.code);
//        coursesID_CODE.remove(course.key);
        if (listeners != null) for (CourseEventListener obj : listeners) obj.onCourseRemoved(oldCourse.getCourseObject());

    }

    /**
     * Adds course to the tracking collection
     * @param course The course to be added.
     */
    private static void addCourse(CourseDataModel course) {
        Log.i(TAG, "Added Course: " + course.toString());
        courses.put(course.code, course);
        if (course.key != null) Log.i("ADDED ", course.key + " -> " + course.code);
        if (listeners != null) for (CourseEventListener obj : listeners) obj.onCourseAdded(course.getCourseObject());
    }

    public static void addListener(CourseEventListener listener) {
        if (listeners == null) listeners = new ArrayList<>();
        listeners.add(listener);
    }

    public static void removeListener(CourseEventListener listener) {
        Log.d("COURSE", "Detached Listener.");
        listeners.remove(listener);
    }

    // Getters
    @Exclude
    public String getKey() { return key; }
    public String getCode() { return code; }
    public String getName() { return name; }
    public Map<String, Boolean> getSessionOffering() {
        Map<String, Boolean> result = new HashMap<>();
        //for (Integer c : sessionOffering)//sessionOffering.keySet())
        //    result.put(c.toString(), sessionOffering.get(c));
        for (int i = 0; i < sessionOffering.size(); i++) {
            result.put(Integer.toString(i), sessionOffering.get(i));
        }
        return result;
    }
    public Map<String, String> getPrerequisites() {
        Map<String, String> result = new HashMap<>();
        for (int i = 0; i < prerequisites.size(); i++)
            result.put(Integer.toString(i), prerequisites.get(i));
        return result;
    }

    // Contains the courses that are prerequisites or have been recently deleted
    private static final HashMap<String, Course> nonExistentCourses = new HashMap<>();

    // TODO - Simplify this
    // Called by setKey()
    protected Course generateCourseObject() {
        Log.d(TAG, "Generating Course Object for " + code);
        // Convert to the required types
        List<Course> child_prerequisites = new ArrayList<>();
        List<YearlySession> child_sessionOfferings = new ArrayList<>();

        for (String prerequisite : prerequisites) {
            // check if course exists.
            Course prerequisiteCourse = CourseDataModel.getCourses().get(prerequisite);
            if (prerequisiteCourse == null) {
                // Check if prerequisite exists in the Nonexistent-Course Collection
                if (!nonExistentCourses.containsKey(prerequisite)) {    // Course not yet in NEC
                    prerequisiteCourse = new Course();
                    nonExistentCourses.put(prerequisite, prerequisiteCourse);
                    Log.d(TAG, code + " | Looking for: " + prerequisite + " | -> NEC: " + nonExistentCourses);
                } else {    // Course exists so placeholder reference it.
                    prerequisiteCourse = nonExistentCourses.get(code);
                }
            }
            Log.d("Adding: ", prerequisite + prerequisiteCourse);
            child_prerequisites.add(prerequisiteCourse);
            //CourseDataModel.getCourses().get(prerequisite));
        }

        // TODO - Fix this
        if (Boolean.TRUE.equals(sessionOffering.get(0))) child_sessionOfferings.add(new YearlySession(Term.WINTER));
        if (Boolean.TRUE.equals(sessionOffering.get(1))) child_sessionOfferings.add(new YearlySession(Term.SUMMER));
        if (Boolean.TRUE.equals(sessionOffering.get(2))) child_sessionOfferings.add(new YearlySession(Term.FALL));

//        StringBuilder res = new StringBuilder();
//        for (YearlySession y : child_sessionOfferings) {
//            res.append(y.getTerm());
//        }
//        Log.d("Session Offering", res.toString());
        // Check if this course exists in our nonexistant Course collection
        Course child = nonExistentCourses.remove(code);
        if (child == null) child = new Course();

        child.setCourseCode(code);
        child.setName(name);
        child.setPrerequisites(child_prerequisites);
        child.setSessionOffering(child_sessionOfferings);
        return child;
    }

    // Setters

    /**
     * Called after deserializing.
     * Set object key and insert into a collection that associates key to code.
     * Additionally, generates a Course object
     * @param key The courses key generated by firebase
     */
    @Exclude
    public void setKey(String key) {
        this.key = key;
        coursesID_CODE.put(this.key, this.code);

        Log.i(TAG, "-> Referencing Key: " + key + " " + coursesID_CODE.get(key));

        setCourseObject(generateCourseObject());
        CourseDataModel.addCourse(this);
    }

    public void setName(String name) { this.name = name; }
    public void setCode(String code) {
        this.code = code;
    }
    public void setSessionOffering(ArrayList<Boolean> session) {
        sessionOffering = session;
    }
    public void setPrerequisites(List<String> prerequisites) {
        this.prerequisites = prerequisites;
    }

    public boolean equals(Object other) {
        if (!(other instanceof CourseDataModel)) return false;
        return name.equals(((CourseDataModel) other).getName());
    }

    private void callCourseRemovedListeners() { // Pass reference of self
        for (CourseEventListener listener : myListeners) listener.onCourseRemoved(this.getCourseObject());
    }
    private void callCourseChangedListeners(CourseDataModel newCourse) { // Pass new course
        for (CourseEventListener listener : myListeners) listener.onCourseChanged(newCourse.getCourseObject());
    }
    private void transferCourseListeners(List<CourseEventListener> listeners) {this.myListeners = listeners; }

    /**
     * Similar to Course.addListener() except it triggers when new information is sent to this course.
     * @param listener The listener object to include
     */
    public void addCourseListener(CourseEventListener listener) {
        myListeners.add(listener);
    }

    public void removeCourseListener(CourseEventListener listener) {
        myListeners.remove(listener);
    }

    /** Converts the Course object into a CourseDataModel
     * @param course The Course to convert.
     * @return the CourseDataModel generated from Course
     */
    public static CourseDataModel readCourse(Course course) {
        // Convert YearlySession objects to ArrayList<Boolean> session
        ArrayList<Boolean> sessions = new ArrayList<>(List.of(false, false, false));

        if (course.getSessionOffering() != null) {
            for (YearlySession session : course.getSessionOffering()) {
                sessions.set(session.getTerm().getTerm(), true);
            }
        }

        // Convert Course object Prerequisites
        List<String> prerequisites = new ArrayList<>();

        if (course.getPrerequisites() != null) {
            for (Course prerequisite : course.getPrerequisites()) {
                prerequisites.add(prerequisite.getCode());
            }
        }

        CourseDataModel result = new CourseDataModel();

        result.setName(course.getName());
        result.setCode(course.getCode());
        result.setSessionOffering(sessions);
        result.setPrerequisites(prerequisites);

        return result;
    }
    @NonNull
    public String toString() {
        return code + ": " + name;
    }
}
