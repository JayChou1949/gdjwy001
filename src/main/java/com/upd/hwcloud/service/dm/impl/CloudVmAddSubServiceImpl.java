package com.upd.hwcloud.service.dm.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.upd.hwcloud.bean.entity.dm.CloudVmAddSub;
import com.upd.hwcloud.dao.dm.CloudVmAddSubMapper;
import com.upd.hwcloud.service.dm.ICloudVmAddSubService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yyc
 * @since 2019-09-04
 */
@Service
public class CloudVmAddSubServiceImpl extends ServiceImpl<CloudVmAddSubMapper, CloudVmAddSub> implements ICloudVmAddSubService {


    @Override
    public boolean insertData(List<CloudVmAddSub> cloudVmAddSubList, int size) {
        return  this.saveBatch(cloudVmAddSubList,cloudVmAddSubList.size());
    }
}
