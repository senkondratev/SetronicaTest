package senkondratev.demos.DemoSetronica.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String currency;

    private Integer price;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
