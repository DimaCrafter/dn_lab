package ru.csit.shiryaev_lab.core.entities;

import jakarta.persistence.*;


@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    public String name = "";
    public String description = "";
    public int count = 1;
}
