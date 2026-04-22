package cn.iocoder.yudao.module.rental.dal.mysql;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.rental.dal.dataobject.RentalDeviceDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 附加设备 Mapper
 */
@Mapper
public interface RentalDeviceMapper extends BaseMapperX<RentalDeviceDO> {

    default List<RentalDeviceDO> selectListByStatus(Integer status) {
        return selectList(new LambdaQueryWrapperX<RentalDeviceDO>()
                .eqIfPresent(RentalDeviceDO::getStatus, status)
                .orderByDesc(RentalDeviceDO::getId));
    }

    default List<RentalDeviceDO> selectListByFranchisee(Long franchiseeId) {
        return selectList(new LambdaQueryWrapperX<RentalDeviceDO>()
                .eqIfPresent(RentalDeviceDO::getFranchiseeId, franchiseeId)
                .orderByDesc(RentalDeviceDO::getId));
    }

}
