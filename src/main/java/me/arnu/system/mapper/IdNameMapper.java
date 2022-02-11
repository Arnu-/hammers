
package me.arnu.system.mapper;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * <p>
 * 用于下拉选框的IdName映射 Mapper 接口
 * </p>
 *
 * @author Arnu
 * @since 2020-04-20
 */
public interface IdNameMapper {
    List<LinkedHashMap> selectAny(String sql);
}
