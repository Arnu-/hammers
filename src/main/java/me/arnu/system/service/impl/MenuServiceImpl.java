/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/


package me.arnu.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import me.arnu.common.common.BaseQuery;
import me.arnu.common.config.CommonConfig;
import me.arnu.common.utils.DateUtils;
import me.arnu.common.utils.JsonResult;
import me.arnu.common.utils.StringUtils;
import me.arnu.system.common.BaseServiceImpl;
import me.arnu.system.utils.ShiroUtils;
import me.arnu.system.constant.MenuConstant;
import me.arnu.system.entity.Menu;
import me.arnu.system.mapper.MenuMapper;
import me.arnu.system.query.MenuQuery;
import me.arnu.system.service.IMenuService;
import me.arnu.system.utils.UserUtils;
import me.arnu.system.vo.MenuListVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 菜单 服务实现类
 * </p>
 *
 * @author Arnu
 * @since 2020-05-07
 */
@Service
public class MenuServiceImpl extends BaseServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Autowired
    private MenuMapper menuMapper;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        MenuQuery menuQuery = (MenuQuery) query;
        // 查询条件
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        // 上级ID
        if (StringUtils.isNotNull(menuQuery.getPid()) && menuQuery.getPid() > 0) {
            queryWrapper.eq("pid", menuQuery.getPid());
        } else {
            queryWrapper.eq("pid", 0);
        }
        // 菜单名称
        if (!StringUtils.isEmpty(menuQuery.getName())) {
            queryWrapper.like("name", menuQuery.getName());
        }
        // 类型：1模块 2导航 3菜单 4节点
        if (menuQuery.getType() != null && menuQuery.getType() > 0) {
            queryWrapper.eq("type", menuQuery.getType());
        }
        // 是否显示：1显示 2不显示
        if (menuQuery.getStatus() != null && menuQuery.getStatus() > 0) {
            queryWrapper.eq("status", menuQuery.getStatus());
        }
        // 是否公共：1是 2否
        if (menuQuery.getIsPublic() != null && menuQuery.getIsPublic() > 0) {
            queryWrapper.eq("is_public", menuQuery.getIsPublic());
        }
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByAsc("sort");

        // 查询数据
        List<Menu> menuList = menuMapper.selectList(queryWrapper);
        List<MenuListVo> menuListVoList = new ArrayList<>();
        if (!menuList.isEmpty()) {
            menuList.forEach(item -> {
                MenuListVo menuListVo = new MenuListVo();
                // 拷贝属性
                BeanUtils.copyProperties(item, menuListVo);
                // 类型描述
                if (menuListVo.getType() != null && menuListVo.getType() > 0) {
                    menuListVo.setTypeName(MenuConstant.MENU_TYPE_LIST.get(menuListVo.getType()));
                }
                // 是否显示描述
                if (menuListVo.getStatus() != null && menuListVo.getStatus() > 0) {
                    menuListVo.setStatusName(MenuConstant.MENU_STATUS_LIST.get(menuListVo.getStatus()));
                }
                // 是否公共描述
                if (menuListVo.getIsPublic() != null && menuListVo.getIsPublic() > 0) {
                    menuListVo.setIsPublicName(MenuConstant.MENU_ISPUBLIC_LIST.get(menuListVo.getIsPublic()));
                }
                // 创建人名称
                if (menuListVo.getCreateUser() != null && menuListVo.getCreateUser() > 0) {
                    menuListVo.setCreateUserName(UserUtils.getName((menuListVo.getCreateUser())));
                }
                // 更新人名称
                if (menuListVo.getUpdateUser() != null && menuListVo.getUpdateUser() > 0) {
                    menuListVo.setUpdateUserName(UserUtils.getName((menuListVo.getUpdateUser())));
                }
                // 是否有子级
                if (item.getType() < 4) {
                    menuListVo.setHaveChild(true);
                }
                menuListVoList.add(menuListVo);
            });
        }
        return JsonResult.success("操作成功", menuListVoList);
    }

    /**
     * 获取记录详情
     *
     * @param id 记录ID
     * @return
     */
    @Override
    public Object getInfo(Serializable id) {
        Menu entity = (Menu) super.getInfo(id);
        // 获取权限节点ID
        List<String> stringList = new ArrayList<>();
        if (entity.getType().equals(3)) {
            List<Menu> menuList = menuMapper.selectList(new LambdaQueryWrapper<Menu>()
                    .eq(Menu::getPid, entity.getId())
                    .eq(Menu::getMark, 1));
            if (!menuList.isEmpty()) {
                menuList.forEach(item -> {
                    stringList.add(item.getSort().toString());
                });
            }
            // 字符串拼接
            entity.setFuncIds(StringUtils.join(stringList.toArray(), ","));
        }
        return entity;
    }

    /**
     * 添加或编辑记录
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult edit(Menu entity) {
        if (CommonConfig.appDebug) {
            return JsonResult.error("演示环境禁止操作");
        }
        if (entity == null) {
            return JsonResult.error("实体对象不存在");
        }
        boolean result = false;
        if (entity.getId() != null && entity.getId() > 0) {
            // 修改记录
            entity.setUpdateUser(ShiroUtils.getUserId());
            entity.setUpdateTime(DateUtils.now());
            result = this.updateById(entity);
        } else {
            // 新增记录
            entity.setCreateUser(ShiroUtils.getUserId());
            entity.setCreateTime(DateUtils.now());
            entity.setMark(1);
            result = this.save(entity);
        }
        if (!result) {
            return JsonResult.error();
        }

        // 菜单节点处理
        if (!StringUtils.isEmpty(entity.getUrl()) && !entity.getUrl().equals("#")) {
            String[] urlItem = entity.getUrl().split("/");
            // 模块名
            String module = urlItem[1];
            if (!StringUtils.isEmpty(entity.getFuncIds())) {
                String[] item = entity.getFuncIds().split(",");
                for (String s : item) {
                    Menu funcInfo = new Menu();
                    funcInfo.setPid(entity.getId());
                    funcInfo.setType(4);
                    funcInfo.setStatus(1);
                    funcInfo.setIsPublic(2);
                    funcInfo.setSort(Integer.valueOf(s));
                    if (s.equals("1")) {
                        // 列表
                        Menu menuInfo = menuMapper.selectOne(new LambdaQueryWrapper<Menu>()
                                .eq(Menu::getPid, entity.getId())
                                .eq(Menu::getType, 4)
                                .eq(Menu::getName, "列表")
                                .eq(Menu::getMark, 1)
                                .last("limit 1"));
                        if (menuInfo != null) {
                            funcInfo.setId(menuInfo.getId());
                            funcInfo.setUpdateUser(ShiroUtils.getUserId());
                            funcInfo.setUpdateTime(DateUtils.now());
                        }
                        funcInfo.setName("列表");
                        funcInfo.setUrl(String.format("/%s/list", module));
                        funcInfo.setPermission(String.format("sys:%s:list", module));
                    } else if (s.equals("5")) {
                        // 添加
                        Menu menuInfo = menuMapper.selectOne(new LambdaQueryWrapper<Menu>()
                                .eq(Menu::getPid, entity.getId())
                                .eq(Menu::getType, 4)
                                .eq(Menu::getName, "添加")
                                .eq(Menu::getMark, 1)
                                .last("limit 1"));
                        if (menuInfo != null) {
                            funcInfo.setId(menuInfo.getId());
                            funcInfo.setUpdateUser(ShiroUtils.getUserId());
                            funcInfo.setUpdateTime(DateUtils.now());
                        }
                        funcInfo.setName("添加");
                        funcInfo.setUrl(String.format("/%s/add", module));
                        funcInfo.setPermission(String.format("sys:%s:add", module));
                    } else if (s.equals("10")) {
                        // 修改
                        Menu menuInfo = menuMapper.selectOne(new LambdaQueryWrapper<Menu>()
                                .eq(Menu::getPid, entity.getId())
                                .eq(Menu::getType, 4)
                                .eq(Menu::getName, "修改")
                                .eq(Menu::getMark, 1)
                                .last("limit 1"));
                        if (menuInfo != null) {
                            funcInfo.setId(menuInfo.getId());
                            funcInfo.setUpdateUser(ShiroUtils.getUserId());
                            funcInfo.setUpdateTime(DateUtils.now());
                        }
                        funcInfo.setName("修改");
                        funcInfo.setUrl(String.format("/%s/update", module));
                        funcInfo.setPermission(String.format("sys:%s:update", module));
                    } else if (s.equals("15")) {
                        // 删除
                        Menu menuInfo = menuMapper.selectOne(new LambdaQueryWrapper<Menu>()
                                .eq(Menu::getPid, entity.getId())
                                .eq(Menu::getType, 4)
                                .eq(Menu::getName, "删除")
                                .eq(Menu::getMark, 1)
                                .last("limit 1"));
                        if (menuInfo != null) {
                            funcInfo.setId(menuInfo.getId());
                            funcInfo.setUpdateUser(ShiroUtils.getUserId());
                            funcInfo.setUpdateTime(DateUtils.now());
                        }
                        funcInfo.setName("删除");
                        funcInfo.setUrl(String.format("/%s/delete", module));
                        funcInfo.setPermission(String.format("sys:%s:delete", module));
                    } else if (s.equals("20")) {
                        // 状态
                        Menu menuInfo = menuMapper.selectOne(new LambdaQueryWrapper<Menu>()
                                .eq(Menu::getPid, entity.getId())
                                .eq(Menu::getType, 4)
                                .eq(Menu::getName, "状态")
                                .eq(Menu::getMark, 1)
                                .last("limit 1"));
                        if (menuInfo != null) {
                            funcInfo.setId(menuInfo.getId());
                            funcInfo.setUpdateUser(ShiroUtils.getUserId());
                            funcInfo.setUpdateTime(DateUtils.now());
                        }
                        funcInfo.setName("状态");
                        funcInfo.setUrl(String.format("/%s/setStatus", module));
                        funcInfo.setPermission(String.format("sys:%s:status", module));
                    } else if (s.equals("25")) {
                        // 批量删除
                        Menu menuInfo = menuMapper.selectOne(new LambdaQueryWrapper<Menu>()
                                .eq(Menu::getPid, entity.getId())
                                .eq(Menu::getType, 4)
                                .eq(Menu::getName, "批量删除")
                                .eq(Menu::getMark, 1)
                                .last("limit 1"));
                        if (menuInfo != null) {
                            funcInfo.setId(menuInfo.getId());
                            funcInfo.setUpdateUser(ShiroUtils.getUserId());
                            funcInfo.setUpdateTime(DateUtils.now());
                        }
                        funcInfo.setName("批量删除");
                        funcInfo.setUrl(String.format("/%s/batchDelete", module));
                        funcInfo.setPermission(String.format("sys:%s:dall", module));
                    } else if (s.equals("30")) {
                        // 全部展开
                        Menu menuInfo = menuMapper.selectOne(new LambdaQueryWrapper<Menu>()
                                .eq(Menu::getPid, entity.getId())
                                .eq(Menu::getType, 4)
                                .eq(Menu::getName, "全部展开")
                                .eq(Menu::getMark, 1)
                                .last("limit 1"));
                        if (menuInfo != null) {
                            funcInfo.setId(menuInfo.getId());
                            funcInfo.setUpdateUser(ShiroUtils.getUserId());
                            funcInfo.setUpdateTime(DateUtils.now());
                        }
                        funcInfo.setName("全部展开");
                        funcInfo.setUrl(String.format("/%s/expand", module));
                        funcInfo.setPermission(String.format("sys:%s:expand", module));
                    } else if (s.equals("35")) {
                        // 全部折叠
                        Menu menuInfo = menuMapper.selectOne(new LambdaQueryWrapper<Menu>()
                                .eq(Menu::getPid, entity.getId())
                                .eq(Menu::getType, 4)
                                .eq(Menu::getName, "全部折叠")
                                .eq(Menu::getMark, 1)
                                .last("limit 1"));
                        if (menuInfo != null) {
                            funcInfo.setId(menuInfo.getId());
                            funcInfo.setUpdateUser(ShiroUtils.getUserId());
                            funcInfo.setUpdateTime(DateUtils.now());
                        }
                        funcInfo.setName("全部折叠");
                        funcInfo.setUrl(String.format("/%s/collapse", module));
                        funcInfo.setPermission(String.format("sys:%s:collapse", module));
                    } else if (s.equals("40")) {
                        // 添加子级
                        Menu menuInfo = menuMapper.selectOne(new LambdaQueryWrapper<Menu>()
                                .eq(Menu::getPid, entity.getId())
                                .eq(Menu::getType, 4)
                                .eq(Menu::getName, "添加子级")
                                .eq(Menu::getMark, 1)
                                .last("limit 1"));
                        if (menuInfo != null) {
                            funcInfo.setId(menuInfo.getId());
                            funcInfo.setUpdateUser(ShiroUtils.getUserId());
                            funcInfo.setUpdateTime(DateUtils.now());
                        }
                        funcInfo.setName("添加子级");
                        funcInfo.setUrl(String.format("/%s/addz", module));
                        funcInfo.setPermission(String.format("sys:%s:addz", module));
                    } else if (s.equals("45")) {
                        // 导出数据
                        Menu menuInfo = menuMapper.selectOne(new LambdaQueryWrapper<Menu>()
                                .eq(Menu::getPid, entity.getId())
                                .eq(Menu::getType, 4)
                                .eq(Menu::getName, "导出数据")
                                .eq(Menu::getMark, 1)
                                .last("limit 1"));
                        if (menuInfo != null) {
                            funcInfo.setId(menuInfo.getId());
                            funcInfo.setUpdateUser(ShiroUtils.getUserId());
                            funcInfo.setUpdateTime(DateUtils.now());
                        }
                        funcInfo.setName("导出数据");
                        funcInfo.setUrl(String.format("/%s/export", module));
                        funcInfo.setPermission(String.format("sys:%s:export", module));
                    } else if (s.equals("50")) {
                        // 导入数据
                        Menu menuInfo = menuMapper.selectOne(new LambdaQueryWrapper<Menu>()
                                .eq(Menu::getPid, entity.getId())
                                .eq(Menu::getType, 4)
                                .eq(Menu::getName, "导入数据")
                                .eq(Menu::getMark, 1)
                                .last("limit 1"));
                        if (menuInfo != null) {
                            funcInfo.setId(menuInfo.getId());
                            funcInfo.setUpdateUser(ShiroUtils.getUserId());
                            funcInfo.setUpdateTime(DateUtils.now());
                        }
                        funcInfo.setName("导入数据");
                        funcInfo.setUrl(String.format("/%s/import", module));
                        funcInfo.setPermission(String.format("sys:%s:import", module));
                    }
                    if (StringUtils.isNull(funcInfo.getId())) {
                        // 创建
                        menuMapper.insert(funcInfo);
                    } else {
                        // 更新
                        menuMapper.updateById(funcInfo);
                    }
                }
            }
        }
        return JsonResult.success("操作成功");
    }

    /**
     * 删除记录
     *
     * @param id 记录ID
     * @return
     */
    @Override
    public JsonResult deleteById(Integer id) {
        if (id == null || id == 0) {
            return JsonResult.error("记录ID不能为空");
        }
        Menu entity = this.getById(id);
        if (entity == null) {
            return JsonResult.error("记录不存在");
        }
        return super.delete(entity);
    }

    /**
     * 设置状态
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult setStatus(Menu entity) {
        if (entity.getId() == null || entity.getId() <= 0) {
            return JsonResult.error("记录ID不能为空");
        }
        if (entity.getStatus() == null) {
            return JsonResult.error("记录状态不能为空");
        }
        return super.setStatus(entity);
    }

    /**
     * 获取导航菜单
     *
     * @param userId 人员ID
     * @return
     */
    @Override
    public List<MenuListVo> getNavbarMenu(Integer userId) {
        if (userId.equals(1)) {
            // 管理员(管理员拥有全部权限)
            List<MenuListVo> menuListVoList = getPermissionMenuAll(0);
            return menuListVoList;
        } else {
            // 非管理员
            List<MenuListVo> menuListVoList = getPermissionMenuList(0);
            return menuListVoList;
        }
    }

    /**
     * 遍历获取权限菜单
     *
     * @param pid 上级ID
     * @return
     */
    private List<MenuListVo> getPermissionMenuList(Integer pid) {
        List<MenuListVo> menuListVoList = menuMapper.getPermissionMenuList(ShiroUtils.getUserId(), pid);
        if (!menuListVoList.isEmpty()) {
            menuListVoList.forEach(item -> {
                List<MenuListVo> menuList = getPermissionMenuList(item.getId());
                if (!menuList.isEmpty()) {
                    item.setChildren(menuList);
                }
            });
        }
        return menuListVoList;
    }

    /**
     * 根据父级ID获取子级菜单
     *
     * @param pid 上级ID
     * @return
     */
    public List<MenuListVo> getPermissionMenuAll(Integer pid) {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pid", pid);
        queryWrapper.eq("status", 1);
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByAsc("sort");
        List<Menu> menuList = list(queryWrapper);
        List<MenuListVo> menuListVoList = new ArrayList<>();
        if (!menuList.isEmpty()) {
            menuList.forEach(item -> {
                MenuListVo menuListVo = new MenuListVo();
                // 拷贝属性
                BeanUtils.copyProperties(item, menuListVo);
                List<MenuListVo> childrenList = getPermissionMenuAll(item.getId());
                menuListVo.setChildren(childrenList);
                menuListVoList.add(menuListVo);
            });
        }
        return menuListVoList;
    }

    /**
     * 根据人员ID获取菜单权限列表
     *
     * @param userId 人员ID
     * @return
     */
    @Override
    public List<Menu> getMenuListByUserId(Integer userId) {
        return menuMapper.getMenuListByUserId(userId);
    }
}