package online.test.models.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import online.test.models.Tests;

@Transactional
public interface TestsDao extends CrudRepository<Tests, Long> {

	@Query("from Tests")
	public List<Tests> getAllTests();
	
}