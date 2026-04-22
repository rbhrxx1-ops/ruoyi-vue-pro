package cn.iocoder.yudao.module.rental.service.selfservice;

import cn.iocoder.yudao.module.rental.dal.dataobject.RentalSelfServiceDeviceDO;
import cn.iocoder.yudao.module.rental.dal.dataobject.RentalSelfServiceRegionDO;
import cn.iocoder.yudao.module.rental.dal.dataobject.RentalSelfServiceTaskDO;
import cn.iocoder.yudao.module.rental.dal.mysql.RentalSelfServiceDeviceMapper;
import cn.iocoder.yudao.module.rental.dal.mysql.RentalSelfServiceTaskMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 自助取还 Service 实现类
 */
@Service
@Validated
@Slf4j
public class RentalSelfServiceServiceImpl implements RentalSelfServiceService {

    @Resource
    private RentalSelfServiceDeviceMapper deviceMapper;

    @Resource
    private RentalSelfServiceTaskMapper taskMapper;

    // ========== 设备管理 ==========

    @Override
    public Long createDevice(RentalSelfServiceDeviceDO device) {
        deviceMapper.insert(device);
        return device.getId();
    }

    @Override
    public void updateDevice(RentalSelfServiceDeviceDO device) {
        deviceMapper.updateById(device);
    }

    @Override
    public void deleteDevice(Long id) {
        deviceMapper.deleteById(id);
    }

    @Override
    public RentalSelfServiceDeviceDO getDevice(Long id) {
        return deviceMapper.selectById(id);
    }

    @Override
    public List<RentalSelfServiceDeviceDO> getDeviceList() {
        return deviceMapper.selectList();
    }

    @Override
    public List<RentalSelfServiceDeviceDO> getDeviceListByStore(Long storeId) {
        return deviceMapper.selectListByStore(storeId);
    }

    @Override
    public void enableDevice(Long id) {
        RentalSelfServiceDeviceDO d = new RentalSelfServiceDeviceDO();
        d.setId(id); d.setStatus(1);
        deviceMapper.updateById(d);
    }

    @Override
    public void disableDevice(Long id) {
        RentalSelfServiceDeviceDO d = new RentalSelfServiceDeviceDO();
        d.setId(id); d.setStatus(0);
        deviceMapper.updateById(d);
    }

    // ========== 服务区域管理（使用设备Mapper临时处理，后续补充独立Mapper）==========

    @Override
    public Long createRegion(RentalSelfServiceRegionDO region) {
        // TODO: 补充 RentalSelfServiceRegionMapper
        return null;
    }

    @Override
    public void updateRegion(RentalSelfServiceRegionDO region) {
        // TODO: 补充 RentalSelfServiceRegionMapper
    }

    @Override
    public void deleteRegion(Long id) {
        // TODO: 补充 RentalSelfServiceRegionMapper
    }

    @Override
    public RentalSelfServiceRegionDO getRegion(Long id) {
        // TODO: 补充 RentalSelfServiceRegionMapper
        return null;
    }

    @Override
    public List<RentalSelfServiceRegionDO> getRegionList() {
        // TODO: 补充 RentalSelfServiceRegionMapper
        return List.of();
    }

    @Override
    public List<RentalSelfServiceRegionDO> getRegionListByStore(Long storeId) {
        // TODO: 补充 RentalSelfServiceRegionMapper
        return List.of();
    }

    // ========== 自助取还任务 ==========

    @Override
    public Long createTask(RentalSelfServiceTaskDO task) {
        taskMapper.insert(task);
        return task.getId();
    }

    @Override
    public void updateTask(RentalSelfServiceTaskDO task) {
        taskMapper.updateById(task);
    }

    @Override
    public RentalSelfServiceTaskDO getTask(Long id) {
        return taskMapper.selectById(id);
    }

    @Override
    public List<RentalSelfServiceTaskDO> getTaskList() {
        return taskMapper.selectList();
    }

    @Override
    public List<RentalSelfServiceTaskDO> getTaskListByOrder(Long orderId) {
        return taskMapper.selectListByOrder(orderId);
    }

    @Override
    public void completeTask(Long id) {
        RentalSelfServiceTaskDO task = new RentalSelfServiceTaskDO();
        task.setId(id);
        task.setTaskStatus("completed");
        task.setActualTime(LocalDateTime.now());
        taskMapper.updateById(task);
    }

    @Override
    public void cancelTask(Long id) {
        RentalSelfServiceTaskDO task = new RentalSelfServiceTaskDO();
        task.setId(id);
        task.setTaskStatus("cancelled");
        taskMapper.updateById(task);
    }

}
