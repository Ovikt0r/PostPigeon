package com.oviktor.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recipient {

    private Long id;
    private String email;
    private String name;
    private String surname;
    private String patronymic;
}
