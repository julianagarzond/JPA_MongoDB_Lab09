package eci.ieti;

import eci.ieti.data.CustomerRepository;
import eci.ieti.data.ProductRepository;
import eci.ieti.data.TodoRepository;
import eci.ieti.data.UserRepository;
import eci.ieti.data.config.AppConfiguration;
import eci.ieti.data.model.Customer;
import eci.ieti.data.model.Product;

import eci.ieti.data.model.Todo;
import eci.ieti.data.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;


import java.util.Date;
import java.util.List;

@SpringBootApplication
public class Application implements CommandLineRunner {


    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TodoRepository todoRepository;
    
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {



        customerRepository.deleteAll();

        customerRepository.save(new Customer("Alice", "Smith"));
        customerRepository.save(new Customer("Bob", "Marley"));
        customerRepository.save(new Customer("Jimmy", "Page"));
        customerRepository.save(new Customer("Freddy", "Mercury"));
        customerRepository.save(new Customer("Michael", "Jackson"));

        System.out.println("Customers found with findAll():");
        System.out.println("-------------------------------");
        
        customerRepository.findAll().stream().forEach(System.out::println);
        System.out.println();
        
        productRepository.deleteAll();

        productRepository.save(new Product(1L, "Samsung S8", "All new mobile phone Samsung S8"));
        productRepository.save(new Product(2L, "Samsung S8 plus", "All new mobile phone Samsung S8 plus"));
        productRepository.save(new Product(3L, "Samsung S9", "All new mobile phone Samsung S9"));
        productRepository.save(new Product(4L, "Samsung S9 plus", "All new mobile phone Samsung S9 plus"));
        productRepository.save(new Product(5L, "Samsung S10", "All new mobile phone Samsung S10"));
        productRepository.save(new Product(6L, "Samsung S10 plus", "All new mobile phone Samsung S10 plus"));
        productRepository.save(new Product(7L, "Samsung S20", "All new mobile phone Samsung S20"));
        productRepository.save(new Product(8L, "Samsung S20 plus", "All new mobile phone Samsung S20 plus"));
        productRepository.save(new Product(9L, "Samsung S20 ultra", "All new mobile phone Samsung S20 ultra"));
        
        System.out.println("Paginated search of products by criteria:");
        System.out.println("-------------------------------");
        
        productRepository.findByDescriptionContaining("plus", PageRequest.of(0, 2)).stream()
        	.forEach(System.out::println);
   
        System.out.println();

        todoRepository.deleteAll();

        todoRepository.save(new Todo("task1", 5,new Date(2020,10,23),"j@mail.com","pending"));
        todoRepository.save(new Todo("task2", 5,new Date(2019,10,02),"j@mail.com","pending"));
        todoRepository.save(new Todo("task3", 5,new Date(2020,10,10),"a@mail.com","pending"));
        todoRepository.save(new Todo("task4", 5,new Date(2022,11,21),"j@mail.com","pending"));
        todoRepository.save(new Todo("task5", 5,new Date(2021,12,14),"b@mail.com","pending"));
        todoRepository.save(new Todo("task6", 5,new Date(2020,12,13),"c@mail.com","pending"));
        todoRepository.save(new Todo("task7", 5,new Date(2020,9,12),"d@mail.com","pending"));
        todoRepository.save(new Todo("task8", 5,new Date(2020,12,3),"e@mail.com","pending"));
        todoRepository.save(new Todo("task9", 5,new Date(2021,11,12),"f@mail.com","pending"));
        todoRepository.save(new Todo("task10", 5,new Date(2020,12,17),"g@mail.com","pending"));
        todoRepository.save(new Todo("task11", 5,new Date(2020,12,18),"j@mail.com","pending"));
        todoRepository.save(new Todo("task12", 5,new Date(2020,12,1),"h@mail.com","pending"));
        todoRepository.save(new Todo("task13", 5,new Date(2020,12,19),"j@mail.com","pending"));
        todoRepository.save(new Todo("task14", 5,new Date(2020,12,21),"j@mail.com","pending"));
        todoRepository.save(new Todo("task15", 5,new Date(2020,12,11),"i@mail.com","pending"));
        todoRepository.save(new Todo("task16", 5,new Date(2020,6,17),"a@mail.com","pending"));
        todoRepository.save(new Todo("task17", 5,new Date(2020,5,17),"a@mail.com","pending"));
        todoRepository.save(new Todo("task19", 5,new Date(2020,4,17),"b@mail.com","pending"));
        todoRepository.save(new Todo("task20", 5,new Date(2020,3,17),"d@mail.com","pending"));
        todoRepository.save(new Todo("task21", 5,new Date(2023,12,17),"i@mail.com","pending"));
        todoRepository.save(new Todo("task22", 5,new Date(2024,12,17),"d@mail.com","pending"));
        todoRepository.save(new Todo("task23", 5,new Date(2025,12,17),"j@mail.com","pending"));
        todoRepository.save(new Todo("task24", 5,new Date(2026,12,17),"j@mail.com","pending"));
        todoRepository.save(new Todo("task25", 5,new Date(2027,12,17),"d@mail.com","pending"));



         todoRepository.findByResponsibleContaining("j@mail.com", PageRequest.of(0, 2)).stream()
                .forEach(System.out::println);

        System.out.println();

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        MongoOperations mongoOperation = (MongoOperations) applicationContext.getBean("mongoTemplate");

        Query query = new Query();
        query.addCriteria(Criteria.where("firstName").is("Alice"));
        Customer customer = mongoOperation.findOne(query, Customer.class);
        System.out.println(customer);

        //dueDate has expired

        Query query2 = new Query();
        query.addCriteria(Criteria.where("dueDute").lt(new Date()));
        List<Todo> todos=mongoOperation.find(query2,Todo.class);
        System.out.println(todos);

        //Todos that are assigned to given user and have priority greater equal to 5
        Query query3 = new Query();
        query.addCriteria(Criteria.where("responsible").is("j@mail.com").and("priority").gt(4));
        List<Todo> todos2=mongoOperation.find(query3,Todo.class);
        System.out.println(todos2);

        //Todos that contains a description with a length greater than 30 characters
        Query query4 = new Query();
        query.addCriteria(Criteria.where("description").gt(4));
        List<Todo> todos4=mongoOperation.find(query3,Todo.class);
        System.out.println(todos4);









    }

}