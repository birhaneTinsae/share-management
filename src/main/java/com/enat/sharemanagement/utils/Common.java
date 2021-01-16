/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enat.sharemanagement.utils;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author birhane
 */
public interface Common<T, Z,Y> {
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "", produces = {"application/json"})
    Z store(@RequestBody @Valid T t);

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/list", produces = {"application/json"})
    Iterable<Z> store(@RequestBody List<@Valid T> t);

    @GetMapping(value = "/{id}", produces = {"application/json"})
    Z show(@PathVariable Y id);

    @PutMapping(value = "/{id}", produces = {"application/json"})
    Z update(@PathVariable Y id, @RequestBody @Valid T t);

    @DeleteMapping(value = "/{id}", produces = {"application/json"})
    boolean delete(@PathVariable Y id);

    /**
     * @return
     */
//    @GetMapping("/")
//    Iterable<Z> getAll();
    @GetMapping(value = "/all", produces = {"application/json"})
    Iterable<Z> getAll(Pageable pageable);

//    @GetMapping("/search")
//    @Nullable
//    Iterable<Z> search(@RequestParam(value = "search") String searchQuery);
}
