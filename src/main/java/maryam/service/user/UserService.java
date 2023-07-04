package maryam.service.user;
import lombok.RequiredArgsConstructor;
import maryam.controller.user.extra.AuthenticationRequest;
import maryam.controller.user.extra.AuthenticationResponse;
import maryam.controller.user.extra.RegisterRequest;
import maryam.data.role.RoleRepository;
import maryam.data.user.UserRepository;
import maryam.data.user.VerificationCodeRepository;
import maryam.filter.JwtService;
import maryam.models.role.Role;
import maryam.models.user.User;
import maryam.models.user.VerificationCode;
//import maryam.service.mail.EmailSenderService;
import maryam.service.visit.UserVisitService;
import maryam.storage.FileStorageService;
import org.jetbrains.annotations.NotNull;
//import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.transaction.Transactional;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor @Transactional
public class UserService implements UserServiceInterface, UserDetailsService {
    private final UserRepository userRepository;
    //private final RoleRepository roleRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final FileStorageService storageService;
//    private final EmailSenderService emailSenderService;
    private final VerificationCodeRepository verificationCodeRepository;
    private final SellerPropertiesService sellerPropertiesService;
    private final UserVisitService userVisitService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("User not found in the database");
        } else {
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });

        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),authorities);
    }

    @Override
    public User saveUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = userRepository.save(user);
        System.out.println(user);
        userVisitService.createUserVisits(user);
        System.out.println("121");
        sendVerificationEmail(user.getEmail());
        System.out.println("122");
        roleService.setUserRole(user);
        return user;
    }

//    @Override
//    public Role saveRole(Role role){
//        return roleRepository.save(role);
//    }

//    @Override
//    public void addRoleToUser(String username,String name){
//        User user = userRepository.findByUsername(username);
//        Role role = roleRepository.findByName(name);
//        user.getRoles().add(role);
//    }

    @Override
    public User getUser(String username){
        return userRepository.findByUsername(username);
    }
    public Optional<User> getUser(Long id){return userRepository.findById(id);}
    @Override
    public List<User> getUsers(){
        return (List<User>) userRepository.findAll();
    }
    public User getCurrentUser(){
        if (!SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")){
            return (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
        else {
            return null;
        }
    }
    public User changeName(String name){
        User user = getCurrentUser();
        user.setName(name);
        userRepository.save(user);
        return user;
    }
    public User changeAddress(String address){
        User user = getCurrentUser();
        user.setAddress(address);
        userRepository.save(user);
        return user;
    }

    public User changeEmail(String email){
        System.out.println(email);
        User user = getCurrentUser();
        user.setEmail(email);
//        sendVerificationEmail(email);
////        System.out.println(4);
        userRepository.save(user);
        return user;
    }
    public User changeGender(@NotNull String gender){
        User user = getCurrentUser();
        if(gender.toLowerCase().equals("male")){
            user.setGender(User.Gender.Male);
        } else if (gender.toLowerCase().equals("female")) {
            user.setGender(User.Gender.Female);
        }
        userRepository.save(user);
        return user;
    }
    public User changePhoneNumber(String phoneNumber){
        User user = getCurrentUser();
        user.setPhoneNumber(phoneNumber);
        userRepository.save(user);
        return user;
    }
    public User changeProfilePic(MultipartFile profilePic){
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User user = userRepository.findByUsername(username);
        String picName = storageService.save(profilePic);
        user.setProfilePic(picName);
        userRepository.save(user);
        return user;
    }
    public User changeAge(String age){
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User user = userRepository.findByUsername(username);
        user.setAge(Integer.parseInt(age));
        userRepository.save(user);
        return user;
    }

    public void sendVerificationEmail(String email){
//        System.out.println(11);
        User user = userRepository.findByEmail(email);
//        System.out.println(12);
        Integer code = new Random().nextInt(900000) + 100000;
//        System.out.println(13);
        Optional<VerificationCode> verificationCodeOptional = verificationCodeRepository.findByUser(user);
//        System.out.println(14);
        if(verificationCodeOptional.isPresent()){
//            System.out.println(15);
            VerificationCode verificationCode = verificationCodeOptional.get();
//            System.out.println(16);
            verificationCode.setCode(code);
//            System.out.println(17);
        }
        else {
//            System.out.println(18);
            VerificationCode verificationCode = verificationCodeRepository.save(new VerificationCode(code,user));
//            System.out.println(19);
        }
//        System.out.println(20);
//        emailSenderService.sendMail(email,
//                "Verifying your email",
//                "Your verification code is "+code+". Please do not give this code to anybody else");
//        System.out.println(21);
    }
    public User completeVerification(String email,Integer code) throws Exception{
        User user  = userRepository.findByEmail(email);
        VerificationCode verificationCode = verificationCodeRepository.findByUser(user).get();
        if (code.equals(verificationCode.getCode())){
            user.setActive(true);
        }
        else {
            throw new RuntimeException("Wrong code your email is not verified try agin ");
        }
        verificationCodeRepository.delete(verificationCode);
        return user;
    }
    public User upgradeToSeller(){
        User user = getCurrentUser();
        if(user.getSellerProperties()==null) {
            user.setSellerProperties(sellerPropertiesService.createSellerProperties(user));
        }
        user = roleService.setSellerRole(user);
        user = userRepository.save(user);
        return user;
    }
    public User upgradeToManager(){
        User user = getCurrentUser();
        user = roleService.setManagerRole(user);
        userRepository.save(user);
        return user;
    }
    public AuthenticationResponse register(RegisterRequest request){
        var user = User
                .builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .build();
        userRepository.save(user);
        user = roleService.setUserRole(user);
        userRepository.save(user);
        var accessToken = jwtService.generateToken(user,1);
        var refreshToken = jwtService.generateToken(user,2);
        return AuthenticationResponse.builder().access_token(accessToken).refresh_token(refreshToken).build();
    }
    public AuthenticationResponse refreshToken(){
        User user = getCurrentUser();
        var accessToken = jwtService.generateToken(user,1);
        var refreshToken = jwtService.generateToken(user,2);
        return AuthenticationResponse.builder().access_token(accessToken).refresh_token(refreshToken).build();
    }
    public AuthenticationResponse authenticate(AuthenticationRequest request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));
        var user = userRepository.findByUsername(request.getUsername());
        var accessToken = jwtService.generateToken(user,1);
        var refreshToken = jwtService.generateToken(user,2);
        return AuthenticationResponse.builder().access_token(accessToken).refresh_token(refreshToken).build();

    }
    public AuthenticationResponse authenticateSeller(AuthenticationRequest request) throws Exception{
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));
        var user = userRepository.findByUsername(request.getUsername());

        if(user.getSellerProperties()!=null) {
            var accessToken = jwtService.generateToken(user, 1);
            var refreshToken = jwtService.generateToken(user, 2);
            return AuthenticationResponse.builder().access_token(accessToken).refresh_token(refreshToken).build();
        }
        else{
            throw new Exception("This is not a seller");
        }
    }
}
