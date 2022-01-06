package senkondratev.demos.DemoSetronica.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "product")
    private List<Info> info;


    @OneToMany(mappedBy = "product")
    private List<Price> price;

    @Column(name = "creation_date")
    private Date creationDate;

    @Column(name = "modification_date")
    private Date modificationDate;
}
