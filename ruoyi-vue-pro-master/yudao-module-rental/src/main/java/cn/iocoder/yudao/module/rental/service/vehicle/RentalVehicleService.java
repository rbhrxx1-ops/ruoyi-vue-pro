package cn.iocoder.yudao.module.rental.service.vehicle;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.rental.controller.admin.vehicle.vo.*;
import javax.validation.Valid;

/**
 * 租车车辆 Service 接口
 */
public interface RentalVehicleService {

    Long createVehicle(@Valid RentalVehicleSaveReqVO createReqVO);

    void updateVehicle(@Valid RentalVehicleSaveReqVO updateReqVO);

    void deleteVehicle(Long id);

    RentalVehicleRespVO getVehicle(Long id);

    PageResult<RentalVehicleRespVO> getVehiclePage(@Valid RentalVehiclePageReqVO pageReqVO);
}
