package com.revamp.springdal.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CustomerOutputDTO {

    private Integer id;
    private String name;
    private String phone;
    private String uniqueId;
    private String email;
    private String ssn;
    private String batchId;
    private String batchName;
    private boolean status;

}