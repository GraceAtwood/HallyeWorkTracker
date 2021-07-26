package com.hallye.application.person.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Data
@Builder
@Entity
@Table(name = "person")
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, insertable = true, updatable = true)
    private String name;
}
