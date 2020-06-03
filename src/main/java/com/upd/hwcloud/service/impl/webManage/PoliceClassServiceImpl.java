package com.upd.hwcloud.service.impl.webManage;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.upd.hwcloud.bean.contains.ReviewStatus;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.entity.webManage.Apply;
import com.upd.hwcloud.bean.entity.webManage.PoliceClass;
import com.upd.hwcloud.dao.webManage.ApplyMapper;
import com.upd.hwcloud.dao.webManage.PoliceClassMapper;
import com.upd.hwcloud.service.IOperateRecordService;
import com.upd.hwcloud.service.webManage.IApplyService;
import com.upd.hwcloud.service.webManage.IPoliceClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 警种上云 服务实现类
 * </p>
 *
 * @author huru
 * @since 2018-10-26
 */
@Service
public class PoliceClassServiceImpl extends ServiceImpl<PoliceClassMapper,PoliceClass> implements IPoliceClassService {

    @Autowired
    private IOperateRecordService operateRecordService;
    @Autowired
    PoliceClassMapper policeClassMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void publish(User user, String id, Integer result, String remark) {
        PoliceClass policeClass = this.getById(id);
        if (result.equals(1)) { // 上线
            policeClass.setStatus(ReviewStatus.ONLINE.getCode());
            policeClass.updateById();

            operateRecordService.insertRecord(id, user.getIdcard(), "上/下线", "上线", remark);
        } else { // 下线
            policeClass.setStatus(ReviewStatus.PRO_ONLINE.getCode());
            policeClass.updateById();

            operateRecordService.insertRecord(id, user.getIdcard(), "上/下线", "下线", remark);
        }
    }

    @Override
    public Page<PoliceClass> getPage(Page<PoliceClass> page, User user, Integer status, String name) {
        return policeClassMapper.getPage(page,user,status,name);
    }

    @Override
    public Page<PoliceClass> getPoliceClassByPage(Page<PoliceClass> page) {
        return policeClassMapper.getPoliceClassByPage(page);
    }
}
