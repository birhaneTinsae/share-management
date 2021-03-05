package com.enat.sharemanagement.vote;

import com.enat.sharemanagement.security.IsAdmin;
import com.enat.sharemanagement.utils.Common;
import com.enat.sharemanagement.utils.PaginatedResultsRetrievedEvent;
import com.enat.sharemanagement.validation.MaxSizeConstraint;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

import static com.enat.sharemanagement.utils.Util.dtoMapper;
import static com.enat.sharemanagement.utils.Util.mapList;

@RestController
@RequestMapping("/candidates")
@RequiredArgsConstructor
public class CandidateController implements Common<CandidateDTO, CandidateDTO, Long> {
    private final CandidateService service;
    private final ModelMapper modelMapper;
    private final ApplicationEventPublisher eventPublisher;

    @IsAdmin
    @Override
    public CandidateDTO store(@Valid CandidateDTO candidateDTO) {
        return dtoMapper(service.store(dtoMapper(candidateDTO, Candidate.class, modelMapper)), CandidateDTO.class, modelMapper);
    }

    @Override
    public Iterable<CandidateDTO> store(List<@Valid CandidateDTO> t) {
        return null;
    }

    @Override
    public CandidateDTO show(Long id) {
        return dtoMapper(service.show(id), CandidateDTO.class, modelMapper);
    }

    @IsAdmin
    @Override
    public CandidateDTO update(Long id, @Valid CandidateDTO candidateDTO) {
        return dtoMapper(service.update(id, dtoMapper(candidateDTO, Candidate.class, modelMapper)), CandidateDTO.class, modelMapper);
    }

    @IsAdmin
    @Override
    public boolean delete(Long id) {
        return service.delete(id);
    }

    @IsAdmin
    @GetMapping
    public ResponseEntity<PagedModel<CandidateDTO>> getAll(@Parameter(description = "pagination object",
            schema = @Schema(implementation = Pageable.class))
                                                           @Valid Pageable pageable
            , PagedResourcesAssembler assembler
            , UriComponentsBuilder uriBuilder
            , final HttpServletResponse response) {
        eventPublisher.publishEvent(new PaginatedResultsRetrievedEvent<>(
                CandidateDTO.class, uriBuilder, response, pageable.getPageNumber(), service.getAll(pageable).getTotalPages(), pageable.getPageSize()));
        return new ResponseEntity<PagedModel<CandidateDTO>>(assembler.toModel(service.getAll(pageable).map(r -> dtoMapper(r, CandidateDTO.class, modelMapper))), HttpStatus.OK);

    }

    @Override
    public Iterable<CandidateDTO> getAll(Pageable pageable) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @PutMapping("/vote/{shareholderId}")
    @Transactional
    public boolean vote(@PathVariable long shareholderId, @RequestBody @MaxSizeConstraint List<CandidateDTO> candidateDTOS) {
        List<Candidate> candidates = mapList(candidateDTOS, Candidate.class, modelMapper);
        return service.vote(shareholderId, candidates);

    }

    @GetMapping("/by-user")
    public ResponseEntity<PagedModel<CandidateDTO>> getVoteByUser(@Parameter(description = "pagination object",
            schema = @Schema(implementation = Pageable.class))
                                                                  @Valid Pageable pageable
            , PagedResourcesAssembler assembler
            , UriComponentsBuilder uriBuilder
            , final HttpServletResponse response
            , Principal principal) {
        eventPublisher.publishEvent(new PaginatedResultsRetrievedEvent<>(
                CandidateDTO.class, uriBuilder, response, pageable.getPageNumber(), service.getVoteByUser(pageable, principal).getTotalPages(), pageable.getPageSize()));
        return new ResponseEntity<PagedModel<CandidateDTO>>(assembler.toModel(service.getVoteByUser(pageable, principal).map(r -> dtoMapper(r, CandidateDTO.class, modelMapper))), HttpStatus.OK);

    }

    @PutMapping("/reverse/{id}")
    @Transactional
    public boolean reverseVote(@PathVariable long id) {
        return service.reverseVote(id);
    }

}
