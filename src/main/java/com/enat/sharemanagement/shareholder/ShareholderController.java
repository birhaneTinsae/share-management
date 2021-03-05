package com.enat.sharemanagement.shareholder;

import com.enat.sharemanagement.guardian.Guardian;
import com.enat.sharemanagement.guardian.GuardianDTO;
import com.enat.sharemanagement.share.Share;
import com.enat.sharemanagement.utils.*;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import static com.enat.sharemanagement.utils.Util.dtoMapper;
import static com.enat.sharemanagement.utils.Util.mapList;
import static java.util.Objects.isNull;

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
        if (Period.between(shareholderDTO.getDob(), LocalDate.now()).getYears() < 18 && isNull(shareholderDTO.getGuardian())) {
            throw new IllegalArgumentException("Shareholder below age of 18 should have guardian");
        }
        Shareholder shareholder = dtoMapper(shareholderDTO, Shareholder.class, modelMapper);
        if (!isNull(shareholderDTO.getGuardian())) {
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
    @PostMapping("/share/{id}")
    public ShareholderDTO addShare(@PathVariable("id") long id,@RequestBody Share share){
        return dtoMapper(service.addShare(id,share),ShareholderDTO.class,modelMapper);
    }

    @GetMapping("/search")
    public ResponseEntity<PagedModel<ShareholderDTO>> search(@Parameter(description = "pagination object",
            schema = @Schema(implementation = Pageable.class))
                                                             @Valid Pageable pageable
            , PagedResourcesAssembler assembler
            , UriComponentsBuilder uriBuilder
            , @RequestParam(value = "search") String searchQuery
            , final HttpServletResponse response) {
        Specification<Shareholder> spec = resolveSpecificationFromInfixExpr(searchQuery);
        eventPublisher.publishEvent(new PaginatedResultsRetrievedEvent<>(
                ShareholderDTO.class, uriBuilder, response, pageable.getPageNumber(), service.search(spec,pageable).getTotalPages(), pageable.getPageSize()));
        return new ResponseEntity<PagedModel<ShareholderDTO>>(assembler.toModel(service.search(spec,pageable).map(this::getShareholderDTO)), HttpStatus.OK);

    }

    public ShareholderDTO getShareholderDTO(Shareholder shareholder) {
        var shareholderDTO = dtoMapper(shareholder, ShareholderDTO.class, modelMapper);
        if (!isNull(shareholder.getGuardian())) {
            shareholderDTO.setGuardian(mapList(shareholder.getGuardian(), GuardianDTO.class, modelMapper));
        }
        return shareholderDTO;
    }

    protected Specification<Shareholder> resolveSpecificationFromInfixExpr(String searchParameters) {
        CriteriaParser parser = new CriteriaParser();
        GenericSpecificationsBuilder<Shareholder> specBuilder = new GenericSpecificationsBuilder<>();
        return specBuilder.build(parser.parse(searchParameters), ShareholderSpecification::new);
    }
}
