package be.faros.quizzes.repositories;

import org.neo4j.graphdb.Relationship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.repository.IndexRepository;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import be.faros.quizzes.domain.*;
import be.faros.quizzes.exceptions.*;


@Repository("questionrepo")
public class QuestionRepositoryImpl {
	

	@Autowired
    private QuizRepositoryImpl QuizRep;
	@Autowired
	private Neo4jTemplate template;
	
	
	@Transactional
	public Question create(Question q) throws ObjectAlreadyFoundException {
		if (!isFound(q)) {
			q = template.save(q);
			for(Relationship r:template.getReferenceNode().getRelationships()){
				if(r.isType(RelTypes.QUESTION_REFERENCE)){					 
					r.getEndNode().createRelationshipTo(template.getNode(q.getQuestionId()), RelTypes.QUESTION);
				}
			}	
			return q;
		} else
			throw new ObjectAlreadyFoundException(
					"Dit object bestaat reeds in onze databank");
	}
	
	@Transactional
	public Question update(Question o) throws ObjectNotFoundException {
		if (isFound(o)) {
			return template.save(o);
		} else
			throw new ObjectNotFoundException(
					"Dit object bestaat niet in onze databank");
	}

	
	public boolean isFound(Question q){
		if(findQuestion("question", q.getQuestion()) != null){
			return true;
		}
		
		return false;
	}


	@Transactional
	public void removeQuestionFromQuiz(Quiz quiz, Question q) throws QuizException, ObjectNotFoundException {
		template.fetch(quiz.getQuestions());
		quiz.removeQuestion(q);
		QuizRep.update(quiz);	
	}

	@Transactional
	public Question findQuestion(String indexName, String fieldName) {
		IndexRepository<Question> repo = template.repositoryFor(Question.class);
		Question question = repo.findByPropertyValue(indexName, fieldName);
		return question;
	}
	
	public Quiz addQuestion(Long quizId, Question newQuestion) throws QuizException, ObjectNotFoundException, ObjectAlreadyFoundException {
		Quiz quiz = (Quiz) template.findOne(quizId, Quiz.class);
		template.fetch(quiz.getQuestions());
		if(newQuestion.getQuestionId() == null){
			newQuestion = (Question) create(newQuestion);
		}
		quiz.addQuestion(newQuestion);
		quiz = (Quiz) QuizRep.update(quiz);
		
		return quiz;
	}
	
	public void removeQuestion(Long questionId) throws QuizException, ObjectNotFoundException {
		Question q=  (Question) template.findOne(questionId, Question.class);
		template.delete(q);
	}
}
