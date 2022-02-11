
package me.arnu.system.service.impl;

import me.arnu.system.dto.IdNameDto;
import me.arnu.system.mapper.IdNameMapper;
import me.arnu.system.service.IIdNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * <p>
 * 字典 服务实现类
 * </p>
 *
 * @author Arnu
 * @since 2020-04-20
 */
@Service
public class IdNameServiceImpl implements IIdNameService {
    @Autowired
    private IdNameMapper mapper;

    @Override
    public List<IdNameDto> GetList(String sql) {
        List<LinkedHashMap> result = mapper.selectAny(sql);
        List<IdNameDto> list = new ArrayList<>();
        for (LinkedHashMap map : result) {
            list.add(new IdNameDto(map.get("id").toString(), map.get("name").toString()));
        }
        return list;
    }
}
