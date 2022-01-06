package senkondratev.demos.DemoSetronica.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Data
public class PriceDTO {
    @Null(groups = CreatePrice.class)
    @NotNull(groups = UpdatePrice.class)
    private Long priceId;

    @NotBlank(groups = CreatePrice.class)
    private String currencyName;

    @NotNull(groups = {CreatePrice.class, UpdatePrice.class})
    @Min(value = 1, groups = {CreatePrice.class, UpdatePrice.class})
    private Integer currencyAmount;

    public interface CreatePrice{}
    public interface UpdatePrice{}
}
