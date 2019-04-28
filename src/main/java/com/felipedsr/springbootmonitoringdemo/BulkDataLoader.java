package com.felipedsr.springbootmonitoringdemo;

import com.felipedsr.springbootmonitoringdemo.model.Person;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoOperations;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@Slf4j
public class BulkDataLoader {

    @Bean
    public CommandLineRunner initData(MongoOperations mongo) {
        return (String... args) -> {
            mongo.dropCollection(Person.class);
            log.info("Person collection dropped");
            mongo.createCollection(Person.class);
            log.info("Person collection created");
            loadPeopleNames().forEach(mongo::save);
            log.info("Bulk person data successfully saved in db");
        };
    }

    private List<Person> loadPeopleNames() {
        try {
            return IOUtils.readLines(getClass().getResourceAsStream("/person-list.txt"), "utf-8")
                    .stream()
                    .map(this::toMap)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            log.error("Error occurred while reading file - Error Message : {} - Stack Trace : " +
                    "{}", e.getMessage(), e.getStackTrace());
            throw new RuntimeException(e);
        }
    }

    private Person toMap(String line) {
        String[] person = line.split("\\s");
        return new Person(person[0], person[1]);
    }
}