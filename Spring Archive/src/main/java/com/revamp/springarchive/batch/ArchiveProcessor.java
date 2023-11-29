//package com.revamp.springarchive.batch;
//
//import com.revamp.springdal.dto.CustomerDTO;
//import com.revamp.springdal.dto.CustomerOutputDTO;
//import org.springframework.batch.item.ItemProcessor;
//import org.springframework.stereotype.Component;
//
//@Component
//public class ArchiveProcessor implements ItemProcessor<CustomerDTO, CustomerOutputDTO> {
//
//    @Override
//    public CustomerOutputDTO process(CustomerDTO customer) throws Exception {
//        System.out.println("Processor :- "+customer);
//        CustomerOutputDTO customerOutputDTO = new CustomerOutputDTO();
//        customerOutputDTO.setBatchId(customer.getBatchId());
//        customerOutputDTO.setBatchName(customer.getBatchName());
//        customerOutputDTO.setId(customer.getId());
//        customerOutputDTO.setName(customer.getName());
//        customerOutputDTO.setEmail(customer.getEmail());
//        customerOutputDTO.setPhone(customer.getPhone());
//        customerOutputDTO.setSsn(customer.getSsn());
//        customerOutputDTO.setUniqueId(customer.getUniqueId());
//        customerOutputDTO.setStatus(true);
//        return customerOutputDTO;
//    }
//}
