package com.revamp.springdal.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HeaderData extends MessageDto {
    private String batchID;
    private String tag;
    private String date;
    private String userName;
    private String csp;

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(tag);
        stringBuilder.append(",");
        stringBuilder.append(date);
        stringBuilder.append(",");
        stringBuilder.append(csp);
        stringBuilder.append(",");
        stringBuilder.append(userName);
        return stringBuilder.toString();
    }
}
