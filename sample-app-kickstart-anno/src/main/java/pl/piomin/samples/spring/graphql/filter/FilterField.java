package pl.piomin.samples.spring.graphql.filter;

import lombok.Data;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;

@GraphQLName("FilterField")
@Data
public class FilterField {
    @GraphQLField
    @GraphQLNonNull
	private String operator;

    @GraphQLField
    @GraphQLNonNull
	private String value;

	public Predicate generateCriteria(CriteriaBuilder builder, Path field) {
		try {
			int v = Integer.parseInt(value);
			switch (operator) {
			case "lt": return builder.lt(field, v);
			case "le": return builder.le(field, v);
			case "gt": return builder.gt(field, v);
			case "ge": return builder.ge(field, v);
			case "eq": return builder.equal(field, v);
			}
		} catch (NumberFormatException e) {
			switch (operator) {
			case "endsWith": return builder.like(field, "%" + value);
			case "startsWith": return builder.like(field, value + "%");
			case "contains": return builder.like(field, "%" + value + "%");
			case "eq": return builder.equal(field, value);
			}
		}

		return null;
	}
}
