package com.upd.hwcloud.service.impl.webManage;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.upd.hwcloud.bean.contains.ReviewStatus;
import com.upd.hwcloud.bean.entity.Saas;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.entity.webManage.Case;
import com.upd.hwcloud.dao.webManage.CaseMapper;
import com.upd.hwcloud.service.IOperateRecordService;
import com.upd.hwcloud.service.webManage.ICaseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 典型案例 服务实现类
 * </p>
 *
 * @author huru
 * @since 2018-10-25
 */
@Service
public class CaseServiceImpl extends ServiceImpl<CaseMapper, Case> implements ICaseService {

    @Autowired
    private IOperateRecordService operateRecordService;
    @Autowired
    private CaseMapper caseMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void publish(User user, String id, Integer result, String remark) {
        Case c = this.getById(id);
        if (result.equals(1)) { // 上线
            c.setStatus(ReviewStatus.ONLINE.getCode());
            this.updateById(c);

            operateRecordService.insertRecord(id, user.getIdcard(), "上/下线", "上线", remark);
        } else { // 下线
            c.setStatus(ReviewStatus.PRO_ONLINE.getCode());
            this.updateById(c);

            operateRecordService.insertRecord(id, user.getIdcard(), "上/下线", "下线", remark);
        }
    }

    @Override
    public Page<Case> getPage(Page<Case> page,User user,Integer status, String name) {
        return caseMapper.getPage(page,user,status,name);
    }

    @Override
    public Page<Case> getCaseByPage(Page<Case> page) {
        return caseMapper.getCaseByPage(page);
    }
}
