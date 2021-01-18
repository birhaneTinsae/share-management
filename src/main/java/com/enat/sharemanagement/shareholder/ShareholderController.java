package com.enat.sharemanagement.shareholder;

import com.enat.sharemanagement.guardian.Guardian;
import com.enat.sharemanagement.guardian.GuardianDTO;
import com.enat.sharemanagement.utils.BarCodeUtils;
import com.enat.sharemanagement.utils.Common;
import com.enat.sharemanagement.utils.PaginatedResultsRetrievedEvent;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import static com.enat.sharemanagement.utils.Util.dtoMapper;
import static com.enat.sharemanagement.utils.Util.mapList;

@RestController
@RequestMapping("/shareholders")
@RequiredArgsConstructor
@Log4j2
public class ShareholderController implements Common<ShareholderDTO, ShareholderDTO, Long> {
    private final ShareholderService service;
    private final ModelMapper modelMapper;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public ShareholderDTO store(@Valid ShareholderDTO shareholderDTO) {
        log.info("Create new shareholder");
        if (Period.between(shareholderDTO.getDob(), LocalDate.now()).getYears() < 18 && shareholderDTO.getGuardian() == null) {
            throw new IllegalArgumentException("Shareholder below age of 18 should have guardian");
        }
        Shareholder shareholder = dtoMapper(shareholderDTO, Shareholder.class, modelMapper);
        if (shareholderDTO.getGuardian() != null) {
            List<Guardian> guardians = mapList(shareholderDTO.getGuardian(), Guardian.class, modelMapper);
            shareholder.setGuardian(guardians);
        }
        return getShareholderDTO(service.store(shareholder));
    }

    @Override
    public Iterable<ShareholderDTO> store(List<@Valid ShareholderDTO> t) {
        return null;
    }

    @Override
    public ShareholderDTO show(Long id) {
        return getShareholderDTO(service.show(id));
    }

    @Override
    public ShareholderDTO update(Long id, @Valid ShareholderDTO shareholderDTO) {
        return getShareholderDTO(service.update(id, dtoMapper(shareholderDTO, Shareholder.class, modelMapper)));
    }

    @Override
    public boolean delete(Long id) {
        return service.delete(id);
    }

    @GetMapping()
    public ResponseEntity<PagedModel<ShareholderDTO>> getAll(@Parameter(description = "pagination object",
            schema = @Schema(implementation = Pageable.class))
                                                             @Valid Pageable pageable
            , PagedResourcesAssembler assembler
            , UriComponentsBuilder uriBuilder

            , final HttpServletResponse response) {
        eventPublisher.publishEvent(new PaginatedResultsRetrievedEvent<>(
                ShareholderDTO.class, uriBuilder, response, pageable.getPageNumber(), service.getAll(pageable).getTotalPages(), pageable.getPageSize()));
        return new ResponseEntity<PagedModel<ShareholderDTO>>(assembler.toModel(service.getAll(pageable).map(this::getShareholderDTO)), HttpStatus.OK);

    }

    @Override
    public Iterable<ShareholderDTO> getAll(Pageable pageable) {
        return null;
    }


    @GetMapping(value = "/QRCode/{shareholderId}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<BufferedImage> generateIDQRCode(@PathVariable("shareholderId") Long shareholderId)
            throws Exception {
        Shareholder shareholder = service.show(shareholderId);
        String qrText = String.format("Id : %d %nName: %s", shareholder.getId(), shareholder.getFirstName());
        return ResponseEntity.ok(BarCodeUtils.generateQRCodeImage(qrText));
    }

//    public ResponseEntity<> issueCertificate(){
//
//    }

    public ShareholderDTO getShareholderDTO(Shareholder shareholder) {
        ShareholderDTO shareholderDTO = dtoMapper(shareholder, ShareholderDTO.class, modelMapper);
        if (shareholder.getGuardian() != null) {
            shareholderDTO.setGuardian(mapList(shareholder.getGuardian(), GuardianDTO.class, modelMapper));
        }

        return shareholderDTO;
    }
}
