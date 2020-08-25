package com.hirisun.cloud.iaas.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hirisun.cloud.api.system.FilesApi;
import com.hirisun.cloud.api.system.OperateRecordApi;
import com.hirisun.cloud.api.system.SystemApi;
import com.hirisun.cloud.common.contains.ReviewStatus;
import com.hirisun.cloud.common.exception.CustomException;
import com.hirisun.cloud.common.util.IpUtil;
import com.hirisun.cloud.common.vo.CommonCode;
import com.hirisun.cloud.iaas.bean.IaasConfig;
import com.hirisun.cloud.iaas.mapper.IaasConfigMapper;
import com.hirisun.cloud.iaas.service.IaasConfigService;
import com.hirisun.cloud.model.app.param.SubpageParam;
import com.hirisun.cloud.model.file.FilesVo;
import com.hirisun.cloud.model.system.OperateRecordVo;
import com.hirisun.cloud.model.user.UserVO;

@Service
public class IaasConfigServiceImpl implements IaasConfigService {

	@Autowired
	private FilesApi filesApi;
	@Autowired
	private IaasConfigMapper iaasConfigMapper;
	@Autowired
	private OperateRecordApi operateRecordApi;
	@Autowired
	private SystemApi systemApi;
	
	/**
	 * 保存iaas流程配置及文档上传
	 */
	@Transactional(rollbackFor = Exception.class)
	public String create(UserVO user, IaasConfig iaas) {
		
		iaas.setCreator(user.getIdCard());
        iaas.setStatus(ReviewStatus.PRO_ONLINE.getCode());
        verifyParams(iaas);
        iaasConfigMapper.insert(iaas);
        
        SubpageParam param = new SubpageParam();
        param.setRefId(iaas.getId());
        param.setFiles(iaas.getFileList());
        filesApi.refFiles(param);
        
        return iaas.getId();
		
	}

	private void verifyParams(IaasConfig iaas) {

        if (!"0".equals(iaas.getCanApplication()) || "1".equals(iaas.getCanApplication())) {
            throw new CustomException(CommonCode.INVALID_PARAM);
        }
    }

	/**
	 * iaas 配置上下线，并记录操作人
	 */
	@Transactional(rollbackFor = Exception.class)
	public void publish(UserVO user, String id, Integer result, String remark) {
		
        IaasConfig iaas = iaasConfigMapper.selectById(id);
        if (result.equals(1)) { // 上线
            iaas.setStatus(ReviewStatus.ONLINE.getCode());
            iaasConfigMapper.updateById(iaas);
            
            
            OperateRecordVo vo = new OperateRecordVo();
            vo.setTargetId(id);
			vo.setOperator(user.getIdCard());
			vo.setOperate("上/下线");
			vo.setResult("上线");
			vo.setRemark(remark);
			operateRecordApi.save(vo);
        } else { // 下线
            iaas.setStatus(ReviewStatus.PRO_ONLINE.getCode());
            iaasConfigMapper.updateById(iaas);
            
            OperateRecordVo vo = new OperateRecordVo();
            vo.setTargetId(id);
			vo.setOperator(user.getIdCard());
			vo.setOperate("上/下线");
			vo.setResult("下线");
			vo.setRemark(remark);
			operateRecordApi.save(vo);
        }
	}
	
	/**
	 * 操作上下移动
	 * @param id
	 * @param ope
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
    public Boolean serviceSort(String id, String ope) {
        IaasConfig entity = iaasConfigMapper.selectById(id);
        IaasConfig change = null;
        if ("down".equals(ope)) {
            List<IaasConfig> nexts = iaasConfigMapper.selectPage(new Page<IaasConfig>(1, 1), new QueryWrapper<IaasConfig>().eq("status", entity.getStatus()).gt("sort", entity.getSort()).orderByAsc("sort")).getRecords();
            if (nexts != null && nexts.size() == 1) {
                change = nexts.get(0);
            }
        } else if ("up".equals(ope)) {
            List<IaasConfig> pres = iaasConfigMapper.selectPage(new Page<IaasConfig>(1, 1), new QueryWrapper<IaasConfig>().eq("status", entity.getStatus()).lt("sort", entity.getSort()).orderByDesc("sort")).getRecords();
            if (pres != null && pres.size() == 1) {
                change = pres.get(0);
            }
        }
        if (change != null) {
            Long sort = change.getSort();
            change.setSort(entity.getSort());
            entity.setSort(sort);
            iaasConfigMapper.updateById(entity);
            iaasConfigMapper.updateById(change);
        }
        return true;
    }

	/**
	 * 分页查询iaas 服务列表
	 */
	public IPage<IaasConfig> getPage(IPage<IaasConfig> page, UserVO user, 
			Integer status, String name, String subType) {
		IPage<IaasConfig> iaasPage = iaasConfigMapper.getPage(page, user, status, name,subType);
		return iaasPage;
	}

	/**
	 * 逻辑删除 iaas 配置,并记录系统日志
	 */
	@Transactional(rollbackFor = Exception.class)
	public void delete(UserVO user, String id) {
		
		IaasConfig iaas = iaasConfigMapper.selectById(id);
        iaas.setStatus(ReviewStatus.DELETE.getCode());
        iaasConfigMapper.updateById(iaas);
        systemApi.saveLog(user.getIdCard(), "IaaS服务id："+id, "删除服务", IpUtil.getIp());
		
	}

	/**
	 * 修改服务并重新上传文件
	 */
	@Transactional(rollbackFor = Exception.class)
	public String edit(UserVO user,IaasConfig iaas) {
		
		verifyParams(iaas);
        if (StringUtils.isEmpty(iaas.getCreator())) iaas.setCreator(null);
        iaas.setStatus(ReviewStatus.PRO_ONLINE.getCode());
        iaasConfigMapper.updateById(iaas);
        
        SubpageParam param = new SubpageParam();
        param.setRefId(iaas.getId());
        param.setFiles(iaas.getFileList());
        filesApi.refFiles(param);
        
		return iaas.getId();
	}

	/**
	 * 根据id 获取Iaas配置详情
	 */
	public IaasConfig getDetail(UserVO user, String id) {
		
		IaasConfig iaas = iaasConfigMapper.selectById(id);
        if (iaas != null) {
            iaas.setUser(user);
            
            SubpageParam param = new SubpageParam();
            param.setRefId(id);
			List<FilesVo> filesList = filesApi.find(param);
            
            iaas.setFileList(filesList);
        }
        return iaas;
	}

	/**
	 * 设置流程id
	 */
	@Transactional(rollbackFor = Exception.class)
	public IaasConfig setWorkflow(String id, String workFlowId) {
		
		IaasConfig iaas = iaasConfigMapper.selectById(id);
		if(iaas != null) {
			iaas.setWorkFlowId(workFlowId);
			iaasConfigMapper.updateById(iaas);
		}
		return iaas;
	}

}
