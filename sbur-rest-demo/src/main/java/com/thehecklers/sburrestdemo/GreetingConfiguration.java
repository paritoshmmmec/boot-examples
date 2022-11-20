package com.thehecklers.sburrestdemo;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "greeting")
public class GreetingConfiguration {
    private String name;
    private String coffee;
    private String anotherCoffee;
}
