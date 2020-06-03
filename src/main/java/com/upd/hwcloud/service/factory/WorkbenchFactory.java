package com.upd.hwcloud.service.factory;

import com.upd.hwcloud.common.exception.BaseException;
import com.upd.hwcloud.service.workbench.Workbench;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class WorkbenchFactory {
    private static final Logger logger = LoggerFactory.getLogger(WorkbenchFactory.class);
    @Autowired
    Map<String, Workbench> workbenchMap = new ConcurrentHashMap<>();

    public Workbench getWorkbench(String resourceType){
        if(StringUtils.isEmpty(resourceType)){
            throw  new BaseException("resourceType is empty");
        }
        resourceType = resourceType+"Handler";
        Workbench workbench =workbenchMap.get(resourceType);
        if(workbench == null){
            logger.error("resourceType:" + resourceType);
            throw  new RuntimeException("no such workbench");
        }
        return workbench;
    }
}
