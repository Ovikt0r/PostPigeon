package entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Getter
@Setter
@ToString
public class Recipient {

    private long id;
    private String email;
    private String name;
    private String surname;
    private String patronymic;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Recipient)) return false;
        Recipient recipient = (Recipient) o;
        return getId() == recipient.getId() && getEmail().equals(recipient.getEmail()) && getName().equals(recipient.getName()) && getSurname().equals(recipient.getSurname()) && getPatronymic().equals(recipient.getPatronymic());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getEmail(), getName(), getSurname(), getPatronymic());
    }
}
