package cn.iocoder.yudao.module.rental.dal.mysql;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.rental.dal.dataobject.RentalReservePolicyDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 预定政策 Mapper
 */
@Mapper
public interface RentalReservePolicyMapper extends BaseMapperX<RentalReservePolicyDO> {

    default List<RentalReservePolicyDO> selectListByFranchisee(Long franchiseeId) {
        return selectList(new LambdaQueryWrapperX<RentalReservePolicyDO>()
                .eqIfPresent(RentalReservePolicyDO::getFranchiseeId, franchiseeId)
                .orderByDesc(RentalReservePolicyDO::getId));
    }

    default List<RentalReservePolicyDO> selectListByStore(Long storeId) {
        return selectList(new LambdaQueryWrapperX<RentalReservePolicyDO>()
                .eqIfPresent(RentalReservePolicyDO::getStoreId, storeId)
                .eq(RentalReservePolicyDO::getStatus, 1)
                .orderByDesc(RentalReservePolicyDO::getId));
    }

}
