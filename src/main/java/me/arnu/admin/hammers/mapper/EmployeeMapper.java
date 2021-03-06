/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/


package me.arnu.admin.hammers.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import me.arnu.admin.hammers.entity.Employee;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import me.arnu.admin.hammers.query.EmployeeQuery;
import me.arnu.admin.hammers.vo.EmployeeListVo;

/**
 * <p>
 * 员工 Mapper 接口
 * </p>
 *
 * @author Arnu
 * @since 2022-02-12
 */
public interface EmployeeMapper extends BaseMapper<Employee> {

    IPage<EmployeeListVo> selectVoPage(IPage<Employee> page, EmployeeQuery query);
}
