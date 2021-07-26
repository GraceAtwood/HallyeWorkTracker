package com.hallye.application.workflow.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import com.hallye.application.contribution.model.Contribution;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "workflow")
public class Workflow {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = true, insertable = false, updatable = true)
    private Double startWeight;

    @Column(nullable = false, insertable = true, updatable = false)
    private String packageNumber;

    @Column(nullable = false, insertable = true, updatable = false)
    private String strain;

    @Column(nullable = true, insertable = false, updatable = true)
    private Double startingHumidity;

    @Column
    private WorkflowType workflowType;

    @Column(nullable = true, insertable = true, updatable = false)
    private Boolean isComplete;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Contribution> contributions;
}
