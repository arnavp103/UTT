package com.example.utt;


import androidx.annotation.Nullable;

import com.example.utt.database.DatabaseHandler;
import com.example.utt.models.Course;
import com.example.utt.models.Listener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import kotlin.NotImplementedError;

/* A bunch of concrete methods for dealing with the database
 * They should all be concrete so they can be inherited from the specific
 * models
 * for example a loginModel would extend BaseModel and could only use the
 * methods it needs as opposed to having query methods for courses when you
 * don't need it in the login. The loginModel could then query the students 
 * and do all the logic of handling the dataSnapshot and removing null values.
 * It sends it result to the presenter who manipulates the view accordingly
 */
public abstract class BaseModel {
	// Has the database references
	 DatabaseReference dbStudents;
	
	public void queryStudentByID(String uname) {
		// TODO
		throw new NotImplementedError();
	}

	public void queryCoursesByName(String name, Listener<Course> callback) {
		DatabaseHandler.queryCourseWithField("name", name, callback);
	}

	// add all the other database handling stuff as needed
	public void addCourse(Course course) {
		DatabaseHandler.addCourse(course);
	}

	// note that every subclass of this should define their own interface of what
	// possible states can occur so that the presenter can handle them
	// for the login model their should be an interface that covers onLoginSuccess 
	// and onLoginFailure. Technically speaking there should be an
	// onDatabaseFailure as well but we'll just have the model throw an error
}

