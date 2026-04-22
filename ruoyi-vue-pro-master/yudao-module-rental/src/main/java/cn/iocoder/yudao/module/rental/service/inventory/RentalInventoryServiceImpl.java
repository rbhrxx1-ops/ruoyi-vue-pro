package cn.iocoder.yudao.module.rental.service.inventory;

import cn.iocoder.yudao.module.rental.dal.dataobject.RentalInventoryDO;
import cn.iocoder.yudao.module.rental.dal.mysql.RentalInventoryMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

/**
 * 车型库存 Service 实现类
 */
@Service
@Validated
@Slf4j
public class RentalInventoryServiceImpl implements RentalInventoryService {

    @Resource
    private RentalInventoryMapper inventoryMapper;

    @Override
    public Long createInventory(RentalInventoryDO inventory) {
        inventoryMapper.insert(inventory);
        return inventory.getId();
    }

    @Override
    public void updateInventory(RentalInventoryDO inventory) {
        inventoryMapper.updateById(inventory);
    }

    @Override
    public void deleteInventory(Long id) {
        inventoryMapper.deleteById(id);
    }

    @Override
    public RentalInventoryDO getInventory(Long id) {
        return inventoryMapper.selectById(id);
    }

    @Override
    public List<RentalInventoryDO> getInventoryList() {
        return inventoryMapper.selectList();
    }

    @Override
    public List<RentalInventoryDO> getInventoryListByStore(Long storeId) {
        return inventoryMapper.selectListByStore(storeId);
    }

    @Override
    public void adjustAvailableStock(Long id, int delta) {
        RentalInventoryDO inventory = inventoryMapper.selectById(id);
        if (inventory == null) {
            throw new IllegalArgumentException("库存记录不存在，ID: " + id);
        }
        int newStock = inventory.getAvailableStock() + delta;
        if (newStock < 0) {
            throw new IllegalStateException("可用库存不足，当前可用: " + inventory.getAvailableStock());
        }
        inventory.setAvailableStock(newStock);
        inventoryMapper.updateById(inventory);
    }

}
