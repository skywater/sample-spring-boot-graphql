package pl.piomin.samples.spring.graphql.annoMybatisPlus.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import pl.piomin.samples.spring.graphql.annoMybatisPlus.entity.po.EmployeePO;
import pl.piomin.samples.spring.graphql.annoMybatisPlus.mapper.EmployeeMapper;

@Service
public class EmployeeService extends ServiceImpl<EmployeeMapper, EmployeePO> {

	public List<EmployeePO> listByDeptId(Integer deptId){
		return lambdaQuery().eq(EmployeePO::getDepartmentId, deptId).list();
	}
	

	public List<EmployeePO> listByOrgId(Integer orgId){
		return lambdaQuery().eq(EmployeePO::getOrganizationId, orgId).list();
	}
}
