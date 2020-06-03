package com.upd.hwcloud.service.impl.iaasConfig;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.upd.hwcloud.bean.entity.iaasConfig.PhyDevice;
import com.upd.hwcloud.dao.iaasConfig.PhyDeviceMapper;
import com.upd.hwcloud.service.iaasConfig.IPhyDeviceService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 物理设备 服务实现类
 * </p>
 *
 * @author huru
 * @since 2018-11-17
 */
@Service
public class PhyDeviceServiceImpl extends ServiceImpl<PhyDeviceMapper, PhyDevice> implements IPhyDeviceService {

}
