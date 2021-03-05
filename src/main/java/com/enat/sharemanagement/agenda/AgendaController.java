package com.enat.sharemanagement.agenda;

import com.enat.sharemanagement.security.IsAdmin;
import com.enat.sharemanagement.utils.Common;
import com.enat.sharemanagement.utils.PaginatedResultsRetrievedEvent;
import com.google.common.collect.Lists;
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
import javax.validation.Valid;
import java.util.List;

import static com.enat.sharemanagement.utils.Util.dtoMapper;
import static com.enat.sharemanagement.utils.Util.mapList;

@RestController
@RequestMapping("/agendas")
@RequiredArgsConstructor
public class AgendaController implements Common<AgendaDTO, AgendaDTO, Long> {
    private final AgendaService agendaService;
    private final ModelMapper modelMapper;
    private final ApplicationEventPublisher eventPublisher;

    @IsAdmin
    @Override
    public AgendaDTO store(@Valid AgendaDTO agendaDTO) {
        return dtoMapper(agendaService.store(dtoMapper(agendaDTO, Agenda.class, modelMapper)), AgendaDTO.class, modelMapper);
    }

    @IsAdmin
    @Override
    public Iterable<AgendaDTO> store(List<@Valid AgendaDTO> t) {
        List<Agenda> agendas = Lists.newArrayList(agendaService.store(mapList(t, Agenda.class, modelMapper)));
        return mapList(agendas, AgendaDTO.class, modelMapper);
    }

    @Override
    public AgendaDTO show(Long id) {
        return dtoMapper(agendaService.show(id), AgendaDTO.class, modelMapper);
    }

    @IsAdmin
    @Override
    public AgendaDTO update(Long id, @Valid AgendaDTO agendaDTO) {
        return dtoMapper(agendaService.update(id, dtoMapper(agendaDTO, Agenda.class, modelMapper)), AgendaDTO.class, modelMapper);
    }

    @IsAdmin
    @Override
    public boolean delete(Long id) {
        return agendaService.delete(id);
    }


    @GetMapping()
    public ResponseEntity<PagedModel<AgendaDTO>> getAll(@Parameter(description = "pagination object",
            schema = @Schema(implementation = Pageable.class))
                                                        @Valid Pageable pageable
            , PagedResourcesAssembler assembler
            , UriComponentsBuilder uriBuilder

            , final HttpServletResponse response) {
        eventPublisher.publishEvent(new PaginatedResultsRetrievedEvent<>(
                AgendaDTO.class, uriBuilder, response, pageable.getPageNumber(), agendaService.getAll(pageable).getTotalPages(), pageable.getPageSize()));
        return new ResponseEntity<PagedModel<AgendaDTO>>(assembler.toModel(agendaService.getAll(pageable).map(a -> dtoMapper(a, AgendaDTO.class, modelMapper))), HttpStatus.OK);

    }

    @Override
    public Iterable<AgendaDTO> getAll(Pageable pageable) {
        throw new UnsupportedOperationException("Not Implemented yet");
    }

    @PostMapping("/vote/{agendaId}/attendance/{attendanceId}")
    public AgendaVoteDTO vote(@PathVariable("agendaId") Long agendaId, @PathVariable Long attendanceId, @RequestParam int vote) {
        return dtoMapper(agendaService.vote(agendaId, attendanceId, vote), AgendaVoteDTO.class, modelMapper);
    }

    @IsAdmin
    @GetMapping("/summary")
    public List<AgendaSummaryDTO> getAgendaSummary() {
        return mapList(agendaService.getAgendaSummery(), AgendaSummaryDTO.class, modelMapper);
    }
}
