package cn.iocoder.yudao.module.rental.controller.admin.store;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.rental.controller.admin.store.vo.*;
import cn.iocoder.yudao.module.rental.service.store.RentalStoreService;
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

@Tag(name = "管理后台 - 门店管理")
@RestController
@RequestMapping("/rental/store")
@Validated
public class RentalStoreController {

    @Resource
    private RentalStoreService rentalStoreService;

    @PostMapping("/create")
    @Operation(summary = "创建门店")
    @PreAuthorize("@ss.hasPermission('rental:store:create')")
    public CommonResult<Long> createStore(@Valid @RequestBody RentalStoreSaveReqVO createVO) {
        return success(rentalStoreService.createStore(createVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新门店信息")
    @PreAuthorize("@ss.hasPermission('rental:store:update')")
    public CommonResult<Boolean> updateStore(@Valid @RequestBody RentalStoreSaveReqVO updateVO) {
        rentalStoreService.updateStore(updateVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除门店")
    @Parameter(name = "id", description = "门店编号", required = true)
    @PreAuthorize("@ss.hasPermission('rental:store:delete')")
    public CommonResult<Boolean> deleteStore(@RequestParam("id") Long id) {
        rentalStoreService.deleteStore(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获取门店详情")
    @Parameter(name = "id", description = "门店编号", required = true)
    @PreAuthorize("@ss.hasPermission('rental:store:query')")
    public CommonResult<RentalStoreRespVO> getStore(@RequestParam("id") Long id) {
        return success(rentalStoreService.getStore(id));
    }

    @GetMapping("/page")
    @Operation(summary = "获取门店分页列表")
    @PreAuthorize("@ss.hasPermission('rental:store:query')")
    public CommonResult<PageResult<RentalStoreRespVO>> getStorePage(@Valid RentalStorePageReqVO pageVO) {
        return success(rentalStoreService.getStorePage(pageVO));
    }

    @GetMapping("/list")
    @Operation(summary = "获取所有启用门店列表（下拉用）")
    @PreAuthorize("@ss.hasPermission('rental:store:query')")
    public CommonResult<List<RentalStoreRespVO>> getStoreList() {
        return success(rentalStoreService.getStoreList());
    }
}
