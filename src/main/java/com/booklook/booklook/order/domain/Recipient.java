package com.booklook.booklook.order.domain;

import com.booklook.booklook.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Recipient extends BaseEntity {

    private String name;
    private String phone;
    private String city;
    private String street;
    private String zipCode;
    private String email;

}
