package entity;

import lombok.Getter;
import lombok.Setter;


import java.util.Objects;

@Getter
@Setter
public class Recipient {

    private long id;
    private String email;
    private String name;
    private String surname;
    private String patronymic;

    @Override
    public String toString() {
        return  '\n' + "id: " + id +
                " | email: " + email +
                " | name: " + name +
                " | surname: " + surname +
                " | patronymic: " + patronymic +
                " | " + '\n';
    }

    public  static class Builder {
        private final Recipient newRecipient;


        public Builder() {
            newRecipient = new Recipient();
        }

        public Builder withId(long id) {
            newRecipient.id = id;
            return this;
        }

        public Builder withEmail(String email) {
            newRecipient.email = email;
            return this;
        }

        public Builder withName(String name) {
            newRecipient.name = name;
            return this;
        }

        public Builder withSurname(String surname) {
            newRecipient.surname = surname;
            return this;
        }

        public Builder withPatronymic(String patronymic) {
            newRecipient.patronymic = patronymic;
            return this;
        }

        public Recipient build() {
            return newRecipient;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Recipient)) return false;
        Recipient recipient;
        recipient = (Recipient) o;
        return getId() == recipient.getId() && getEmail().equals(recipient.getEmail()) && getName().equals(recipient.getName()) && getSurname().equals(recipient.getSurname()) && getPatronymic().equals(recipient.getPatronymic());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getEmail(), getName(), getSurname(), getPatronymic());
    }
}
