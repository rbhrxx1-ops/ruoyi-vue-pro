package cn.iocoder.yudao.module.rental.controller.admin.franchisee.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 加盟商 Response VO")
@Data
public class RentalFranchiseeRespVO {

    @Schema(description = "加盟商编号")
    private Long id;

    @Schema(description = "加盟商名称")
    private String name;

    @Schema(description = "联系人姓名")
    private String contactName;

    @Schema(description = "联系人电话")
    private String contactPhone;

    @Schema(description = "状态（0-停用, 1-启用）")
    private Integer status;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;
}
