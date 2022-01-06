package senkondratev.demos.DemoSetronica.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;

@Data
public class ProductDTO {
    @Null(groups = CreateProduct.class)
    @NotNull(groups = UpdateProduct.class)
    @JsonProperty("productId")
    private Long id;

    @JsonProperty("info")
    @Valid
    private List<InfoDTO> infoDTO;

    @JsonProperty("prices")
    @Valid
    private List<PriceDTO> priceDTO;

    @Null
    private Date creationDate;

    @Null
    private Date modificationDate;

    public interface CreateProduct {
    }

    public interface UpdateProduct {
    }
}
