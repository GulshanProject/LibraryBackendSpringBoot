package com.example.StudentLibrary.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Student {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    private String emailId;
    private String name;
    private int age; // in case we want to check on the basis of age while issuing

    private String country;

    public Student() {
    }
    public Student(String emailId, String name, int age, String country){
        this.emailId=emailId;
        this.name=name;
        this.age=age;
        this.country=country;
    }

    // alter table student add foreign key constraint card references Card(id)

    @OneToOne
    @JoinColumn   // join this column to the primary key of Card table
    @JsonIgnoreProperties("student")
    private Card card;


    @CreationTimestamp
    private Date createdOn;

    @UpdateTimestamp
    private Date updatedOn;

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", email='" + emailId + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", country='" + country + '\'' +
                ", createdOn=" + createdOn +
                ", updatedOn=" + updatedOn +
                '}';
    }
}
