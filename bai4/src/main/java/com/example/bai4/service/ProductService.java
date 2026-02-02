package com.example.bai4.service;

import com.example.bai4.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService {
    @Autowired
    private CategoryService categoryService;
    
    private List<Product> listProduct = new ArrayList<>();
    
    public ProductService() {
        // Initialize sample data
        Product p1 = new Product();
        p1.setId(1);
        p1.setName("Laptop 1");
        p1.setPrice(30000L);
        p1.setImage("laptop.jpg");
        p1.setCategory(new com.example.bai4.model.Category(1, "Laptop"));
        listProduct.add(p1);
    }
    
    public List<Product> getAll() {
        return listProduct;
    }
    
    public Product get(int id) {
        return listProduct.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }
    
    public void add(Product newProduct) {
        int maxId = listProduct.stream()
                .mapToInt(Product::getId)
                .max()
                .orElse(0);
        newProduct.setId(maxId + 1);
        listProduct.add(newProduct);
    }
    
    public void update(Product editProduct) {
        Product find = get(editProduct.getId());
        if (find != null) {
            find.setName(editProduct.getName());
            find.setPrice(editProduct.getPrice());
            find.setCategory(editProduct.getCategory());
            if (editProduct.getImage() != null && !editProduct.getImage().isEmpty()) {
                find.setImage(editProduct.getImage());
            }
        }
    }
    
    public void delete(int id) {
        listProduct.removeIf(p -> p.getId() == id);
    }
    
    public void updateImage(Product newProduct, MultipartFile imageProduct) {
        if (!imageProduct.isEmpty()) {
            try {
                String contentType = imageProduct.getContentType();
                if (contentType != null && !contentType.startsWith("image/")) {
                    throw new IllegalArgumentException("Tệp tải lên không phải là hình ảnh!");
                }
                
                Path dirImages = Paths.get("static/images");
                if (!Files.exists(dirImages)) {
                    Files.createDirectories(dirImages);
                }
                
                String newFileName = UUID.randomUUID() + "_" + imageProduct.getOriginalFilename();
                Path pathFileUpload = dirImages.resolve(newFileName);
                Files.copy(imageProduct.getInputStream(), pathFileUpload, StandardCopyOption.REPLACE_EXISTING);
                newProduct.setImage(newFileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
