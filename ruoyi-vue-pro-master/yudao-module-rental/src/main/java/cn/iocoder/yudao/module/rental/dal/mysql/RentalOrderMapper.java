package cn.iocoder.yudao.module.rental.dal.mysql;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.rental.controller.admin.order.vo.RentalOrderPageReqVO;
import cn.iocoder.yudao.module.rental.controller.admin.order.vo.RentalOrderRespVO;
import cn.iocoder.yudao.module.rental.dal.dataobject.RentalOrderDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 租车订单 Mapper
 */
@Mapper
public interface RentalOrderMapper extends BaseMapperX<RentalOrderDO> {

    default PageResult<RentalOrderRespVO> selectPage(RentalOrderPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<RentalOrderDO>()
                .likeIfPresent(RentalOrderDO::getOrderNo, reqVO.getOrderNo())
                .eqIfPresent(RentalOrderDO::getStatus, reqVO.getStatus())
                .eqIfPresent(RentalOrderDO::getStoreId, reqVO.getStoreId())
                .eqIfPresent(RentalOrderDO::getVehicleId, reqVO.getVehicleId())
                .eqIfPresent(RentalOrderDO::getChannelSource, reqVO.getChannelSource())
                .betweenIfPresent(RentalOrderDO::getCreateTime, reqVO.getCreateTimeStart(), reqVO.getCreateTimeEnd())
                .orderByDesc(RentalOrderDO::getId))
                .convert(this::toRespVO);
    }

    default RentalOrderRespVO toRespVO(RentalOrderDO order) {
        RentalOrderRespVO vo = new RentalOrderRespVO();
        vo.setId(order.getId());
        vo.setOrderNo(order.getOrderNo());
        vo.setUserId(order.getUserId());
        vo.setStoreId(order.getStoreId());
        vo.setVehicleId(order.getVehicleId());
        vo.setStatus(order.getStatus());
        vo.setTotalAmount(order.getTotalAmount());
        vo.setPayAmount(order.getPayAmount());
        vo.setStartTime(order.getStartTime());
        vo.setEndTime(order.getEndTime());
        vo.setActualStartTime(order.getActualStartTime());
        vo.setActualEndTime(order.getActualEndTime());
        vo.setPickupStoreId(order.getPickupStoreId());
        vo.setReturnStoreId(order.getReturnStoreId());
        vo.setChannelSource(order.getChannelSource());
        vo.setRemark(order.getRemark());
        vo.setCreateTime(order.getCreateTime());
        return vo;
    }
}
