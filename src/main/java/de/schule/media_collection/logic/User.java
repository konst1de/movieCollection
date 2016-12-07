package de.schule.media_collection.logic;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User {
	
	private final IntegerProperty id;
	private final StringProperty userName;
	private final StringProperty firstName;
	private final StringProperty lastName;

	public User() {
		this(0, null, null, null);
	}
	
	public User(int id, String userName, String firstName, String lastName) {
		this.id = new SimpleIntegerProperty(id);
		this.userName = new SimpleStringProperty(userName);
		this.firstName = new SimpleStringProperty(firstName);
		this.lastName = new SimpleStringProperty(lastName);
	}
		
	public IntegerProperty idProperty() {
		return this.id;
	}
	
	public int getId() {
		return this.idProperty().get();
	}

	public void setId(int id) {
		this.idProperty().set(id);
	}
	
	public StringProperty userNameProperty() {
		return this.userName;
	}

	public String getUserName() {
		return this.userNameProperty().get();
	}

	public void setUserName(String userName) {
		this.userName.set(userName);
	}

	public StringProperty firstNameProperty() {
		return this.firstName;
	}
	
	public String getFirstName() {
		return this.firstName.get();
	}

	public void setFirstName(String firstName) {
		this.firstName.set(firstName);
	}

	public StringProperty lastNameProperty() {
		return this.lastName;
	}
	
	public String getLastName() {
		return this.lastName.get();
	}

	public void setLastName(String lastName) {
		this.lastName.set(lastName);
	}
}