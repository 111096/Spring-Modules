package com.revamp.springdal.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UniqueNumber {
    private static UniqueNumber uniqueNumber = null;

    private UniqueNumber() {
        // TODO Auto-generated constructor stub

    }

    public static UniqueNumber getInstance() {
        if (uniqueNumber == null) {
            uniqueNumber = new UniqueNumber();
        }
        return uniqueNumber;
    }

    SimpleDateFormat dateFormat = new SimpleDateFormat("ddMyyyyhhmmss");
    Date currentDate = new Date();

    public String uniqueId() {
        String uniqueId = dateFormat.format(currentDate);
        return uniqueId;
    }
}
