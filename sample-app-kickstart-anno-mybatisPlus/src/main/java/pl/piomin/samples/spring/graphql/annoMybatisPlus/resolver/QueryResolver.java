
/**
 * Project Name: sample-app-kickstart-anno
 * File Name: QueryResolver.java
 * @date 2023年7月17日 下午2:50:58
 * Copyright (c) 2023 jpq.com All Rights Reserved.
 */

package pl.piomin.samples.spring.graphql.annoMybatisPlus.resolver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.schema.DataFetchingEnvironment;
import lombok.RequiredArgsConstructor;
import pl.piomin.samples.spring.graphql.annoMybatisPlus.entity.po.DepartmentPO;
import pl.piomin.samples.spring.graphql.annoMybatisPlus.entity.po.EmployeePO;
import pl.piomin.samples.spring.graphql.annoMybatisPlus.entity.po.OrganizationPO;
import pl.piomin.samples.spring.graphql.annoMybatisPlus.filter.EmployeeFilter;
import pl.piomin.samples.spring.graphql.annoMybatisPlus.filter.FilterField;
import pl.piomin.samples.spring.graphql.annoMybatisPlus.service.DepartmentSerice;
import pl.piomin.samples.spring.graphql.annoMybatisPlus.service.EmployeeService;
import pl.piomin.samples.spring.graphql.annoMybatisPlus.service.OrganizationService;
import pl.piomin.samples.spring.graphql.annoMybatisPlus.utils.GraphqlUtil;

/**
 * TODO <br/>
 * @date 2023年7月17日 下午2:50:58
 * @author jiangpq
 * @version
 */
//Multiple query resolvers provided. GraphQL Annotations allows only one resolver.
@Component
@graphql.kickstart.annotations.GraphQLQueryResolver
@RequiredArgsConstructor
public class QueryResolver {
	private final DepartmentSerice deptService;
	private final EmployeeService empService;
	private final OrganizationService orgService;
    
	@GraphQLField
	public Iterable<DepartmentPO> departments(DataFetchingEnvironment environment) {
		List<DepartmentPO> ret = deptService.list();
		Map<Long, Set<EmployeePO>> empMap = GraphqlUtil.runThen(environment, "employees", () -> {
			List<EmployeePO> list = empService.list();
			Map<Long, Set<EmployeePO>> map = list.stream().collect(Collectors.groupingBy(EmployeePO::getDepartmentId, Collectors.toSet()));
    		return map;
    	}, () -> new HashMap<>());
		Map<Long, OrganizationPO> orgMap = GraphqlUtil.runThen(environment, "organization", () -> { 
			return orgService.list().stream().collect(Collectors.toMap(e -> e.getId(), e -> e));
		}, () -> new HashMap<>());
		ret.forEach(e -> e.setOrganization(orgMap.get(e.getOrganizationId())).setEmployees(empMap.get(e.getOrganizationId())));
		return ret;
	}

    @GraphQLField
	public DepartmentPO department(Integer id, DataFetchingEnvironment environment) {
    	DepartmentPO po = deptService.getById(id);
    	GraphqlUtil.runTrue(environment, "employees", () -> {
	    	List<EmployeePO> list = empService.listByDeptId(id);
	    	return po.setEmployees(new HashSet<>(list));
    	});
    	GraphqlUtil.runTrue(environment, "organization", () -> {
			return po.setOrganization(orgService.getById(po.getOrganizationId()));
    	});
    	return po;
	}
	
    @GraphQLField
	public Iterable<EmployeePO> employees() {
		return empService.list();
	}

    @GraphQLField
	public EmployeePO employee(Integer id) {
		return empService.getById(id);
	}

    @GraphQLField
	public Iterable<EmployeePO> employeesWithFilter(EmployeeFilter filter) {
    	LambdaQueryChainWrapper<EmployeePO> lambdaQuery = empService.lambdaQuery();
    	lambdaQuery.func(filter.getSalary() != null, e -> filter.getSalary().generateCriteria(lambdaQuery, EmployeePO::getSalary));
    	lambdaQuery.func(filter.getAge() != null, e -> filter.getAge().generateCriteria(lambdaQuery, EmployeePO::getAge));
    	lambdaQuery.func(filter.getPosition() != null, e -> filter.getPosition().generateCriteria(lambdaQuery, EmployeePO::getPosition));
		return lambdaQuery.list();
	}

    @GraphQLField
	public Iterable<OrganizationPO> organizations() {
		return orgService.list();
	}

    @GraphQLField
	public OrganizationPO organization(Integer id) {
		return orgService.getById(id);
	}
}

 