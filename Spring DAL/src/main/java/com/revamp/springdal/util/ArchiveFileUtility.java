package com.revamp.springdal.util;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.revamp.springdal.dto.MessageDto;
import com.revamp.springdal.dto.NotifyDto;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.io.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author ِAhmed.Elbahy
 */
@Component
public class ArchiveFileUtility {
    private static final Logger LOGGER = LoggerFactory.getLogger(ArchiveFileUtility.class);
    private static final String SYSTEM_NOTIFICATION_TOPIC = "archive";
    @Autowired
    private KafkaTemplate<String, NotifyDto> kafkaTemplate;
    @Autowired
    private GridFsTemplate gridFsTemplate;

    public boolean startArchiveOutputFile(Map<String, List<MessageDto>> groupingBatchData) {
        if (groupingBatchData == null || groupingBatchData.isEmpty())
            return false;
        List<String> newOutPutID = new ArrayList<>();
        Map<String, String> parameterMap = null;
        for (Map.Entry<String, List<MessageDto>> entry : groupingBatchData.entrySet()) {
            String title = "OutPut" + System.currentTimeMillis();
            parameterMap = new HashMap<>();
            parameterMap.put("outPut-title", title);
            parameterMap.put("LocalTime", LocalTime.now() + "");
            parameterMap.put("InputFileId", entry.getKey());
            try {
                InputStream inputStream = writeDataToInputStream(entry.getValue());
                newOutPutID.add(addFile(title, parameterMap, "csv", inputStream));
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        LOGGER.info("####### Out Put File ids ["+newOutPutID.toString()+"]########");
        return true;
    }

    private InputStream writeDataToInputStream(List<MessageDto> records) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);

//        HeaderData header = (HeaderData) records.stream().filter(e -> e instanceof HeaderData).findFirst().get();
//        oos.writeObject(header.toString());

        List<MessageDto> dataList = records.stream().filter(e -> e instanceof MessageDto).collect(Collectors.toList());
        dataList.forEach(record -> {
            try {
                oos.writeObject(record);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

//        FooterData footer = (FooterData) records.stream().filter(e -> e instanceof FooterData).findFirst().get();
//        oos.writeObject(footer.toString());

        oos.flush();
        oos.close();

        return new ByteArrayInputStream(baos.toByteArray());
    }

    public String addFile(String title, Map<String, String> parameterMap, String contentType, InputStream inputStream) throws IOException {
        DBObject metaData = new BasicDBObject();
        metaData.putAll(parameterMap);
        ObjectId id = gridFsTemplate.store(
                inputStream, title, contentType, metaData);
        kafkaTemplate.send(SYSTEM_NOTIFICATION_TOPIC, new NotifyDto(id.toString(), "File Upload Successfully"));
        return id.toString();
    }
}
