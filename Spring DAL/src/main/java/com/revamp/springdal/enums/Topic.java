package com.revamp.springdal.enums;

import lombok.Data;

public enum Topic {
    BATCH("batch"),TRANSFORM("transform"),BUSINESS("business"),EVENT("event"),Archive("archive");

    String name;

    Topic(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
