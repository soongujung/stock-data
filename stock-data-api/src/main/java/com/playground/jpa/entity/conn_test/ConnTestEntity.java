package com.playground.jpa.entity.conn_test;

import javax.persistence.*;

@Entity
@Table(name = "conn_test_jpa")
public class ConnTestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    public ConnTestEntity(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
