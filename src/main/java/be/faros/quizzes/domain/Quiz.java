package be.faros.quizzes.domain;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import be.faros.quizzes.exceptions.QuizException;

@NodeEntity
public class Quiz {
	@GraphId
	private Long id;
	@Indexed
	private String name;
	private Date dateCreated;
	@RelatedTo(type="CREATED_BY",direction=Direction.OUTGOING)
	@Fetch
	private User creator;
	private String description;
	private boolean enabled;
	private String imageURL;
	@Indexed
	private double rating;	
	private boolean visible;
	private int maxTime;
	private String quizInfo;
	
	
	@RelatedTo(type="HAS",direction = Direction.OUTGOING)
//	@Fetch
	private Set<Question> questions;

	
	
	/**
	 * empty constructor
	 * used by neo4j
	 */
	public Quiz()
	{
		
	}
		
	/**
	 * Constructor
	 * Initializes a new Quiz object with the given values
	 * @param name name of the quiz
	 * @param description short description of the quiz
	 * @param creator user that created the quiz
	 * @param creationdate date of creation
	 * @param visible private or public quiz
	 */
	public Quiz(String name,String description,String quizInfo,User creator,Date creationdate,boolean visible){
		setName(name);
		setDescription(description);
		setQuizInfo(quizInfo);
		setCreator(creator);		
		setDateCreated(creationdate);
		setVisibility(visible);
		setEnabled(true);
		
	}
	
	//GETTERS & SETTERS

	
	/**
	 * @return id of the quiz
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return name of the quiz
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return date of creation
	 */
	public Date getDateCreated() {
		return dateCreated;
	}

	/**
	 * @return user that created the quiz
	 */
	public User getCreator() {
		return creator;
	}

	/**
	 * @return description of the quiz
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the availability of the quiz
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * @return imageURL of the quiz
	 */
	public String getImageURL() {
		return imageURL;
	}

	/**
	 * @return set of questions 
	 */
	public Set<Question> getQuestions() {
		return questions;
	}



	/**
	 * @return the rating of the quiz
	 */
	public double getRating() {
		return rating;
	}

	/**
	 * @return public or private quiz
	 */
	public boolean isVisible() {
		return visible;
	}

	
	/**
	 * @return the maxTime
	 */
	public int getMaxTime() {
		return maxTime;
	}

	

	/**
	 * @return the quizInfo
	 */
	public String getQuizInfo() {
		return quizInfo;
	}

	/**
	 * @param quizInfo the quizInfo to set
	 */
	public void setQuizInfo(String quizInfo) {
		this.quizInfo = quizInfo;
	}

	

	/**
	 * @param maxTime the maxTime to set
	 */
	public void setMaxTime(int maxTime) {
		this.maxTime = maxTime;
	}

	/**
	 * Setter for the id of the quizobject
	 * @param id value to be set
	 */
	public void setId(Long id) {
		if(id!=0){
			this.id = id;
		}
	}

	/**
	 * Setter for the set of questions of the quizobject
	 * @param questions value to be set
	 */
	public void setQuestions(Set<Question> questions) {
		this.questions = questions;
	}

	

	/**
	 * Setter for the rating of the quizobject
	 * @param rating value to be set
	 */
	public void setRating(double rating) {
		if(rating >= 0.0 && rating <= 5.0){
			this.rating = rating;
		}
	}

	/**
	 * Setter for the visibility of the quiz
	 * @param visibility value to be set
	 */
	public void setVisibility(boolean visibility) {		
		this.visible = visibility;		
	}

	/**
	 * Setter for the name of the quiz
	 * @param name value to be set
	 */
	public void setName(String name) {
		String regex = "^([a-zA-Z0-9_ ])+{3,50}$";
		if(name !=null){
			if(name.matches(regex)){
				this.name = name;
			}
		}
	}

	/**
	 * Setter for the creationdate of the quiz
	 * @param dateCreated value to be set
	 */
	public void setDateCreated(Date dateCreated) {
		if(dateCreated!=null){
			this.dateCreated = dateCreated;
		}
	}

	/**
	 * Setter for the user that created the quiz
	 * @param creator value to be set
	 */
	public void setCreator(User creator) {
		if(creator!=null){
			this.creator = creator;
		}
	}

	/**
	 * Setter for the description fo the quiz
	 * @param description value to be set
	 */
	public void setDescription(String description) {
		String regex = "^([a-zA-Z0-9_\\.?:!ιΰθηρωυλο, ])+{3,500}$";
		if(description!=null){
			if(description.matches(regex)){
				this.description = description;
			}
		}
	}

	/**
	 * Setter for the availability of the quiz
	 * @param enabled value to be set
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * Setter for the imageURL of the quiz
	 * @param imageURL value to be set
	 */
	public void setImageURL(String imageURL) {
		if(imageURL!=null){
			
				this.imageURL = imageURL;
			
		}
	}	
	
		

	/**
	 * Method which is used to add a question to the quiz
	 * @param q  question to be added to the set of questions
	 * @throws QuizException thrown when q is already added
	 */
	public void addQuestion(Question q) throws QuizException{
		if(this.getQuestions()== null){
			questions= new LinkedHashSet<Question>();
			questions.add(q);
		}else if(!this.getQuestions().contains(q)){
			this.getQuestions().add(q);
		}else throw new QuizException("Question reeds toegevoegd aan quiz");				
	}

	/**
	 * Method which is used to remove a question from the quiz
	 * @param q the question to be removed from the set
	 * @throws QuizException thrown when q isn't in the set 
	 */
	public void removeQuestion(Question q) throws QuizException{
		if(this.getQuestions().contains(q)){
			this.getQuestions().remove(q);
		}else throw new QuizException("Quiz bevat geen question die gelijk is aan het gegeven object");
	}		
	
		
	
	
	
	
	
	
	
	
	@Override
	public boolean equals(Object o){
		if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Quiz quiz = (Quiz) o;
       // if (userId == null) return super.equals(o);
        return getId().equals(quiz.getId());
	}
	

	@Override
	public int hashCode(){
		int result = getId().hashCode();
		return result;
	}

	public String toString(){
		return "Name:" + this.getName() + "\nDescription:" + this.getDescription() ;
	}

	
}
