package cn.iocoder.yudao.module.rental.service.inventory;

import cn.iocoder.yudao.module.rental.dal.dataobject.RentalInventoryDO;

import java.util.List;

/**
 * 车型库存 Service 接口
 */
public interface RentalInventoryService {

    Long createInventory(RentalInventoryDO inventory);

    void updateInventory(RentalInventoryDO inventory);

    void deleteInventory(Long id);

    RentalInventoryDO getInventory(Long id);

    List<RentalInventoryDO> getInventoryList();

    List<RentalInventoryDO> getInventoryListByStore(Long storeId);

    /**
     * 调整库存（增减可用库存）
     * @param id 库存ID
     * @param delta 变化量（正数增加，负数减少）
     */
    void adjustAvailableStock(Long id, int delta);

}
