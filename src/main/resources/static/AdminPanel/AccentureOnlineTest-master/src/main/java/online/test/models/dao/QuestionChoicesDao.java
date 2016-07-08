package online.test.models.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import online.test.models.QuestionChoices;

@Transactional
public interface QuestionChoicesDao extends CrudRepository<QuestionChoices, Long> {

	@Query("select q from QuestionChoices q inner join q.testQuestion as t where t.id = :testQuestionID")
	public List<QuestionChoices> getCurrentTestQuestions(@Param("testQuestionID")Long testQuestionID);

}