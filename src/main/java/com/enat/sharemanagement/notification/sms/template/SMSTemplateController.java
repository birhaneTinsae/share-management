package com.enat.sharemanagement.notification.sms.template;

import com.enat.sharemanagement.notification.sms.SMS;
import com.enat.sharemanagement.notification.sms.SMSDTO;
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

@RestController("/sms-templates")
@RequiredArgsConstructor
public class SMSTemplateController implements Common<SMSTemplateDTO, SMSTemplateDTO, Long> {
    private final SMSTemplateService smsTemplateService;
    private final ModelMapper modelMapper;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public SMSTemplateDTO store(@Valid SMSTemplateDTO smsTemplateDTO) {
        return dtoMapper(smsTemplateService.store(dtoMapper(smsTemplateDTO, SMSTemplate.class, modelMapper)), SMSTemplateDTO.class, modelMapper);
    }

    @Override
    public Iterable<SMSTemplateDTO> store(List<@Valid SMSTemplateDTO> t) {
        return null;
    }

    @Override
    public SMSTemplateDTO show(Long id) {
        return dtoMapper(smsTemplateService.show(id), SMSTemplateDTO.class, modelMapper);
    }

    @Override
    public SMSTemplateDTO update(Long id, @Valid SMSTemplateDTO smsTemplateDTO) {
        return dtoMapper(smsTemplateService.update(id, dtoMapper(smsTemplateDTO, SMSTemplate.class, modelMapper)), SMSTemplateDTO.class, modelMapper);
    }

    @Override
    public boolean delete(Long id) {
        return smsTemplateService.delete(id);
    }

    @GetMapping()
    public ResponseEntity<PagedModel<SMSTemplateDTO>> getAll(@Parameter(description = "pagination object",
            schema = @Schema(implementation = Pageable.class))
                                                             @Valid Pageable pageable
            , PagedResourcesAssembler assembler
            , UriComponentsBuilder uriBuilder

            , final HttpServletResponse response) {
        eventPublisher.publishEvent(new PaginatedResultsRetrievedEvent<>(
                SMSTemplateDTO.class, uriBuilder, response, pageable.getPageNumber(), smsTemplateService.getAll(pageable).getTotalPages(), pageable.getPageSize()));
        return new ResponseEntity<PagedModel<SMSTemplateDTO>>(assembler.toModel(smsTemplateService.getAll(pageable).map(s -> dtoMapper(s, SMSTemplateDTO.class, modelMapper))), HttpStatus.OK);

    }

    @Override
    public Iterable<SMSTemplateDTO> getAll(Pageable pageable) {
        throw new UnsupportedOperationException("Not Implemented yet");
    }
}
