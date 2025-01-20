package org.studing.catalogue.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "catalogue", name = "t_product")
@NamedQueries(
    @NamedQuery(
        name = "Product.findAllByTitleLikeIgnoreCase",
        query = "select p from Product p where p.title ilike :filter"))
public class Product {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    Integer id;

    @Column(name = "c_title")
    @NotNull
    @Size(min = 3, max = 50)
    String title;

    @Column(name = "c_details")
    @NotNull
    @Size(max = 1000)
    String details;
}