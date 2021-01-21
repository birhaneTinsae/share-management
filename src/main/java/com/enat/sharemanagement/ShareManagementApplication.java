package com.enat.sharemanagement;

import com.enat.sharemanagement.storage.StorageProperties;
import com.enat.sharemanagement.security.user.User;
import com.enat.sharemanagement.security.user.UserRepository;
import com.enat.sharemanagement.utils.ApplicationProps;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.awt.image.BufferedImage;
import java.util.Optional;

@SpringBootApplication
@EnableConfigurationProperties(value ={ StorageProperties.class, ApplicationProps.class})
@EnableScheduling
@Log4j2
@RequiredArgsConstructor
public class ShareManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShareManagementApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Component
    class CreatUser implements CommandLineRunner {
        @Autowired
        private UserRepository repository;
        @Autowired
        private PasswordEncoder encoder;

        @Override
        public void run(String... args) throws Exception {
            Optional<User> admin = repository.findByUsername("Admin");
            if (admin.isEmpty()) {
                User user = new User();
                user.setUsername("Admin");
                user.setPassword(encoder.encode("6DKgfJLT56de6jFp"));
                user.setEnabled(true);
                user.setAccountNonExpired(true);
                user.setAccountNonLocked(true);
                user.setActive(true);
                user.setCredentialsNonExpired(true);

                repository.save(user);
            }

            SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
            log.info(Encoders.BASE64.encode(key.getEncoded()));

        }

    }
    @Bean
    public HttpMessageConverter<BufferedImage> createImageHttpMessageConverter() {
        return new BufferedImageHttpMessageConverter();
    }
}
