package cn.iocoder.yudao.module.rental.dal.mysql;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.rental.dal.dataobject.RentalCancelPolicyDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 取消政策 Mapper
 */
@Mapper
public interface RentalCancelPolicyMapper extends BaseMapperX<RentalCancelPolicyDO> {

    default List<RentalCancelPolicyDO> selectListByFranchisee(Long franchiseeId) {
        return selectList(new LambdaQueryWrapperX<RentalCancelPolicyDO>()
                .eqIfPresent(RentalCancelPolicyDO::getFranchiseeId, franchiseeId)
                .orderByDesc(RentalCancelPolicyDO::getId));
    }

    default List<RentalCancelPolicyDO> selectListByStore(Long storeId) {
        return selectList(new LambdaQueryWrapperX<RentalCancelPolicyDO>()
                .eqIfPresent(RentalCancelPolicyDO::getStoreId, storeId)
                .eq(RentalCancelPolicyDO::getStatus, 1)
                .orderByDesc(RentalCancelPolicyDO::getId));
    }

}
