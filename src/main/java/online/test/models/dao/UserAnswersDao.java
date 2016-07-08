package online.test.models.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import online.test.models.UserAnswers;

@Transactional
public interface UserAnswersDao extends CrudRepository<UserAnswers, Long> {
	
	/**
	 * 
	 * @param testID
	 * @param userID
	 * @return user tests where status = 4(answered)
	 */
	@Query("select a from UserAnswers a inner join a.tests as t inner join a.user as u where t.id= :testID and u.id= :userID and a.status = 3")
	public List<UserAnswers> getCurrentUserTestAnswers(@Param("testID") Long testID, @Param("userID")Long userID);
	
	
	@Query("select a from UserAnswers a inner join a.tests as t inner join a.user as u where t.id= :testID and u.id= :userID")
	public List<UserAnswers> getUserTestAllAnswers(@Param("testID") Long testID, @Param("userID")Long userID);
	
	/**
	 * 
	 * @param testID
	 * @param userID
	 * @return user tests where status = 3(pinpointed)
	 */
	@Query("select a from UserAnswers a inner join a.tests as t inner join a.user as u where t.id= :testID and u.id= :userID and a.status = 2")
	public List<UserAnswers> getCurrentUserTestAnswersPinPointed(@Param("testID") Long testID, @Param("userID")Long userID);
	

	@Query("select a from UserAnswers a inner join a.user as u where u.id= :userID and a.status = 1")
	public List<UserAnswers> getCurrentUserStartedTests( @Param("userID")Long userID );
	
	@Query("select a from UserAnswers a inner join a.user as u where u.id= :userID and a.status = 4")
	public List<UserAnswers> getCurrentUserStopedTests( @Param("userID")Long userID );
	
}