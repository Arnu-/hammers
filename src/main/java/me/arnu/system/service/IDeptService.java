package me.arnu.system.service;

import me.arnu.common.common.IBaseService;
import me.arnu.system.entity.Dept;

/**
 * <p>
 * 部门表 服务类
 * </p>
 *
 * @author Arnu
 * @since 2021-01-28
 */
public interface IDeptService extends IBaseService<Dept> {

    /**
     * 根据部门ID获取部门名称
     *
     * @param deptId     部门ID
     * @param delimiter 拼接字符
     * @return
     */
    String getDeptNameById(Integer deptId, String delimiter);

}
