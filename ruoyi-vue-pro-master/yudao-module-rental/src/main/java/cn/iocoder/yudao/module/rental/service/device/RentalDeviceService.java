package cn.iocoder.yudao.module.rental.service.device;

import cn.iocoder.yudao.module.rental.dal.dataobject.RentalDeviceDO;

import java.util.List;

/**
 * 附加设备 Service 接口
 */
public interface RentalDeviceService {

    Long createDevice(RentalDeviceDO device);

    void updateDevice(RentalDeviceDO device);

    void deleteDevice(Long id);

    RentalDeviceDO getDevice(Long id);

    List<RentalDeviceDO> getDeviceList();

    List<RentalDeviceDO> getDeviceListByFranchisee(Long franchiseeId);

    void enableDevice(Long id);

    void disableDevice(Long id);

    void batchEnable(List<Long> ids);

    void batchDisable(List<Long> ids);

}
