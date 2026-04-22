package cn.iocoder.yudao.module.rental.dal.mysql;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.rental.dal.dataobject.RentalInsuranceDO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 保险商品 Mapper
 */
@Mapper
public interface RentalInsuranceMapper extends BaseMapperX<RentalInsuranceDO> {

    default List<RentalInsuranceDO> selectListByFranchisee(Long franchiseeId) {
        return selectList(new LambdaQueryWrapperX<RentalInsuranceDO>()
                .eqIfPresent(RentalInsuranceDO::getFranchiseeId, franchiseeId)
                .eq(RentalInsuranceDO::getStatus, 1)
                .orderByDesc(RentalInsuranceDO::getId));
    }

}
