package com.upd.hwcloud.service.impl.webManage;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.upd.hwcloud.bean.contains.ReviewStatus;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.entity.webManage.Apply;
import com.upd.hwcloud.bean.entity.webManage.Case;
import com.upd.hwcloud.dao.webManage.ApplyMapper;
import com.upd.hwcloud.service.IOperateRecordService;
import com.upd.hwcloud.service.webManage.IApplyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 试点应用 服务实现类
 * </p>
 *
 * @author huru
 * @since 2018-10-26
 */
@Service
public class ApplyServiceImpl extends ServiceImpl<ApplyMapper, Apply> implements IApplyService {

    @Autowired
    private IOperateRecordService operateRecordService;
    @Autowired
    ApplyMapper applyMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void publish(User user, String id, Integer result, String remark) {
        Apply apply = this.getById(id);
        if (result.equals(1)) { // 上线
            apply.setStatus(ReviewStatus.ONLINE.getCode());
            apply.updateById();

            operateRecordService.insertRecord(id, user.getIdcard(), "上/下线", "上线", remark);
        } else { // 下线
            apply.setStatus(ReviewStatus.PRO_ONLINE.getCode());
            apply.updateById();

            operateRecordService.insertRecord(id, user.getIdcard(), "上/下线", "下线", remark);
        }
    }

    @Override
    public Page<Apply> getPage(Page<Apply> page, User user, Integer status, String name) {
        return applyMapper.getPage(page,user,status,name);
    }

    @Override
    public Page<Apply> getApplyByPage(Page<Apply> page) {
        return applyMapper.getApplyByPage(page);
    }
}
