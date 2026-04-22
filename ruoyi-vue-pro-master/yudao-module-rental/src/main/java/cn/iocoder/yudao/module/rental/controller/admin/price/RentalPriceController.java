package cn.iocoder.yudao.module.rental.controller.admin.price;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.rental.dal.dataobject.RentalCarRentRuleDO;
import cn.iocoder.yudao.module.rental.dal.dataobject.RentalPickoffRuleDO;
import cn.iocoder.yudao.module.rental.service.price.RentalPriceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

/**
 * 管理后台 - 租车价格中心
 * 对应 FleetEdge：租车价格中心 > 车辆租金费率 / 异地取还费率
 */
@Tag(name = "管理后台 - 租车价格中心")
@RestController
@RequestMapping("/rental/price")
@Validated
public class RentalPriceController {

    @Resource
    private RentalPriceService priceService;

    // ========== 车辆租金费率 ==========

    @PostMapping("/car-rent/create")
    @Operation(summary = "新增车辆租金费率规则")
    public CommonResult<Long> createCarRentRule(@RequestBody RentalCarRentRuleDO rule) {
        return success(priceService.createCarRentRule(rule));
    }

    @PutMapping("/car-rent/update")
    @Operation(summary = "修改车辆租金费率规则")
    public CommonResult<Boolean> updateCarRentRule(@RequestBody RentalCarRentRuleDO rule) {
        priceService.updateCarRentRule(rule);
        return success(true);
    }

    @DeleteMapping("/car-rent/delete")
    @Operation(summary = "删除车辆租金费率规则")
    public CommonResult<Boolean> deleteCarRentRule(@RequestParam("id") Long id) {
        priceService.deleteCarRentRule(id);
        return success(true);
    }

    @GetMapping("/car-rent/get")
    @Operation(summary = "获取车辆租金费率规则详情")
    public CommonResult<RentalCarRentRuleDO> getCarRentRule(@RequestParam("id") Long id) {
        return success(priceService.getCarRentRule(id));
    }

    @GetMapping("/car-rent/list")
    @Operation(summary = "获取车辆租金费率规则列表")
    public CommonResult<List<RentalCarRentRuleDO>> getCarRentRuleList() {
        return success(priceService.getCarRentRuleList());
    }

    @GetMapping("/car-rent/list-by-store")
    @Operation(summary = "按门店获取车辆租金费率规则列表")
    public CommonResult<List<RentalCarRentRuleDO>> getCarRentRuleListByStore(@RequestParam("storeId") Long storeId) {
        return success(priceService.getCarRentRuleListByStore(storeId));
    }

    @PutMapping("/car-rent/enable")
    @Operation(summary = "启用车辆租金费率规则")
    public CommonResult<Boolean> enableCarRentRule(@RequestParam("id") Long id) {
        priceService.enableCarRentRule(id);
        return success(true);
    }

    @PutMapping("/car-rent/disable")
    @Operation(summary = "禁用车辆租金费率规则")
    public CommonResult<Boolean> disableCarRentRule(@RequestParam("id") Long id) {
        priceService.disableCarRentRule(id);
        return success(true);
    }

    // ========== 异地取还费率 ==========

    @PostMapping("/pickoff/create")
    @Operation(summary = "新增异地取还费率规则")
    public CommonResult<Long> createPickoffRule(@RequestBody RentalPickoffRuleDO rule) {
        return success(priceService.createPickoffRule(rule));
    }

    @PutMapping("/pickoff/update")
    @Operation(summary = "修改异地取还费率规则")
    public CommonResult<Boolean> updatePickoffRule(@RequestBody RentalPickoffRuleDO rule) {
        priceService.updatePickoffRule(rule);
        return success(true);
    }

    @DeleteMapping("/pickoff/delete")
    @Operation(summary = "删除异地取还费率规则")
    public CommonResult<Boolean> deletePickoffRule(@RequestParam("id") Long id) {
        priceService.deletePickoffRule(id);
        return success(true);
    }

    @GetMapping("/pickoff/get")
    @Operation(summary = "获取异地取还费率规则详情")
    public CommonResult<RentalPickoffRuleDO> getPickoffRule(@RequestParam("id") Long id) {
        return success(priceService.getPickoffRule(id));
    }

    @GetMapping("/pickoff/list")
    @Operation(summary = "获取异地取还费率规则列表")
    public CommonResult<List<RentalPickoffRuleDO>> getPickoffRuleList() {
        return success(priceService.getPickoffRuleList());
    }

    @PutMapping("/pickoff/enable")
    @Operation(summary = "启用异地取还费率规则")
    public CommonResult<Boolean> enablePickoffRule(@RequestParam("id") Long id) {
        priceService.enablePickoffRule(id);
        return success(true);
    }

    @PutMapping("/pickoff/disable")
    @Operation(summary = "禁用异地取还费率规则")
    public CommonResult<Boolean> disablePickoffRule(@RequestParam("id") Long id) {
        priceService.disablePickoffRule(id);
        return success(true);
    }

    @PutMapping("/pickoff/batch-enable")
    @Operation(summary = "批量启用异地取还费率规则")
    public CommonResult<Boolean> batchEnablePickoffRule(@RequestBody List<Long> ids) {
        priceService.batchEnablePickoffRule(ids);
        return success(true);
    }

    @PutMapping("/pickoff/batch-disable")
    @Operation(summary = "批量禁用异地取还费率规则")
    public CommonResult<Boolean> batchDisablePickoffRule(@RequestBody List<Long> ids) {
        priceService.batchDisablePickoffRule(ids);
        return success(true);
    }

}
