package me.arnu.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import me.arnu.common.common.BaseQuery;
import me.arnu.common.utils.JsonResult;
import me.arnu.system.entity.User;

import java.io.Serializable;
import java.util.Map;

/**
 * <p>
 * 后台用户管理表 服务类
 * </p>
 *
 * @author Arnu
 * @since 2021-01-28
 */
public interface IUserService extends IService<User> {

    /**
     * 根据查询条件获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    JsonResult getList(BaseQuery query);

    /**
     * 根据ID获取记录信息
     *
     * @param id 记录ID
     * @return
     */
    Map<String, Object> info(Integer id);

    /**
     * 根据ID获取记录信息
     *
     * @param id 记录ID
     * @return
     */

    Object getInfo(Serializable id);

    /**
     * 根据实体对象添加、编辑记录
     *
     * @param entity 实体对象
     * @return
     */
    JsonResult edit(User entity);

    /**
     * 根据ID删除记录
     *
     * @param id 记录ID
     * @return
     */
    JsonResult deleteById(Integer id);

    /**
     * 根据ID删除记录
     *
     * @param ids 记录ID
     * @return
     */
    JsonResult deleteByIds(String ids);

    /**
     * 设置状态
     *
     * @param entity 实体对象
     * @return
     */
    JsonResult setStatus(User entity);

    /**
     * 根据用户名获取人员
     *
     * @param username 用户名
     * @return
     */
    User getUserByUsername(String username);

}
