package com.cherries.ppmtool.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String projectName;
    private String projectIdentifier;
    private String description;
    private Date start_date;
    private Date end_date;

    private Date create_At;
    private Date updated_At;

    @PrePersist
    protected void onCreate(){
        this.create_At = new Date();
    }
    @PreUpdate
    protected void onUpdate(){
        this.updated_At = new Date();
    }



}
