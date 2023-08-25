package dev.farhan.movieist.movies;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Entity
@Table (name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @Column(name = "id")
    public int id;
    @Column(name = "email")
    public String email;
    @Column(name = "password")
    public String password;

    @Column(name = "name")
    public String name;

    public User(String email, String password, String name){
        this.email = email;
        this.password = password;
        this.name = name;
    }

}
