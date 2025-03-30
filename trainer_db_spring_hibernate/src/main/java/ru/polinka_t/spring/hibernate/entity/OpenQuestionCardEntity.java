package ru.polinka_t.spring.hibernate.entity;

import jakarta.persistence.*;

import java.time.ZonedDateTime;

@Entity
@Table(name = "question")
public class OpenQuestionCardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column
    private String code;

    @Column
    private String title;

    @Column
    private String description;

    @Column
    private ZonedDateTime deadLine;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ZonedDateTime getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(ZonedDateTime deadLine) {
        this.deadLine = deadLine;
    }
}