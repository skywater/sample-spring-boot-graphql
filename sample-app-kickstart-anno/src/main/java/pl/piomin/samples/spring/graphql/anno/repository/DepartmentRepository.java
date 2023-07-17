package pl.piomin.samples.spring.graphql.anno.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import pl.piomin.samples.spring.graphql.anno.domain.Department;

public interface DepartmentRepository extends CrudRepository<Department, Integer>,
		JpaSpecificationExecutor<Department> {

}
