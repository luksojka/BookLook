package com.booklook.booklook.order.application;

import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

import java.time.Duration;

@Value
@ConfigurationProperties("app.orders")
public class OrdersProperties {
    Duration paymentPeriod;
    String abandonCron;
}
