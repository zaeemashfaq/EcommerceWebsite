package dev.zaeem.productservice;

import dev.zaeem.productservice.inheritancedemo.joinedtable.MentorRepository;
import dev.zaeem.productservice.inheritancedemo.joinedtable.UserRepository;
import dev.zaeem.productservice.models.Category;
import dev.zaeem.productservice.models.Price;
import dev.zaeem.productservice.models.Product;
import dev.zaeem.productservice.repositories.CategoryRepository;
import dev.zaeem.productservice.repositories.PriceRepository;
import dev.zaeem.productservice.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.UUID;

@SpringBootApplication
public class ProductserviceApplication{
    public static void main(String[] args) {
        SpringApplication.run(ProductserviceApplication.class,args);
    }
}
/*
@SpringBootApplication
public class ProductserviceApplication implements CommandLineRunner {
    private MentorRepository mentorRepository;
    private UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final PriceRepository priceRepository;

    public ProductserviceApplication(@Qualifier("jt_mr") MentorRepository mentorRepository,@Qualifier("jt_ur") UserRepository userRepository,
                                     ProductRepository productRepository,
                                     CategoryRepository categoryRepository,
                                     PriceRepository priceRepository){
        this.mentorRepository = mentorRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.priceRepository = priceRepository;
    }
    public static void main(String[] args) {
        SpringApplication.run(ProductserviceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception{
//        Mentor mentor = new Mentor();
//        mentor.setName("Naman");
//        mentor.setEmail("naman@scaler.com");
//        mentor.setAverageRating(4.65);
//        mentorRepository.save(mentor);
//
//        User user = new User();
//        user.setName("Zaeem");
//        user.setEmail("zaeem@coolmail.com");
//        userRepository.save(user);
//
//        List<User> users = userRepository.findAll();
//        for(User user1 : users){
//            System.out.println(user1);
//        }
        /*Category category = new Category();
        category.setName("Xiaomi Phones");*/

//        Category category1 = categoryRepository.findById(
//                UUID.fromString("f9a5d5ee-fa2d-479e-92e0-188a277d39a7")).get();
//
//        Product product = new Product();
//        //product.setPrice("130000");
//        product.setTitle("Poco F3");
//        product.setDescription("The stable Pocophone!");
//        product.setCategory(category1);
//        productRepository.save(product);
//
//        System.out.println("Category name is :" + category1.getName());
//        System.out.println("Printing all products in this category");
//        for(Product product1: category1.getProducts()){
//            System.out.println(product1.getTitle());
//        }

/*
        Category category = new Category();
        category.setName("Apple Phones");
//        Category savedCategory = categoryRepository.save(category);

        Price price = new Price("Rupee",100);
//        Price savedPrice = priceRepository.save(price);

        Product product = new Product();
        product.setTitle("iPhone 15");
        product.setDescription("The most expensive iPhone ever!");
//        product.setCategory(savedCategory);
        product.setCategory(category);
//        product.setPrice(savedPrice);
        product.setPrice(price);

        productRepository.save(product);
    }

}
 */
