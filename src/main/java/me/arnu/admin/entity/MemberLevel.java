package me.arnu.admin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import me.arnu.common.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 会员级别表
 * </p>
 *
 * @author Arnu
 * @since 2021-01-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ums_member_level")
public class MemberLevel extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 级别名称
     */
    private String name;

    /**
     * 显示顺序
     */
    private Integer sort;


}
