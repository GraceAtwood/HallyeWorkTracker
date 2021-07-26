package com.hallye.application.person.dto;

import com.hallye.application.person.model.Person;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetPersonsResponse {

    private List<Person> persons;
}
