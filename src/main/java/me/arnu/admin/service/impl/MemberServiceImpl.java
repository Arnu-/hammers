package me.arnu.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.arnu.admin.constant.MemberConstant;
import me.arnu.admin.entity.Member;
import me.arnu.admin.mapper.MemberMapper;
import me.arnu.admin.query.MemberQuery;
import me.arnu.admin.service.IMemberService;
import me.arnu.admin.vo.MemberListVo;
import me.arnu.common.common.BaseQuery;
import me.arnu.common.config.CommonConfig;
import me.arnu.common.config.UploadFileConfig;
import me.arnu.common.utils.CommonUtils;
import me.arnu.common.utils.JsonResult;
import me.arnu.common.utils.QRCodeUtil;
import me.arnu.common.utils.StringUtils;
import me.arnu.system.common.BaseServiceImpl;
import me.arnu.system.service.ICityService;
import me.arnu.system.utils.UserUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author Arnu
 * @since 2021-01-28
 */
@Service
public class MemberServiceImpl extends BaseServiceImpl<MemberMapper, Member> implements IMemberService {

    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private ICityService cityService;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        MemberQuery memberQuery = (MemberQuery) query;
        // 查询条件
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        // 手机号
        if (!StringUtils.isEmpty(memberQuery.getMobile())) {
            queryWrapper.like("mobile", memberQuery.getMobile());
        }
        // 设备类型：1苹果 2安卓 3WAP站 4PC站 5微信小程序 6后台添加
        if (memberQuery.getDevice() != null && memberQuery.getDevice() > 0) {
            queryWrapper.eq("device", memberQuery.getDevice());
        }

        // 用户来源：1注册会员 2马甲会员
        if (memberQuery.getSource() != null && memberQuery.getSource() > 0) {
            queryWrapper.eq("source", memberQuery.getSource());
        }
        // 是否启用：1启用  2停用
        if (memberQuery.getStatus() != null && memberQuery.getStatus() > 0) {
            queryWrapper.eq("status", memberQuery.getStatus());
        }
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByDesc("id");

        // 查询数据
        IPage<Member> page = new Page<>(memberQuery.getPage(), memberQuery.getLimit());
        IPage<Member> data = memberMapper.selectPage(page, queryWrapper);
        List<Member> memberList = data.getRecords();
        List<MemberListVo> memberListVoList = new ArrayList<>();
        if (!memberList.isEmpty()) {
            memberList.forEach(item -> {
                MemberListVo memberListVo = new MemberListVo();
                // 拷贝属性
                BeanUtils.copyProperties(item, memberListVo);
                // 性别描述
                if (memberListVo.getGender() != null && memberListVo.getGender() > 0) {
                    memberListVo.setGenderName(MemberConstant.MEMBER_GENDER_LIST.get(memberListVo.getGender()));
                }
                // 用户头像地址
                if (!StringUtils.isEmpty(memberListVo.getAvatar())) {
                    memberListVo.setAvatarUrl(CommonUtils.getImageURL(memberListVo.getAvatar()));
                }
                // 推广二维码地址
                if (!StringUtils.isEmpty(memberListVo.getQrcode())) {
                    memberListVo.setQrcodeUrl(CommonUtils.getImageURL(memberListVo.getQrcode()));
                }
                // 设备类型描述
                if (memberListVo.getDevice() != null && memberListVo.getDevice() > 0) {
                    memberListVo.setDeviceName(MemberConstant.MEMBER_DEVICE_LIST.get(memberListVo.getDevice()));
                }
                // 用户状态描述
                if (memberListVo.getLoginStatus() != null && memberListVo.getLoginStatus() > 0) {
                    memberListVo.setLoginStatusName(MemberConstant.MEMBER_LOGINSTATUS_LIST.get(memberListVo.getLoginStatus()));
                }
                // 用户来源描述
                if (memberListVo.getSource() != null && memberListVo.getSource() > 0) {
                    memberListVo.setSourceName(MemberConstant.MEMBER_SOURCE_LIST.get(memberListVo.getSource()));
                }
                // 是否启用描述
                if (memberListVo.getStatus() != null && memberListVo.getStatus() > 0) {
                    memberListVo.setStatusName(MemberConstant.MEMBER_STATUS_LIST.get(memberListVo.getStatus()));
                }
                // 添加人名称
                if (memberListVo.getCreateUser() != null && memberListVo.getCreateUser() > 0) {
                    memberListVo.setCreateUserName(UserUtils.getName((memberListVo.getCreateUser())));
                }
                // 修改人名称
                if (memberListVo.getUpdateUser() != null && memberListVo.getUpdateUser() > 0) {
                    memberListVo.setUpdateUserName(UserUtils.getName((memberListVo.getUpdateUser())));
                }
                memberListVoList.add(memberListVo);
            });
        }
        return JsonResult.success("操作成功", memberListVoList, data.getTotal());
    }

    /**
     * 获取记录详情
     *
     * @param id 记录ID
     * @return
     */
    @Override
    public Object getInfo(Serializable id) {
        Member entity = (Member) super.getInfo(id);
        // 用户头像解析
        if (!StringUtils.isEmpty(entity.getAvatar())) {
            entity.setAvatar(CommonUtils.getImageURL(entity.getAvatar()));
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
    public JsonResult edit(Member entity) {
        // 用户头像
        if (entity.getAvatar().contains(CommonConfig.imageURL)) {
            entity.setAvatar(entity.getAvatar().replaceAll(CommonConfig.imageURL, ""));
        }
        // 设置密码
        if (!StringUtils.isEmpty(entity.getPassword())) {
            entity.setPassword(CommonUtils.password(entity.getPassword()));
        }
        // 获取城市名称
        if (entity.getDistrictId() != null && entity.getDistrictId() > 0) {
            String cityArea = cityService.getCityNameByCityId(entity.getDistrictId(), " ");
            entity.setCityArea(cityArea);
        }
        // 马甲会员
        entity.setSource(2);
        // 设备类型
        entity.setDevice(6);
        if (entity.getId() == null || entity.getId() == 0 || StringUtils.isEmpty(entity.getCode())) {

            // 推广码
            String code = CommonUtils.getRandomStr(false, 20);
            entity.setCode(code);
            // 生成二维码
            try {
                // 创建二级目录(格式：年月日)
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                String ymd = sdf.format(new Date());
                String uploadPath = UploadFileConfig.uploadFolder;
                String filePath = "images/qrcode/" + ymd + "/";
                File dirFile = new File(uploadPath + filePath);
                if (!dirFile.exists()) {
                    dirFile.mkdirs();
                }
                // 图片文件路径
                String imagePath = String.format("%s%s.jpg", ymd, CommonUtils.getRandomStr(true, 8));
                String destImagePath = uploadPath + filePath + imagePath;
                QRCodeUtil.encodeQrCode(code, 500, 500, destImagePath);
                entity.setQrcode(filePath + imagePath);
            } catch (Exception e) {

            }
        }
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
        Member entity = this.getById(id);
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
    public JsonResult setStatus(Member entity) {
        if (entity.getId() == null || entity.getId() <= 0) {
            return JsonResult.error("记录ID不能为空");
        }
        if (entity.getStatus() == null) {
            return JsonResult.error("记录状态不能为空");
        }
        return super.setStatus(entity);
    }

}
