package com.enat.sharemanagement.notification.sms;

import com.enat.sharemanagement.shareholder.ShareholderDTO;
import com.enat.sharemanagement.utils.Common;
import com.enat.sharemanagement.utils.PaginatedResultsRetrievedEvent;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

import static com.enat.sharemanagement.utils.Util.dtoMapper;

@RestController("/sms")
@RequiredArgsConstructor
public class SMSController implements Common<SMSDTO, SMSDTO, Long> {
    private final SMSService service;
    private final ModelMapper modelMapper;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public SMSDTO store(@Valid SMSDTO smsdto) {
        return dtoMapper(service.store(dtoMapper(smsdto, SMS.class, modelMapper)), SMSDTO.class, modelMapper);
    }

    @Override
    public Iterable<SMSDTO> store(List<@Valid SMSDTO> t) {
        return null;
    }

    @Override
    public SMSDTO show(Long id) {
        return dtoMapper(service.show(id), SMSDTO.class, modelMapper);

    }

    @Override
    public SMSDTO update(Long id, @Valid SMSDTO smsdto) {
        return dtoMapper(service.update(id, dtoMapper(smsdto, SMS.class, modelMapper)), SMSDTO.class, modelMapper);

    }

    @Override
    public boolean delete(Long id) {
        return service.delete(id);
    }

    @GetMapping()
    public ResponseEntity<PagedModel<SMSDTO>> getAll(@Parameter(description = "pagination object",
            schema = @Schema(implementation = Pageable.class))
                                                     @Valid Pageable pageable
            , PagedResourcesAssembler assembler
            , UriComponentsBuilder uriBuilder

            , final HttpServletResponse response) {
        eventPublisher.publishEvent(new PaginatedResultsRetrievedEvent<>(
                SMSDTO.class, uriBuilder, response, pageable.getPageNumber(), service.getAll(pageable).getTotalPages(), pageable.getPageSize()));
        return new ResponseEntity<PagedModel<SMSDTO>>(assembler.toModel(service.getAll(pageable).map(s -> dtoMapper(s, SMSDTO.class, modelMapper))), HttpStatus.OK);

    }

    @Override
    public Iterable<SMSDTO> getAll(Pageable pageable) {
        throw new UnsupportedOperationException("Not yet implemented.");
    }
}
