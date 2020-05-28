package com.cherries.ppmtool.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Data
public class Backlog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(updatable = false)
    private String projectSequence;
    @NotBlank(message = "Please include a project summary")
    private String summary;
    private String acceptanceCriteria;
    private String status;
    private Integer priority;
    private Date dueDate;
    private Date create_at;
    private Date update_at;

    @PreUpdate
    protected void onUpdate(){
        update_at = new Date();
    }
    @PrePersist
    protected void onCreate(){
        create_at = new Date();
    }
}
