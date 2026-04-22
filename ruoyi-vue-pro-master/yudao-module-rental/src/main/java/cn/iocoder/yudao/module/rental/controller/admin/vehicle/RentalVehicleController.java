package cn.iocoder.yudao.module.rental.controller.admin.vehicle;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.rental.controller.admin.vehicle.vo.*;
import cn.iocoder.yudao.module.rental.service.vehicle.RentalVehicleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.validation.Valid;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 租车车辆管理")
@RestController
@RequestMapping("/rental/vehicle")
@Validated
public class RentalVehicleController {

    @Resource
    private RentalVehicleService rentalVehicleService;

    @PostMapping("/create")
    @Operation(summary = "创建车辆")
    @PreAuthorize("@ss.hasPermission('rental:vehicle:create')")
    public CommonResult<Long> createVehicle(@Valid @RequestBody RentalVehicleSaveReqVO createVO) {
        return success(rentalVehicleService.createVehicle(createVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新车辆信息")
    @PreAuthorize("@ss.hasPermission('rental:vehicle:update')")
    public CommonResult<Boolean> updateVehicle(@Valid @RequestBody RentalVehicleSaveReqVO updateVO) {
        rentalVehicleService.updateVehicle(updateVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除车辆")
    @Parameter(name = "id", description = "车辆编号", required = true)
    @PreAuthorize("@ss.hasPermission('rental:vehicle:delete')")
    public CommonResult<Boolean> deleteVehicle(@RequestParam("id") Long id) {
        rentalVehicleService.deleteVehicle(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获取车辆详情")
    @Parameter(name = "id", description = "车辆编号", required = true)
    @PreAuthorize("@ss.hasPermission('rental:vehicle:query')")
    public CommonResult<RentalVehicleRespVO> getVehicle(@RequestParam("id") Long id) {
        return success(rentalVehicleService.getVehicle(id));
    }

    @GetMapping("/page")
    @Operation(summary = "获取车辆分页列表")
    @PreAuthorize("@ss.hasPermission('rental:vehicle:query')")
    public CommonResult<PageResult<RentalVehicleRespVO>> getVehiclePage(@Valid RentalVehiclePageReqVO pageVO) {
        return success(rentalVehicleService.getVehiclePage(pageVO));
    }
}
