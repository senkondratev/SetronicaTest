package senkondratev.demos.DemoSetronica.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Data
public class InfoDTO {
    @Null(groups = CreateInfo.class)
    @NotNull(groups = UpdateInfo.class)
    private Long infoId;

    @NotBlank(groups = {CreateInfo.class, UpdateInfo.class})
    private String goodsName;

    private String goodsDescription;

    @NotBlank(groups = CreateInfo.class)
    private String language;

    public interface CreateInfo{}
    public interface UpdateInfo{}
}
