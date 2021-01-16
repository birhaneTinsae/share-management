package com.enat.sharemanagement.share;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path="shares")
public interface ShareRepository extends PagingAndSortingRepository<Share,Long> {
}
