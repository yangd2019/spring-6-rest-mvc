package guru.springframework.spring6restmvc.controller;

import guru.springframework.spring6restmvc.model.Beer;
import guru.springframework.spring6restmvc.model.Customer;
import guru.springframework.spring6restmvc.services.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@RestController
public class CustomerController {

    public static final String CUSTOMER_PATH = "/api/v1/customer";
    public static final String CUSTOMER_PATH_ID = CUSTOMER_PATH + "/{customerId}";
    private final CustomerService customerService;


    @GetMapping(CUSTOMER_PATH)
    public List<Customer> listCustomers(){
        return customerService.listCustomers();
    }

    @GetMapping(CUSTOMER_PATH_ID)
    public Customer getCustomerById(@PathVariable("customerId") UUID customerId){

        log.debug("Get Customer by Id - in controller");

        return customerService.getCustomerById(customerId);
    }
    @PostMapping(CUSTOMER_PATH)
    public ResponseEntity handlePost(@RequestBody Customer customer){

        Customer savedCustomer = customerService.saveNewCustomer(customer);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping(CUSTOMER_PATH_ID)
    public ResponseEntity updateCustomerById(@PathVariable("customerId") UUID customerId, @RequestBody Customer customer){

        log.debug("Get updateCustomerById by Id - in controller");

        customerService.updateCustomerById(customerId,customer);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    @DeleteMapping(CUSTOMER_PATH_ID)
    public ResponseEntity deleteCustomerById(@PathVariable("customerId") UUID customerId){

        log.debug("Get deleteCustomerById by Id - in controller");

        customerService.deleteCustomerById(customerId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
