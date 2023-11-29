package com.revamp.springdal.util;

import com.revamp.springdal.dto.CustomerDTO;
import com.revamp.springdal.dto.CustomerOutputDTO;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ValidateCsv {
    private static String csvExtension = "csv";

    private static ValidateCsv validateCsv = null;

    private ValidateCsv() {
        // TODO Auto-generated constructor stub

    }

    public static ValidateCsv getInstance() {
        if (validateCsv == null) {
            validateCsv = new ValidateCsv();
        }
        return validateCsv;
    }

    public static boolean customersToCsvHeader(Writer writer, List<CustomerOutputDTO> customers) throws IOException {

        try (CSVPrinter csvPrinter = new CSVPrinter(writer,
                CSVFormat.DEFAULT.builder().setHeader("id", "name", "phone", "uniqueId", "email", "ssn", "batchId", "batchName", "status").build());) {
            for (CustomerOutputDTO customer : customers) {
                List<String> data = Arrays.asList(String.valueOf(customer.getId()), customer.getName(),
                        customer.getPhone(), customer.getUniqueId(), customer.getEmail(), customer.getSsn(),
                        customer.getBatchId(), customer.getBatchName(), String.valueOf(customer.isStatus()));

                csvPrinter.printRecord(data);
            }
            csvPrinter.flush();
            return true;
        } catch (Exception e) {
            System.out.println("Writing CSV error!");
            e.printStackTrace();
            return false;
        }
    }

    public static boolean customersToCsv(Writer writer, List<CustomerOutputDTO> customers) throws IOException {

        try (CSVPrinter csvPrinter = new CSVPrinter(writer,
                CSVFormat.DEFAULT.builder().build());) {
            for (CustomerOutputDTO customer : customers) {
                List<String> data = Arrays.asList(String.valueOf(customer.getId()), customer.getName(),
                        customer.getPhone(), customer.getUniqueId(), customer.getEmail(), customer.getSsn(),
                        customer.getBatchId(), customer.getBatchName(), String.valueOf(customer.isStatus()));

                csvPrinter.printRecord(data);
            }
            csvPrinter.flush();
            return true;
        } catch (Exception e) {
            System.out.println("Writing CSV error!");
            e.printStackTrace();
            return false;
        }
    }

    public static List<CustomerDTO> parseCsvFile(InputStream is) {
        BufferedReader fileReader = null;
        CSVParser csvParser = null;

        List<CustomerDTO> customers = new ArrayList<>();

        try {
            fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            csvParser = new CSVParser(fileReader,
                    CSVFormat.DEFAULT.builder().setIgnoreEmptyLines(true).setHeader().setSkipHeaderRecord(true).setTrim(true).setDelimiter(',').build());

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                CustomerDTO customer = new CustomerDTO(Integer.parseInt(csvRecord.get("id")), csvRecord.get("name"),
                        csvRecord.get("phone"), csvRecord.get("ssn"),
                        csvRecord.get("email"));

                customers.add(customer);
            }

        } catch (Exception e) {
            System.out.println("Reading CSV Error!");
            e.printStackTrace();
        } finally {
            try {
                fileReader.close();
                csvParser.close();
            } catch (IOException e) {
                System.out.println("Closing fileReader/csvParser Error!");
                e.printStackTrace();
            }
        }

        return customers;
    }

    public static boolean isCSVFile(MultipartFile file) {
        String extension = file.getOriginalFilename().split("\\.")[1];

        if (!extension.equals(csvExtension)) {
            return false;
        }

        return true;
    }
}
