package com.booklook.booklook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BooklookOnlineStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(BooklookOnlineStoreApplication.class, args);
    }

}
