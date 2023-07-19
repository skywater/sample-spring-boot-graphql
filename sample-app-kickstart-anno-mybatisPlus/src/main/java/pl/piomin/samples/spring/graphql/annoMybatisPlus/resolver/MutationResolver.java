
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
import pl.piomin.samples.spring.graphql.annoMybatisPlus.entity.dto.DepartmentDTO;
import pl.piomin.samples.spring.graphql.annoMybatisPlus.entity.dto.EmployeeDTO;
import pl.piomin.samples.spring.graphql.annoMybatisPlus.entity.dto.OrganizationDTO;
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
	public DepartmentPO newDepartment(DepartmentDTO department) {
		OrganizationPO organization = orgService.getById(department.getOrganizationId());
		DepartmentPO departmentPO = new DepartmentPO(null, department.getName(), organization.getId(), null, null);
		deptService.save(departmentPO);
		return departmentPO;
	}
    
    @GraphQLField
	public EmployeePO newEmployee(EmployeeDTO employee) {
		DepartmentPO department = deptService.getById(employee.getDepartmentId());
		OrganizationPO organization = orgService.getById(employee.getOrganizationId());
		EmployeePO employeePO = new EmployeePO(null, employee.getFirstName(), employee.getLastName(),
				employee.getPosition(), employee.getAge(), employee.getSalary(),
				department.getId(), organization.getId(), null, null);
		empService.save(employeePO);
		return employeePO;
	}
    
    @GraphQLField
	public OrganizationPO newOrganization(OrganizationDTO organization) {
    	OrganizationPO organizationPO = new OrganizationPO(null, organization.getName(), null, null);
		orgService.save(organizationPO);
		return organizationPO;
	}
}

 