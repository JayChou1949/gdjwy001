package com.hirisun.cloud.system.timer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hirisun.cloud.api.system.SmsApi;
import com.hirisun.cloud.api.user.UserApi;
import com.hirisun.cloud.common.constant.BusinessName;
import com.hirisun.cloud.common.contains.ApplicationInfoStatus;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.system.bean.ResourceRecoverAppInfo;
import com.hirisun.cloud.system.service.IResourceRecoverAppInfoService;
import com.hirisun.cloud.system.service.IResourceRecoverService;

/**
 * @author wuxiaoxing
 * 2020-06-15
 * 执行定时任务
 */
@Component
public class Timer {

    @Autowired
    private IResourceRecoverAppInfoService appInfoService;

    @Autowired
    private SmsApi smsApi;

    @Autowired
    private IResourceRecoverService resourceRecoverService;

    @Autowired
    private UserApi userApi;

    private static final Logger logger = LoggerFactory.getLogger(Timer.class);
    /**
     * 资源回收定时检查，按时间设定，到时间后自动执行程序
     *
     */
    public void startRecoverCheck(String date,String userId, ResourceRecoverAppInfo info) throws Exception{
        // 规定的时间进行
        final SimpleDateFormat sdf = new SimpleDateFormat(date);
        Date startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(sdf.format(new Date()));
        java.util.Timer t = new java.util.Timer();
        TimerTask task = new TimerTask(){
            @Override
            public void run() {
                // 限时已过，检查工单状态
                //数据库查询
//                System.out.println("进入工单检查，检查资源回收状态");
                logger.debug("进入工单检查，检查资源回收状态");
                ResourceRecoverAppInfo appInfo=appInfoService.getById(info.getId());
                if(appInfo==null){
                    return ;
                }
                if(appInfo.getStatus().equals(ApplicationInfoStatus.RESENT.getCode())){//已完成，则不处理
                    return ;
                }
                if(appInfo.getRecoveredAgree()!=null){//被负责人已处理过工单
                    return ;
                }
                //再次发提醒短信
                //查出其中一个缩配资源的缩配时间
//                ResourceRecover resourceRecover=resourceRecoverService.getOne(new QueryWrapper<ResourceRecover>().eq("ref_Id",info.getId()));
//                //时间分割
//                String[] shrinkTime=null;
//                String month = "";
//                String day = "";
//                if(resourceRecover!=null&&resourceRecover.getShrinkTime()!=null){
//                    shrinkTime=resourceRecover.getShrinkTime().split(" ")[0].split("-");
//                }else{
//                    shrinkTime= DateUtil.formateDate(info.getCreateTime(),"yyyy-MM-dd").split("-");
//                }
//                month = shrinkTime[1];
//                day = shrinkTime[2];
                /**
                 * 此处处理人短信调整，不过需求不明确，有可能理解出错
                 */
//                messageProvider.sendMessageAsync(messageProvider.buildRecoverMessage(userId, BusinessName.RECOVER, info.getOrderNumber(),month,day));
                UserVO user = userApi.getUserById(userId);
                smsApi.buildSuccessMessage(user.getIdcard(), BusinessName.RECOVER, info.getOrderNumber());
//                messageProvider.sendMessageAsync(messageProvider.buildSuccessMessage(user, BusinessName.RECOVER, info.getOrderNumber()));
                //更新状态
                appInfo.setStatus(ApplicationInfoStatus.RESENT.getCode());
                appInfoService.updateById(appInfo);
                t.cancel();
                logger.debug("向回收负责人重发回收资源短信完成");
            }
        };
        // 按时执行一次
        t.schedule(task,startTime);

    }
}
