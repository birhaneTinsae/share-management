package com.enat.sharemanagement.guardian;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "guardian",collectionResourceRel = "guardian")
public interface GuardianRepository extends PagingAndSortingRepository<Guardian, Long> {
}
