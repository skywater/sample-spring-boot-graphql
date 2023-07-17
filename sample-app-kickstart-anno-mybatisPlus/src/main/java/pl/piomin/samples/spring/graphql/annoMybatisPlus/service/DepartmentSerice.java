package pl.piomin.samples.spring.graphql.annoMybatisPlus.service;

import pl.piomin.samples.spring.graphql.annoMybatisPlus.entity.po.DepartmentPO;
import pl.piomin.samples.spring.graphql.annoMybatisPlus.mapper.DepartmentMapper;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

@Service
public class DepartmentSerice extends ServiceImpl<DepartmentMapper, DepartmentPO> {

}
