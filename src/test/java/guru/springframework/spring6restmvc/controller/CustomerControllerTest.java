package guru.springframework.spring6restmvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.spring6restmvc.model.BeerDTO;
import guru.springframework.spring6restmvc.model.CustomerDTO;
import guru.springframework.spring6restmvc.services.CustomerService;
import guru.springframework.spring6restmvc.services.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    CustomerService customerService;

    CustomerServiceImpl customerServiceImpl;


    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;

    @BeforeEach
    void setUp() {
        customerServiceImpl = new CustomerServiceImpl();
    }

    @Test
    void testGetCustomerById() throws Exception {
        CustomerDTO testCustomer = customerServiceImpl.getAllCustomers().get(0);
        given(customerService.getCustomerById(testCustomer.getId())).willReturn(java.util.Optional.of(testCustomer));

        mockMvc.perform(get(CustomerController.CUSTOMER_PATH+ "/"+ testCustomer.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(testCustomer.getId().toString())))
                .andExpect(jsonPath("$.name", is(testCustomer.getName())));
    }
    @Test
   void listCustomersTest() throws Exception {
        given(customerService.getAllCustomers()).willReturn(customerServiceImpl.getAllCustomers());

        mockMvc.perform(get(CustomerController.CUSTOMER_PATH)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()",is(2)));

    }
    @Test
    void createNewCusotmerTest() throws Exception {
        CustomerDTO customer1 = customerServiceImpl.getAllCustomers().get(0);
        customer1.setVersion (null);
        customer1.setName("Jeremy Brady");
      //  customer1.setLastName("Jeremy");
      //  customer1.setLastName("Brady");
        CustomerDTO customer2 = customerServiceImpl.getAllCustomers().get(1);
        given(customerService.saveNewCustomer(any(CustomerDTO.class))).willReturn(customer2);
        mockMvc.perform(post(CustomerController.CUSTOMER_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customer1)))
                .andExpect(status().isCreated());


    }
    @Test
    void updateCustomerByIdTest() throws Exception {
        CustomerDTO customer = customerServiceImpl.getAllCustomers().get(0);

        given(customerService.updateCustomerById(any(),any(CustomerDTO.class))).willReturn(Optional.of(customer));
        mockMvc.perform(put(CustomerController.CUSTOMER_PATH+ "/" + customer.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isNoContent());

        verify(customerService).updateCustomerById(any(UUID.class), any(CustomerDTO.class));
    }

    @Test
    void deleteCustomerByIdTest() throws Exception {
        CustomerDTO customer = customerServiceImpl.getAllCustomers().get(0);


        mockMvc.perform(delete(CustomerController.CUSTOMER_PATH+ "/" + customer.getId())
                .accept(MediaType.APPLICATION_JSON))
                 .andExpect(status().isNoContent());

        verify(customerService).deleteCustomerById(uuidArgumentCaptor.capture());

        assertThat(customer.getId()).isEqualTo(uuidArgumentCaptor.getValue());

    }


}