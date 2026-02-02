package com.example.bai4.service;

import com.example.bai4.model.Category;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {
    private List<Category> listCategory = new ArrayList<>();
    
    public CategoryService() {
        listCategory.add(new Category(1, "Laptop"));
        listCategory.add(new Category(2, "Điện thoại"));
        listCategory.add(new Category(3, "Máy tính bảng"));
    }
    
    public List<Category> getAll() {
        return listCategory;
    }
    
    public Category get(int id) {
        return listCategory.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
    }
    
    public void add(Category category) {
        int maxId = listCategory.stream()
                .mapToInt(Category::getId)
                .max()
                .orElse(0);
        category.setId(maxId + 1);
        listCategory.add(category);
    }
    
    public void update(Category editCategory) {
        Category find = get(editCategory.getId());
        if (find != null) {
            find.setName(editCategory.getName());
        }
    }
    
    public void delete(int id) {
        listCategory.removeIf(c -> c.getId() == id);
    }
}
