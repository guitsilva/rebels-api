package com.github.guitsilva.rebelsapi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "inventories")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Integer weapons;

    @Column
    private Integer ammo;

    @Column
    private Integer water;

    @Column
    private Integer food;

    @JsonIgnore
    @OneToOne
    private Rebel rebel;
}
