package pl.piomin.samples.spring.graphql.annoMybatisPlus.entity.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@GraphQLName("Organization")
@TableName("Organization")
@Entity(name="Organization")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class OrganizationPO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@TableId(type = IdType.ASSIGN_ID)
    @JsonFormat(shape = Shape.STRING)
	@EqualsAndHashCode.Include
    @GraphQLField
    @GraphQLNonNull
	private Long id;

    @GraphQLField
    @GraphQLNonNull
	private String name;

    @GraphQLField
	@OneToMany(mappedBy = "organization")  // 一个组织机构有多个部门，1:N=组织机构:部门
    @TableField(exist = false)
	private Set<DepartmentPO> departments;

    @GraphQLField
	@OneToMany(mappedBy = "organization")  // 一个组织机构有多个人，1:N=组织机构:人
    @TableField(exist = false)
	private Set<EmployeePO> employees;
}
