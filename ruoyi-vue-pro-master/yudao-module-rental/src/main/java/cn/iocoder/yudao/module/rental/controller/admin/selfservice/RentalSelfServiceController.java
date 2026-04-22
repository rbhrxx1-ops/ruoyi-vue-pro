package cn.iocoder.yudao.module.rental.controller.admin.selfservice;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.rental.dal.dataobject.RentalSelfServiceDeviceDO;
import cn.iocoder.yudao.module.rental.dal.dataobject.RentalSelfServiceRegionDO;
import cn.iocoder.yudao.module.rental.dal.dataobject.RentalSelfServiceTaskDO;
import cn.iocoder.yudao.module.rental.service.selfservice.RentalSelfServiceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

/**
 * 管理后台 - 自助取还管理
 * 对应 FleetEdge：自助取还
 *   - 设备管理（Qube）
 *   - 服务区域管理
 *   - 自助取还任务
 */
@Tag(name = "管理后台 - 自助取还管理")
@RestController
@RequestMapping("/rental/self-service")
@Validated
public class RentalSelfServiceController {

    @Resource
    private RentalSelfServiceService selfServiceService;

    // ========== 设备管理 ==========

    @PostMapping("/device/create")
    @Operation(summary = "新增自助取还设备")
    public CommonResult<Long> createDevice(@RequestBody RentalSelfServiceDeviceDO device) {
        return success(selfServiceService.createDevice(device));
    }

    @PutMapping("/device/update")
    @Operation(summary = "修改自助取还设备")
    public CommonResult<Boolean> updateDevice(@RequestBody RentalSelfServiceDeviceDO device) {
        selfServiceService.updateDevice(device);
        return success(true);
    }

    @DeleteMapping("/device/delete")
    @Operation(summary = "删除自助取还设备")
    public CommonResult<Boolean> deleteDevice(@RequestParam("id") Long id) {
        selfServiceService.deleteDevice(id);
        return success(true);
    }

    @GetMapping("/device/get")
    @Operation(summary = "获取自助取还设备详情")
    public CommonResult<RentalSelfServiceDeviceDO> getDevice(@RequestParam("id") Long id) {
        return success(selfServiceService.getDevice(id));
    }

    @GetMapping("/device/list")
    @Operation(summary = "获取自助取还设备列表")
    public CommonResult<List<RentalSelfServiceDeviceDO>> getDeviceList() {
        return success(selfServiceService.getDeviceList());
    }

    @GetMapping("/device/list-by-store")
    @Operation(summary = "按门店获取自助取还设备列表")
    public CommonResult<List<RentalSelfServiceDeviceDO>> getDeviceListByStore(@RequestParam("storeId") Long storeId) {
        return success(selfServiceService.getDeviceListByStore(storeId));
    }

    @PutMapping("/device/enable")
    @Operation(summary = "启用自助取还设备")
    public CommonResult<Boolean> enableDevice(@RequestParam("id") Long id) {
        selfServiceService.enableDevice(id);
        return success(true);
    }

    @PutMapping("/device/disable")
    @Operation(summary = "禁用自助取还设备")
    public CommonResult<Boolean> disableDevice(@RequestParam("id") Long id) {
        selfServiceService.disableDevice(id);
        return success(true);
    }

    // ========== 服务区域管理 ==========

    @PostMapping("/region/create")
    @Operation(summary = "新增服务区域")
    public CommonResult<Long> createRegion(@RequestBody RentalSelfServiceRegionDO region) {
        return success(selfServiceService.createRegion(region));
    }

    @PutMapping("/region/update")
    @Operation(summary = "修改服务区域")
    public CommonResult<Boolean> updateRegion(@RequestBody RentalSelfServiceRegionDO region) {
        selfServiceService.updateRegion(region);
        return success(true);
    }

    @DeleteMapping("/region/delete")
    @Operation(summary = "删除服务区域")
    public CommonResult<Boolean> deleteRegion(@RequestParam("id") Long id) {
        selfServiceService.deleteRegion(id);
        return success(true);
    }

    @GetMapping("/region/get")
    @Operation(summary = "获取服务区域详情")
    public CommonResult<RentalSelfServiceRegionDO> getRegion(@RequestParam("id") Long id) {
        return success(selfServiceService.getRegion(id));
    }

    @GetMapping("/region/list")
    @Operation(summary = "获取服务区域列表")
    public CommonResult<List<RentalSelfServiceRegionDO>> getRegionList() {
        return success(selfServiceService.getRegionList());
    }

    @GetMapping("/region/list-by-store")
    @Operation(summary = "按门店获取服务区域列表")
    public CommonResult<List<RentalSelfServiceRegionDO>> getRegionListByStore(@RequestParam("storeId") Long storeId) {
        return success(selfServiceService.getRegionListByStore(storeId));
    }

    // ========== 自助取还任务 ==========

    @PostMapping("/task/create")
    @Operation(summary = "创建自助取还任务")
    public CommonResult<Long> createTask(@RequestBody RentalSelfServiceTaskDO task) {
        return success(selfServiceService.createTask(task));
    }

    @PutMapping("/task/update")
    @Operation(summary = "更新自助取还任务")
    public CommonResult<Boolean> updateTask(@RequestBody RentalSelfServiceTaskDO task) {
        selfServiceService.updateTask(task);
        return success(true);
    }

    @GetMapping("/task/get")
    @Operation(summary = "获取自助取还任务详情")
    public CommonResult<RentalSelfServiceTaskDO> getTask(@RequestParam("id") Long id) {
        return success(selfServiceService.getTask(id));
    }

    @GetMapping("/task/list")
    @Operation(summary = "获取自助取还任务列表")
    public CommonResult<List<RentalSelfServiceTaskDO>> getTaskList() {
        return success(selfServiceService.getTaskList());
    }

    @GetMapping("/task/list-by-order")
    @Operation(summary = "按订单获取自助取还任务列表")
    public CommonResult<List<RentalSelfServiceTaskDO>> getTaskListByOrder(@RequestParam("orderId") Long orderId) {
        return success(selfServiceService.getTaskListByOrder(orderId));
    }

    @PutMapping("/task/complete")
    @Operation(summary = "完成自助取还任务")
    public CommonResult<Boolean> completeTask(@RequestParam("id") Long id) {
        selfServiceService.completeTask(id);
        return success(true);
    }

    @PutMapping("/task/cancel")
    @Operation(summary = "取消自助取还任务")
    public CommonResult<Boolean> cancelTask(@RequestParam("id") Long id) {
        selfServiceService.cancelTask(id);
        return success(true);
    }

}
