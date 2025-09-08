package io.zipcoder.crudapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
public class PersonController {
    
    @Autowired
    private PersonRepository personRepository;
    
    @PostMapping("/people")
    public ResponseEntity<Person> createPerson(@RequestBody Person person) {
        Person savedPerson = personRepository.save(person);
        return new ResponseEntity<>(savedPerson, HttpStatus.CREATED);
    }
    
    @GetMapping("/people/{id}")
    public ResponseEntity<Person> getPerson(@PathVariable Integer id) {
        Person person = personRepository.findOne(id);
        if (person != null) {
            return new ResponseEntity<>(person, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/people")
    public ResponseEntity<List<Person>> getPersonList() {
        List<Person> people = new ArrayList<>();
        personRepository.findAll().forEach(people::add);
        return new ResponseEntity<>(people, HttpStatus.OK);
    }
    
    @PutMapping("/people/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable Integer id, @RequestBody Person person) {
        boolean exists = personRepository.exists(id);
        person.setId(id);
        Person savedPerson = personRepository.save(person);
        
        if (exists) {
            return new ResponseEntity<>(savedPerson, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(savedPerson, HttpStatus.CREATED);
        }
    }
    
    @DeleteMapping("/people/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable Integer id) {
        if (personRepository.exists(id)) {
            personRepository.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
