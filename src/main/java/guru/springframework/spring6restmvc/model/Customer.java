package guru.springframework.spring6restmvc.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
public class Customer {
    private UUID id;
    private Integer version;
    private String firstName;
    private String lastName;
    private String country;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
