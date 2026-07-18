package in.amulya.dingdong.controller;

import in.amulya.dingdong.model.ioobject.AuthenticatinRequest;
import in.amulya.dingdong.model.ioobject.AuthenticationResponse;
import in.amulya.dingdong.service.AppUserDetailService;
import in.amulya.dingdong.util.jwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class AuthController {
    AuthenticationManager authenticationManager;
    AppUserDetailService userDetailsService;
    jwtUtil jwtUtil;
    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody AuthenticatinRequest request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
         UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
         String jwtToken = jwtUtil.generateToken(userDetails);
        return new  AuthenticationResponse(request.getEmail(), jwtToken);
    }

}
