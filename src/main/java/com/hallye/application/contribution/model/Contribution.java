package com.hallye.application.contribution.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.hallye.application.person.model.Person;
import com.hallye.application.workflow.model.Workflow;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import com.hallye.application.workflow.model.WorkflowStep;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
@Entity
@Table(name = "contribution")
@NoArgsConstructor
@AllArgsConstructor
public class Contribution {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    private Person person;

    @ManyToOne
    private Workflow workflow;

    @Column(nullable = false, insertable = true, updatable = false)
    private WorkflowStep workflowStep;

    @Column(nullable = false, insertable = true, updatable = false)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-ddTHH:mm:ss")
    @ApiParam(example = "2021-07-26T11:15:23")
    private LocalDateTime startTime;

    @Column(nullable = true, insertable = true, updatable = true)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-ddTHH:mm:ss")
    @ApiParam(example = "2021-07-26T11:15:23")
    private LocalDateTime endTime;

    public Duration getTotalTime() {

        if (endTime == null) {
            return null;
        }

        return Duration.between(startTime, endTime);
    }
}
