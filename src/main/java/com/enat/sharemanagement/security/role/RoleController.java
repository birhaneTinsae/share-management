package com.enat.sharemanagement.security.role;


import com.enat.sharemanagement.security.IsAdmin;
import com.enat.sharemanagement.utils.Common;
import com.enat.sharemanagement.utils.PaginatedResultsRetrievedEvent;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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


@RestController
@RequestMapping("roles")
@Tag(name = "Role", description = "Role API")
@RequiredArgsConstructor
public class RoleController implements Common<RoleDTO, RoleDTO,Long> {

    private final RoleService roleService;
    private final ModelMapper modelMapper;
    private final ApplicationEventPublisher eventPublisher;

    @IsAdmin
    @Operation(summary = "Create new Role",
            description = "This API creates new Role",
            tags = {"Role"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Role created",
                    content = @Content(schema = @Schema(implementation = Role.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "409", description = "Role already exists")})
    @Override
    public RoleDTO store(@Parameter(description = "Role to add. Cannot null or empty.",
            required = true, schema = @Schema(implementation = Role.class))
                         @Valid @RequestBody RoleDTO role) {
        return dtoMapper(roleService.store(dtoMapper(role, Role.class, modelMapper)), RoleDTO.class, modelMapper);
    }

    @IsAdmin
    @Override
    public Iterable<RoleDTO> store(List<RoleDTO> t) {
        return null;
    }

    @IsAdmin
    @Operation(summary = "Find Role by ID",
            description = "Returns a single Role",
            tags = {"Role"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(schema = @Schema(implementation = Role.class))),
            @ApiResponse(responseCode = "404", description = "Role not found")})
    @Override
    public RoleDTO show(@Parameter(description = "Id of the Role to be obtained. Cannot be empty.", required = true)
                        @PathVariable Long id) {
        return dtoMapper(roleService.show(id), RoleDTO.class,modelMapper);
    }

    @IsAdmin
    @Operation(summary = "Update an existing Role", description = "", tags = {"Role"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
            @ApiResponse(responseCode = "404", description = "Contact not found"),
            @ApiResponse(responseCode = "405", description = "Validation exception")})

    @Override
    public RoleDTO update(@Parameter(description = "Id of the Role to be update. Cannot be empty.",
            required = true)
                          @PathVariable Long id,
                          @Parameter(description = "Role to update. Cannot null or empty.",
                                  required = true, schema = @Schema(implementation = Role.class))
                          @Valid @RequestBody RoleDTO role) {
        return dtoMapper(roleService.update(id, dtoMapper(role,Role.class,modelMapper)), RoleDTO.class,modelMapper);
    }

    @IsAdmin
    @Operation(summary = "Deletes a Role", description = "", tags = {"Role"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "404", description = "Role not found")})
    @Override
    public boolean delete(@Parameter(description = "Id of the Role to be obtained. Cannot be empty.", required = true) @PathVariable Long id) {
        return roleService.delete(id);
    }

    @IsAdmin
    @Operation(summary = "Find all Role", description = "Name search by %name% format", tags = {"Role"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Role.class))))})
    @GetMapping()
    public ResponseEntity<PagedModel<RoleDTO>> getAll(@Parameter(description = "pagination object",
            schema = @Schema(implementation = Pageable.class))
                                                      @Valid Pageable pageable, Sort sort
            , PagedResourcesAssembler assembler
            , UriComponentsBuilder uriBuilder

            , final HttpServletResponse response) {
        eventPublisher.publishEvent(new PaginatedResultsRetrievedEvent<>(
                Role.class, uriBuilder, response, pageable.getPageNumber(), roleService.getAll(pageable).getTotalPages(), pageable.getPageSize()));
        return new ResponseEntity<PagedModel<RoleDTO>>(assembler.toModel(roleService.getAll(pageable).map(r->dtoMapper(r, RoleDTO.class,modelMapper))), HttpStatus.OK);

    }

    @IsAdmin
    @Operation(summary = "Find all Role", description = "Name search by %name% format", tags = {"Role"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Role.class))))})

    @Override
    public Iterable<RoleDTO> getAll(Pageable pageable) {
        return roleService.getAll(pageable).map(r->dtoMapper(r, RoleDTO.class,modelMapper));
    }
}
