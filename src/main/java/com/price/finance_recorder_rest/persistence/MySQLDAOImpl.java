package com.price.finance_recorder_rest.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.springframework.beans.BeanUtils;

import com.price.finance_recorder_rest.service.UserDTO;


public class MySQLDAOImpl implements MySQLDAO{

	private static SessionFactory sessionFactory = null;
     
    private static SessionFactory getSessionFactory() {
    	if (sessionFactory == null)
    	{
            Configuration conf = new Configuration();
            conf.configure();
            try 
            {
//                sessionFactory = new Configuration().configure().buildSessionFactory();
            	sessionFactory = conf.buildSessionFactory();
            } 
            catch (HibernateException e) 
            {
                System.err.println("Initial SessionFactory creation failed: " + e);
                throw new ExceptionInInitializerError(e);
            }
            catch (Exception e)
            {
                System.err.println("Exception occurs due to: " + e);
                throw e;
            }
    	}
        return sessionFactory;
    } 
	
	Session session;

	@Override
	public void openConnection() {
        SessionFactory sessionFactory = getSessionFactory();
        session = sessionFactory.openSession();
	}

	@Override
	public void closeConnection() {
        if (session != null) {
            session.close();
        }
	}
	
	@Override
	public UserDTO getUserByUserName(String userName) {
        UserDTO userDto = null;

        CriteriaBuilder cb = session.getCriteriaBuilder();

        //Create Criteria against a particular persistent class
        CriteriaQuery<UserEntity> criteria = cb.createQuery(UserEntity.class);

        //Query roots always reference entitie
        Root<UserEntity> profileRoot = criteria.from(UserEntity.class);
        criteria.select(profileRoot);
        criteria.where(cb.equal(profileRoot.get("email"), userName));

        // Fetch single result
        Query<UserEntity> query = session.createQuery(criteria);
        List<UserEntity> resultList = query.getResultList();
        if (resultList != null && resultList.size() > 0) {
            UserEntity userEntity = resultList.get(0);
            userDto = new UserDTO();
            BeanUtils.copyProperties(userEntity, userDto);
        }

        return userDto;
	}

	@Override
	public UserDTO saveUser(UserDTO user) {
        UserDTO returnValue = null;
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);
        
        session.beginTransaction();
        session.save(userEntity);
        session.getTransaction().commit();
        
        returnValue = new UserDTO();
        BeanUtils.copyProperties(userEntity, returnValue);
        
        return returnValue;
	}

	@Override
	public UserDTO getUser(String id) {
       CriteriaBuilder cb = session.getCriteriaBuilder();

       //Create Criteria against a particular persistent class
       CriteriaQuery<UserEntity> criteria = cb.createQuery(UserEntity.class);

       //Query roots always reference entitie
       Root<UserEntity> profileRoot = criteria.from(UserEntity.class);
       criteria.select(profileRoot);
       criteria.where(cb.equal(profileRoot.get("userId"), id));

       // Fetch single result
       UserEntity userEntity = session.createQuery(criteria).getSingleResult();
       
       UserDTO userDto = new UserDTO();
       BeanUtils.copyProperties(userEntity, userDto);
       
       return userDto;
	}

	@Override
	public List<UserDTO> getUsers(int start, int limit) {
        CriteriaBuilder cb = session.getCriteriaBuilder();

        //Create Criteria against a particular persistent class
        CriteriaQuery<UserEntity> criteria = cb.createQuery(UserEntity.class);

        //Query roots always reference entities
        Root<UserEntity> userRoot = criteria.from(UserEntity.class);
        criteria.select(userRoot);

        // Fetch results from start to a number of "limit"
        List<UserEntity> searchResults = session.createQuery(criteria).
                setFirstResult(start).
                setMaxResults(limit).
                getResultList();
 
        List<UserDTO> returnValue = new ArrayList<UserDTO>();
        for (UserEntity userEntity : searchResults) {
            UserDTO userDto = new UserDTO();
            BeanUtils.copyProperties(userEntity, userDto);
            returnValue.add(userDto);
        }

        return returnValue;
	}

	@Override
    public void updateUser(UserDTO userProfile) {
	    UserEntity userEntity = new UserEntity();
	    BeanUtils.copyProperties(userProfile, userEntity);
	     
	    session.beginTransaction();
	    session.update(userEntity);
	    session.getTransaction().commit();
	}

	@Override
	public void deleteUser(UserDTO userProfile) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userProfile, userEntity);
        
        session.beginTransaction();
        session.delete(userEntity);
        session.getTransaction().commit();
	}

// For email verification, used for preventing user from login until they verify email
	@Override
	public UserDTO getUserByEmailToken(String token) {
	    CriteriaBuilder cb = session.getCriteriaBuilder();
//Create Criteria against a particular persistent class
	    CriteriaQuery<UserEntity> criteria = cb.createQuery(UserEntity.class);

//Query roots always reference entitie
	    Root<UserEntity> profileRoot = criteria.from(UserEntity.class);
	    criteria.select(profileRoot);
	    criteria.where(cb.equal(profileRoot.get("emailVerificationToken"), token));

// Fetch single result
	    UserEntity userEntity = session.createQuery(criteria).getSingleResult();
	        
	    UserDTO userDto = new UserDTO();
	    BeanUtils.copyProperties(userEntity, userDto);
	        
	    return userDto;
	}
}
