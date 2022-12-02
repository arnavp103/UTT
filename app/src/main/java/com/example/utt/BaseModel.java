package com.example.utt;

/* A bunch of concrete methods for dealing with the database
 * They should all be concrete so they can be inherited from the specific
 * models
 * for example a loginModel would extend BaseModel and could only use the
 * methods it needs as opposed to having query methods for courses when you
 * don't need it in the login. The loginModel could then query the students 
 * and do all the logic of handling the dataSnapshot and removing null values.
 * It sends it result to the presenter who manipulates the view accordingly
 */
public abstract Class BaseModel {
	// Has the database references
	// Databasereference dbStudents;	
	
	public dataSnapshot queryStudentsByName(String uname) {
		// TODO
	}

	public dataSnapshot queryCoursesByID(int ID) {
		// TODO
	}

	// add all the other database handling stuff as needed
	public void addCourse(String courseCode) {
	}

	// note that every subclass of this should define their own interface of what
	// possible states can occur so that the presenter can handle them
	// for the login model their should be an interface that covers onLoginSuccess 
	// and onLoginFailure. Technically speaking there should be an
	// onDatabaseFailure as well but we'll just have the model throw an error
}

