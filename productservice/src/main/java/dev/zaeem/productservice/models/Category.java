package dev.zaeem.productservice.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Category extends BaseModel {
    //@Column
    private String name;
    @OneToMany(mappedBy = "category")
    @Fetch(FetchMode.SUBSELECT)
    private List<Product> products;
    //@OneToMany(mappedBy = "category") tells spring that this is the same relation
    // being mapped by category attribute in the other class
}
