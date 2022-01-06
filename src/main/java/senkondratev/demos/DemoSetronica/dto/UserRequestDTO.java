package senkondratev.demos.DemoSetronica.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UserRequestDTO {
    @NotNull(groups = UserSingleProductRequest.class)
    @Min(value = 1, groups = UserSingleProductRequest.class)
    private Long productId;

    @NotBlank(groups = UserRequest.class)
    private String infoLanguage;

    @NotBlank(groups = UserRequest.class)
    private String priceCurrency;

    @NotBlank(groups = UserRequestByName.class)
    private String goodsName;

    @NotBlank(groups = UserRequestByDescription.class)
    private String goodsDescription;

    public interface UserRequest{}
    public interface UserSingleProductRequest{}
    public interface UserRequestByName{}
    public interface UserRequestByDescription{}
}
