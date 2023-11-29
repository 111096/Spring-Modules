package com.revamp.springbusiness.transform;

import com.revamp.springdal.dto.CustomerDTO;
import com.revamp.springdal.dto.MessageDto;
import com.revamp.springdal.model.CustomerEntity;
import com.revamp.springdal.transform.TransformerInterface;

public class CustomerTransform implements TransformerInterface<CustomerEntity, MessageDto> {
    CustomerEntity entity = new CustomerEntity();
    MessageDto dto = new MessageDto();

    @Override
    public void convertFromEntityToDTO(CustomerEntity customerEntity, MessageDto customerDTO) {
        dto = new MessageDto();
        ((CustomerDTO) dto).setId(customerEntity.getId());
        ((CustomerDTO) dto).setName(customerEntity.getName());
        ((CustomerDTO) dto).setEmail(customerEntity.getEmail());
        ((CustomerDTO) dto).setPhone(customerEntity.getPhone());
        ((CustomerDTO) dto).setSsn(customerEntity.getSsn());
        dto.setBatchId(customerEntity.getBatchId());
        dto.setBatchName(customerEntity.getBatchName());
    }

    @Override
    public void convertFromDTOToEntity(MessageDto customerDTO, CustomerEntity customerEntity) {
        customerEntity.setId(((CustomerDTO) customerDTO).getId());
        customerEntity.setName(((CustomerDTO) customerDTO).getName());
        customerEntity.setEmail(((CustomerDTO) customerDTO).getEmail());
        customerEntity.setPhone(((CustomerDTO) customerDTO).getPhone());
        customerEntity.setSsn(((CustomerDTO) customerDTO).getSsn());
        customerEntity.setUniqueId(((CustomerDTO) customerDTO).getUniqueId());
        customerEntity.setBatchId(customerDTO.getBatchId());
        customerEntity.setBatchName(customerDTO.getBatchName());
    }
}
