package ale.fuentes.springfirststepwebflux.product.repository;

import ale.fuentes.springfirststepwebflux.product.entity.Product;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends ReactiveCrudRepository<Product, Integer> {
}
