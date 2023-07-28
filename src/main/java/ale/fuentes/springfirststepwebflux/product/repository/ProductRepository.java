package ale.fuentes.springfirststepwebflux.product.repository;

import ale.fuentes.springfirststepwebflux.product.entity.Product;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ProductRepository extends ReactiveCrudRepository<Product, Integer> {

    Mono<Product> findByName(String name);

    @Query("select * from Product where id <> :id and name = :name")
    Mono<Product> repeatedName(int id, String name);
}
