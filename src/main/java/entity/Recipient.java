package entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class Recipient {

    private int id;
    private String email;
    private String name;
    private String surname;
    private String patronymic;

    }
