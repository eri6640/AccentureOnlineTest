package online.test.models.dao;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import online.test.models.User;

/**
 * A DAO for the entity User is simply created by extending the CrudRepository
 * interface provided by spring. The following methods are some of the ones
 * available from such interface: save, delete, deleteAll, findOne and findAll.
 * The magic is that such methods must not be implemented, and moreover it is
 * possible create new query methods working only by defining their signature!
 * 
 * @author netgloo
 */
@Transactional
public interface UserDao extends CrudRepository<User, Long> {

	/**
	 * Return the user having the passed email or null if no user is found.
	 * 
	 * @param email
	 *            the user email.
	 */
	public User findByEmail( String email );

	/**
	 * Return the user having the passed id or null if no user is found.
	 * 
	 * @param id
	 *            the user id.
	 */
	public User findById(long id);
	
	public User findByIp( String ip );
	
	/**
	 * Return the user having the passed token or null if no user is found.
	 * 
	 * @param token
	 *            the user token.
	 */
	//@Query( "from User where email = ?1" )
	public User findByToken( String token ) throws Exception;

} // class UserDao
