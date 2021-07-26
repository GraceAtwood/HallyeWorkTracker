package com.hallye.application.person;

import com.google.inject.Inject;
import com.hallye.application.person.model.Person;

import java.util.List;

public class PersonService {

    private final PersonRepository personRepository;

    @Inject
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> loadAllPersons() {

        return personRepository.loadPersons();
    }

    public Person createNewPerson(String name) {
        return personRepository.createNewPerson(name);
    }
}
