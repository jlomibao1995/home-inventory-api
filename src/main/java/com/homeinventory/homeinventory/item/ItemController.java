package com.homeinventory.homeinventory.item;

import com.homeinventory.homeinventory.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(path = "inventory/api/v1/item")
@CrossOrigin
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService){
        this.itemService = itemService;
    }

    @GetMapping
    public List<Item> getItems(){
        return itemService.getItems();
    }

    @GetMapping(path = "{email}")
    public List<Item> getItems(@PathVariable("email") String email, Principal user ){
        return itemService.getItems(email, user);
    }

    @PostMapping
    public void addItem(@RequestBody Item item){
        itemService.addItem(item);
    }

    @DeleteMapping(path = "{itemId}") void deleteItem(@PathVariable("itemId") Long itemId, Principal user){
        itemService.deleteItem(itemId, user);
    }

    @PutMapping(path = "{itemId}")
    public void updateItem(@PathVariable("itemId") Long itemId,
                           @RequestParam(required = false) String itemName,
                           @RequestParam(required = false, defaultValue = "0") double price,
                           @RequestParam(required = false, defaultValue = "0") Long categoryId,
                           Principal user){
        itemService.updateItem(itemId, itemName, price, categoryId, user);
    }
}
