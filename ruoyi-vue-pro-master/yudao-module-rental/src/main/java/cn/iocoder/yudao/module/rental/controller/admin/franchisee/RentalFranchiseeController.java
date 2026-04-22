package cn.iocoder.yudao.module.rental.controller.admin.franchisee;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.rental.controller.admin.franchisee.vo.*;
import cn.iocoder.yudao.module.rental.service.franchisee.RentalFranchiseeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 加盟商管理")
@RestController
@RequestMapping("/rental/franchisee")
@Validated
public class RentalFranchiseeController {

    @Resource
    private RentalFranchiseeService rentalFranchiseeService;

    @PostMapping("/create")
    @Operation(summary = "创建加盟商")
    @PreAuthorize("@ss.hasPermission('rental:franchisee:create')")
    public CommonResult<Long> createFranchisee(@Valid @RequestBody RentalFranchiseeSaveReqVO createVO) {
        return success(rentalFranchiseeService.createFranchisee(createVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新加盟商信息")
    @PreAuthorize("@ss.hasPermission('rental:franchisee:update')")
    public CommonResult<Boolean> updateFranchisee(@Valid @RequestBody RentalFranchiseeSaveReqVO updateVO) {
        rentalFranchiseeService.updateFranchisee(updateVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除加盟商")
    @Parameter(name = "id", description = "加盟商编号", required = true)
    @PreAuthorize("@ss.hasPermission('rental:franchisee:delete')")
    public CommonResult<Boolean> deleteFranchisee(@RequestParam("id") Long id) {
        rentalFranchiseeService.deleteFranchisee(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获取加盟商详情")
    @Parameter(name = "id", description = "加盟商编号", required = true)
    @PreAuthorize("@ss.hasPermission('rental:franchisee:query')")
    public CommonResult<RentalFranchiseeRespVO> getFranchisee(@RequestParam("id") Long id) {
        return success(rentalFranchiseeService.getFranchisee(id));
    }

    @GetMapping("/page")
    @Operation(summary = "获取加盟商分页列表")
    @PreAuthorize("@ss.hasPermission('rental:franchisee:query')")
    public CommonResult<PageResult<RentalFranchiseeRespVO>> getFranchiseePage(@Valid RentalFranchiseePageReqVO pageVO) {
        return success(rentalFranchiseeService.getFranchiseePage(pageVO));
    }

    @GetMapping("/list")
    @Operation(summary = "获取所有启用加盟商列表（下拉用）")
    @PreAuthorize("@ss.hasPermission('rental:franchisee:query')")
    public CommonResult<List<RentalFranchiseeRespVO>> getFranchiseeList() {
        return success(rentalFranchiseeService.getFranchiseeList());
    }
}
