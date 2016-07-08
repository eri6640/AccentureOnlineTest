package online.test.models.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import online.test.models.TestQuestions;
import online.test.models.Tests;


@Transactional
public interface TestQuestionsDao extends CrudRepository<TestQuestions, Long> {

	@Query("select q from TestQuestions q inner join q.tests as t where t.id = :testID")
	public List<TestQuestions> getCurrentTestQuestions(@Param("testID")Long testID);
	
	public TestQuestions findById( long id );

	public TestQuestions findById(long id);
}