package io.dickson.liquibasesample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("students")
public class Controller {

    @Autowired
    private CustomerEntityRepository customerRepository;

    @PostMapping("student")
    public String createStudent(@RequestParam String name) {
        customerRepository.save(new CustomerEntity(name));
        CustomerEntity nameToReturn = customerRepository.findByName(name);
        return name + " Saved successfully";
    }

    @GetMapping("student")
    public List<CustomerEntity> getAllStudents() {
        return (List<CustomerEntity>) customerRepository.findAll();
    }

}
