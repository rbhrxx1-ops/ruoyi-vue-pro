package cn.iocoder.yudao.module.rental.controller.admin.vehicle.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "管理后台 - 租车车辆分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RentalVehiclePageReqVO extends PageParam {

    @Schema(description = "车牌号")
    private String plateNo;

    @Schema(description = "车型编号")
    private Long modelId;

    @Schema(description = "所属门店编号")
    private Long storeId;

    @Schema(description = "车辆状态")
    private Integer status;
}
