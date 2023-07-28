package ale.fuentes.springfirststepwebflux.product.router;

import ale.fuentes.springfirststepwebflux.product.handler.ProductHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@Slf4j
public class ProductRouter {

    private static final String PATH_MAPPING = "/product";

    @Bean
    public WebProperties.Resources resources() {
        return new WebProperties.Resources();
    }

    @Bean
    RouterFunction<ServerResponse> router(ProductHandler handler){
        return RouterFunctions.route()
                .GET(PATH_MAPPING, handler::getAll)
                .GET(PATH_MAPPING.concat("/{id}"), handler::getOne)
                .POST(PATH_MAPPING, handler::save)
                .PUT(PATH_MAPPING.concat("/{id}"), handler::update)
                .DELETE(PATH_MAPPING.concat("/{id}"), handler::delete)
                .build();
    }
}
