package com.booklook.booklook;

import com.booklook.booklook.order.application.OrdersProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableJpaAuditing
@EnableScheduling
@SpringBootApplication
@EnableConfigurationProperties(OrdersProperties.class)
public class BooklookOnlineStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(BooklookOnlineStoreApplication.class, args);
    }

}
