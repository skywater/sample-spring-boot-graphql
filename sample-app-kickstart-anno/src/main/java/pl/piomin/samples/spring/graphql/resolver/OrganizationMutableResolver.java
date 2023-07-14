package pl.piomin.samples.spring.graphql.resolver;

import graphql.annotations.annotationTypes.GraphQLField;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;
import pl.piomin.samples.spring.graphql.domain.Organization;
import pl.piomin.samples.spring.graphql.domain.OrganizationInput;
import pl.piomin.samples.spring.graphql.repository.OrganizationRepository;

@Component
@RequiredArgsConstructor
public class OrganizationMutableResolver {

	final OrganizationRepository repository;

    @GraphQLField
	public Organization newOrganization(OrganizationInput organizationInput) {
		return repository.save(new Organization(null, organizationInput.getName(), null, null));
	}

}
