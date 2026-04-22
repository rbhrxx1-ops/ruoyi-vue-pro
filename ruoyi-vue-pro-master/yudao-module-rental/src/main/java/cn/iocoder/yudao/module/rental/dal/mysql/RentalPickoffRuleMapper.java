package cn.iocoder.yudao.module.rental.dal.mysql;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.rental.dal.dataobject.RentalPickoffRuleDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 异地取还费率规则 Mapper
 */
@Mapper
public interface RentalPickoffRuleMapper extends BaseMapperX<RentalPickoffRuleDO> {

    default List<RentalPickoffRuleDO> selectListByPickupAndReturn(Long pickupStoreId, Long returnStoreId) {
        return selectList(new LambdaQueryWrapperX<RentalPickoffRuleDO>()
                .eqIfPresent(RentalPickoffRuleDO::getPickupStoreId, pickupStoreId)
                .eqIfPresent(RentalPickoffRuleDO::getReturnStoreId, returnStoreId)
                .eq(RentalPickoffRuleDO::getStatus, 1)
                .orderByDesc(RentalPickoffRuleDO::getId));
    }

}
