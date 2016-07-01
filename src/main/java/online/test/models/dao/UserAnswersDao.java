package online.test.models.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import online.test.models.UserAnswers;

@Transactional
public interface UserAnswersDao extends CrudRepository<UserAnswers, Long> {
	@Query("select a from UserAnswers a inner join a.tests as t inner join a.user as u where t.id= :testID and u.id= :userID")
	public List<UserAnswers> getCurrentUserTestAnswers(Long testID, Long userID);
}