package cn.iocoder.yudao.module.rental.controller.admin.store.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 门店 Response VO")
@Data
public class RentalStoreRespVO {

    @Schema(description = "门店编号")
    private Long id;

    @Schema(description = "门店名称")
    private String name;

    @Schema(description = "加盟商编号")
    private Long franchiseeId;

    @Schema(description = "加盟商名称")
    private String franchiseeName;

    @Schema(description = "城市编号")
    private Integer cityId;

    @Schema(description = "详细地址")
    private String address;

    @Schema(description = "经度")
    private BigDecimal longitude;

    @Schema(description = "纬度")
    private BigDecimal latitude;

    @Schema(description = "联系电话")
    private String contactPhone;

    @Schema(description = "营业时间")
    private String businessHours;

    @Schema(description = "状态（0-停业, 1-营业）")
    private Integer status;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;
}
