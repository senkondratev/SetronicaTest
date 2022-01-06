package senkondratev.demos.DemoSetronica.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Info {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private String language;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
