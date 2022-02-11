/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/


package me.arnu.system.service;

import me.arnu.system.entity.ItemCate;
import me.arnu.common.common.IBaseService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 栏目 服务类
 * </p>
 *
 * @author Arnu
 * @since 2020-05-03
 */
public interface IItemCateService extends IBaseService<ItemCate> {

    /**
     * 根据栏目ID获取栏目名称
     * @param cateId 栏目ID
     * @param delimiter 拼接字符
     * @return
     */
    String getCateNameByCateId(Integer cateId, String delimiter);

    /**
     * 根据站点ID获取栏目
     *
     * @param itemId   站点ID
     * @param pid 栏目ID
     * @return
     */
    List<Map<String, Object>> getItemCateListByItemId(Integer itemId, Integer pid);

}