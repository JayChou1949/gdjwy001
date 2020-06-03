package com.upd.hwcloud.service.msg;

import com.dragonsoft.mq.client.model.entity.MessageInfoDTO;
import com.dragonsoft.mq.client.spring.service.ProducerService;
import com.upd.hwcloud.bean.contains.NotifyType;
import com.upd.hwcloud.bean.dto.Message;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.common.utils.EnvironmentUtils;
import com.upd.hwcloud.common.utils.UUIDUtil;
import com.upd.hwcloud.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Component
public class MessageProvider {

    private static final Logger logger = LoggerFactory.getLogger(MessageProvider.class);

    @Autowired
    private ProducerService producerService;
    @Autowired
    private ApplicationContext app;
    @Resource
    private TaskExecutor taskExecutor;
    @Autowired
    private IUserService userService;
    @Value("${msg.topic}")
    private String TOPIC;
    @Value("${msg.template.submit_success}")
    private String msgSubmitSuccess;
    @Value("${msg.template.processing_person}")
    private String msgProcessingPerson;
    @Value("${msg.template.reject}")
    private String msgReject;
    @Value("${msg.template.complete}")
    private String msgComplete;

    public void sendMessageAsync(Message... message) {
        if (message == null) {
            logger.error("send message is null");
            return;
        }
        sendMessageAsync(Arrays.asList(message));
    }

    public void sendMessageAsync(List<Message> message) {
        if (message == null || message.isEmpty()) {
            logger.error("send message is null");
            return;
        }

        taskExecutor.execute(() -> {
            for (Message m : message) {
                String content = m.getContent();
                User user = m.getUser();
                sendMessage(content, user);
            }
        });
    }

    private void sendMessage(String content, User user) {
        try {
            String profile = EnvironmentUtils.getCurrentProfile(app);
            if (EnvironmentUtils.ONLINE_PROFILES.contains(profile)) {
                if (user == null) {
                    return;
                }
                String notifyType = user.getNotifyType();
                String[] nt = notifyType.split(",");
                if (nt != null && nt.length > 0) {
                    for (String type : nt) {
                        if (NotifyType.SMS.getCode().equals(type)) {
                            MessageInfoDTO msg = getSmsMsg(user.getMobileWork(), String.format(content, "短信"));
                            producerService.sendMsg(TOPIC, msg,0);
                        }
                        if (NotifyType.EMAIL.getCode().equals(type)) {
                            MessageInfoDTO msg = getEmailMsg(user.getEmail(), String.format(content, "邮件"));
                            producerService.sendMsg(TOPIC, msg,0);
                        }
                        if (NotifyType.WX.getCode().equals(type)) {
                            MessageInfoDTO msg = getWxMsg(user.getIdcard(), String.format(content, "微信"));
                            producerService.sendMsg(TOPIC, msg,0);
                        }
                    }
                }
            } else {
                logger.error(content + " --> " + (user == null ? "" : user.getName()));
            }
        } catch (Exception e) {
            logger.error("kafka消息发送失败", e);
        }
    }

//    /**
//     * @param receiver 接受者，多个以逗号分隔
//     * @param content 消息内容
//     * @return true: 发送成功
//     */
//    private boolean sendMessage(String content, String... receiver){
//        if (receiver != null && receiver.length > 0) {
//            String receiverJoin = StringUtils.join(receiver, ",");
//            MessageInfoDTO messageInfoDTO = getMsg(receiverJoin, content);//构造消息对象
//            /*
//                返回 Map<String,Object> 对象，包含code和message两个key。
//                键值对信息为：
//                code	message
//                200	    发送成功
//                1301	发送超时
//                1302	未返回结果
//                1303	未返回消息位移
//             */
//            Map<String,Object> map = producerService.sendMsg(TOPIC, messageInfoDTO,0);
//            String code = map.get("code").toString();
//            return "200".equals(code);
//        }
//        return false;
//    }

    /**
     * 发送手机短信
     * @param phone 手机号，多个以逗号分隔
     * @param content 消息内容
     */
    private MessageInfoDTO getSmsMsg(String phone, String content) {
        return getMsg(phone, content);
    }

    /**
     * 发送微信信息
     * @param idcard 身份证，多个以逗号分隔
     * @param content 消息内容
     */
    private MessageInfoDTO getWxMsg(String idcard, String content) {
        return getMsg(idcard, content);
    }

    /**
     * 发送邮件信息
     * @param email 邮箱号，多个以逗号分隔
     * @param content 消息内容
     */
    private MessageInfoDTO getEmailMsg(String email, String content) {
        MessageInfoDTO msg = getMsg(email, content);
        msg.setReceiveRoles("4400");
        return msg;
    }

    /**
     * 消息基础信息
     * @param receiver 接受者，多个以逗号分隔
     * @param content 消息内容
     */
    private MessageInfoDTO getMsg(String receiver, String content){
        MessageInfoDTO messageInfoDTO=new MessageInfoDTO();
        messageInfoDTO.setUserCode("hailian");
        messageInfoDTO.setUserDeptName("广东省运维平台");
        messageInfoDTO.setUserSystemCode("QBXXGL");
        //构造接收者信息
        //接收手机号，多个以逗号隔开
        messageInfoDTO.setReceiveUsers(receiver);
        //定义消息内容
        messageInfoDTO.setMessageId(UUIDUtil.getUUID());
        messageInfoDTO.setInfoType("消息类型编码");
        messageInfoDTO.setTitle("发送测试标题");
        messageInfoDTO.setContent(content);
        messageInfoDTO.setCreateTime("20180601101010");
        //构造消息内容
        return  messageInfoDTO;
    }

    /**
     * 构造提交成功短信通知消息
     */
    public Message buildSuccessMessage(User submitor, String bizName, String orderNum) {
        if (submitor == null) {
            logger.error("to send user is null");
            return null;
        }
        Message toUser = new Message();
        toUser.setContent(String.format(msgSubmitSuccess, submitor.getName(), bizName, orderNum, "%s"));
        toUser.setUser(submitor);
        return toUser;
    }

    /**
     * 构造发送给处理人的短信消息
     */
    public Message buildProcessingMessage(String bizName, String orderNumber,String userId) {
        User user = userService.findUserByIdcard(userId);
        if (user == null) {
            logger.error("to send user is null");
            return null;
        }
        return buildProcessingMessage(bizName,orderNumber,user);
    }

    private Message buildProcessingMessage(String bizName,String orderNumber,User user) {
        Message toProcessingPerson = new Message();
        toProcessingPerson.setContent(String.format(msgProcessingPerson, user.getName(),bizName,orderNumber));
        toProcessingPerson.setUser(user);
        return toProcessingPerson;
    }

    /**
     * 构造完成申请短信通知消息
     */
    public Message buildCompleteMessage(String creator, String bizName, String orderNum) {
        User user = userService.findUserByIdcard(creator);
        if (user == null) {
            logger.error("to send user is null");
            return null;
        }
        Message message = new Message();
        message.setContent(String.format(msgComplete, user.getName(), bizName, orderNum));
        message.setUser(user);
        return message;
    }

    /**
     * 构造审核不通过的通知消息
     */
    public Message buildRejectMessage(String creator, String bizName) {
        User user = userService.findUserByIdcard(creator);
        if (user == null) {
            logger.error("to send user is null");
            return null;
        }
        Message message = new Message();
        message.setContent(String.format(msgReject, user.getName(), bizName));
        message.setUser(user);
        return message;
    }


}
