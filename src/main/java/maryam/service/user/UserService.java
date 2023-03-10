package maryam.service.user;

import lombok.RequiredArgsConstructor;
import maryam.data.role.RoleRepository;
import maryam.data.user.UserRepository;
import maryam.data.user.VerificationCodeRepository;
import maryam.models.role.Role;
import maryam.models.user.User;
import maryam.models.user.VerificationCode;
import maryam.service.mail.EmailSenderService;
import maryam.storage.FileStorageService;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

@Service @RequiredArgsConstructor @Transactional
public class UserService implements UserServiceInterface, UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final FileStorageService storageService;
    private final EmailSenderService emailSenderService;
    private final VerificationCodeRepository verificationCodeRepository;

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
        return userRepository.save(user);
    }

    @Override
    public Role saveRole(Role role){
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String username,String name){
        User user = userRepository.findByUsername(username);
        Role role = roleRepository.findByName(name);
        user.getRoles().add(role);
    }

    @Override
    public User getUser(String username){
        return userRepository.findByUsername(username);
    }
    @Override
    public List<User> getUsers(){
        return (List<User>) userRepository.findAll();
    }
    public User changeName(String name){
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User user = userRepository.findByUsername(username);
        user.setName(name);
        userRepository.save(user);
        return user;
    }

    public User changeEmail(String email){
        System.out.println(1);
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        System.out.println(2);
        User user = userRepository.findByUsername(username);
        System.out.println(3);
        user.setEmail(email);
        System.out.println(4);
        userRepository.save(user);
        System.out.println(5);
        return user;
    }
    public User changeGender(String gender){
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User user = userRepository.findByUsername(username);

        if(gender.equals("male")){
            user.setGender(User.Gender.Male);
        } else if (gender.equals("female")) {
            user.setGender(User.Gender.Female);
        }
        userRepository.save(user);
        return user;
    }
    public User changePhoneNumber(String phoneNumber){
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User user = userRepository.findByUsername(username);
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
        emailSenderService.sendMail(email,
                "Verifying your email",
                "Your verification code is "+code+". Please do not give this code to anybody else");
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
}
