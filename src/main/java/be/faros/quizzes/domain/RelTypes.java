package be.faros.quizzes.domain;

import org.neo4j.graphdb.RelationshipType;

public enum RelTypes implements RelationshipType{
	USER_REFERENCE,
	USER,
	QUIZ_REFERENCE,
	QUIZ,
	GROUP_REFERENCE,
	GROUP,
	QUESTION_REFERENCE,
	QUESTION,
	INVITE_REFERENCE,
	INVITE,
	SCORE_REFERENCE,
	SCORE,
	
}