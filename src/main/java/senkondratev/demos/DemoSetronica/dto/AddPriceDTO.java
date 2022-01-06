package senkondratev.demos.DemoSetronica.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AddPriceDTO {

    @NotNull
    private Long productId;

    @NotNull
    private Long priceId;
}
