package com.upd.hwcloud.service.dm;

import com.baomidou.mybatisplus.extension.service.IService;
import com.upd.hwcloud.bean.entity.dm.CloudVmAddSub;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yyc
 * @since 2019-09-04
 */
public interface ICloudVmAddSubService extends IService<CloudVmAddSub> {

    boolean insertData(List<CloudVmAddSub> cloudVmAddSubList, int size);

}
