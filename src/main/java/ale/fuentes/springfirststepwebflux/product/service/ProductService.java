package ale.fuentes.springfirststepwebflux.product.service;

import ale.fuentes.springfirststepwebflux.product.entity.Product;
import ale.fuentes.springfirststepwebflux.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Flux<Product> getAll(){
        return productRepository.findAll();
    }

    public Mono<Product> getById(int id){
        return productRepository.findById(id)
                .switchIfEmpty(Mono.error(new Exception("The product was not found!")));
    }

    public Mono<Product> save(Product product){
        Mono<Boolean> existsName = productRepository.findByName(product.getName()).hasElement();

        return existsName.flatMap(exists -> exists
                ? Mono.error(new Exception("Product with this name already in use!"))
                : productRepository.save(product)
        );
    }

    public Mono<Product> update(int id, Product product){

        Mono<Boolean> existsId = productRepository.findById(id).hasElement();
        Mono<Boolean> existsName = productRepository.repeatedName(id, product.getName()).hasElement();

        return existsId.flatMap(productId -> productId
                ? existsName.flatMap(productName -> productName
                            ? Mono.error(new Exception("Product with this name already in use!"))
                            : productRepository.save(new Product(id, product.getName(), product.getPrice())))
                : Mono.error(new Exception("The product was not found!"))
        );

    }

    public Mono<Void> delete(int id){
        Mono<Boolean> existsId = productRepository.findById(id).hasElement();

        return existsId.flatMap(exists -> exists
                ? productRepository.deleteById(id)
                : Mono.error(new Exception("The product was not found!"))
        );
    }

}
