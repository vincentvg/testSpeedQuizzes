package be.faros.quizzes;
import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.graphdb.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import be.faros.quizzes.domain.*;
import be.faros.quizzes.exceptions.IncorrectValueException;
import be.faros.quizzes.exceptions.ObjectAlreadyFoundException;
import be.faros.quizzes.exceptions.ObjectNotFoundException;
import be.faros.quizzes.exceptions.QuizException;
import be.faros.quizzes.repositories.QuestionRepositoryImpl;
import be.faros.quizzes.repositories.QuizRepositoryImpl;
import be.faros.quizzes.repositories.UserRepositoryImpl;



@ContextConfiguration(locations = {"classpath:META-INF/spring/app-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class SpeedTest {
	
	@Autowired
	private UserRepositoryImpl userRepo;
	@Autowired
	private QuizRepositoryImpl quizRepo;
	@Autowired
	private QuestionRepositoryImpl questionRepo;
	@Autowired
	private Neo4jTemplate template;

	
	/**
	 * Fills database with NodeEntities when they don't exist.
	 */
	@Before
	public void fill() {
		try {
		createParentNodes();
		for(int i = 0; i<100; i++){
			
			User user = new User("vin" +i, "vifdsn", "vinded", new Date(), "fdsfdsfdsf", "fdfdsf@fdsf.fds", new Date());
			user = userRepo.create(user);
			Quiz quiz = new Quiz("abc" + i, "description","{\"Quiz\":{\"type\":\"Interactive\",\"minPlayers\":\"1\",\"maxPlayers\":\"4\"}}",user,new Date(),true);
			quiz = quizRepo.create(quiz);
			
			Question q1 = new Question("{\"QuizQuestion\":{\"answer\":{\"text\":\"duh\"},\"question\":\"is vincent een vrouw?"+i+"\"}}", "ans", 18);
			Question q2 = new Question("{\"QuizQuestion\":{\"answer\":{\"text\":\"duh\"},\"question\":\"is koen homo?"+i+"\"}}", "ans", 18);
			Question q3 = new Question("{\"QuizQuestion\":{\"answer\":{\"text\":\"duh\"},\"question\":\"is robin homo?"+i+"\"}}", "ans", 18);
			questionRepo.addQuestion(quiz.getId(), q1);
			questionRepo.addQuestion(quiz.getId(), q2);
			questionRepo.addQuestion(quiz.getId(), q3);
		}
		} catch (IncorrectValueException e) {
			e.printStackTrace();
		} catch (ObjectAlreadyFoundException e) {
			e.printStackTrace();
		} catch (QuizException e) {
			e.printStackTrace();
		} catch (ObjectNotFoundException e) {
			e.printStackTrace();
		}
		

	}
	
	
	
	@Test
	public void testFindAllQuizzes(){
		
		quizRepo.findAllQuizzes();
		
	}
	
	
	
	private void createParentNodes(){
		if(!template.getReferenceNode().hasRelationship(RelTypes.USER_REFERENCE)){
			Node userReferenceNode  =template.createNode();	
			template.getReferenceNode().createRelationshipTo(userReferenceNode, RelTypes.USER_REFERENCE);
		} if(!template.getReferenceNode().hasRelationship(RelTypes.QUIZ_REFERENCE)){
			Node quizReferenceNode  =template.createNode();
			template.getReferenceNode().createRelationshipTo(quizReferenceNode, RelTypes.QUIZ_REFERENCE);
		} if(!template.getReferenceNode().hasRelationship(RelTypes.QUESTION_REFERENCE)){
			Node questionReferenceNode  =template.createNode();
			template.getReferenceNode().createRelationshipTo(questionReferenceNode, RelTypes.QUESTION_REFERENCE);
		}
	}

}
