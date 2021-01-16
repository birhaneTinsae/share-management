/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enat.sharemanagement.config;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

/**
 *
 * @author birhane
 */
@RepositoryRestResource(collectionResourceRel = "settings",path = "settings")
public interface SettingRepository extends PagingAndSortingRepository<Setting, Long> {

    Optional<Setting> findByName(String name);
}
