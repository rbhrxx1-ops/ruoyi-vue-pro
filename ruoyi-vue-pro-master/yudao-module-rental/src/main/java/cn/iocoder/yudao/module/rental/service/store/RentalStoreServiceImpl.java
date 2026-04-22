package cn.iocoder.yudao.module.rental.service.store;

import cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.rental.controller.admin.store.vo.*;
import cn.iocoder.yudao.module.rental.dal.dataobject.RentalStoreDO;
import cn.iocoder.yudao.module.rental.dal.mysql.RentalStoreMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;
import static cn.iocoder.yudao.module.rental.enums.RentalErrorCodeConstants.STORE_NOT_EXISTS;

@Service
@Validated
@Slf4j
public class RentalStoreServiceImpl implements RentalStoreService {

    @Resource
    private RentalStoreMapper rentalStoreMapper;

    @Override
    public Long createStore(RentalStoreSaveReqVO createReqVO) {
        RentalStoreDO store = toStoreDO(createReqVO);
        if (store.getStatus() == null) store.setStatus(1);
        rentalStoreMapper.insert(store);
        return store.getId();
    }

    @Override
    public void updateStore(RentalStoreSaveReqVO updateReqVO) {
        validateStoreExists(updateReqVO.getId());
        RentalStoreDO updateObj = toStoreDO(updateReqVO);
        rentalStoreMapper.updateById(updateObj);
    }

    @Override
    public void deleteStore(Long id) {
        validateStoreExists(id);
        rentalStoreMapper.deleteById(id);
    }

    @Override
    public RentalStoreRespVO getStore(Long id) {
        RentalStoreDO store = validateStoreExists(id);
        return toRespVO(store);
    }

    @Override
    public PageResult<RentalStoreRespVO> getStorePage(RentalStorePageReqVO pageReqVO) {
        return rentalStoreMapper.selectPage(pageReqVO);
    }

    @Override
    public List<RentalStoreRespVO> getStoreList() {
        return rentalStoreMapper.selectList(
                new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<RentalStoreDO>()
                        .eq(RentalStoreDO::getStatus, 1)
                        .orderByAsc(RentalStoreDO::getId)
        ).stream().map(this::toRespVO).collect(Collectors.toList());
    }

    private RentalStoreDO validateStoreExists(Long id) {
        RentalStoreDO store = rentalStoreMapper.selectById(id);
        if (store == null) {
            throw ServiceExceptionUtil.exception(STORE_NOT_EXISTS);
        }
        return store;
    }

    private RentalStoreDO toStoreDO(RentalStoreSaveReqVO vo) {
        RentalStoreDO store = new RentalStoreDO();
        store.setId(vo.getId());
        store.setName(vo.getName());
        store.setFranchiseeId(vo.getFranchiseeId());
        store.setCityId(vo.getCityId());
        store.setAddress(vo.getAddress());
        store.setLongitude(vo.getLongitude());
        store.setLatitude(vo.getLatitude());
        store.setContactPhone(vo.getContactPhone());
        store.setBusinessHours(vo.getBusinessHours());
        store.setStatus(vo.getStatus());
        return store;
    }

    private RentalStoreRespVO toRespVO(RentalStoreDO s) {
        RentalStoreRespVO vo = new RentalStoreRespVO();
        vo.setId(s.getId());
        vo.setName(s.getName());
        vo.setFranchiseeId(s.getFranchiseeId());
        vo.setCityId(s.getCityId());
        vo.setAddress(s.getAddress());
        vo.setLongitude(s.getLongitude());
        vo.setLatitude(s.getLatitude());
        vo.setContactPhone(s.getContactPhone());
        vo.setBusinessHours(s.getBusinessHours());
        vo.setStatus(s.getStatus());
        vo.setCreateTime(s.getCreateTime());
        return vo;
    }
}
