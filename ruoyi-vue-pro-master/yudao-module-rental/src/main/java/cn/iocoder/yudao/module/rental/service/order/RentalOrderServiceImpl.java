package cn.iocoder.yudao.module.rental.service.order;

import cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.rental.controller.admin.order.vo.*;
import cn.iocoder.yudao.module.rental.dal.dataobject.RentalOrderDO;
import cn.iocoder.yudao.module.rental.dal.mysql.RentalOrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import javax.annotation.Resource;
import static cn.iocoder.yudao.module.rental.enums.RentalErrorCodeConstants.*;

/**
 * 租车订单 Service 实现类
 */
@Service
@Validated
@Slf4j
public class RentalOrderServiceImpl implements RentalOrderService {

    @Resource
    private RentalOrderMapper rentalOrderMapper;

    @Override
    public PageResult<RentalOrderRespVO> getOrderPage(RentalOrderPageReqVO pageReqVO) {
        return rentalOrderMapper.selectPage(pageReqVO);
    }

    @Override
    public RentalOrderRespVO getOrder(Long id) {
        RentalOrderDO order = rentalOrderMapper.selectById(id);
        if (order == null) {
            throw ServiceExceptionUtil.exception(ORDER_NOT_EXISTS);
        }
        return convertToRespVO(order);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOrderStatus(RentalOrderUpdateStatusReqVO updateReqVO) {
        RentalOrderDO order = rentalOrderMapper.selectById(updateReqVO.getId());
        if (order == null) {
            throw ServiceExceptionUtil.exception(ORDER_NOT_EXISTS);
        }
        RentalOrderDO updateObj = new RentalOrderDO();
        updateObj.setId(updateReqVO.getId());
        updateObj.setStatus(updateReqVO.getStatus());
        updateObj.setRemark(updateReqVO.getRemark());
        rentalOrderMapper.updateById(updateObj);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteOrder(Long id) {
        RentalOrderDO order = rentalOrderMapper.selectById(id);
        if (order == null) {
            throw ServiceExceptionUtil.exception(ORDER_NOT_EXISTS);
        }
        rentalOrderMapper.deleteById(id);
    }

    @Override
    public RentalOrderDO getOrderDO(Long id) {
        return rentalOrderMapper.selectById(id);
    }

    private RentalOrderRespVO convertToRespVO(RentalOrderDO order) {
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
