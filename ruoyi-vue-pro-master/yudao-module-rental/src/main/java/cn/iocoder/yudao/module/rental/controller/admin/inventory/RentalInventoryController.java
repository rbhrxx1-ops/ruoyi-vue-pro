package cn.iocoder.yudao.module.rental.controller.admin.inventory;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.rental.dal.dataobject.RentalInventoryDO;
import cn.iocoder.yudao.module.rental.service.inventory.RentalInventoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

/**
 * 管理后台 - 车型库存管理
 * 对应 FleetEdge：租车库存管理 > 车型库存管理
 */
@Tag(name = "管理后台 - 车型库存管理")
@RestController
@RequestMapping("/rental/inventory")
@Validated
public class RentalInventoryController {

    @Resource
    private RentalInventoryService inventoryService;

    @PostMapping("/create")
    @Operation(summary = "新增库存记录")
    public CommonResult<Long> createInventory(@RequestBody RentalInventoryDO inventory) {
        return success(inventoryService.createInventory(inventory));
    }

    @PutMapping("/update")
    @Operation(summary = "修改库存记录")
    public CommonResult<Boolean> updateInventory(@RequestBody RentalInventoryDO inventory) {
        inventoryService.updateInventory(inventory);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除库存记录")
    public CommonResult<Boolean> deleteInventory(@RequestParam("id") Long id) {
        inventoryService.deleteInventory(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获取库存记录详情")
    public CommonResult<RentalInventoryDO> getInventory(@RequestParam("id") Long id) {
        return success(inventoryService.getInventory(id));
    }

    @GetMapping("/list")
    @Operation(summary = "获取全部库存列表")
    public CommonResult<List<RentalInventoryDO>> getInventoryList() {
        return success(inventoryService.getInventoryList());
    }

    @GetMapping("/list-by-store")
    @Operation(summary = "按门店获取库存列表")
    public CommonResult<List<RentalInventoryDO>> getInventoryListByStore(@RequestParam("storeId") Long storeId) {
        return success(inventoryService.getInventoryListByStore(storeId));
    }

    @PutMapping("/adjust")
    @Operation(summary = "调整可用库存")
    public CommonResult<Boolean> adjustStock(@RequestParam("id") Long id, @RequestParam("delta") int delta) {
        inventoryService.adjustAvailableStock(id, delta);
        return success(true);
    }

}
