package com.example.utt.database;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.utt.models.Course;
import com.example.utt.models.firebase.datamodel.CourseDataModel;
import com.example.utt.models.ExcludableCourse;
import com.example.utt.models.Listener;
import com.example.utt.models.Student;
import com.example.utt.models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.common.hash.Hashing;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import kotlin.NotImplementedError;

/** This class connects directly to the firebase database
 *
 */
public abstract class DatabaseHandler {

    private static final String emulatorURL = "https://utsc-b07-projcourses.firebaseio.com";
    private static final String databaseURL = "https://utsc-b07-projcourses-default-rtdb.firebaseio.com";

    // Logcat Tag Name
    private static final String TAG = "DATABASE HANDLER";

    // Connect to the firebase emulator
    private static final boolean USE_EMULATOR = false;

    // The root of the database JSON
    private static DatabaseReference dbRootRef;
    private static DatabaseReference dbCoursesRef;
    private static DatabaseReference dbUsersRef;
    private static DatabaseReference dbStudentsRef;

    private static ChildEventListener onCourseAdded;

    private DatabaseHandler() {initialise();}

    /**
     * Converts plaintext string into hashed string
     * @param plain the input string
     * @return sha256 hashed input string
     */
    public static String hashString(String plain) {
        return Hashing.sha256().hashBytes(plain.getBytes(StandardCharsets.UTF_8)).toString();
    }

    public static void getStudentData(String userId, Listener<String> callback) {
        Log.d("USER ID: ", userId);
        dbStudentsRef.orderByKey().equalTo(userId).get()
                .addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                    @Override
                    public void onSuccess(DataSnapshot dataSnapshot) {
                        // Check length
                        int i = (int) dataSnapshot.getChildrenCount();
                        if (i > 1 || i == 0) { callback.onFailure(null); }
                        else {
                            List<String> result = new ArrayList<>();
                            for (DataSnapshot child : dataSnapshot.getChildren()) {
                                Log.d("Student: ", child.toString()+" ");

                                for (DataSnapshot o : child.getChildren()) {
                                    result.add(Objects.requireNonNull(o.getValue()).toString());
                                }
                                callback.onSuccess(dataSnapshot.toString(), result);
                                return;
                            }
                        }
                    }
                });
    }

    /**
     * Queries the database for a user whose email and password match the given input
     * @param email The email provided by the user
     * @param rawPassword The password provided by the user
     * @param callback A callback interface to call after events are triggered
     */
    public static void getUser(String email, String rawPassword, Listener<User> callback) {
        String password = hashString(rawPassword);
        dbUsersRef.orderByChild("password").equalTo(password).getRef()
                .orderByChild("email").equalTo(email).get()
                .addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                    @Override
                    public void onSuccess(DataSnapshot dataSnapshot) {
                        // Check length
                        int i = (int) dataSnapshot.getChildrenCount();

                        if (i > 1 || i == 0) {callback.onFailure(null);}
                        else {
                            User instance;
                            for (DataSnapshot child : dataSnapshot.getChildren()) {
                                instance = child.getValue(User.class);
                                assert instance != null;
                                instance.setId(child.getKey());
                                callback.onSuccess(dataSnapshot.toString(), List.of(instance));
                                return;
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onFailure(e.toString());
                    }
                });
    }

    /**
     * Inserts into database.courses a new course.
     * @param course The object model of the course
     */
    public static void addCourse(CourseDataModel course) {
        DatabaseReference id = dbCoursesRef.push();
        id.setValue(course)
            .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {}
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(TAG, "Failure: " + e);
                }
            });
    }

    /**
     * Searches our collection for an existing course that shares the code, if it doesn't exist
     * then read the course object and insert into our database.
     * @param course The course to add into the database.
     */
    public static void addCourse(Course course) {
        CourseDataModel output = CourseDataModel.readCourse(course);
        DatabaseReference id = dbCoursesRef.push();
        id.setValue(output).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "Failure: " + e);
                    }
                });
    }

    public static void removeCourse(CourseDataModel course) {
        throw new NotImplementedError();
    }

    /**
     * Inserts into database.students a new student. Called by addUser()
     * @param userId A foreign key to the corresponding database.users entry
     * @param student The object model of the student
     */
    private static void addStudent(String userId, Student student) {
       dbStudentsRef.child(userId).setValue(student._getCoursesTaken());
    }

    /**
     * Inserts into database.users a new user.
     * If the User was upcasted from Student, calls addStudent()
     * @param user The object model of the user
     */
    public static void addUser(User user) {
        DatabaseReference newUser = dbUsersRef.push();
        newUser.setValue(user);
        // Generate entry in student table
        if (user instanceof Student) {
            addStudent(newUser.getKey(), (Student) user);
        }
    }

    /**
     * Called on startup.
     */
    private static void attachCourseListener() {
        if (onCourseAdded == null) onCourseAdded = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot,
                                     @Nullable String previousChildName) {
                Log.d(TAG, "Added Course: " + snapshot);

                // TODO: Safety Checks
                CourseDataModel result = snapshot.getValue(CourseDataModel.class);
                assert result != null;
                result.setKey(snapshot.getKey());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot,
                                       @Nullable String previousChildName) {
                Log.d(TAG, "Child Changed: " + snapshot + "\n" + previousChildName);

                CourseDataModel photo = snapshot.getValue(ExcludableCourse.class);
                CourseDataModel.updateCourse(Objects.requireNonNull(photo), snapshot.getKey());//previousChildName);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Log.d(TAG, "Removed Course: " + snapshot);
                // TODO: Safety Checks
                CourseDataModel photo = snapshot.getValue(ExcludableCourse.class);
                assert photo != null;
                photo.setKey(snapshot.getKey());
                CourseDataModel.removeCourse(Objects.requireNonNull(photo));
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot,
                                     @Nullable String previousChildName) {}

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "Cancelled? " + error);
            }
        };
        dbCoursesRef.addChildEventListener(onCourseAdded);
    }

    /**
     * Connects to the Firebase Database (Emulator or online) and then assigns the reference vars.
     */
    private static void connect() {
        FirebaseDatabase database;
        if (USE_EMULATOR) { // Connect to the local database
            database = FirebaseDatabase.getInstance(emulatorURL);
            database.useEmulator("10.0.2.2", 9000);
            Log.d(TAG, "Emulator Connected");
        } else { // Connect to the online database
            database = FirebaseDatabase.getInstance(databaseURL);
        }
        // Assign references to the top-level tables
        dbRootRef = database.getReference();
        dbCoursesRef = dbRootRef.child("courses");
        dbUsersRef = dbRootRef.child("users");
        dbStudentsRef = dbRootRef.child("students");
    }

    public static void initialise() {
        Log.d(TAG, "Initialising Database");
        connect();

        // Begin listening to new data provided
        attachCourseListener();
        generateSample();

        Log.d(TAG, "Database initialised.");
    }

    // TODO - Solve this and then remove it
    private static void queryTesting() {
        // Testing stuff again
        String query = "CSC";

        dbCoursesRef.orderByChild("code")
                .startAt("%${query}%")
                .endAt(query+"\uf8ff")
                .get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                    @Override
                    public void onSuccess(DataSnapshot dataSnapshot) {
                        Log.d("DATABASE STUFF", dataSnapshot.toString());
                        ArrayList<CourseDataModel> courses = new ArrayList<>();
                        for (DataSnapshot s : dataSnapshot.getChildren()) {
                            courses.add(s.getValue(CourseDataModel.class));
                        }

                        Log.d("DATABASE: ", courses.toString());
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("DATABASE STUFF", e.toString());
                    }
                });
    }

    //    public static void queryCourseMatchString(String childName)
    public static void generateSample() {
        Log.d("TEST", "Running Tests");
        dbRootRef.setValue(null);
        DatabaseSamples.generate();
    }
}