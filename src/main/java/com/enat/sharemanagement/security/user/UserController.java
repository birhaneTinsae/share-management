package com.enat.sharemanagement.security.user;


import com.enat.sharemanagement.utils.Common;
import com.enat.sharemanagement.utils.PaginatedResultsRetrievedEvent;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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
import java.security.Principal;
import java.util.List;

import static com.enat.sharemanagement.utils.Util.dtoMapper;

@RestController
@RequestMapping("users")
@Log4j2
@RequiredArgsConstructor
public class UserController implements Common<UserDTO, UserResponseDTO,Long> {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final ApplicationEventPublisher eventPublisher;


    @Operation(summary = "My endpoint", security = @SecurityRequirement(name = "basicAuth"))
    @GetMapping("/authorities")
    public/* java.util.Collection<? extends org.springframework.security.core.GrantedAuthority>*/User getUser(Principal principal) {
        return userService.getUserByUsername(principal.getName());
    }


    @Operation(summary = "Find all Users", security = @SecurityRequirement(name = "basicAuth"), description = "Return all user with paging", tags = {"Users"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = User.class))))})
    @GetMapping()
    public ResponseEntity<PagedModel<UserResponseDTO>> getAll(@Parameter(description = "pagination object",
            schema = @Schema(implementation = Pageable.class))
                                                      @Valid Pageable pageable
            , PagedResourcesAssembler assembler
            , UriComponentsBuilder uriBuilder

            , final HttpServletResponse response) {
        eventPublisher.publishEvent(new PaginatedResultsRetrievedEvent<>(
                UserResponseDTO.class, uriBuilder, response, pageable.getPageNumber(), userService.getAll(pageable).getTotalPages(), pageable.getPageSize()));
        return new ResponseEntity<PagedModel<UserResponseDTO>>(assembler.toModel(userService.getAll(pageable).map(u->dtoMapper(u, UserResponseDTO.class,modelMapper))), HttpStatus.OK);

    }

    @Operation(summary = "User password Reset endpoint", security = @SecurityRequirement(name = "basicAuth"))
    @PutMapping("/password-reset")
    public UserResponseDTO passwordReset(@RequestBody UserPasswordDTO user) {

        return dtoMapper(userService.passwordRest(user), UserResponseDTO.class,modelMapper);
    }

    @Operation(summary = "Create User", security = @SecurityRequirement(name = "basicAuth"))
    @Override
    public UserResponseDTO store(@Valid UserDTO userDto) {
        User user = dtoMapper(userDto, User.class, modelMapper);
        return dtoMapper(userService.store(user), UserResponseDTO.class, modelMapper);
    }

    @Override
    public Iterable<UserResponseDTO> store(List<@Valid UserDTO> t) {
        return null;
    }

    @Override
    public UserResponseDTO show(Long id) {
        return dtoMapper(userService.show(id), UserResponseDTO.class, modelMapper);
    }

    @Override
    public UserResponseDTO update(Long id, @Valid UserDTO userDto) {
        User user = userService.update(id, dtoMapper(userDto, User.class, modelMapper));
        return dtoMapper(user, UserResponseDTO.class, modelMapper);
    }

    @Override
    public boolean delete(Long id) {
        return userService.delete(id);
    }



    @Override
    public Iterable<UserResponseDTO> getAll(Pageable pageable) {
        return null;
    }


}