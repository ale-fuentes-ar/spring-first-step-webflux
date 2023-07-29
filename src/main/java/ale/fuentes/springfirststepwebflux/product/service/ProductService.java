package ale.fuentes.springfirststepwebflux.product.service;

import ale.fuentes.springfirststepwebflux.config.exception.CustomException;
import ale.fuentes.springfirststepwebflux.product.dto.ProductDto;
import ale.fuentes.springfirststepwebflux.product.entity.Product;
import ale.fuentes.springfirststepwebflux.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    private final static String NOTFOUND_MESSAGE = "The product was not found!";
    private final static String NAMEUSSING_MESSAGE = "Product with this name already in use!";

    private final ProductRepository productRepository;

    public Flux<Product> getAll(){
        return productRepository.findAll();
    }

    public Mono<Product> getById(int id){
        return productRepository.findById(id)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.NOT_FOUND, NOTFOUND_MESSAGE)));
    }

    public Mono<Product> save(ProductDto productDto){
        Mono<Boolean> existsName = productRepository.findByName(productDto.getName()).hasElement();

        return existsName.flatMap(exists -> exists
                ? Mono.error(new CustomException(HttpStatus.BAD_REQUEST, NAMEUSSING_MESSAGE))
                : productRepository.save(Product.builder().name(productDto.getName()).price(productDto.getPrice()).build())
        );
    }

    public Mono<Product> update(int id, ProductDto productDto){

        Mono<Boolean> existsId = productRepository.findById(id).hasElement();
        Mono<Boolean> existsName = productRepository.repeatedName(id, productDto.getName()).hasElement();

        return existsId.flatMap(productId -> productId
                ? existsName.flatMap(productName -> productName
                            ? Mono.error(new CustomException(HttpStatus.BAD_REQUEST, NAMEUSSING_MESSAGE))
                            : productRepository.save(new Product(id, productDto.getName(), productDto.getPrice())))
                : Mono.error(new CustomException(HttpStatus.NOT_FOUND, NOTFOUND_MESSAGE))
        );

    }

    public Mono<Void> delete(int id){
        Mono<Boolean> existsId = productRepository.findById(id).hasElement();

        return existsId.flatMap(exists -> exists
                ? productRepository.deleteById(id)
                : Mono.error(new CustomException(HttpStatus.NOT_FOUND, NOTFOUND_MESSAGE))
        );
    }

}
