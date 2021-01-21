package com.enat.sharemanagement.security;

import com.enat.sharemanagement.exceptions.ApiError;
import com.enat.sharemanagement.security.user.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@RequiredArgsConstructor
public class MyAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            AuthenticationRequest authenticationRequest = new ObjectMapper()
                    .readValue(request.getInputStream(),
                            AuthenticationRequest.class);

//            String usernamePoskey = String.format("%s%s%s", authenticationRequest.getUsername().trim(),
//                    String.valueOf(Character.LINE_SEPARATOR), authenticationRequest.getPosKey());

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(),
                            authenticationRequest.getPassword(),
                            new ArrayList<>()
                    )
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (AuthenticationException e) {
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e);
            try {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write(new ObjectMapper().writeValueAsString(apiError));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        UserDetails userDetails = (UserDetails) authResult.getPrincipal();
        var user = userRepository.findByUsername(userDetails.getUsername()).get();

        String token = jwtUtil.generateToken(userDetails,user.getFullName());
        response.setContentType("application/json");
        response.getWriter().write(new ObjectMapper().writeValueAsString(new AuthenticationResponse(token, userDetails.getUsername(), user.getFullName(), user.isFirstLogin())));
    }


}
