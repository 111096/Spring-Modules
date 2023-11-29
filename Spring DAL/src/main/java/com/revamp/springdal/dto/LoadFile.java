package com.revamp.springdal.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoadFile {

    private String filename;
    private String fileType;
    private String fileSize;
    private String batchName;
    private byte[] file;

}
