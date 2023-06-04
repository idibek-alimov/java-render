package maryam.controller.user;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import maryam.controller.user.extra.AuthenticationRequest;
import maryam.controller.user.extra.AuthenticationResponse;
import maryam.controller.user.extra.RegisterRequest;
import maryam.models.role.Role;
import maryam.models.user.SellerProperties;
import maryam.models.user.User;
//import maryam.service.mail.EmailSenderService;
import maryam.service.user.UserService;
import maryam.service.visit.UserVisitService;
import maryam.types.Email;
import maryam.types.EmailVerifyType;
import maryam.types.TextString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserVisitService userVisitService;

    @GetMapping(path="/current")
    public User getUser(){
        return userService.getCurrentUser();
    }
    @PostMapping(path = "/save")
    public ResponseEntity<User> saveUser(@RequestBody User user){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
        user = userService.saveUser(user);
        return ResponseEntity.created(uri).body(user);
    }
    @PostMapping(path="/name/change")
    public User nameChange(@RequestBody TextString name){
        return userService.changeName(name.getText()
        );
    }
    @PostMapping(path="/email/change")
    public User emailChange(@RequestBody TextString email){
        return userService.changeEmail(email.getText());
    }
    @PostMapping(path="/email/verify")
    public User emailVerify(@RequestBody EmailVerifyType emailVerify) throws  Exception{
       return userService.completeVerification(emailVerify.getEmail(),emailVerify.getCode());
    }

    @PostMapping(path="/gender/change")
    public User genderChange(@RequestBody TextString gender){
        System.out.println(gender.getText());
        return userService.changeGender(gender.getText());
    }
    @PostMapping(path = "/phone/change")
    public User phoneNumberChange(@RequestBody TextString phoneNumber){
        return userService.changePhoneNumber(phoneNumber.getText());
    }
    @PostMapping(path = "/picture/change")
    public User profilePicChange(@RequestBody MultipartFile profilePic){
        return userService.changeProfilePic(profilePic);
    }
    @PostMapping(path = "/age/change")
    public User ageChange(@RequestBody TextString age){
        return userService.changeAge(age.getText());
    }

    @PostMapping(path="/email/send")
    public void sendEmail(@RequestBody Email email){
        System.out.println(email);
//        emailSenderService.sendMail(email.getToEmail(),email.getSubject(),email.getBody());
        System.out.println("mail send i gues");
    }
    @GetMapping(path = "/upgrade/seller")
    public User upgradeToSeller(){
        return userService.upgradeToSeller();
    }
    @GetMapping(path = "/seller/properties")
    public SellerProperties getSellerProperties(){
        return userService.getCurrentUser().getSellerProperties();
    }
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ){
        return ResponseEntity.ok(userService.register(request));
    }
    @CrossOrigin("*")
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ){
        System.out.println("authenticate request");
        System.out.println(request);
        return ResponseEntity.ok(userService.authenticate(request));
    }
    @CrossOrigin("*")
    @PostMapping("/seller/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticateSeller(
            @RequestBody AuthenticationRequest request
    ) throws  Exception{
        AuthenticationResponse authenticationResponse  = userService.authenticateSeller(request);
        if(authenticationResponse!=null) {
            return ResponseEntity.ok(userService.authenticateSeller(request));
        }
        else{
            throw new UsernameNotFoundException("this is not a seller accaunt");
        }
    }
}