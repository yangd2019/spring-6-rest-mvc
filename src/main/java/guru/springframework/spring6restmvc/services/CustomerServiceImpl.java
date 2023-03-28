package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.model.CustomerDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class  CustomerServiceImpl implements CustomerService {
    private Map<UUID, CustomerDTO> customerMap=new HashMap<>() ;

    public CustomerServiceImpl()    {
            CustomerDTO customer1 = CustomerDTO.builder()
                    .id(UUID.randomUUID())
                    .version(1)
                    .name("John Doe")
                 //   .firstName("John")
                 //   .lastName("Doe")
              //      .country("United States")
               .createdDate(LocalDateTime.now())
                    .updatedDate(LocalDateTime.now())
                      .build();
            customerMap.put(customer1.getId(),customer1);

            CustomerDTO customer2 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                    .name("Eric Johnson")
               // .firstName("Eric")
              //  .lastName("Johnson")
                //    .country("United States")
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

             customerMap.put(customer2.getId(),customer2);
        }
        @Override
        public List<CustomerDTO> getAllCustomers(){
            log.debug("Get Customer List");

            return new ArrayList<>(customerMap.values());
        }
        @Override
        public Optional<CustomerDTO> getCustomerById(UUID id){
            log.debug("Get Customer by Id - in service. Id: " + id.toString());

            return Optional.of(customerMap.get(id));
        }

    @Override
    public CustomerDTO saveNewCustomer(CustomerDTO customer) {

        CustomerDTO savedCustomer = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .name(customer.getName())
           //     .lastName(customer.getLastName())
           //     .firstName(customer.getFirstName())
            //    .country(customer.getCountry())
                .build();

        customerMap.put(savedCustomer.getId(), savedCustomer);

        return savedCustomer;
    }

    @Override
    public Optional<CustomerDTO> updateCustomerById(UUID customerId, CustomerDTO customer) {

        CustomerDTO existingCustomer = customerMap.get(customerId);

        existingCustomer.setUpdatedDate(LocalDateTime.now());
       if (StringUtils.hasText(customer.getName())) {
            existingCustomer.setName(customer.getName());
        }

       /* if (StringUtils.hasText(customer.getLastName())) {
            existingCustomer.setLastName(customer.getLastName());
        }
        if (StringUtils.hasText(customer.getFirstName())) {
            existingCustomer.setFirstName(customer.getFirstName());
        }
        if (StringUtils.hasText(customer.getCountry())) {
               existingCustomer.setCountry(customer.getCountry());
        } */
        customerMap.put(customerId, existingCustomer);
        return Optional.of(existingCustomer);
    }

    @Override
    public Boolean deleteCustomerById (UUID customerId){
        if(customerMap.containsKey(customerId)) {
            customerMap.remove(customerId);
            return true;
        }
        return false;
     }

    @Override
    public void patchCustomerById(UUID customerId, CustomerDTO customer) {

    }
    }