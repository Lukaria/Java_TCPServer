package entity;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;


import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
@JsonIgnoreProperties(ignoreUnknown = true)

@XmlType(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class User {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;

    @JsonManagedReference(value = "User-companies")
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Company> companies;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "image")
    private int image;

    public User(String login, String password, String role, String name, String email) {
        this.login = login;
        this.password = password;
        this.role = role;
        this.name = name;
        this.email = email;
        this.companies = new ArrayList<>();
    }

}
