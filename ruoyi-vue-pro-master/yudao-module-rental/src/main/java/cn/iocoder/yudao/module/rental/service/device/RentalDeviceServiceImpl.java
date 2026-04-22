package cn.iocoder.yudao.module.rental.service.device;

import cn.iocoder.yudao.module.rental.dal.dataobject.RentalDeviceDO;
import cn.iocoder.yudao.module.rental.dal.mysql.RentalDeviceMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

/**
 * 附加设备 Service 实现类
 */
@Service
@Validated
@Slf4j
public class RentalDeviceServiceImpl implements RentalDeviceService {

    @Resource
    private RentalDeviceMapper deviceMapper;

    @Override
    public Long createDevice(RentalDeviceDO device) {
        deviceMapper.insert(device);
        return device.getId();
    }

    @Override
    public void updateDevice(RentalDeviceDO device) {
        deviceMapper.updateById(device);
    }

    @Override
    public void deleteDevice(Long id) {
        deviceMapper.deleteById(id);
    }

    @Override
    public RentalDeviceDO getDevice(Long id) {
        return deviceMapper.selectById(id);
    }

    @Override
    public List<RentalDeviceDO> getDeviceList() {
        return deviceMapper.selectList();
    }

    @Override
    public List<RentalDeviceDO> getDeviceListByFranchisee(Long franchiseeId) {
        return deviceMapper.selectListByFranchisee(franchiseeId);
    }

    @Override
    public void enableDevice(Long id) {
        RentalDeviceDO device = new RentalDeviceDO();
        device.setId(id);
        device.setStatus(1);
        deviceMapper.updateById(device);
    }

    @Override
    public void disableDevice(Long id) {
        RentalDeviceDO device = new RentalDeviceDO();
        device.setId(id);
        device.setStatus(0);
        deviceMapper.updateById(device);
    }

    @Override
    public void batchEnable(List<Long> ids) {
        ids.forEach(this::enableDevice);
    }

    @Override
    public void batchDisable(List<Long> ids) {
        ids.forEach(this::disableDevice);
    }

}
