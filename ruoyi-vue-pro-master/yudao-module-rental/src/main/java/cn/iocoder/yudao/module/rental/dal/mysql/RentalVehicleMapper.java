package cn.iocoder.yudao.module.rental.dal.mysql;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.rental.controller.admin.vehicle.vo.RentalVehiclePageReqVO;
import cn.iocoder.yudao.module.rental.controller.admin.vehicle.vo.RentalVehicleRespVO;
import cn.iocoder.yudao.module.rental.dal.dataobject.RentalVehicleDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 租车车辆 Mapper
 */
@Mapper
public interface RentalVehicleMapper extends BaseMapperX<RentalVehicleDO> {

    default PageResult<RentalVehicleRespVO> selectPage(RentalVehiclePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<RentalVehicleDO>()
                .likeIfPresent(RentalVehicleDO::getPlateNo, reqVO.getPlateNo())
                .eqIfPresent(RentalVehicleDO::getModelId, reqVO.getModelId())
                .eqIfPresent(RentalVehicleDO::getStoreId, reqVO.getStoreId())
                .eqIfPresent(RentalVehicleDO::getStatus, reqVO.getStatus())
                .orderByDesc(RentalVehicleDO::getId))
                .convert(v -> {
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
                });
    }
}
