package com.revamp.springdal.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Column;
import java.io.Serializable;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "CustomerMongo")
public class CustomerMongo implements Serializable {

    @Id
    private Integer id;
    private String name;
    private String phone;
    private String uniqueId;
    private String email;
    private String ssn;
}