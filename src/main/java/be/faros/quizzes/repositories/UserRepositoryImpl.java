package be.faros.quizzes.repositories;

import java.util.LinkedHashSet;
import java.util.Set;

import org.neo4j.graphdb.Relationship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.repository.IndexRepository;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import be.faros.quizzes.domain.*;
import be.faros.quizzes.exceptions.*;


@Repository("userrepo")
public class UserRepositoryImpl {


	@Autowired
	private Neo4jTemplate template;



	@Transactional
	public User create(User u) throws ObjectAlreadyFoundException {
		if (!isFound(u)) {
			u=  template.save(u);
			for(Relationship r:template.getReferenceNode().getRelationships()){
				if(r.isType(RelTypes.USER_REFERENCE)){		
					r.getEndNode().createRelationshipTo(template.getNode(u.getUserId()), RelTypes.USER);
				}
			}
			
			return u;
		} else
			throw new ObjectAlreadyFoundException(
					"Dit object bestaat reeds in onze databank");
	}
	@Transactional
	public User update(User o) throws ObjectNotFoundException {
		if (isFound(o)) {
			return template.save(o);
		} else
			throw new ObjectNotFoundException(
					"Dit object bestaat niet in onze databank");
	}
	
	public boolean isFound(User u){
		if(findUser("username", u.getUsername()) != null){
			return true;
		}
		
		return false;
	}
	

	public User findUser(String indexname, String fieldName) {
		IndexRepository<User> userRepo = template.repositoryFor(User.class);
		User user = userRepo.findByPropertyValue(indexname, fieldName);			
		return user;

	}



	public Set<User> findAllUsers() {
		Set<User> users = new LinkedHashSet<User>();
		
		for(User u:template.findAll(User.class)){
			users.add(u);
		}
		return users;
	}
	
	
	
}
