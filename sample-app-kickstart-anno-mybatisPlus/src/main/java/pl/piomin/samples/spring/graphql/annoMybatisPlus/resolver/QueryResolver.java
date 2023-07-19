
/**
 * Project Name: sample-app-kickstart-anno
 * File Name: QueryResolver.java
 * @date 2023年7月17日 下午2:50:58
 * Copyright (c) 2023 jpq.com All Rights Reserved.
 */

package pl.piomin.samples.spring.graphql.annoMybatisPlus.resolver;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.annotations.annotationTypes.GraphQLType;
import graphql.annotations.connection.GraphQLConnection;
import graphql.schema.DataFetchingEnvironment;
import lombok.RequiredArgsConstructor;
import pl.piomin.samples.spring.graphql.annoMybatisPlus.config.GenericFunction;
import pl.piomin.samples.spring.graphql.annoMybatisPlus.entity.base.NaivePageData;
import pl.piomin.samples.spring.graphql.annoMybatisPlus.entity.base.NaivePageInfo;
import pl.piomin.samples.spring.graphql.annoMybatisPlus.entity.filter.EmployeeFilter;
import pl.piomin.samples.spring.graphql.annoMybatisPlus.entity.po.DepartmentPO;
import pl.piomin.samples.spring.graphql.annoMybatisPlus.entity.po.EmployeePO;
import pl.piomin.samples.spring.graphql.annoMybatisPlus.entity.po.OrganizationPO;
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
    @GraphQLType(GenericFunction.class)
    @GraphQLName("pageDepartments") // 将该方法名 pageDepartmentPOs 重新定义为 pageDepartments
	public NaivePageInfo<DepartmentPO> pageDepartmentPOs(@GraphQLNonNull Integer pageNo, @GraphQLNonNull Integer pageSize, DataFetchingEnvironment environment) {
    	return new NaivePageInfo<>(departments(pageNo, pageSize, environment));
	}
    
	private Page<DepartmentPO> departments(Integer pageNo, Integer pageSize, DataFetchingEnvironment environment) {
    	Page<DepartmentPO> pageDto = new Page<DepartmentPO>(pageNo, pageSize);
    	Page<DepartmentPO> page = deptService.page(pageDto);
    	List<DepartmentPO> datas = page.getRecords(); // deptService.list();
		Map<Long, Set<EmployeePO>> empMap = GraphqlUtil.runThen(environment, "*/employees", () -> {
			List<EmployeePO> list = empService.list();
			Map<Long, Set<EmployeePO>> map = list.stream().collect(Collectors.groupingBy(EmployeePO::getDepartmentId, Collectors.toSet()));
    		return map;
    	}, () -> new HashMap<>());
		Map<Long, OrganizationPO> orgMap = GraphqlUtil.runThen(environment, "*/organization", () -> { 
			return orgService.list().stream().collect(Collectors.toMap(e -> e.getId(), e -> e));
		}, () -> new HashMap<>());
		datas.forEach(e -> e.setOrganization(orgMap.get(e.getOrganizationId())).setEmployees(empMap.get(e.getOrganizationId())));
		return page;
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
	public NaivePageData<EmployeePO> employees(@GraphQLNonNull Integer pageNo, @GraphQLNonNull Integer pageSize, DataFetchingEnvironment environment) {
    	return new NaivePageData<>(pageEmployeeList(pageNo, pageSize, environment));
	}
    
    private Page<EmployeePO> pageEmployeeList(Integer pageNo, Integer pageSize, DataFetchingEnvironment environment){
    	Page<EmployeePO> pageDto = new Page<EmployeePO>(pageNo, pageSize);
    	Page<EmployeePO> page = empService.page(pageDto);
    	List<EmployeePO> pos = page.getRecords();
    	Map<Long, DepartmentPO> deptMap = GraphqlUtil.runThen(environment, "*/department", () -> {
	    	List<DepartmentPO> list = deptService.listByIds(pos.stream().map(e -> e.getDepartmentId()).collect(Collectors.toList()));
	    	return list.stream().collect(Collectors.toMap(e -> e.getId(), e -> e));
    	}, () -> new HashMap<>());
    	Map<Long, OrganizationPO> orgMap = GraphqlUtil.runThen(environment, "*/organization", () -> {
    		List<OrganizationPO> list = orgService.listByIds(pos.stream().map(e -> e.getDepartmentId()).collect(Collectors.toList()));
	    	return list.stream().collect(Collectors.toMap(e -> e.getId(), e -> e));
    	}, () -> new HashMap<>());
    	pos.forEach(e -> e.setDepartment(deptMap.get(e.getDepartmentId())).setOrganization(orgMap.get(e.getOrganizationId())));
		return page;
    }
	
    @GraphQLField
    @GraphQLType(GenericFunction.class)
    @GraphQLDescription("分页查询人员")
	public NaivePageInfo<EmployeePO> pageEmployees(@GraphQLNonNull Integer pageNo, @GraphQLNonNull Integer pageSize, DataFetchingEnvironment environment) {
    	return new NaivePageInfo<>(pageEmployeeList(pageNo, pageSize, environment));
	}
    
    @GraphQLField
    @GraphQLConnection // (connectionFetcher = PageDataConnectionFetcher.class)
	public NaivePageData<EmployeePO> pageEmployeeByCursor(@GraphQLNonNull Integer pageNo, @GraphQLNonNull Integer pageSize, DataFetchingEnvironment environment) {
    	// 这4个参数实现很麻烦，不如 pageNo, pageSize
//        Integer first = environment.getArgument("first");
//        Integer last = environment.getArgument("last");
//        Integer after = environment.getArgument("after");
//        Integer before = environment.getArgument("before");
        Page<EmployeePO> page = pageEmployeeList(pageNo, pageSize, environment);
        boolean hasPrev = page.getCurrent() > 1;
        boolean hasNext = page.getCurrent() < page.getPages();
        NaivePageData<EmployeePO> paginatedData = new NaivePageData<>(page);
        return paginatedData;
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

 