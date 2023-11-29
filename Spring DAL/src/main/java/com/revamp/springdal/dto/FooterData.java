package com.revamp.springdal.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FooterData extends MessageDto {
    private String batchID;
    private String tag;
    private String recordCountNum;

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(tag);
        stringBuilder.append(",");
        stringBuilder.append(recordCountNum);
        return stringBuilder.toString();
    }
}
