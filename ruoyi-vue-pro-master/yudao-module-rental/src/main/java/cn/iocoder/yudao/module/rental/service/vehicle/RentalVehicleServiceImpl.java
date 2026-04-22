package cn.iocoder.yudao.module.rental.service.vehicle;

import cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.rental.controller.admin.vehicle.vo.*;
import cn.iocoder.yudao.module.rental.dal.dataobject.RentalVehicleDO;
import cn.iocoder.yudao.module.rental.dal.mysql.RentalVehicleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import javax.annotation.Resource;
import static cn.iocoder.yudao.module.rental.enums.RentalErrorCodeConstants.VEHICLE_NOT_EXISTS;

@Service
@Validated
@Slf4j
public class RentalVehicleServiceImpl implements RentalVehicleService {

    @Resource
    private RentalVehicleMapper rentalVehicleMapper;

    @Override
    public Long createVehicle(RentalVehicleSaveReqVO createReqVO) {
        RentalVehicleDO vehicle = new RentalVehicleDO();
        vehicle.setPlateNo(createReqVO.getPlateNo());
        vehicle.setVin(createReqVO.getVin());
        vehicle.setModelId(createReqVO.getModelId());
        vehicle.setStoreId(createReqVO.getStoreId());
        vehicle.setStatus(createReqVO.getStatus() != null ? createReqVO.getStatus() : 10);
        vehicle.setColor(createReqVO.getColor());
        vehicle.setMileage(createReqVO.getMileage() != null ? createReqVO.getMileage() : 0);
        vehicle.setFuelLevel(createReqVO.getFuelLevel() != null ? createReqVO.getFuelLevel() : 100);
        vehicle.setRemark(createReqVO.getRemark());
        rentalVehicleMapper.insert(vehicle);
        return vehicle.getId();
    }

    @Override
    public void updateVehicle(RentalVehicleSaveReqVO updateReqVO) {
        RentalVehicleDO existing = rentalVehicleMapper.selectById(updateReqVO.getId());
        if (existing == null) {
            throw ServiceExceptionUtil.exception(VEHICLE_NOT_EXISTS);
        }
        RentalVehicleDO updateObj = new RentalVehicleDO();
        updateObj.setId(updateReqVO.getId());
        updateObj.setPlateNo(updateReqVO.getPlateNo());
        updateObj.setVin(updateReqVO.getVin());
        updateObj.setModelId(updateReqVO.getModelId());
        updateObj.setStoreId(updateReqVO.getStoreId());
        updateObj.setStatus(updateReqVO.getStatus());
        updateObj.setColor(updateReqVO.getColor());
        updateObj.setMileage(updateReqVO.getMileage());
        updateObj.setFuelLevel(updateReqVO.getFuelLevel());
        updateObj.setRemark(updateReqVO.getRemark());
        rentalVehicleMapper.updateById(updateObj);
    }

    @Override
    public void deleteVehicle(Long id) {
        RentalVehicleDO vehicle = rentalVehicleMapper.selectById(id);
        if (vehicle == null) {
            throw ServiceExceptionUtil.exception(VEHICLE_NOT_EXISTS);
        }
        rentalVehicleMapper.deleteById(id);
    }

    @Override
    public RentalVehicleRespVO getVehicle(Long id) {
        RentalVehicleDO vehicle = rentalVehicleMapper.selectById(id);
        if (vehicle == null) {
            throw ServiceExceptionUtil.exception(VEHICLE_NOT_EXISTS);
        }
        return toRespVO(vehicle);
    }

    @Override
    public PageResult<RentalVehicleRespVO> getVehiclePage(RentalVehiclePageReqVO pageReqVO) {
        return rentalVehicleMapper.selectPage(pageReqVO);
    }

    private RentalVehicleRespVO toRespVO(RentalVehicleDO v) {
        RentalVehicleRespVO vo = new RentalVehicleRespVO();
        vo.setId(v.getId());
        vo.setPlateNo(v.getPlateNo());
        vo.setVin(v.getVin());
        vo.setModelId(v.getModelId());
        vo.setStoreId(v.getStoreId());
        vo.setStatus(v.getStatus());
        vo.setColor(v.getColor());
        vo.setMileage(v.getMileage());
        vo.setFuelLevel(v.getFuelLevel());
        vo.setRemark(v.getRemark());
        vo.setCreateTime(v.getCreateTime());
        return vo;
    }
}
