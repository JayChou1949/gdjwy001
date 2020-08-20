package com.hirisun.cloud.common.util;

import com.hirisun.cloud.model.workflow.WorkflowNodeVO;
import org.apache.commons.lang3.StringUtils;

/**
 * @author  wuxiaoxing -2020-08-19
 * 工作流工具类
 */
public class WorkflowUtil {


    /**
     * 检查环节是否有指定能力
     * @param nodeFeature
     * @param nodeAbilityType
     * @return
     */
    public static boolean compareNodeAbility(String nodeFeature,String nodeAbilityType) {
        if (StringUtils.isEmpty(nodeFeature)) {
            return false;
        }
        String[] curNodeAbilityArr = nodeFeature.split(",");
        for(int i=0;i<curNodeAbilityArr.length;i++){
            String curNodeAbility = curNodeAbilityArr[i];
            if (StringUtils.isNotEmpty(curNodeAbility) && curNodeAbility.equals(nodeAbilityType)) {
                return true;
            }
        }
        return false;
    }
}
