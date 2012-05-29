package be.faros.quizzes.domain;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import be.faros.quizzes.exceptions.*;






@NodeEntity
public class User {

	@GraphId
	private Long userId;
	//@Indexed(indexName="userName",fieldName="userName",indexType=IndexType.FULLTEXT)
	@Indexed
	private String username;
	@Indexed
	private String firstname;
	@Indexed
	private String surname;
	private Date birthdate;
	private Date registerdate;

	private String password;	
	
	private String imageUrl;
	private String email;
	


	
	/**
	 * empty constructor
	 * used by neo4j
	 */
	public User(){
		
	}
	
	public User(String username) throws IncorrectValueException{
		setUsername(username);
	}
		
	
	/**
	 * Constructor
	 * Initializes a new User object with the given parameters
	 * @param username username the user uses to login/logout
	 * @param firstname first name of user 
	 * @param surname last name of user
	 * @param birthdate date of birth of user
	 * @param password password of user
	 * @param email email of user
	 * @throws IncorrectValueException 
	 */
	public User(String username, String firstname, String surname,
			Date birthdate, String password, String email) throws IncorrectValueException {	
		setUsername(username);
		setFirstname(firstname);
		setSurname(surname);
		setBirthdate(birthdate);
		setPassword(password);
		setEmail(email);
		setRegisterdate(new Date());
	}

	/**
	 * Constructor
	 * Initializes a new User object with the given parameters
	 * @param username username the user uses to login/logout
	 * @param firstname first name of user 
	 * @param surname last name of user
	 * @param birthdate date of birth of user
	 * @param password password of user
	 * @param email email of user
	 * @param registerdate date of registration
	 * @throws IncorrectValueException 
	 */
	public User(String username, String firstname, String surname,
			Date birthdate, String password, String email,Date registerdate) throws IncorrectValueException {
		
		setUsername(username);
		setFirstname(firstname);
		setSurname(surname);
		setBirthdate(birthdate);
		setPassword(password);
		setEmail(email);
		setRegisterdate(registerdate);
	}

	
    
	/**
	 * @return id of the user
	 */
	public Long getUserId() {
		return userId;
	}


	/**
	 * @return username of the user
	 */
	public String getUsername() {
		return username;
	}


	/**
	 * @return first name of the user
	 */
	public String getFirstname() {
		return firstname;
	}


	/**
	 * @return surname of the user
	 */
	public String getSurname() {
		return surname;
	}


	/**
	 * @return date of birth
	 */
	public Date getBirthdate() {
		return birthdate;
	}


	/**
	 * @return date of registration
	 */
	public Date getRegisterdate() {
		return registerdate;
	}





	/**
	 * @return password of the user
	 */
	public String getPassword() {
		return password;
	}



	/**
	 * @return imageURL of the user
	 */
	public String getImageUrl() {
		return imageUrl;
	}


	/**
	 * @return email of the user
	 */
	public String getEmail() {
		return email;
	}


	

	/**
	 * Setter for theid variable of the userobject
	 * @param userId value to be set
	 */
	public void setUserId(Long userId) {
		if(userId !=0){
			this.userId = userId;
		}
	}



	/**
	 * Setter for the username of the user
	 * @param username value to be set
	 * @throws IncorrectValueException 
	 */
	public void setUsername(String username) throws IncorrectValueException {
		String regex ="^([a-zA-Z0-9_ ])+{4,25}$";	
		if(username != null){			
			if(username.matches(regex)){				
				this.username = username;
			}else{
				System.out.println("Username mag enkel alfanumerieke tekens bevatten!");
				throw new IncorrectValueException("Username mag enkel alfanumerieke tekens bevatten!");
			}
		}
	}


	/**
	 * Setter for the first name of the user
	 * @param firstname value to be set
	 * @throws IncorrectValueException 
	 */
	public void setFirstname(String firstname) throws IncorrectValueException {	
		String regex = "^([a-zA-Z יאט])+{1,25}$";
		if(firstname != null){
			if(firstname.matches(regex)){
				this.firstname = firstname;
			}else{
				System.out.println("first name mag enkel alfanumerieke tekens bevatten!");
				throw new IncorrectValueException("first name mag enkel alfanumerieke tekens bevatten!");
			}
		}
	}


	/**
	 * Setter for the surname of the user
	 * @param surname value to be set
	 * @throws IncorrectValueException 
	 */
	public void setSurname(String surname) throws IncorrectValueException {
		String regex = "^([a-zA-Z ])+{1,25}$";
		if(surname !=null){
			if(surname.matches(regex)){
				this.surname = surname;
			}else{
				System.out.println("surname mag enkel alfanumerieke tekens bevatten!");
				throw new IncorrectValueException("surname mag enkel alfanumerieke tekens bevatten!");
			}
		}
	}


	/**
	 * Setter for the birthdate of the suer
	 * @param birthdate value to be set
	 */
	public void setBirthdate(Date birthdate) {
		if(birthdate!=null){
			this.birthdate = birthdate;
		}
	}


	/**
	 * Setter for the date of registration of the user
	 * @param registerdate value to be set
	 */
	public void setRegisterdate(Date registerdate) {
		if(registerdate !=null){
			this.registerdate = registerdate;
		}
	}


	


	/**
	 * Setter for the password of the user
	 * @param password value to be set
	 * @throws IncorrectValueException 
	 */
	public void setPassword(String password) throws IncorrectValueException {
		String regex = "^([a-zA-Z_0-9 ])+{6,35}$";		
		if(password!=null){
			if(password.matches(regex)){
				this.password = password;
			}else{
				System.out.println("Passwoord moet minstens 9 karakters bevatten!");
				throw new IncorrectValueException("Passwoord moet minstens 9 karakters bevatten!");
			}
		}	
//		this.password = password;
	}

	




	/**
	 * Setter for the imageURL of the user
	 * @param imageUrl value to be set
	 */
	public void setImageUrl(String imageUrl) {
		if(imageUrl!=null){
			this.imageUrl = imageUrl;
		}
	}


	/**
	 * Setter for the email of the user
	 * @param email value to be set
	 */
	public void setEmail(String email) {
		if(email !=null){
			this.email = email;
		}
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o){
		if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;      
        return getUserId().equals(user.getUserId());
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode(){
		int result = getUserId().hashCode();
		return result;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		return this.getFirstname() + " " + this.getSurname() + "\n" + this.getUsername();
	}
	
	
	/**
	 * Method that allows the user to update the password
	 * @param old representation of the old password
	 * @param newPass1 newpassword
	 * @param newPass2 newpassword
	 */
	public void updatePassword(String old, String newPass1, String newPass2) {
        if (!password.equals(encode(old))) throw new IllegalArgumentException("Existing Password invalid");
        if (!newPass1.equals(newPass2)) throw new IllegalArgumentException("New Passwords don't match");
        this.password = encode(newPass1);
    }
	
	
	/**
	 * Method to check if the user entered coorect password
	 * @param passw password entered by the user
	 * @return true if the passwords match
	 */
	public boolean checkPassword(String passw){
		if(this.getPassword().equals(encode(passw))){
			return true;
		}
		return false;
	}
	
	/**
	 * Method that encodes the password withan algorithm
	 * @param password 
	 * @return encoded string
	 */
	public String encode(String password){
		MessageDigest m;
		try {
			m = MessageDigest.getInstance("MD5");
			m.update(password.getBytes(),0,password.length());
	        String p = new BigInteger(1,m.digest()).toString(16).toString();
	       return p;
	        
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}	
}

