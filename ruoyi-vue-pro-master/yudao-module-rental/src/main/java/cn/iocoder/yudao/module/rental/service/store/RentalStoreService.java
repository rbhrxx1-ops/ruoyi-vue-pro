package cn.iocoder.yudao.module.rental.service.store;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.rental.controller.admin.store.vo.*;
import javax.validation.Valid;
import java.util.List;

/**
 * 门店 Service 接口
 */
public interface RentalStoreService {

    Long createStore(@Valid RentalStoreSaveReqVO createReqVO);

    void updateStore(@Valid RentalStoreSaveReqVO updateReqVO);

    void deleteStore(Long id);

    RentalStoreRespVO getStore(Long id);

    PageResult<RentalStoreRespVO> getStorePage(@Valid RentalStorePageReqVO pageReqVO);

    List<RentalStoreRespVO> getStoreList();
}
