package io.dickson.liquibasesample;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CUSTOMER")
public class CustomerEntity implements Serializable {

    @Id
    @Column(name = "ID")
    Integer id;

    @Column(name = "UNIQUE_ID")
    String uniqueId;

    @Column(name = "NAME")
    String name;

    @Column(name = "EMAIL")
    String email;

    @Column(name = "PHONE")
    String phone;

    @Column(name = "SSN")
    String ssn;

    @Column(name = "BATCH_ID")
    String batchId;

    @Column(name = "BATCH_NAME")
    String batchName;

    public CustomerEntity(String name) {
        this.name = name;
    }
}
