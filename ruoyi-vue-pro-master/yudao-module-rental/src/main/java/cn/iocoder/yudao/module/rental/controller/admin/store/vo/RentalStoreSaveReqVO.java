package cn.iocoder.yudao.module.rental.controller.admin.store.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 门店新增/修改 Request VO")
@Data
public class RentalStoreSaveReqVO {

    @Schema(description = "门店编号（修改时必填）")
    private Long id;

    @Schema(description = "门店名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "门店名称不能为空")
    private String name;

    @Schema(description = "加盟商编号（为空则为直营）")
    private Long franchiseeId;

    @Schema(description = "所在城市编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "城市不能为空")
    private Integer cityId;

    @Schema(description = "详细地址", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "详细地址不能为空")
    private String address;

    @Schema(description = "经度")
    private BigDecimal longitude;

    @Schema(description = "纬度")
    private BigDecimal latitude;

    @Schema(description = "联系电话", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "联系电话不能为空")
    private String contactPhone;

    @Schema(description = "营业时间")
    private String businessHours;

    @Schema(description = "状态（0-停业, 1-营业）")
    private Integer status;
}
