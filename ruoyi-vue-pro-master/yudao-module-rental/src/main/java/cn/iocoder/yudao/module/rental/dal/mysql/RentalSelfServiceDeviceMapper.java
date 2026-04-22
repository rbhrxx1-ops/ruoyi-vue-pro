package cn.iocoder.yudao.module.rental.dal.mysql;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.rental.dal.dataobject.RentalSelfServiceDeviceDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 自助取还设备 Mapper
 */
@Mapper
public interface RentalSelfServiceDeviceMapper extends BaseMapperX<RentalSelfServiceDeviceDO> {

    default List<RentalSelfServiceDeviceDO> selectListByStore(Long storeId) {
        return selectList(new LambdaQueryWrapperX<RentalSelfServiceDeviceDO>()
                .eqIfPresent(RentalSelfServiceDeviceDO::getStoreId, storeId)
                .orderByDesc(RentalSelfServiceDeviceDO::getId));
    }

    default List<RentalSelfServiceDeviceDO> selectListByStatus(String deviceStatus) {
        return selectList(new LambdaQueryWrapperX<RentalSelfServiceDeviceDO>()
                .eqIfPresent(RentalSelfServiceDeviceDO::getDeviceStatus, deviceStatus)
                .orderByDesc(RentalSelfServiceDeviceDO::getId));
    }

}
