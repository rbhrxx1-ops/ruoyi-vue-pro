package cn.iocoder.yudao.module.rental.dal.mysql;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.rental.dal.dataobject.RentalDepositPolicyDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 取用车政策 - 押金政策 Mapper
 */
@Mapper
public interface RentalDepositPolicyMapper extends BaseMapperX<RentalDepositPolicyDO> {

    default List<RentalDepositPolicyDO> selectListByFranchisee(Long franchiseeId) {
        return selectList(new LambdaQueryWrapperX<RentalDepositPolicyDO>()
                .eqIfPresent(RentalDepositPolicyDO::getFranchiseeId, franchiseeId)
                .orderByDesc(RentalDepositPolicyDO::getId));
    }

    default List<RentalDepositPolicyDO> selectListByStoreAndSipp(Long storeId, String sippCode) {
        return selectList(new LambdaQueryWrapperX<RentalDepositPolicyDO>()
                .eqIfPresent(RentalDepositPolicyDO::getStoreId, storeId)
                .eqIfPresent(RentalDepositPolicyDO::getSippCode, sippCode)
                .eq(RentalDepositPolicyDO::getStatus, 1)
                .orderByDesc(RentalDepositPolicyDO::getId));
    }

}
