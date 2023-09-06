package org.sid.inventoryservice.web;


import org.sid.inventoryservice.dto.ProductRequestDTO;
import org.sid.inventoryservice.entities.Category;
import org.sid.inventoryservice.entities.Product;
import org.sid.inventoryservice.repository.CategoryRepository;
import org.sid.inventoryservice.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
public class ProductGraphqlController {

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    public ProductGraphqlController(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @QueryMapping
    public List<Product> productList(){
        return productRepository.findAll();
    }

    @QueryMapping
    public List<Product> productCategory(@Argument String category){
        return productRepository.findByCategory(category);
    }



    @QueryMapping
    public Product productById(@Argument String id){

        return productRepository.findById(id).orElseThrow(
                ()->new RuntimeException(String.format("Le produit %s n\'existe pas",id))
        );
    }



    // implementation de la pagination grapghql tres top
    @QueryMapping
    public Page<Product> allProductPaged(@Argument  int page, @Argument  int size){
        PageRequest pr = PageRequest.of(page,size);
        return productRepository.findAll(pr);
    }


    @QueryMapping
    public List<Category> categoryList(){
      return categoryRepository.findAll();
    }

    @QueryMapping
    public Category categoryById(@Argument Long id){
        return categoryRepository.findById(id).orElseThrow(
                ()->new RuntimeException(String.format("la categorie %s n\'existe pas",id)));
    }


    @MutationMapping
    public Product saveProduct(@Argument ProductRequestDTO product){

        Category category=categoryRepository.findById(product.categoryId()).orElse(null);
        Product productToSave=new Product();
        productToSave.setId(UUID.randomUUID().toString());
        productToSave.setName(product.name());
        productToSave.setPrice(product.price());
        productToSave.setQuantity(product.quantity());
        productToSave.setCategory(category  );
        return  productRepository.save(productToSave);
    }



    @MutationMapping
    public Product updateProduct(@Argument String id,@Argument ProductRequestDTO product){

        Category category=categoryRepository.findById(product.categoryId()).orElse(null);
        Product productToSave=new Product();
        productToSave.setId(id);
        productToSave.setName(product.name());
        productToSave.setPrice(product.price());
        productToSave.setQuantity(product.quantity());
        productToSave.setCategory(category  );
        return  productRepository.save(productToSave);
    }

    @MutationMapping

    public void deleteProduct(@Argument String id){
        productRepository.deleteById(id);
    }


}
