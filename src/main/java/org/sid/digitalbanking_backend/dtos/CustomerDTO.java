package org.sid.digitalbanking_backend.dtos;

import lombok.Data;


@Data
public class CustomerDTO {
    private Long id;
    private String name;
    private String email;
}
