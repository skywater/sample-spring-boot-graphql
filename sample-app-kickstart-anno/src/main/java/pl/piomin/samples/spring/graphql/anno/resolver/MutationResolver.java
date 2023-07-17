
/**
 * Project Name: sample-app-kickstart-anno
 * File Name: MutableResolver.java
 * @date 2023年7月17日 下午2:51:18
 * Copyright (c) 2023 jpq.com All Rights Reserved.
 */

package pl.piomin.samples.spring.graphql.anno.resolver;

import org.springframework.stereotype.Component;

import graphql.annotations.annotationTypes.GraphQLField;
import lombok.RequiredArgsConstructor;
import pl.piomin.samples.spring.graphql.anno.domain.Department;
import pl.piomin.samples.spring.graphql.anno.domain.DepartmentInput;
import pl.piomin.samples.spring.graphql.anno.domain.Employee;
import pl.piomin.samples.spring.graphql.anno.domain.EmployeeInput;
import pl.piomin.samples.spring.graphql.anno.domain.Organization;
import pl.piomin.samples.spring.graphql.anno.domain.OrganizationInput;
import pl.piomin.samples.spring.graphql.anno.repository.DepartmentRepository;
import pl.piomin.samples.spring.graphql.anno.repository.EmployeeRepository;
import pl.piomin.samples.spring.graphql.anno.repository.OrganizationRepository;

/**
 * TODO <br/>
 * @date 2023年7月17日 下午2:51:18
 * @author jiangpq
 * @version
 */
//Multiple mutation resolvers provided. GraphQL Annotations allows only one resolver.
@Component
@graphql.kickstart.annotations.GraphQLMutationResolver
@RequiredArgsConstructor
public class MutationResolver {
	private final DepartmentRepository deptRepository;
	private final EmployeeRepository empRepository;
	private final OrganizationRepository orgRepository;
	
    @GraphQLField
	public Department newDepartment(DepartmentInput departmentInput) {
		Organization organization = orgRepository.findById(departmentInput.getOrganizationId()).get();
		return deptRepository.save(new Department(null, departmentInput.getName(), null, organization));
	}
    
    @GraphQLField
	public Employee newEmployee(EmployeeInput employeeInput) {
		Department department = deptRepository.findById(employeeInput.getDepartmentId()).get();
		Organization organization = orgRepository.findById(employeeInput.getOrganizationId()).get();
		return empRepository.save(new Employee(null, employeeInput.getFirstName(), employeeInput.getLastName(),
				employeeInput.getPosition(), employeeInput.getAge(), employeeInput.getSalary(),
				department, organization));
	}
    
    @GraphQLField
	public Organization newOrganization(OrganizationInput organizationInput) {
		return orgRepository.save(new Organization(null, organizationInput.getName(), null, null));
	}
}

 