package com.cherries.ppmtool.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Data
public class ProjectTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(updatable = false)
    private String projectSequence;
    @NotBlank(message = "Please include a project summary")
    private String summary;
    private String acceptanceCriteria;
    private String status;
    private String priority;
    private String dueDate;
    //manytomany with backlog
    @Column(updatable = false)
    private String projectIdentifier;
    @JsonFormat(pattern = "yyyy-mm-dd HH:MM")
    private Date create_at;
    @JsonFormat(pattern = "yyyy-mm-dd HH:MM")
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
