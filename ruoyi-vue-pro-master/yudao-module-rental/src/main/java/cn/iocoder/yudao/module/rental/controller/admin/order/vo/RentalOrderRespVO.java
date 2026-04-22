package cn.iocoder.yudao.module.rental.controller.admin.order.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 租车订单 Response VO")
@Data
public class RentalOrderRespVO {

    @Schema(description = "订单编号", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "订单号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String orderNo;

    @Schema(description = "用户编号")
    private Long userId;

    @Schema(description = "门店编号")
    private Long storeId;

    @Schema(description = "门店名称")
    private String storeName;

    @Schema(description = "车辆编号")
    private Long vehicleId;

    @Schema(description = "车牌号")
    private String plateNo;

    @Schema(description = "订单状态")
    private Integer status;

    @Schema(description = "订单总金额（分）")
    private Integer totalAmount;

    @Schema(description = "实际支付金额（分）")
    private Integer payAmount;

    @Schema(description = "预计取车时间")
    private LocalDateTime startTime;

    @Schema(description = "预计还车时间")
    private LocalDateTime endTime;

    @Schema(description = "实际取车时间")
    private LocalDateTime actualStartTime;

    @Schema(description = "实际还车时间")
    private LocalDateTime actualEndTime;

    @Schema(description = "取车门店编号")
    private Long pickupStoreId;

    @Schema(description = "取车门店名称")
    private String pickupStoreName;

    @Schema(description = "还车门店编号")
    private Long returnStoreId;

    @Schema(description = "还车门店名称")
    private String returnStoreName;

    @Schema(description = "渠道来源")
    private String channelSource;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;
}
