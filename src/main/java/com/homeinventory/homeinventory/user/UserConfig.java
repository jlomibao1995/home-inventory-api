package com.homeinventory.homeinventory.user;

import com.homeinventory.homeinventory.category.Category;
import com.homeinventory.homeinventory.category.CategoryRepository;
import com.homeinventory.homeinventory.item.Item;
import com.homeinventory.homeinventory.role.Role;
import com.homeinventory.homeinventory.role.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {

    @Bean
    CommandLineRunner commandLineRunner(
            RoleRepository roleRepository, CategoryRepository categoryRepository, UserRepository userRepository) {
        return args -> {
            //make roles and add users to them
            Role role1 = new Role("Admin");
            Role role2 = new Role("Regular User");

            User user1 = new User("jean@gmail.com", true, "Jean", "password");
            User user2 = new User("admin@gmail.com", true, "Admin", "password");
            User user3 = new User("jane@gmail.com", true, "Jane", "password");

            user2.setRole(role1);
            user1.setRole(role2);
            user3.setRole(role2);
            role1.getUsers().add(user2);
            role2.getUsers().add(user3);
            role2.getUsers().add(user1);

            roleRepository.save(role1);
            roleRepository.save(role2);

            //make categories
            categoryRepository.saveAndFlush(new Category("Bed Room"));
            categoryRepository.saveAndFlush(new Category("Living Room"));
            categoryRepository.saveAndFlush(new Category("Kitchen"));
            categoryRepository.saveAndFlush(new Category("Garage"));

            Category category = categoryRepository.findByCategoryName("Kitchen").get();
            User owner = userRepository.getOne(2L);

            //create items
            Item item1 = new Item("Blender", 15);
            category.getItems().add(item1);
            item1.setCategory(category);
            item1.setOwner(owner);

            Item item2 = new Item("Fan", 20);
            category.getItems().add(item2);
            item2.setCategory(category);
            item2.setOwner(owner);

            Item item3 = new Item("Sofa", 499.99);
            category.getItems().add(item3);
            item3.setCategory(category);
            item3.setOwner(owner);

            categoryRepository.saveAndFlush(category);

        };
    }
}
