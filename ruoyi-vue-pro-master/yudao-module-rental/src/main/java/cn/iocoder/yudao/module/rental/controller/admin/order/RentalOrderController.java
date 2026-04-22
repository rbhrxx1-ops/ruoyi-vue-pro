package cn.iocoder.yudao.module.rental.controller.admin.order;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.rental.controller.admin.order.vo.*;
import cn.iocoder.yudao.module.rental.service.order.RentalOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.validation.Valid;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 租车订单")
@RestController
@RequestMapping("/rental/order")
@Validated
public class RentalOrderController {

    @Resource
    private RentalOrderService rentalOrderService;

    @GetMapping("/page")
    @Operation(summary = "获取租车订单分页")
    @PreAuthorize("@ss.hasPermission('rental:order:query')")
    public CommonResult<PageResult<RentalOrderRespVO>> getOrderPage(@Valid RentalOrderPageReqVO pageVO) {
        return success(rentalOrderService.getOrderPage(pageVO));
    }

    @GetMapping("/get")
    @Operation(summary = "获取租车订单详情")
    @Parameter(name = "id", description = "订单编号", required = true)
    @PreAuthorize("@ss.hasPermission('rental:order:query')")
    public CommonResult<RentalOrderRespVO> getOrder(@RequestParam("id") Long id) {
        return success(rentalOrderService.getOrder(id));
    }

    @PutMapping("/update-status")
    @Operation(summary = "更新订单状态")
    @PreAuthorize("@ss.hasPermission('rental:order:update')")
    public CommonResult<Boolean> updateOrderStatus(@Valid @RequestBody RentalOrderUpdateStatusReqVO updateVO) {
        rentalOrderService.updateOrderStatus(updateVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除租车订单")
    @Parameter(name = "id", description = "订单编号", required = true)
    @PreAuthorize("@ss.hasPermission('rental:order:delete')")
    public CommonResult<Boolean> deleteOrder(@RequestParam("id") Long id) {
        rentalOrderService.deleteOrder(id);
        return success(true);
    }
}
