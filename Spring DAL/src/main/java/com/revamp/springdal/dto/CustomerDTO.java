package com.revamp.springdal.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO extends MessageDto {

    private Integer id;
    private String name;
    private String phone;
    private String uniqueId;
    private String email;
    private String ssn;

    public CustomerDTO(Integer id, String name, String phone, String email, String ssn) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.ssn = ssn;
    }
}