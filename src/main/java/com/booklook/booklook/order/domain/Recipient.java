package com.booklook.booklook.order.domain;

import com.booklook.booklook.jpa.BaseEntity;
import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Recipient extends BaseEntity {
    private String email;
    private String name;
    private String phone;
    private String city;
    private String street;
    private String zipCode;
}
