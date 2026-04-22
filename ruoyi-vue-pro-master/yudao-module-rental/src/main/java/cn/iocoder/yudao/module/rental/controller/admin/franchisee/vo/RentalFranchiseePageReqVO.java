package cn.iocoder.yudao.module.rental.controller.admin.franchisee.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "管理后台 - 加盟商分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RentalFranchiseePageReqVO extends PageParam {

    @Schema(description = "加盟商名称")
    private String name;

    @Schema(description = "联系人姓名")
    private String contactName;

    @Schema(description = "状态")
    private Integer status;
}
