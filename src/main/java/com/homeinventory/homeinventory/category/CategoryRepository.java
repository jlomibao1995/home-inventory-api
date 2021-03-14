package com.homeinventory.homeinventory.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query
    Optional<Category> findByCategoryName(String categoryName);
}
