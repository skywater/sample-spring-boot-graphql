package pl.piomin.samples.spring.graphql.resolver;

import graphql.annotations.annotationTypes.GraphQLField;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;
import pl.piomin.samples.spring.graphql.domain.Organization;
import pl.piomin.samples.spring.graphql.repository.OrganizationRepository;

@Component
@RequiredArgsConstructor
public class OrganizationQueryResolver {

	private final OrganizationRepository repository;

    @GraphQLField
	public Iterable<Organization> organizations() {
		return repository.findAll();
	}

    @GraphQLField
	public Organization organization(Integer id) {
		return repository.findById(id).orElseThrow(() -> new RuntimeException("数据不存在！"));
	}
}
