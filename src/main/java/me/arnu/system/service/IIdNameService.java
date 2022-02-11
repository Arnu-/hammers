
package me.arnu.system.service;

import me.arnu.system.dto.IdNameDto;

import java.util.List;

/**
 * <p>
 * 字典 服务类
 * </p>
 *
 * @author Arnu
 * @since 2020-04-20
 */
public interface IIdNameService {
    List<IdNameDto> GetList(String sql);
}