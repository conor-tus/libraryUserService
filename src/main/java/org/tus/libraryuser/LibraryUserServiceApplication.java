package org.tus.libraryuser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@EnableFeignClients
public class LibraryUserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibraryUserServiceApplication.class, args);
    }

}
