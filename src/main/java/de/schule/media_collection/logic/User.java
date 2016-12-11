package de.schule.media_collection.logic;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * General class that provides informations given by the datalayer
 * @author ReneSachse
 *
 */
public class User {
	
	private final IntegerProperty id;
	private final StringProperty userName;
	private final StringProperty firstName;
	private final StringProperty lastName;

	/**
	 * Default Constructor
	 */
	public User() {
		this(0, null, null, null);
	}
	
	/**
	 * Full Constructor filled with parameters
	 * @param id
	 * @param userName
	 * @param firstName
	 * @param lastName
	 */
	public User(int id, String userName, String firstName, String lastName) {
		this.id = new SimpleIntegerProperty(id);
		this.userName = new SimpleStringProperty(userName);
		this.firstName = new SimpleStringProperty(firstName);
		this.lastName = new SimpleStringProperty(lastName);
	}
	
	/**
	 * Returns the javaFX id property
	 * @return IntegerProperty
	 */
	public IntegerProperty idProperty() {
		return this.id;
	}
	
	/**
	 * Getter for the id
	 * @return integer
	 */
	public int getId() {
		return this.idProperty().get();
	}

	/**
	 * Setter for the id
	 * @param id
	 */
	public void setId(int id) {
		this.idProperty().set(id);
	}
	
	/**
	 * Returns the javaFX userName property
	 * @return StringProperty
	 */
	public StringProperty userNameProperty() {
		return this.userName;
	}

	/**
	 * Getter for the user name
	 * @return String
	 */
	public String getUserName() {
		return this.userNameProperty().get();
	}

	/**
	 * Setter for the user name
	 * @param userName
	 */
	public void setUserName(String userName) {
		this.userName.set(userName);
	}

	/**
	 * Returns the javaFX firstName property
	 * @return StringProperty
	 */
	public StringProperty firstNameProperty() {
		return this.firstName;
	}
	
	/**
	 * Getter for the first name
	 * @return String
	 */
	public String getFirstName() {
		return this.firstName.get();
	}

	/**
	 * Setter for the first name
	 * @param firstName
	 */
	public void setFirstName(String firstName) {
		this.firstName.set(firstName);
	}

	/**
	 * returns the javaFX lastName property
	 * @return StringProperty
	 */
	public StringProperty lastNameProperty() {
		return this.lastName;
	}
	
	/**
	 * Getter for the last name
	 * @return String
	 */
	public String getLastName() {
		return this.lastName.get();
	}

	/**
	 * Setter for the last name
	 * @param lastName
	 */
	public void setLastName(String lastName) {
		this.lastName.set(lastName);
	}
}