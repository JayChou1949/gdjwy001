package com.hirisun.cloud.iaas.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hirisun.cloud.iaas.bean.IaasConfig;
import com.hirisun.cloud.model.user.UserVO;

public interface IaasConfigService {
    
	/**
	 * 创建 iaas 配置
	 * @param user
	 * @param iaas
	 * @return
	 */
	public String create(UserVO user,IaasConfig iaas);
	
	/**
     * 上/下线
     * @param result 操作结果 1:上线,其它:下线
     * @param remark 操作描述
     */
    void publish(UserVO user, String id, Integer result, String remark);
    
    /**
     * 上/下移动
     * @param id
     * @param ope
     * @return
     */
    Boolean serviceSort(String id,String ope);

    /**
     * 分页查询iaas 服务列表
     * @param page
     * @param user
     * @param status
     * @param name
     * @param subType
     * @return
     */
	public IPage<IaasConfig> getPage(IPage<IaasConfig> page, UserVO user, Integer status, String name, String subType);
	
	/**
	 * 删除服务
	 * @param user
	 * @param id
	 */
	public void delete(UserVO user, String id);
	
	/**
	 * 修改服务
	 * @param user
	 * @param id
	 */
	public String edit(UserVO user,IaasConfig iaas);

	/**
	 * 根据id 获取iaas配置详情
	 * @param user
	 * @param id
	 */
	public IaasConfig getDetail(UserVO user, String id);
	
	/**
	 * 设置流程id
	 * @param id
	 * @param workFlowId
	 * @return
	 */
	public IaasConfig setWorkflow(String id,String workFlowId);
	
	
	
}
