package cn.iocoder.yudao.module.rental.dal.mysql;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.rental.dal.dataobject.RentalSelfServiceTaskDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 自助取还任务 Mapper
 */
@Mapper
public interface RentalSelfServiceTaskMapper extends BaseMapperX<RentalSelfServiceTaskDO> {

    default List<RentalSelfServiceTaskDO> selectListByOrder(Long orderId) {
        return selectList(new LambdaQueryWrapperX<RentalSelfServiceTaskDO>()
                .eqIfPresent(RentalSelfServiceTaskDO::getOrderId, orderId)
                .orderByDesc(RentalSelfServiceTaskDO::getId));
    }

    default List<RentalSelfServiceTaskDO> selectListByStatus(String taskStatus) {
        return selectList(new LambdaQueryWrapperX<RentalSelfServiceTaskDO>()
                .eqIfPresent(RentalSelfServiceTaskDO::getTaskStatus, taskStatus)
                .orderByDesc(RentalSelfServiceTaskDO::getId));
    }

}
