
/**
 * Project Name: sample-app-kickstart-anno
 * File Name: MutableResolver.java
 * @date 2023年7月17日 下午2:51:18
 * Copyright (c) 2023 jpq.com All Rights Reserved.
 */

package pl.piomin.samples.spring.graphql.annoMybatisPlus.resolver;

import org.springframework.stereotype.Component;

import graphql.annotations.annotationTypes.GraphQLField;
import lombok.RequiredArgsConstructor;
import pl.piomin.samples.spring.graphql.annoMybatisPlus.entity.dto.DepartmentInput;
import pl.piomin.samples.spring.graphql.annoMybatisPlus.entity.dto.EmployeeInput;
import pl.piomin.samples.spring.graphql.annoMybatisPlus.entity.dto.OrganizationInput;
import pl.piomin.samples.spring.graphql.annoMybatisPlus.entity.po.DepartmentPO;
import pl.piomin.samples.spring.graphql.annoMybatisPlus.entity.po.EmployeePO;
import pl.piomin.samples.spring.graphql.annoMybatisPlus.entity.po.OrganizationPO;
import pl.piomin.samples.spring.graphql.annoMybatisPlus.mapper.DepartmentMapper;
import pl.piomin.samples.spring.graphql.annoMybatisPlus.mapper.EmployeeMapper;
import pl.piomin.samples.spring.graphql.annoMybatisPlus.mapper.OrganizationMapper;
import pl.piomin.samples.spring.graphql.annoMybatisPlus.service.DepartmentSerice;
import pl.piomin.samples.spring.graphql.annoMybatisPlus.service.EmployeeService;
import pl.piomin.samples.spring.graphql.annoMybatisPlus.service.OrganizationService;

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
	private final DepartmentSerice deptService;
	private final EmployeeService empService;
	private final OrganizationService orgService;
	
    @GraphQLField
	public DepartmentPO newDepartment(DepartmentInput departmentInput) {
		OrganizationPO organization = orgService.getById(departmentInput.getOrganizationId());
		DepartmentPO department = new DepartmentPO(null, departmentInput.getName(), organization.getId(), null, null);
		deptService.save(department);
		return department;
	}
    
    @GraphQLField
	public EmployeePO newEmployee(EmployeeInput employeeInput) {
		DepartmentPO department = deptService.getById(employeeInput.getDepartmentId());
		OrganizationPO organization = orgService.getById(employeeInput.getOrganizationId());
		EmployeePO employee = new EmployeePO(null, employeeInput.getFirstName(), employeeInput.getLastName(),
				employeeInput.getPosition(), employeeInput.getAge(), employeeInput.getSalary(),
				department.getId(), organization.getId(), null, null);
		empService.save(employee);
		return employee;
	}
    
    @GraphQLField
	public OrganizationPO newOrganization(OrganizationInput organizationInput) {
    	OrganizationPO organization = new OrganizationPO(null, organizationInput.getName(), null, null);
		orgService.save(organization);
		return organization;
	}
}

 