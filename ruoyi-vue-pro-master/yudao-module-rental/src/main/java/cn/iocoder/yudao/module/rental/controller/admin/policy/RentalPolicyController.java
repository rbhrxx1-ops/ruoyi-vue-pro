package cn.iocoder.yudao.module.rental.controller.admin.policy;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.rental.dal.dataobject.RentalCancelPolicyDO;
import cn.iocoder.yudao.module.rental.dal.dataobject.RentalDepositPolicyDO;
import cn.iocoder.yudao.module.rental.dal.dataobject.RentalReservePolicyDO;
import cn.iocoder.yudao.module.rental.service.policy.RentalPolicyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

/**
 * 管理后台 - 租车政策管理
 * 对应 FleetEdge：租车政策管理
 *   - 预定政策（预定时间限制 + 驾龄及年龄限制）
 *   - 取用车政策（押金政策 + 超里程限制）
 *   - 取消政策
 */
@Tag(name = "管理后台 - 租车政策管理")
@RestController
@RequestMapping("/rental/policy")
@Validated
public class RentalPolicyController {

    @Resource
    private RentalPolicyService policyService;

    // ========== 预定政策 ==========

    @PostMapping("/reserve/create")
    @Operation(summary = "新增预定政策")
    public CommonResult<Long> createReservePolicy(@RequestBody RentalReservePolicyDO policy) {
        return success(policyService.createReservePolicy(policy));
    }

    @PutMapping("/reserve/update")
    @Operation(summary = "修改预定政策")
    public CommonResult<Boolean> updateReservePolicy(@RequestBody RentalReservePolicyDO policy) {
        policyService.updateReservePolicy(policy);
        return success(true);
    }

    @DeleteMapping("/reserve/delete")
    @Operation(summary = "删除预定政策")
    public CommonResult<Boolean> deleteReservePolicy(@RequestParam("id") Long id) {
        policyService.deleteReservePolicy(id);
        return success(true);
    }

    @GetMapping("/reserve/get")
    @Operation(summary = "获取预定政策详情")
    public CommonResult<RentalReservePolicyDO> getReservePolicy(@RequestParam("id") Long id) {
        return success(policyService.getReservePolicy(id));
    }

    @GetMapping("/reserve/list")
    @Operation(summary = "获取预定政策列表")
    public CommonResult<List<RentalReservePolicyDO>> getReservePolicyList() {
        return success(policyService.getReservePolicyList());
    }

    @PutMapping("/reserve/enable")
    @Operation(summary = "启用预定政策")
    public CommonResult<Boolean> enableReservePolicy(@RequestParam("id") Long id) {
        policyService.enableReservePolicy(id);
        return success(true);
    }

    @PutMapping("/reserve/disable")
    @Operation(summary = "禁用预定政策")
    public CommonResult<Boolean> disableReservePolicy(@RequestParam("id") Long id) {
        policyService.disableReservePolicy(id);
        return success(true);
    }

    // ========== 取用车政策 - 押金政策 ==========

    @PostMapping("/deposit/create")
    @Operation(summary = "新增押金政策")
    public CommonResult<Long> createDepositPolicy(@RequestBody RentalDepositPolicyDO policy) {
        return success(policyService.createDepositPolicy(policy));
    }

    @PutMapping("/deposit/update")
    @Operation(summary = "修改押金政策")
    public CommonResult<Boolean> updateDepositPolicy(@RequestBody RentalDepositPolicyDO policy) {
        policyService.updateDepositPolicy(policy);
        return success(true);
    }

    @DeleteMapping("/deposit/delete")
    @Operation(summary = "删除押金政策")
    public CommonResult<Boolean> deleteDepositPolicy(@RequestParam("id") Long id) {
        policyService.deleteDepositPolicy(id);
        return success(true);
    }

    @GetMapping("/deposit/get")
    @Operation(summary = "获取押金政策详情")
    public CommonResult<RentalDepositPolicyDO> getDepositPolicy(@RequestParam("id") Long id) {
        return success(policyService.getDepositPolicy(id));
    }

    @GetMapping("/deposit/list")
    @Operation(summary = "获取押金政策列表")
    public CommonResult<List<RentalDepositPolicyDO>> getDepositPolicyList() {
        return success(policyService.getDepositPolicyList());
    }

    @PutMapping("/deposit/enable")
    @Operation(summary = "启用押金政策")
    public CommonResult<Boolean> enableDepositPolicy(@RequestParam("id") Long id) {
        policyService.enableDepositPolicy(id);
        return success(true);
    }

    @PutMapping("/deposit/disable")
    @Operation(summary = "禁用押金政策")
    public CommonResult<Boolean> disableDepositPolicy(@RequestParam("id") Long id) {
        policyService.disableDepositPolicy(id);
        return success(true);
    }

    // ========== 取消政策 ==========

    @PostMapping("/cancel/create")
    @Operation(summary = "新增取消政策")
    public CommonResult<Long> createCancelPolicy(@RequestBody RentalCancelPolicyDO policy) {
        return success(policyService.createCancelPolicy(policy));
    }

    @PutMapping("/cancel/update")
    @Operation(summary = "修改取消政策")
    public CommonResult<Boolean> updateCancelPolicy(@RequestBody RentalCancelPolicyDO policy) {
        policyService.updateCancelPolicy(policy);
        return success(true);
    }

    @DeleteMapping("/cancel/delete")
    @Operation(summary = "删除取消政策")
    public CommonResult<Boolean> deleteCancelPolicy(@RequestParam("id") Long id) {
        policyService.deleteCancelPolicy(id);
        return success(true);
    }

    @GetMapping("/cancel/get")
    @Operation(summary = "获取取消政策详情")
    public CommonResult<RentalCancelPolicyDO> getCancelPolicy(@RequestParam("id") Long id) {
        return success(policyService.getCancelPolicy(id));
    }

    @GetMapping("/cancel/list")
    @Operation(summary = "获取取消政策列表")
    public CommonResult<List<RentalCancelPolicyDO>> getCancelPolicyList() {
        return success(policyService.getCancelPolicyList());
    }

    @PutMapping("/cancel/enable")
    @Operation(summary = "启用取消政策")
    public CommonResult<Boolean> enableCancelPolicy(@RequestParam("id") Long id) {
        policyService.enableCancelPolicy(id);
        return success(true);
    }

    @PutMapping("/cancel/disable")
    @Operation(summary = "禁用取消政策")
    public CommonResult<Boolean> disableCancelPolicy(@RequestParam("id") Long id) {
        policyService.disableCancelPolicy(id);
        return success(true);
    }

}
