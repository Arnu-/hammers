package me.arnu.system.service;

import me.arnu.system.entity.UserRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 人员角色表 服务类
 * </p>
 *
 * @author Arnu
 * @since 2021-01-28
 */
public interface IUserRoleService extends IService<UserRole> {


    List<String> getRoleIdsByUserId(Integer userId);

}
