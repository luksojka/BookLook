package com.booklook.booklook.order.domain;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Recipient {
    private String name;
    private String phone;
    private String city;
    private String street;
    private String zipCode;
    private String email;
}
