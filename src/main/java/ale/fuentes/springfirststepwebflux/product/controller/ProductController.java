package ale.fuentes.springfirststepwebflux.product.controller;

import ale.fuentes.springfirststepwebflux.product.entity.Product;
import ale.fuentes.springfirststepwebflux.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/product")
@Slf4j
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public Flux<Product> getAll(){
        return productService.getAll();
    }

    @GetMapping("/{id}")
    public Mono<Product> getByid(@PathVariable("id") int id){
        return productService.getById(id);
    }

    @PostMapping
    public Mono<Product> save(@RequestBody Product product){
        return productService.save(product);
    }

    @PutMapping("/{id}")
    public Mono<Product> update(@PathVariable("id") int id, @RequestBody Product product){
        return productService.update(id, product);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteById(@PathVariable("id") int id){
        return productService.delete(id);
    }
}
