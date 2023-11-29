package com.revamp.springarchive.transform;

import com.revamp.springdal.dto.CustomerDTO;
import com.revamp.springdal.dto.CustomerOutputDTO;
import com.revamp.springdal.transform.TransformerInterface;

public class CustomerTransform implements TransformerInterface<CustomerOutputDTO, CustomerDTO> {
    CustomerOutputDTO outputDTO = new CustomerOutputDTO();
    CustomerDTO dto = new CustomerDTO();

    @Override
    public void convertFromEntityToDTO(CustomerOutputDTO customerOutputDTO, CustomerDTO customerDTO) {
        dto = new CustomerDTO();
        dto.setId(customerOutputDTO.getId());
        dto.setName(customerOutputDTO.getName());
        dto.setEmail(customerOutputDTO.getEmail());
        dto.setPhone(customerOutputDTO.getPhone());
        dto.setSsn(customerOutputDTO.getSsn());
        dto.setBatchId(customerOutputDTO.getBatchId());
        dto.setBatchName(customerOutputDTO.getBatchName());
    }

    @Override
    public void convertFromDTOToEntity(CustomerDTO customerDTO, CustomerOutputDTO customerOutputDTO) {
        outputDTO = new CustomerOutputDTO();
        outputDTO.setId(customerDTO.getId());
        outputDTO.setName(customerDTO.getName());
        outputDTO.setEmail(customerDTO.getEmail());
        outputDTO.setPhone(customerDTO.getPhone());
        outputDTO.setSsn(customerDTO.getSsn());
        outputDTO.setBatchId(customerDTO.getBatchId());
        outputDTO.setBatchName(customerDTO.getBatchName());
    }
}
