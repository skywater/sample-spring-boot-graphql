package pl.piomin.samples.spring.graphql.annoMybatisPlus.service;

import pl.piomin.samples.spring.graphql.annoMybatisPlus.entity.po.OrganizationPO;
import pl.piomin.samples.spring.graphql.annoMybatisPlus.mapper.OrganizationMapper;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

@Service
public class OrganizationService extends ServiceImpl<OrganizationMapper, OrganizationPO> {

}
