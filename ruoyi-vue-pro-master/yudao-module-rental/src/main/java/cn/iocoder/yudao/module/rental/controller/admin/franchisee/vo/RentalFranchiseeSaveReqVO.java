package cn.iocoder.yudao.module.rental.controller.admin.franchisee.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import javax.validation.constraints.NotBlank;

@Schema(description = "管理后台 - 加盟商新增/修改 Request VO")
@Data
public class RentalFranchiseeSaveReqVO {

    @Schema(description = "加盟商编号（修改时必填）")
    private Long id;

    @Schema(description = "加盟商名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "加盟商名称不能为空")
    private String name;

    @Schema(description = "联系人姓名", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "联系人姓名不能为空")
    private String contactName;

    @Schema(description = "联系人电话", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "联系人电话不能为空")
    private String contactPhone;

    @Schema(description = "状态（0-停用, 1-启用）")
    private Integer status;

    @Schema(description = "备注")
    private String remark;
}
