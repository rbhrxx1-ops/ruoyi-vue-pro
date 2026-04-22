package cn.iocoder.yudao.module.rental.controller.admin.insurance;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.rental.dal.dataobject.RentalInsuranceDO;
import cn.iocoder.yudao.module.rental.service.insurance.RentalInsuranceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

/**
 * 管理后台 - 保险商品管理
 * 对应 FleetEdge：租车商品管理 > 保险商品管理
 */
@Tag(name = "管理后台 - 保险商品管理")
@RestController
@RequestMapping("/rental/insurance")
@Validated
public class RentalInsuranceController {

    @Resource
    private RentalInsuranceService insuranceService;

    @PostMapping("/create")
    @Operation(summary = "新增保险商品")
    public CommonResult<Long> createInsurance(@RequestBody RentalInsuranceDO insurance) {
        return success(insuranceService.createInsurance(insurance));
    }

    @PutMapping("/update")
    @Operation(summary = "修改保险商品")
    public CommonResult<Boolean> updateInsurance(@RequestBody RentalInsuranceDO insurance) {
        insuranceService.updateInsurance(insurance);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除保险商品")
    public CommonResult<Boolean> deleteInsurance(@RequestParam("id") Long id) {
        insuranceService.deleteInsurance(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获取保险商品详情")
    public CommonResult<RentalInsuranceDO> getInsurance(@RequestParam("id") Long id) {
        return success(insuranceService.getInsurance(id));
    }

    @GetMapping("/list")
    @Operation(summary = "获取保险商品列表")
    public CommonResult<List<RentalInsuranceDO>> getInsuranceList() {
        return success(insuranceService.getInsuranceList());
    }

    @GetMapping("/list-by-franchisee")
    @Operation(summary = "按加盟商获取保险商品列表")
    public CommonResult<List<RentalInsuranceDO>> getInsuranceListByFranchisee(@RequestParam("franchiseeId") Long franchiseeId) {
        return success(insuranceService.getInsuranceListByFranchisee(franchiseeId));
    }

    @PutMapping("/enable")
    @Operation(summary = "启用保险商品")
    public CommonResult<Boolean> enableInsurance(@RequestParam("id") Long id) {
        insuranceService.enableInsurance(id);
        return success(true);
    }

    @PutMapping("/disable")
    @Operation(summary = "禁用保险商品")
    public CommonResult<Boolean> disableInsurance(@RequestParam("id") Long id) {
        insuranceService.disableInsurance(id);
        return success(true);
    }

}
