/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/


package me.arnu.admin.hammers.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.arnu.common.common.BaseQuery;
import me.arnu.system.common.BaseServiceImpl;
import me.arnu.common.config.CommonConfig;
import me.arnu.common.utils.CommonUtils;
import me.arnu.common.utils.JsonResult;
import me.arnu.common.utils.StringUtils;
import me.arnu.admin.hammers.constant.NatureYearAnnualVacationBalanceConstant;
import me.arnu.admin.hammers.entity.NatureYearAnnualVacationBalance;
import me.arnu.admin.hammers.mapper.NatureYearAnnualVacationBalanceMapper;
import me.arnu.admin.hammers.query.NatureYearAnnualVacationBalanceQuery;
import me.arnu.admin.hammers.service.INatureYearAnnualVacationBalanceService;
import me.arnu.system.utils.UserUtils;
import me.arnu.admin.hammers.vo.NatureYearAnnualVacationBalanceListVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 自然年结余天数 服务实现类
 * </p>
 *
 * @author Arnu
 * @since 2022-02-13
 */
@Service
public class NatureYearAnnualVacationBalanceServiceImpl extends BaseServiceImpl<NatureYearAnnualVacationBalanceMapper, NatureYearAnnualVacationBalance> implements INatureYearAnnualVacationBalanceService {

    @Autowired
    private NatureYearAnnualVacationBalanceMapper natureYearAnnualVacationBalanceMapper;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        NatureYearAnnualVacationBalanceQuery natureYearAnnualVacationBalanceQuery = (NatureYearAnnualVacationBalanceQuery) query;
        // 查询条件
        QueryWrapper<NatureYearAnnualVacationBalance> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByDesc("id");

        // 查询数据
        IPage<NatureYearAnnualVacationBalance> page = new Page<>(natureYearAnnualVacationBalanceQuery.getPage(), natureYearAnnualVacationBalanceQuery.getLimit());
        IPage<NatureYearAnnualVacationBalance> data = natureYearAnnualVacationBalanceMapper.selectPage(page, queryWrapper);
        List<NatureYearAnnualVacationBalance> natureYearAnnualVacationBalanceList = data.getRecords();
        List<NatureYearAnnualVacationBalanceListVo> natureYearAnnualVacationBalanceListVoList = new ArrayList<>();
        if (!natureYearAnnualVacationBalanceList.isEmpty()) {
            natureYearAnnualVacationBalanceList.forEach(item -> {
                NatureYearAnnualVacationBalanceListVo natureYearAnnualVacationBalanceListVo = new NatureYearAnnualVacationBalanceListVo();
                // 拷贝属性
                BeanUtils.copyProperties(item, natureYearAnnualVacationBalanceListVo);
                // 创建人名称
                if (natureYearAnnualVacationBalanceListVo.getCreateUser() != null && natureYearAnnualVacationBalanceListVo.getCreateUser() > 0) {
                    natureYearAnnualVacationBalanceListVo.setCreateUserName(UserUtils.getName((natureYearAnnualVacationBalanceListVo.getCreateUser())));
                }
                // 更新人名称
                if (natureYearAnnualVacationBalanceListVo.getUpdateUser() != null && natureYearAnnualVacationBalanceListVo.getUpdateUser() > 0) {
                    natureYearAnnualVacationBalanceListVo.setUpdateUserName(UserUtils.getName((natureYearAnnualVacationBalanceListVo.getUpdateUser())));
                }
                natureYearAnnualVacationBalanceListVoList.add(natureYearAnnualVacationBalanceListVo);
            });
        }
        return JsonResult.success("操作成功", natureYearAnnualVacationBalanceListVoList, data.getTotal());
    }

    /**
     * 获取记录详情
     *
     * @param id 记录ID
     * @return
     */
    @Override
    public Object getInfo(Serializable id) {
        NatureYearAnnualVacationBalance entity = (NatureYearAnnualVacationBalance) super.getInfo(id);
        return entity;
    }

    /**
     * 添加或编辑记录
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult edit(NatureYearAnnualVacationBalance entity) {
        return super.edit(entity);
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
        NatureYearAnnualVacationBalance entity = this.getById(id);
        if (entity == null) {
            return JsonResult.error("记录不存在");
        }
        return super.delete(entity);
    }

}