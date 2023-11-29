package com.revamp.springdal.model;

import lombok.*;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("CustomerRedis")
public class CustomerRedis implements Serializable {

    @Id
    @Column(name = "batch_id")
    String batchId;

    @Column(name = "batch_name")
    String batchName;

    @Column(name = "count")
    BigDecimal count;

    @Column(name = "sucess")
    BigDecimal sucess;

    @Column(name = "fail")
    BigDecimal fail;
}
