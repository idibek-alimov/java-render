package maryam;

import lombok.RequiredArgsConstructor;
import maryam.data.user.UserRepository;
import maryam.models.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
@RequiredArgsConstructor
class MaryamApplicationTests {
//	private UserRepository userRepository;
//	private PasswordEncoder passwordEncoder;
	@Test
	void contextLoads() {
		//userRepository.save(new User("username",passwordEncoder.encode("alik3669")));
	}

//	@Test
//	void CreamPie() {
//		userRepository.save(new User("username",passwordEncoder.encode("alik3669")));
//	}

}
