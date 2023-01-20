package com.booklook.booklook.order.domain;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Recipient {
    String name;
    String phone;
    String city;
    String street;
    String zipCode;
    String email;
}
