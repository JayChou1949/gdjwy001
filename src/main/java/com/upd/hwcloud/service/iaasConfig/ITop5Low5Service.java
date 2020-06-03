package com.upd.hwcloud.service.iaasConfig;


import com.baomidou.mybatisplus.extension.service.IService;
import com.upd.hwcloud.bean.entity.iaasConfig.Top5Low5;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


/**
 * <p>
 * 全网 警种 地区 top5 low5 服务类
 * </p>
 *
 * @author huru
 * @since 2018-11-17
 */
public interface ITop5Low5Service extends IService<Top5Low5> {

    List<Top5Low5> getByType(String type, String column);
}
