package pl.piomin.samples.spring.graphql.anno.repository;

import org.springframework.data.repository.CrudRepository;

import pl.piomin.samples.spring.graphql.anno.domain.Organization;


public interface OrganizationRepository extends CrudRepository<Organization, Integer> {
}
