package cn.iocoder.yudao.module.rental.dal.mysql;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.rental.dal.dataobject.RentalCarRentRuleDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 车辆租金费率规则 Mapper
 */
@Mapper
public interface RentalCarRentRuleMapper extends BaseMapperX<RentalCarRentRuleDO> {

    default List<RentalCarRentRuleDO> selectListByStore(Long storeId) {
        return selectList(new LambdaQueryWrapperX<RentalCarRentRuleDO>()
                .eqIfPresent(RentalCarRentRuleDO::getPickupStoreId, storeId)
                .orderByDesc(RentalCarRentRuleDO::getId));
    }

    default List<RentalCarRentRuleDO> selectListByFranchiseeAndSipp(Long franchiseeId, String sippCode) {
        return selectList(new LambdaQueryWrapperX<RentalCarRentRuleDO>()
                .eqIfPresent(RentalCarRentRuleDO::getFranchiseeId, franchiseeId)
                .eqIfPresent(RentalCarRentRuleDO::getSippCode, sippCode)
                .eq(RentalCarRentRuleDO::getStatus, 1)
                .orderByDesc(RentalCarRentRuleDO::getId));
    }

}
