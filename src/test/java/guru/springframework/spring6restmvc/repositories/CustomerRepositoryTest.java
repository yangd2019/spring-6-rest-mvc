package guru.springframework.spring6restmvc.repositories;

import guru.springframework.spring6restmvc.controller.NotFoundException;
import guru.springframework.spring6restmvc.entities.Customer;
import guru.springframework.spring6restmvc.model.BeerDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @Test
    void testSaveCustomer() {
        Customer customer = customerRepository.save(Customer.builder()
                        .name("New Name")
                .build());

        assertThat(customer.getId()).isNotNull();

    }


}