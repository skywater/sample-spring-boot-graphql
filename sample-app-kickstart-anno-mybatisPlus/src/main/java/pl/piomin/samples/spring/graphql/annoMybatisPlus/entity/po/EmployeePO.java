package pl.piomin.samples.spring.graphql.annoMybatisPlus.entity.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;

@GraphQLName("Employee")
@TableName("Employee")
@Entity(name="Employee")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class EmployeePO {
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
	private String firstName;

    @GraphQLField
    @GraphQLNonNull
	private String lastName;

    @GraphQLField
    @GraphQLNonNull
	private String position;

    @GraphQLField
	private Integer salary;

    @GraphQLField
	private Integer age;
    
    @Transient
	private Long departmentId;
    
    @Transient
	private Long organizationId;

    @GraphQLField
	@ManyToOne(fetch = FetchType.LAZY)  // 一个部门有多个人，N:1=人:部门
    @TableField(exist = false)
	private DepartmentPO department;

    @GraphQLField
	@ManyToOne(fetch = FetchType.LAZY)  // 一个组织有多个人，N:1=人:组织
    @TableField(exist = false)
	private OrganizationPO organization;
}
