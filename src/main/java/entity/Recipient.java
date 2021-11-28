package entity;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Recipient {

    private int id;
    private String email;
    private String name;
    private String surname;
    private String patronymic;

    @Override
    public String toString() {
        return "entity.Recipient {" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", patronymic='" + patronymic + '\'' +
                "}\n";
    }
}
