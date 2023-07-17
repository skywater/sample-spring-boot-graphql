package pl.piomin.samples.spring.graphql.annoMybatisPlus.entity.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;


@GraphQLName("Department")
@TableName("Department")
@Entity(name="Department")
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DepartmentPO {
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

    @Transient
	private Long organizationId;
    
	@OneToMany(mappedBy = "department")  // 一个组织机构有多个人，1:N=部门:人
    @GraphQLField
    @TableField(exist = false)
	private Set<EmployeePO> employees;

    @GraphQLField
	@ManyToOne(fetch = FetchType.LAZY)  // 一个组织机构有多个部门，1:N=组织机构:部门
    @TableField(exist = false)
	private OrganizationPO organization;
}
