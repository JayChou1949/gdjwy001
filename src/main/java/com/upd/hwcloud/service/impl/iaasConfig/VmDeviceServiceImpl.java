package com.upd.hwcloud.service.impl.iaasConfig;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.upd.hwcloud.bean.entity.iaasConfig.VmDevice;
import com.upd.hwcloud.dao.iaasConfig.VmDeviceMapper;
import com.upd.hwcloud.service.iaasConfig.IVmDeviceService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 虚拟设备 服务实现类
 * </p>
 *
 * @author huru
 * @since 2018-11-17
 */
@Service
public class VmDeviceServiceImpl extends ServiceImpl<VmDeviceMapper, VmDevice> implements IVmDeviceService {

}
