package model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "Name")
    private String name;

    @Column(name = "Price")
    private int price;

    @Column(name = "Description")
    private String description;

    @Column(name = "Image")
    private String image;

    @Column(name = "Category")
    private String category;

    @Column(name = "Status")
    private int status;
}
