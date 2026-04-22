package cn.iocoder.yudao.module.rental.dal.mysql;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.rental.dal.dataobject.RentalInventoryDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 车型库存 Mapper
 */
@Mapper
public interface RentalInventoryMapper extends BaseMapperX<RentalInventoryDO> {

    default List<RentalInventoryDO> selectListByStore(Long storeId) {
        return selectList(new LambdaQueryWrapperX<RentalInventoryDO>()
                .eqIfPresent(RentalInventoryDO::getStoreId, storeId)
                .orderByDesc(RentalInventoryDO::getId));
    }

    default List<RentalInventoryDO> selectListByStoreAndSipp(Long storeId, String sippCode) {
        return selectList(new LambdaQueryWrapperX<RentalInventoryDO>()
                .eqIfPresent(RentalInventoryDO::getStoreId, storeId)
                .eqIfPresent(RentalInventoryDO::getSippCode, sippCode)
                .orderByDesc(RentalInventoryDO::getId));
    }

}
