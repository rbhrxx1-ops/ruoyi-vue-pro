package cn.iocoder.yudao.module.rental.controller.admin.vehicle.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 租车车辆 Response VO")
@Data
public class RentalVehicleRespVO {

    @Schema(description = "车辆编号")
    private Long id;

    @Schema(description = "车牌号")
    private String plateNo;

    @Schema(description = "车架号(VIN)")
    private String vin;

    @Schema(description = "车型编号")
    private Long modelId;

    @Schema(description = "车型名称")
    private String modelName;

    @Schema(description = "所属门店编号")
    private Long storeId;

    @Schema(description = "门店名称")
    private String storeName;

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

    @Schema(description = "创建时间")
    private LocalDateTime createTime;
}
