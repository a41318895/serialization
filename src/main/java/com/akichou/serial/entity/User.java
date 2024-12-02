package com.akichou.serial.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Objects;

@Getter
@Setter
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L ;

    private String name ;

    private Integer age ;

    // NoArgConstructor for jackson deserialization
    public User() {}

    public User (String name, int age) {

        this.name = name ;
        this.age = age ;
    }

    @Override
    public String toString() {

        return MessageFormat.format("NAME: {0}, AGE: {1}", name, age) ;
    }

    @Override
    public int hashCode() {

        return 31 * name.hashCode() + age.hashCode() ;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == this) return true ;

        if (obj == null || getClass() != obj.getClass()) return false ;

        User user = (User) obj ;

        return Objects.equals(name, user.name) && Objects.equals(age, user.age) ;
    }
}
