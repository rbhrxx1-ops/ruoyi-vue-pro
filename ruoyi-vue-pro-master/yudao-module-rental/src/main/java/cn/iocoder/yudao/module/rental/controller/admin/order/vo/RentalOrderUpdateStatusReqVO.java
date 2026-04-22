package cn.iocoder.yudao.module.rental.controller.admin.order.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 租车订单状态更新 Request VO")
@Data
public class RentalOrderUpdateStatusReqVO {

    @Schema(description = "订单编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "订单编号不能为空")
    private Long id;

    @Schema(description = "订单状态", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "订单状态不能为空")
    private Integer status;

    @Schema(description = "备注")
    private String remark;
}
