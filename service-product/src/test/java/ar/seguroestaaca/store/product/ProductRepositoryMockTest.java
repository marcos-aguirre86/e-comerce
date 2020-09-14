package ar.seguroestaaca.store.product;


import ar.seguroestaaca.store.product.entity.Category;
import ar.seguroestaaca.store.product.entity.Product;
import ar.seguroestaaca.store.product.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;
import java.util.List;

@DataJpaTest
public class ProductRepositoryMockTest {

    @Autowired
    public ProductRepository productRepository;

    @Test
    public void whenFindCategory_thenReturnListProducts(){
        Product product01 = Product.builder()
                .name("computer")
                .category(Category.builder().id(1L).build())
                .description("")
                .stock(Double.parseDouble("20"))
                .price(Double.parseDouble("1245.00"))
                .status("Crated")
                .createAt(new Date()).build();
        productRepository.save(product01);

        List<Product> ltsProducts = productRepository.findByCategory(product01.getCategory());

        Assertions.assertThat(ltsProducts.size()).isEqualTo(3);
    }
}
