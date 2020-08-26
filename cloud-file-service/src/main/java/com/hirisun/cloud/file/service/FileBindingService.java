package com.hirisun.cloud.file.service;

import com.hirisun.cloud.file.bean.FileBinding;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 主表-文件系统绑定表 服务类
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-08-25
 */
public interface FileBindingService extends IService<FileBinding> {

    public void saveFileBinding(String masterId, String fileId);

    public void saveByIds(List<String> ids, String fileId);

}
