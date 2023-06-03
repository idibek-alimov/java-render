//package maryam.models.product;
//
//import com.fasterxml.jackson.databind.annotation.JsonSerialize;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import maryam.serializer.BrandSerializer;
//import maryam.serializer.ProductSerializer;
//
//import jakarta.persistence.*;
//import java.util.HashSet;
//import java.util.Set;
//
//@Entity
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@JsonSerialize(using= BrandSerializer.class)
//public class Brand {
//    @Id
//    @GeneratedValue(generator = "brand_id_generator", strategy = GenerationType.SEQUENCE)
//    @SequenceGenerator(name = "brand_id_generator",initialValue = 1, allocationSize = 1,sequenceName = "Brand_id_generator")
//    private Long id;
//
//    private String name;
//    @ManyToMany(cascade = {CascadeType.ALL})
//    @JoinTable(
//            name="Brand_Product",
//            joinColumns = {@JoinColumn(name="brand_id")},
//            inverseJoinColumns = {@JoinColumn(name="product_id")}
//    )
//    private Set<Product> products = new HashSet<>();
//
//}
