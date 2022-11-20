package com.thehecklers.sburrestdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootApplication
@ConfigurationPropertiesScan
public class SburRestDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SburRestDemoApplication.class, args);
    }

}

@RestController
class RestApiDemoController {
    private final GreetingConfiguration configuration;
    private final CoffeeRepository coffeeRepository;

    public RestApiDemoController(GreetingConfiguration configuration,
                                 CoffeeRepository coffeeRepository) {
        this.configuration = configuration;
        this.coffeeRepository = coffeeRepository;
        System.out.println(this.configuration.getAnotherCoffee());
    }

    //@RequestMapping(value = "/coffees", method = RequestMethod.GET)
    @GetMapping("/api/coffees")
    Iterable<Coffee> getCoffee() {
        return coffeeRepository.findAll();
    }

    @GetMapping("/api/coffees/{id}")
    Optional<Coffee> getCoffeeById(@PathVariable String id){
         return coffeeRepository.findById(id);
    }

    @PostMapping("/api/coffees")
    Coffee postCoffee(@RequestBody Coffee coffee){
       return coffeeRepository.save(coffee);
    }

    @PutMapping("/api/coffees/{id}")
    ResponseEntity<Coffee> putCoffee(@PathVariable String id, @RequestBody Coffee coffee){
        return (!coffeeRepository.existsById(id))
                ? new ResponseEntity<>(coffeeRepository.save(coffee),
                HttpStatus.CREATED)
                : new ResponseEntity<>(coffeeRepository.save(coffee), HttpStatus.OK);
    }

    @DeleteMapping("/api/coffees/{id}")
    void deleteCoffee(@PathVariable String id) {
        coffeeRepository.deleteById(id);
    }

}

@Entity

class Coffee {
    @Id
    private String id;
    private String name;

    public Coffee(){

    }

    public Coffee(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Coffee(String name) {
        this(UUID.randomUUID().toString(), name);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

interface CoffeeRepository extends CrudRepository<Coffee, String> {

}