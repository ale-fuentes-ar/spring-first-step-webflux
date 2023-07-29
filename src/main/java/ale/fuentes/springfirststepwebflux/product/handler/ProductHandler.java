package ale.fuentes.springfirststepwebflux.product.handler;

import ale.fuentes.springfirststepwebflux.config.validation.ObjectValidator;
import ale.fuentes.springfirststepwebflux.product.dto.ProductDto;
import ale.fuentes.springfirststepwebflux.product.entity.Product;
import ale.fuentes.springfirststepwebflux.product.repository.ProductRepository;
import ale.fuentes.springfirststepwebflux.product.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@RequiredArgsConstructor
public class ProductHandler {

    private final ProductService productService;
    private final ObjectValidator objectValidator;

    public Mono<ServerResponse> getAll(ServerRequest request){
        Flux<Product> products = productService.getAll();
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(products, Product.class);
    }

    public Mono<ServerResponse> getOne(ServerRequest request){
        int id = Integer.valueOf(request.pathVariable("id"));
        Mono<Product> product = productService.getById(id);

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(product, Product.class);
    }

    public Mono<ServerResponse> save(ServerRequest request){
        Mono<ProductDto> dtoMono = request.bodyToMono(ProductDto.class).doOnNext(objectValidator::validate);

        return dtoMono.flatMap(productDto ->
            ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(productService.save(productDto), Product.class)
        );
    }

    public Mono<ServerResponse> update(ServerRequest request){
        int id = Integer.valueOf(request.pathVariable("id"));
        Mono<ProductDto> dtoMono = request.bodyToMono(ProductDto.class).doOnNext(objectValidator::validate);

        return dtoMono.flatMap(productDto ->
                ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(productService.update(id, productDto), Product.class)
        );
    }

    public Mono<ServerResponse> delete(ServerRequest request){
        int id = Integer.valueOf(request.pathVariable("id"));

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(productService.delete(id), Product.class);
    }

}
