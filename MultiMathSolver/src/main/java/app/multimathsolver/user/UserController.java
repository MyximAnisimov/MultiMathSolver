package app.multimathsolver.user;

import app.multimathsolver.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {
    @Autowired
    private JwtUtils jwtTokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping(path = "/registration", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> registration(@RequestBody AuthDTO authDTO){
        if(userRepository.existsByEmail(authDTO.getEmail())){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
       User user = new User();
       user.setEmail(authDTO.getEmail());
       user.setPassword(passwordEncoder.encode(authDTO.getPassword()));
       Role roles = roleRepository.findByName("ROLE_USER").get();
       user.setRoles(Collections.singleton(roles));
       userRepository.save(user);

       return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO){
        if(!userRepository.existsByEmail(loginDTO.getEmail())){
            return new ResponseEntity<>("Данная почта не привзяна к аккаунту!", HttpStatus.BAD_REQUEST);
        }
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = userRepository.findByEmail(loginDTO.getEmail());
        String token = "";
        if(user != null){
            token = jwtTokenRepository.generateToken(authentication);
        }
        return new ResponseEntity<>(token, HttpStatus.OK);
    }
}
