package com.upd.hwcloud.timer;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.upd.hwcloud.bean.contains.ApplicationInfoStatus;
import com.upd.hwcloud.bean.contains.BusinessName;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.entity.application.ResourceRecover;
import com.upd.hwcloud.bean.entity.application.ResourceRecoverAppInfo;
import com.upd.hwcloud.common.utils.DateUtil;
import com.upd.hwcloud.service.application.IResourceRecoverAppInfoService;
import com.upd.hwcloud.service.application.IResourceRecoverService;
import com.upd.hwcloud.service.impl.wfm.ActivityServiceImpl;
import com.upd.hwcloud.service.msg.MessageProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

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
    private MessageProvider messageProvider;

    @Autowired
    private IResourceRecoverService resourceRecoverService;

    private static final Logger logger = LoggerFactory.getLogger(ActivityServiceImpl.class);
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
                ResourceRecover resourceRecover=resourceRecoverService.getOne(new QueryWrapper<ResourceRecover>().eq("ref_Id",info.getId()));
                //时间分割
                String[] shrinkTime=null;
                String month = "";
                String day = "";
                if(resourceRecover!=null&&resourceRecover.getShrinkTime()!=null){
                    shrinkTime=resourceRecover.getShrinkTime().split(" ")[0].split("-");
                }else{
                    shrinkTime= DateUtil.formateDate(info.getCreateTime(),"yyyy-MM-dd").split("-");
                }
                month = shrinkTime[1];
                day = shrinkTime[2];
                messageProvider.sendMessageAsync(messageProvider.buildRecoverMessage(userId, BusinessName.RECOVER, info.getOrderNumber(),month,day));

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
