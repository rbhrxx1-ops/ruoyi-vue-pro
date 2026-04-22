package cn.iocoder.yudao.module.rental.controller.admin.store.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "管理后台 - 门店分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RentalStorePageReqVO extends PageParam {

    @Schema(description = "门店名称")
    private String name;

    @Schema(description = "加盟商编号")
    private Long franchiseeId;

    @Schema(description = "城市编号")
    private Integer cityId;

    @Schema(description = "状态")
    private Integer status;
}
