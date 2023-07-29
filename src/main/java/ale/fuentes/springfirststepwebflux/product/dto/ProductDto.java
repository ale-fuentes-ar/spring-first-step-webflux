package ale.fuentes.springfirststepwebflux.product.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductDto {

    @NotBlank(message="Name is mandatory")
    private String name;
    @Min(value=1, message="price need must be greater than zero")
    private float price;

}
