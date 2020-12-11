package com.jjstudio.controller;

import com.jjstudio.dto.AuthenticationRequest;
import com.jjstudio.dto.AuthenticationResponse;
import com.jjstudio.dto.InvalidateTokenRequest;
import com.jjstudio.service.MyUserDetailsServiceImpl;
import com.jjstudio.service.JwtUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/v1/auth")
@RestController
@CrossOrigin("http://localhost:3000")
public class AuthController {

    @Autowired
    private MyUserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @ApiOperation(value = "Get JWT", notes = "${AuthController.authenticateUser.notes}")
    @PostMapping
    public ResponseEntity<AuthenticationResponse> authenticateUser(@RequestBody AuthenticationRequest request) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());

        final String jwt = jwtUtil.generateToken(userDetails);

        return new ResponseEntity<>(new AuthenticationResponse(jwt), HttpStatus.OK);
    }

    @ApiOperation(value = "Invalidate JWT", notes = "${AuthController.invalidateToken.notes")
    @PostMapping("/logout")
    public ResponseEntity<String> invalidateToken(@RequestBody InvalidateTokenRequest request,
                                                  Authentication authentication) {
        // TODO: Invalidate user if JWT is valid
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        return new ResponseEntity<>("User has been logged out", HttpStatus.NO_CONTENT);
    }

}
