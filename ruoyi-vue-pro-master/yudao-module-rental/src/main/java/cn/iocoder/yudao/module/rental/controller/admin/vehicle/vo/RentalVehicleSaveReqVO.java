package cn.iocoder.yudao.module.rental.controller.admin.vehicle.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 租车车辆新增/修改 Request VO")
@Data
public class RentalVehicleSaveReqVO {

    @Schema(description = "车辆编号（修改时必填）")
    private Long id;

    @Schema(description = "车牌号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "车牌号不能为空")
    private String plateNo;

    @Schema(description = "车架号(VIN)", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "车架号不能为空")
    private String vin;

    @Schema(description = "车型编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "车型编号不能为空")
    private Long modelId;

    @Schema(description = "所属门店编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "所属门店不能为空")
    private Long storeId;

    @Schema(description = "车辆状态")
    private Integer status;

    @Schema(description = "颜色")
    private String color;

    @Schema(description = "里程数(km)")
    private Integer mileage;

    @Schema(description = "油量/电量百分比")
    private Integer fuelLevel;

    @Schema(description = "备注")
    private String remark;
}
