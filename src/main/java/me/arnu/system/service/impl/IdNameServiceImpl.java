
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
            String id = "";
            String name = "";
            if (map.containsKey("ID")) {
                id = map.get("ID").toString();
            } else if (map.containsKey("id")) {
                id = map.get("id").toString();
            } else if (map.containsKey("Id")) {
                id = map.get("Id").toString();
            } else {
                continue;
            }
            if (map.containsKey("name")) {
                name = map.get("name").toString();
            } else if (map.containsKey("NAME")) {
                name = map.get("NAME").toString();
            } else if (map.containsKey("Name")) {
                name = map.get("Name").toString();
            } else {
                continue;
            }
            list.add(new IdNameDto(id, name));
        }
        return list;
    }
}
