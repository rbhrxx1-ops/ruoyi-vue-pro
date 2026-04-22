package cn.iocoder.yudao.module.rental.service.franchisee;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.rental.controller.admin.franchisee.vo.*;
import javax.validation.Valid;
import java.util.List;

/**
 * 加盟商 Service 接口
 */
public interface RentalFranchiseeService {

    Long createFranchisee(@Valid RentalFranchiseeSaveReqVO createReqVO);

    void updateFranchisee(@Valid RentalFranchiseeSaveReqVO updateReqVO);

    void deleteFranchisee(Long id);

    RentalFranchiseeRespVO getFranchisee(Long id);

    PageResult<RentalFranchiseeRespVO> getFranchiseePage(@Valid RentalFranchiseePageReqVO pageReqVO);

    List<RentalFranchiseeRespVO> getFranchiseeList();
}
