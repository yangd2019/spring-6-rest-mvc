package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.model.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class  CustomerServiceImpl implements CustomerService {
    private Map<UUID, Customer> customerMap=new HashMap<>() ;

    public CustomerServiceImpl()    {
            Customer customer1 = Customer.builder()
                    .id(UUID.randomUUID())
                    .version(1)
                    .firstName("John")
                    .lastName("Doe")
                    .createdDate(LocalDateTime.now())
                    .updatedDate(LocalDateTime.now())
                    .country("United States")
                    .build();
            customerMap.put(customer1.getId(),customer1);

            Customer customer2 = Customer.builder()
                .id(UUID.randomUUID())
                    .version(1)
                .firstName("Eric")
                .lastName("Johnson")
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .country("United States")
                .build();

             customerMap.put(customer2.getId(),customer2);
        }
        @Override
        public List<Customer> listCustomers(){
            log.debug("Get Customer List");

            return new ArrayList<>(customerMap.values());
        }
        @Override
        public Customer getCustomerById(UUID id){
            log.debug("Get Customer by Id - in service. Id: " + id.toString());

            return customerMap.get(id);
        }

    @Override
    public Customer saveNewCustomer(Customer customer) {

        Customer savedCustomer = Customer.builder()
                .id(UUID.randomUUID())
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .lastName(customer.getLastName())
                .firstName(customer.getFirstName())
                .country(customer.getCountry())
                .build();

        customerMap.put(savedCustomer.getId(), savedCustomer);

        return savedCustomer;
    }

    @Override
    public void updateCustomerById(UUID customerId, Customer customer) {

        Customer existingCustomer = customerMap.get(customerId);

        existingCustomer.setUpdatedDate(LocalDateTime.now());
        if (StringUtils.hasText(customer.getLastName())) {
            existingCustomer.setLastName(customer.getLastName());
        }
        if (StringUtils.hasText(customer.getFirstName())) {
            existingCustomer.setFirstName(customer.getFirstName());
        }
        if (StringUtils.hasText(customer.getCountry())) {
               existingCustomer.setCountry(customer.getCountry());
        }
        customerMap.put(customerId, existingCustomer);

    }

    @Override
    public void deleteCustomerById (UUID customerId){
        customerMap.remove(customerId);
     }
}