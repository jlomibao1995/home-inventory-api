package com.homeinventory.homeinventory.item;

import com.homeinventory.homeinventory.category.Category;
import com.homeinventory.homeinventory.category.CategoryRepository;
import com.homeinventory.homeinventory.user.User;
import com.homeinventory.homeinventory.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository,
                       UserRepository userRepository,
                       CategoryRepository categoryRepository){
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    public void addItem(Item item) {
        Optional<User> owner = userRepository.findUserByEmail(item.getUser().getEmail());

        if (!owner.isPresent()){
            throw new IllegalStateException("Owner with email " + item.getUser() + " does not exist");
        }
        item.setUser(owner.get());

        Optional<Category> category = categoryRepository.findByCategoryName(item.getCategory().getCategoryName());

        if (!category.isPresent()){
            throw new IllegalStateException("Category " + item.getCategory().getCategoryName() + " does not exist");
        }
        item.setCategory(category.get());

        itemRepository.save(item);
    }

    public List<Item> getItems() {
        return itemRepository.findAll();
    }

    public void deleteItem(Long itemId) {
        boolean exists = itemRepository.existsById(itemId);
        if (!exists){
            throw new IllegalStateException("Item with id " + itemId + " does not exist");
        }

        Item item = itemRepository.getOne(itemId);
        item.getUser().removeItem(item);

        itemRepository.deleteById(itemId);
    }

    @Transactional
    public void updateItem(Long itemId, String itemName, double price, Long categoryId) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new IllegalStateException("Item with id " + itemId + "does not exist"));

        if (itemName != null && !Objects.equals(item.getItemName(), itemName)){
            item.setItemName(itemName);
        }

        if (!Objects.equals(item.getPrice(), price) && price != 0){
            item.setPrice(price);
        }

        if (!Objects.equals(item.getCategory().getId(), categoryId) && categoryId != 0){
            Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new IllegalStateException("Category with id " + categoryId + "does not exist"));
            item.setCategory(category);
        }

    }

    public List<Item> getItems(String email) {
        Optional<List<Item>> items = itemRepository.findAllByUser_Email(email);

        if (!items.isPresent()){
            throw new IllegalStateException("No items for user with email " + email);
        }

        for (Item item: items.get()){
            item.setUser(null);
        }

        return items.get();
    }
}
