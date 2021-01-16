package com.enat.sharemanagement.attendance;

import com.enat.sharemanagement.shareholder.ShareholderDTO;
import com.enat.sharemanagement.utils.Common;
import com.enat.sharemanagement.utils.PaginatedResultsRetrievedEvent;
import com.enat.sharemanagement.vote.MetricDto;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.guieffect.qual.PolyUIType;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

import static com.enat.sharemanagement.utils.Util.dtoMapper;

@RestController
@RequestMapping(value ="/attenance")
@RequiredArgsConstructor
public class AttendanceController implements Common<Attendance, Attendance, Long> {
    private final AttendanceService attendanceService;

    private final ApplicationEventPublisher eventPublisher;

    @Override
    public Attendance store(@Valid Attendance attendance) {
        return attendanceService.store(attendance);
    }

    @Override
    public Iterable<Attendance> store(List<@Valid Attendance> t) {
        return attendanceService.store(t);
    }

    @Override
    public Attendance show(Long id) {
        return attendanceService.show(id);
    }

    @Override
    public Attendance update(Long id, @Valid Attendance attendance) {
        return attendanceService.update(id, attendance);
    }

    @Override
    public boolean delete(Long id) {
        return attendanceService.delete(id);
    }


    @GetMapping()
    public ResponseEntity<PagedModel<Attendance>> getAll(@Parameter(description = "pagination object",
            schema = @Schema(implementation = Pageable.class))
                                                             @Valid Pageable pageable
            , PagedResourcesAssembler assembler
            , UriComponentsBuilder uriBuilder

            , final HttpServletResponse response) {
        eventPublisher.publishEvent(new PaginatedResultsRetrievedEvent<>(
                Attendance.class, uriBuilder, response, pageable.getPageNumber(), attendanceService.getAll(pageable).getTotalPages(), pageable.getPageSize()));
        return new ResponseEntity<PagedModel<Attendance>>(assembler.toModel(attendanceService.getAll(pageable)), HttpStatus.OK);

    }

    @Override
    public Page<Attendance> getAll(Pageable pageable) {
        return attendanceService.getAll(pageable);
    }

    @PutMapping("/attend/{id}")
    public Attendance takeAttendance(@PathVariable("id") long id) {
        return attendanceService.takeAttendance(id);
    }

    @PutMapping("/reverse/{id}")
    public Attendance reverseAttendance(@PathVariable("id") long id) {
        return attendanceService.reverseAttendance(id);
    }

    @GetMapping("/metrics")
    public MetricDto attendanceMetric() {
        return attendanceService.attendanceMetric();
    }
}
