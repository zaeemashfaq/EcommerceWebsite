package dev.zaeem.productservice.models;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;
@Getter
@MappedSuperclass //Tells ORM layer that this particular
                    // class's attributes need to be present in all child classes
public class BaseModel {
    @Id //This is the primary key for Product and category
    //private long id;
    @GeneratedValue(generator = "uuid2") //Specifying a UUID generator
    @GenericGenerator(name = "uuid2", strategy = "uuid2") //Specifying the UUID type
    @Column(name = "id", columnDefinition = "binary(16)",nullable = false,updatable = false)
    private UUID uuid;
}
