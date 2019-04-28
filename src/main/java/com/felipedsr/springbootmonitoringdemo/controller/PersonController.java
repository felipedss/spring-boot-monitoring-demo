package com.felipedsr.springbootmonitoringdemo.controller;

import com.felipedsr.springbootmonitoringdemo.model.Person;
import com.felipedsr.springbootmonitoringdemo.repository.PersonRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("person")
public class PersonController {

    private final PersonRepository personRepository;

    public PersonController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @GetMapping
    public ResponseEntity<List<Person>> findAll() {
        return new ResponseEntity<>(personRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Person> post(@RequestBody Person person) {
        return new ResponseEntity<>(personRepository.save(person), HttpStatus.CREATED);
    }

    @GetMapping(path = "/firstName/{firstName}")
    public ResponseEntity<List<Person>> findByFirstName(@PathVariable String firstName) {
        return new ResponseEntity<>(personRepository.findByFirstNameIgnoringCase(firstName), HttpStatus.OK);
    }

    @GetMapping(path = "/lastName/{lastName}")
    public ResponseEntity<List<Person>> findByLastName(@PathVariable String lastName) {
        return new ResponseEntity<>(personRepository.findByLastNameIgnoringCase(lastName
        ), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(String id) {
        personRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}