package com.kinoticket.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Image {

    @Id
    Long id;

    @Lob
    byte[] content;

    String name;

    public Image(byte[] content, String name, long id) {
        this.id = id;
        this.content = content;
        this.name = name;
    }
}