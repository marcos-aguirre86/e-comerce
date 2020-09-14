package ar.seguroestaaca.store.product.repository;

import ar.seguroestaaca.store.product.entity.Category;
import ar.seguroestaaca.store.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    public List<Product> findByCategory(Category category);
}
