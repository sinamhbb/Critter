package com.udacity.jdnd.course3.critter.domain.sample;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class SampleComposite {

    @Id
    @GeneratedValue
    private Long id;
//
//    @EmbeddedId
//    SampleEntity sampleId;

    public SampleComposite() {
    }

    public SampleComposite(Long id, SampleEntity sampleId) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
