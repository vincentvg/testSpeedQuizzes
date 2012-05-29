package be.faros.quizzes.domain;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
public class Question {
	@GraphId
	private Long questionId;
	@Indexed
	private String question;
	private String answer;
	private boolean enabled;
	private int maxTime;

	/**
	 * Empty constructor
	 * used by neo4j
	 */
	public Question(){
		
	}


	/**
	 * Constructor that initializes a new Question object
	 * @param question the question itself as a string of a json object
	 * @param answer the answer of the question
	 * @param maxTime maximum amount of time the quizzers can use to answer this question
	 */
	public Question(String question, String answer,int maxTime) {
		super();
		setAnswer(answer);
		setQuestion(question);		
		setMaxTime(maxTime);
		setEnabled(true);
	}
	
	/**
	 * @return id of the question
	 */
	public Long getQuestionId() {
		return questionId;
	}

	/**
	 * @return question as a string
	 */
	public String getQuestion() {
		return question;
	}

	/**
	 * @return answer of the question
	 */
	public String getAnswer() {
		return answer;
	}

	/**
	 * @return whether the question is usable or not
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * @return the max time users can spent on this question
	 */
	public int getMaxTime() {
		return maxTime;
	}

	/**
	 * Setter for the id of the question
	 * @param questionId value to be set
	 */
	public void setQuestionId(Long questionId) {
		if(questionId != 0){
			this.questionId = questionId;
		}
	}

	/**
	 * Setter for the questionstring of the question object
	 * @param question value to be set. A string representation of a json object
	 */
	public void setQuestion(String question) {
		if(question!=null){
			this.question = question;
		}
	}
	
	/**
	 * Setter for the answer of the question
	 * @param answer value to be set
	 */
	private void setAnswer(String answer){
		if(answer!=null){
			this.answer= answer;
		}
	}
	
	/**
	 * Setter for the availability of the question
	 * @param enabled
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * Setter for the maximum time users have to answer the question
	 * @param maxTime value to be set
	 */
	public void setMaxTime(int maxTime) {
		if(maxTime >= 0){
			this.maxTime = maxTime;
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		return getQuestion() + "   " ;//+ getAnswer();
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o){
		if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Question question = (Question) o;
       // if (userId == null) return super.equals(o);
        if(question.getQuestionId() == null) return false;
        if(getQuestionId() == question.getQuestionId()){
        	return true;
        }return false;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode(){
		int result = getQuestionId().hashCode();
		return result;
	}


}
