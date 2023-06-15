package maryam;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:application-${spring.profiles.active:default}.properties")
public class MaryamApplication {
//	@Bean
//	public ModelMapper modelMapper() {
//		return new ModelMapper();
//	}
	public static void main(String[] args) {
		SpringApplication.run(MaryamApplication.class, args);
	}
}
