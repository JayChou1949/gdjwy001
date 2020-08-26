package com.hirisun.cloud.file.service.impl;

import com.hirisun.cloud.file.bean.FileBinding;
import com.hirisun.cloud.file.mapper.FileBindingMapper;
import com.hirisun.cloud.file.service.FileBindingService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 主表-文件系统绑定表 服务实现类
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-08-25
 */
@Service
public class FileBindingServiceImpl extends ServiceImpl<FileBindingMapper, FileBinding> implements FileBindingService {

    @Override
    public void saveFileBinding(String masterId,String fileId) {
        FileBinding fileBinding = new FileBinding();
        fileBinding.setMasterId(masterId);
        fileBinding.setFileId(fileId);
        this.save(fileBinding);
    }

    @Override
    public void saveByIds(List<String> ids, String fileId) {
        List<FileBinding> list = new ArrayList<>();
        ids.forEach(item->{
            FileBinding fileBinding = new FileBinding();
            fileBinding.setMasterId(item);
            fileBinding.setFileId(fileId);
            list.add(fileBinding);
        });
        this.saveBatch(list);
    }

}
