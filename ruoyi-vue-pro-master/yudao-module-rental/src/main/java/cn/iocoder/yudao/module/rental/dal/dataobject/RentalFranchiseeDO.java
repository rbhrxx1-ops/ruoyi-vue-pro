package cn.iocoder.yudao.module.rental.dal.dataobject;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 加盟商 DO
 */
@TableName("rental_franchisee")
@KeySequence("rental_franchisee_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RentalFranchiseeDO extends BaseDO {

    @TableId
    private Long id;
    /** 加盟商名称 */
    private String name;
    /** 联系人姓名 */
    private String contactName;
    /** 联系人电话 */
    private String contactPhone;
    /** 状态（0-停用, 1-启用） */
    private Integer status;
    /** 备注 */
    private String remark;
}
