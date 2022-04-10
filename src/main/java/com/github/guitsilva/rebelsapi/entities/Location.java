package com.github.guitsilva.rebelsapi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "locations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Location {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private Integer latitude;

    @Column
    private Integer longitude;

    @Column(columnDefinition = "boolean default true")
    private boolean updatedByAdmin = true;

    @JsonIgnore
    @OneToMany
    private List<Rebel> rebels;
}
