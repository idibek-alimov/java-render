package maryam.controller.user;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import maryam.models.role.Role;
import maryam.models.user.User;
import maryam.models.uservisit.Visit;
import maryam.service.mail.EmailSenderService;
import maryam.service.user.UserService;
import maryam.service.visit.UserVisitService;
import maryam.types.Email;
import maryam.types.EmailVerifyType;
import maryam.types.TextInteger;
import maryam.types.TextString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/api")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserVisitService userVisitService;

    @Autowired
    private EmailSenderService emailSenderService;
    @GetMapping(path="/users")
    public ResponseEntity<List<User>> getUsers(){
        //System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return ResponseEntity.ok().body(userService.getUsers());
    }
    @GetMapping(path="/currentuser")
    public User getUser(){
        System.out.println("inside getuser");
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        System.out.println(username);
        return userService.getUser(username);
    }
    @PostMapping(path = "/user/save")
    public ResponseEntity<User> saveUser(@RequestBody User user){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
        User createdUser = userService.saveUser(user);
        System.out.println("1");
        userVisitService.createUserVisits(user);
        System.out.println("2");
        userService.sendVerificationEmail(createdUser.getEmail());
        return ResponseEntity.created(uri).body(createdUser);
    }
    @PostMapping(path = "/role/save")
    public ResponseEntity<Role> saveRole(@RequestBody Role role){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveRole(role));
    }
    @PostMapping(path = "/user/addrole")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUser roleToUser){
        userService.addRoleToUser(roleToUser.getUsername(),roleToUser.getName());
        return ResponseEntity.ok().build();
    }
    @PostMapping(path="/user/name/change")
    public User nameChange(@RequestBody TextString name){
        return userService.changeName(name.getText()
        );
    }
    @PostMapping(path="/user/email/change")
    public User emailChange(@RequestBody TextString email){
     //   System.out.println("received email is "+email);
        return userService.changeEmail(email.getText());
    }
    @PostMapping(path="/user/email/verify")
    public User emailVerify(@RequestBody EmailVerifyType emailVerify) throws  Exception{
       return userService.completeVerification(emailVerify.getEmail(),emailVerify.getCode());
    }

    @PostMapping(path="/user/gender/change")
    public User genderChange(@RequestBody TextString gender){
        System.out.println(gender.getText());
        return userService.changeGender(gender.getText());
    }
    @PostMapping(path = "/user/phone/change")
    public User phoneNumberChange(@RequestBody TextString phoneNumber){
        return userService.changePhoneNumber(phoneNumber.getText());
    }
    @PostMapping(path = "/user/picture/change")
    public User profilePicChange(@RequestBody MultipartFile profilePic){
        return userService.changeProfilePic(profilePic);
    }
    @PostMapping(path = "/user/age/change")
    public User ageChange(@RequestBody TextString age){
        return userService.changeAge(age.getText());
    }

    @PostMapping(path="/user/email/send")
    public void sendEmail(@RequestBody Email email){
        System.out.println(email);
        emailSenderService.sendMail(email.getToEmail(),email.getSubject(),email.getBody());
        System.out.println("mail send i gues");
    }


//    @GetMapping(path = "/role/{id}")
//    public ResponseEntity<User> getUser(@RequestParam() Long id){
//        return ResponseEntity.ok().body(userService.)
//    }
    @PostMapping(path = "/token/refresh")
    public void refreshToken(HttpServletRequest request , HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                User user = userService.getUser(username);
                String access_token = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis()+30*24*60*60*1000))
                        .withIssuer(request.getRequestURI().toString())
                        .withClaim("roles",user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                        .sign(algorithm);



            } catch(Exception exception){
                response.setHeader("Error",exception.getMessage());
                response.setStatus(FORBIDDEN.value());
                Map<String,String> error = new HashMap<>();
                error.put("error message",exception.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(),error);
            }
        } else{
            throw new RuntimeException("Refresh token was not received");
        }
    }

}
@Data
class RoleToUser{
    String username;
    String name;
}
