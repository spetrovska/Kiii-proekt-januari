package org.example.mongodb;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@SpringBootApplication
public class MongodbApplication {
    public static void main(String[] args) {
        SpringApplication.run(MongodbApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(ItemRepository repository) {
        return args -> {
            repository.deleteAll(); // Clear existing data
            repository.save(new Item("Item 1"));
            repository.save(new Item("Item 2"));
            repository.save(new Item("Item 3"));
            repository.save(new Item("Item 4"));
            repository.save(new Item("Item 5"));
        };
    }
}

@Document(collection = "items")
class Item {
    @Id
    private String id;
    private String name;

    public Item() {}
    public Item(String name) { this.name = name; }

    public String getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}

interface ItemRepository extends MongoRepository<Item, String> {}

@RestController
@RequestMapping("/items")
class ItemController {
    private final ItemRepository repository;
    public ItemController(ItemRepository repository) { this.repository = repository; }

    @GetMapping
    public List<Item> getAllItems() { return repository.findAll(); }

    @PostMapping
    public Item createItem(@RequestBody Item item) { return repository.save(item); }
}

@Controller
class HomeController {
    private final ItemRepository repository;

    public HomeController(ItemRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("items", repository.findAll());
        return "index"; // This maps to src/main/resources/templates/index.html
    }
}
