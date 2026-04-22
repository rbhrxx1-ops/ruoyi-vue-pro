package cn.iocoder.yudao.module.rental.service.order;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.rental.controller.admin.order.vo.*;
import cn.iocoder.yudao.module.rental.dal.dataobject.RentalOrderDO;
import javax.validation.Valid;

/**
 * 租车订单 Service 接口
 */
public interface RentalOrderService {

    /**
     * 获取租车订单分页
     */
    PageResult<RentalOrderRespVO> getOrderPage(@Valid RentalOrderPageReqVO pageReqVO);

    /**
     * 获取租车订单详情
     */
    RentalOrderRespVO getOrder(Long id);

    /**
     * 更新订单状态
     */
    void updateOrderStatus(@Valid RentalOrderUpdateStatusReqVO updateReqVO);

    /**
     * 删除租车订单
     */
    void deleteOrder(Long id);

    /**
     * 获取租车订单 DO
     */
    RentalOrderDO getOrderDO(Long id);
}
