package be.faros.quizzes.repositories;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.PropertyContainer;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.ReturnableEvaluator;
import org.neo4j.graphdb.StopEvaluator;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.Traverser;
import org.neo4j.graphdb.Traverser.Order;
import org.neo4j.graphdb.index.Index;
import org.neo4j.helpers.collection.ClosableIterable;
import org.neo4j.helpers.collection.IteratorUtil;
import org.neo4j.helpers.collection.MapUtil;
import org.neo4j.kernel.Traversal;
import org.neo4j.rest.graphdb.RestGraphDatabase;
import org.neo4j.rest.graphdb.query.RestCypherQueryEngine;
import org.neo4j.rest.graphdb.util.ResultConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.neo4j.annotation.QueryType;
import org.springframework.data.neo4j.conversion.EndResult;
import org.springframework.data.neo4j.conversion.QueryMapResulConverter;
import org.springframework.data.neo4j.conversion.Result;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.neo4j.repository.IndexRepository;
import org.springframework.data.neo4j.support.DelegatingGraphDatabase;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.data.neo4j.support.conversion.EntityResultConverter;
import org.springframework.data.neo4j.support.query.CypherQueryEngine;
import org.springframework.data.neo4j.support.query.QueryEngine;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import be.faros.quizzes.domain.*;
import be.faros.quizzes.exceptions.ObjectAlreadyFoundException;
import be.faros.quizzes.exceptions.ObjectNotFoundException;


@Repository("quizrepo")
public class QuizRepositoryImpl{


	@Autowired
	private Neo4jTemplate template;
	@Autowired
    protected ConversionService conversionService;
	
	@Autowired
	RestGraphDatabase restGraphdb;
	
	
	@Transactional
	public Quiz findQuiz(String indexname, String fieldName) {
		IndexRepository<Quiz> userRepo = template.repositoryFor(Quiz.class);
		Quiz quiz = userRepo.findByPropertyValue(indexname, fieldName);
		return quiz;
	}



	
	

	

	
	@Transactional
	public Set<Quiz> findAllQuizzes() {
		
		Set<Quiz> quizzes = new LinkedHashSet<Quiz>();	
		long start = System.currentTimeMillis();
		
		ClosableIterable<Quiz> findAll = template.findAll(Quiz.class);

		long end= System.currentTimeMillis();
		System.out.println("-----Time to findAll quizzes: " + (end - start) + " ms");
		start= System.currentTimeMillis();

		for(Quiz q: findAll){
			template.fetch(q.getQuestions());
			quizzes.add(q);
		}
		
		end= System.currentTimeMillis();
		System.out.println("-----Time to Iterate all quizzes: " + (end - start) + " ms");
		
		return quizzes;
	}

	


	
	public Quiz findQuiz(Long id) throws ObjectNotFoundException {
		 
		Quiz q =(Quiz)template.findOne(id, Quiz.class);
		//template.fetch(q.getQuestions());
		return q;
	}


	public void removeQuiz(Long quizId) throws ObjectNotFoundException {
		Quiz q= template.findOne(quizId,Quiz.class);
		template.delete(q);
		
	}


	
	
	
	@Transactional
	public Quiz create(Quiz q) throws ObjectAlreadyFoundException {
		if (!isFound(q)) {
			q= template.save(q);
			for(Relationship r:template.getReferenceNode().getRelationships()){
				if(r.isType(RelTypes.QUIZ_REFERENCE)){					 
					r.getEndNode().createRelationshipTo(template.getNode(q.getId()), RelTypes.QUIZ);
				}
			}			
			return q;
		} else
			throw new ObjectAlreadyFoundException(
					"Dit object bestaat reeds in onze databank");
	}
	
	@Transactional
	public Quiz update(Quiz o) throws ObjectNotFoundException {
		if (isFound(o)) {
			return template.save(o);
		} else
			throw new ObjectNotFoundException(
					"Dit object bestaat niet in onze databank");
	}

	
	
	public boolean isFound(Quiz q){
		if(findQuiz("name", q.getName())!= null){
			return true;
		}
		
		return false;
	}

	



	
}
