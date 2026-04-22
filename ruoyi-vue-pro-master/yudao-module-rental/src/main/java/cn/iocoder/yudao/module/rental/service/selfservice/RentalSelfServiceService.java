package cn.iocoder.yudao.module.rental.service.selfservice;

import cn.iocoder.yudao.module.rental.dal.dataobject.RentalSelfServiceDeviceDO;
import cn.iocoder.yudao.module.rental.dal.dataobject.RentalSelfServiceRegionDO;
import cn.iocoder.yudao.module.rental.dal.dataobject.RentalSelfServiceTaskDO;

import java.util.List;

/**
 * 自助取还 Service 接口
 * 包含：设备管理 + 服务区域管理 + 自助取还任务
 */
public interface RentalSelfServiceService {

    // ========== 设备管理 ==========

    Long createDevice(RentalSelfServiceDeviceDO device);

    void updateDevice(RentalSelfServiceDeviceDO device);

    void deleteDevice(Long id);

    RentalSelfServiceDeviceDO getDevice(Long id);

    List<RentalSelfServiceDeviceDO> getDeviceList();

    List<RentalSelfServiceDeviceDO> getDeviceListByStore(Long storeId);

    void enableDevice(Long id);

    void disableDevice(Long id);

    // ========== 服务区域管理 ==========

    Long createRegion(RentalSelfServiceRegionDO region);

    void updateRegion(RentalSelfServiceRegionDO region);

    void deleteRegion(Long id);

    RentalSelfServiceRegionDO getRegion(Long id);

    List<RentalSelfServiceRegionDO> getRegionList();

    List<RentalSelfServiceRegionDO> getRegionListByStore(Long storeId);

    // ========== 自助取还任务 ==========

    Long createTask(RentalSelfServiceTaskDO task);

    void updateTask(RentalSelfServiceTaskDO task);

    RentalSelfServiceTaskDO getTask(Long id);

    List<RentalSelfServiceTaskDO> getTaskList();

    List<RentalSelfServiceTaskDO> getTaskListByOrder(Long orderId);

    void completeTask(Long id);

    void cancelTask(Long id);

}
