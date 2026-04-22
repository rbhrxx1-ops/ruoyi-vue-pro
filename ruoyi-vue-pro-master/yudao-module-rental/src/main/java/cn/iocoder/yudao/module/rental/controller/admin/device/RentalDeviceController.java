package cn.iocoder.yudao.module.rental.controller.admin.device;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.rental.dal.dataobject.RentalDeviceDO;
import cn.iocoder.yudao.module.rental.service.device.RentalDeviceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

/**
 * 管理后台 - 附加设备管理
 * 对应 FleetEdge：租车商品管理 > 附加设备管理
 * 真实数据：ETC卡(1500 JPY/次)、防滑链(3500 JPY/次)、儿童座椅(2500 JPY/个)、雪地胎(3500 JPY/次)
 */
@Tag(name = "管理后台 - 附加设备管理")
@RestController
@RequestMapping("/rental/device")
@Validated
public class RentalDeviceController {

    @Resource
    private RentalDeviceService deviceService;

    @PostMapping("/create")
    @Operation(summary = "新增附加设备")
    public CommonResult<Long> createDevice(@RequestBody RentalDeviceDO device) {
        return success(deviceService.createDevice(device));
    }

    @PutMapping("/update")
    @Operation(summary = "修改附加设备")
    public CommonResult<Boolean> updateDevice(@RequestBody RentalDeviceDO device) {
        deviceService.updateDevice(device);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除附加设备")
    public CommonResult<Boolean> deleteDevice(@RequestParam("id") Long id) {
        deviceService.deleteDevice(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获取附加设备详情")
    public CommonResult<RentalDeviceDO> getDevice(@RequestParam("id") Long id) {
        return success(deviceService.getDevice(id));
    }

    @GetMapping("/list")
    @Operation(summary = "获取附加设备列表")
    public CommonResult<List<RentalDeviceDO>> getDeviceList() {
        return success(deviceService.getDeviceList());
    }

    @GetMapping("/list-by-franchisee")
    @Operation(summary = "按加盟商获取附加设备列表")
    public CommonResult<List<RentalDeviceDO>> getDeviceListByFranchisee(@RequestParam("franchiseeId") Long franchiseeId) {
        return success(deviceService.getDeviceListByFranchisee(franchiseeId));
    }

    @PutMapping("/enable")
    @Operation(summary = "启用附加设备")
    public CommonResult<Boolean> enableDevice(@RequestParam("id") Long id) {
        deviceService.enableDevice(id);
        return success(true);
    }

    @PutMapping("/disable")
    @Operation(summary = "禁用附加设备")
    public CommonResult<Boolean> disableDevice(@RequestParam("id") Long id) {
        deviceService.disableDevice(id);
        return success(true);
    }

    @PutMapping("/batch-enable")
    @Operation(summary = "批量启用附加设备")
    public CommonResult<Boolean> batchEnable(@RequestBody List<Long> ids) {
        deviceService.batchEnable(ids);
        return success(true);
    }

    @PutMapping("/batch-disable")
    @Operation(summary = "批量禁用附加设备")
    public CommonResult<Boolean> batchDisable(@RequestBody List<Long> ids) {
        deviceService.batchDisable(ids);
        return success(true);
    }

}
