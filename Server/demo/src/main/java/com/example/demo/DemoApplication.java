package com.example.demo;

import lombok.NonNull;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    ApplicationRunner init(UserRepository repository) {
        return args -> {
            Stream.of("Aa", "Bb", "Cc", "Dd", "Ee","GG","MM").forEach(name -> {
                repository.save(new User(name));
            });
            repository.findAll().forEach(System.out::println);
        };
    }

}

@Entity
class User {

    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    private String name;

    public User() {
    }

    public User(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

@RepositoryRestResource
interface UserRepository extends JpaRepository<User, Long> {
}

@RestController
class CoolCarController {
    private UserRepository repository;

    public CoolCarController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/Users")
    @CrossOrigin
    public Collection<User> Users() {
        return repository.findAll().stream()
                .filter(this::isUser)
                .collect(Collectors.toList());
    }

    private boolean isUser(User user) {
        return !user.getName().equals("Aa") &&
                !user.getName().equals("Bb") &&
                !user.getName().equals("Cc") &&
                !user.getName().equals("Ee");
    }
}
