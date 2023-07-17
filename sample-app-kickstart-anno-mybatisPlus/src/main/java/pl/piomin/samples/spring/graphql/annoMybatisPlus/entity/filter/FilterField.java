package pl.piomin.samples.spring.graphql.annoMybatisPlus.entity.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.conditions.AbstractChainWrapper;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;

@GraphQLName("FilterField")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilterField {
    @GraphQLField
    @GraphQLNonNull
	private String operator;

    @GraphQLField
    @GraphQLNonNull
	private String value;

	public <T, Children extends AbstractChainWrapper<T, SFunction<T, ?>, Children, Param>, Param extends AbstractWrapper<T, SFunction<T, ?>, Param>> 
		Children generateCriteria(Children builder, SFunction<T, ?> field) {
		try {
			int v = Integer.parseInt(value);
			switch (operator) {
			case "lt": return builder.lt(field, v);
			case "le": return builder.le(field, v);
			case "gt": return builder.gt(field, v);
			case "ge": return builder.ge(field, v);
			case "eq": return builder.eq(field, v);
			}
		} catch (NumberFormatException e) {
			switch (operator) {
			case "endsWith": return builder.like(field, "%" + value);
			case "startsWith": return builder.like(field, value + "%");
			case "contains": return builder.like(field, "%" + value + "%");
			case "eq": return builder.eq(field, value);
			}
		}

		return null;
	}
}
