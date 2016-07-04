package online.test.models.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import online.test.models.TestOuestions;

@Transactional
public interface TestQuestionsDao extends CrudRepository<TestOuestions, Long> {
	@Query("select q from TestOuestions q inner join q.tests as t where t.id = :testID")
	public List<TestOuestions> getCurrentTestQuestions(@Param("testID") Long testID);
}