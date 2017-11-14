package com.pawan.security.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.pawan.security.model.Permission;
import com.pawan.security.model.User;


@Repository
public class DAOImpl implements UserDAO{
	
	@Autowired
	protected MongoOperations mongoOperations;

	@Override
	public User findByEmail(String email) throws DataAccessException {
		Query query = new Query(Criteria.where("email").is(email).and("status").is(1));
		return mongoOperations.findOne(query, User.class);
	}
	
	@Override
	public User findByUsernameOrEmail(String username, String email) throws DataAccessException {	
		Query query = new Query(
				Criteria
				.where("status").ne(-1)
				.orOperator(
						Criteria.where("username").is(username)
						,Criteria.where("email").is(email)
						)
				);
		
		return mongoOperations.findOne(query, User.class);
	}

	@Override
	public List<Permission> findUserPermissions() {
		return null;
	}

	@Override
	public User findUserByToken(String token) throws DataAccessException {
		Query query = new Query(Criteria.where("emailVerification.token").is(token));
		return mongoOperations.findOne(query, User.class);
}

	@Override
	public User add(User user) throws DataAccessException {
		mongoOperations.insert(user);
		return user;
	}

	@Override
	public User edit(User user) throws DataAccessException {
		mongoOperations.save(user);
		return user;
	}

}
