package pl.piomin.samples.spring.graphql.anno.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import pl.piomin.samples.spring.graphql.anno.domain.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, Integer>,
		JpaSpecificationExecutor<Employee> {
}
