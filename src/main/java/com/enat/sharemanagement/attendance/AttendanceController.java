package com.enat.sharemanagement.attendance;

import com.enat.sharemanagement.security.IsAdmin;
import com.enat.sharemanagement.utils.Common;
import com.enat.sharemanagement.utils.PaginatedResultsRetrievedEvent;
import com.enat.sharemanagement.vote.AttendanceMetric;
import com.enat.sharemanagement.vote.AttendanceMetricsDTO;
import com.google.common.collect.Lists;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

import static com.enat.sharemanagement.utils.Util.dtoMapper;
import static com.enat.sharemanagement.utils.Util.mapList;

@RestController
@RequestMapping(value = "/attendance")
@RequiredArgsConstructor
public class AttendanceController implements Common<Attendance, AttendanceDTO, Long> {
    private final AttendanceService attendanceService;
    private final ModelMapper modelMapper;

    private final ApplicationEventPublisher eventPublisher;

    @Override
    public AttendanceDTO store(@Valid Attendance attendance) {
        return dtoMapper(attendanceService.store(attendance), AttendanceDTO.class, modelMapper);
    }

    @Override
    public Iterable<AttendanceDTO> store(List<@Valid Attendance> t) {
        List<Attendance> attendances = Lists.newArrayList(attendanceService.store(t));
//                Lists.newArrayList(attendanceService.store(mapList(t, Promotion.class, modelMapper)));
        return mapList(attendances, AttendanceDTO.class, modelMapper);
//        return dtoMapper(attendanceService.store(t),AttendanceDTO.class,modelMapper);
    }

    @Override
    public AttendanceDTO show(Long id) {
        return dtoMapper(attendanceService.show(id), AttendanceDTO.class, modelMapper);
    }

    @Override
    public AttendanceDTO update(Long id, @Valid Attendance attendance) {
        return dtoMapper(attendanceService.update(id, attendance), AttendanceDTO.class, modelMapper);
    }

    @Override
    public boolean delete(Long id) {
        return attendanceService.delete(id);
    }


    @GetMapping()
    public ResponseEntity<PagedModel<AttendanceDTO>> getAll(@Parameter(description = "pagination object",
            schema = @Schema(implementation = Pageable.class))
                                                            @Valid Pageable pageable
            , PagedResourcesAssembler assembler
            , UriComponentsBuilder uriBuilder

            , final HttpServletResponse response) {
        eventPublisher.publishEvent(new PaginatedResultsRetrievedEvent<>(
                AttendanceDTO.class, uriBuilder, response, pageable.getPageNumber(), attendanceService.getAll(pageable).getTotalPages(), pageable.getPageSize()));
        return new ResponseEntity<PagedModel<AttendanceDTO>>(assembler.toModel(attendanceService.getAll(pageable)), HttpStatus.OK);

    }

    @Override
    public Page<AttendanceDTO> getAll(Pageable pageable) {
        return attendanceService.getAll(pageable).map(a -> dtoMapper(a, AttendanceDTO.class, modelMapper));
    }

    @PutMapping("/attend/{id}")
    public AttendanceDTO takeAttendance(@PathVariable("id") long id) {
        return dtoMapper(attendanceService.takeAttendance(id), AttendanceDTO.class, modelMapper);
    }

    @PutMapping("/reverse/{id}")
    public AttendanceDTO reverseAttendance(@PathVariable("id") long id) {
        return dtoMapper(attendanceService.reverseAttendance(id), AttendanceDTO.class, modelMapper);
    }

//    @PostMapping("/create")
//    @ResponseStatus(HttpStatus.ACCEPTED)
//    public boolean createAttendance(@RequestBody AttendanceDTO attendance){
//        return attendanceService.createAttendance(attendance);
//    }

    @IsAdmin
    @GetMapping("/metrics")
    public AttendanceMetricsDTO attendanceMetric() {
        return dtoMapper(attendanceService.attendanceMetric(), AttendanceMetricsDTO.class, modelMapper);
    }


}
