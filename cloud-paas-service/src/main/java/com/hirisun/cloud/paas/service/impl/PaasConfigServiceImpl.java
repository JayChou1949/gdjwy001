package com.hirisun.cloud.paas.service.impl;

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
import com.hirisun.cloud.common.util.UUIDUtil;
import com.hirisun.cloud.common.vo.CommonCode;
import com.hirisun.cloud.model.app.param.SubpageParam;
import com.hirisun.cloud.model.file.FilesVo;
import com.hirisun.cloud.model.system.OperateRecordVo;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.paas.bean.PaasConfig;
import com.hirisun.cloud.paas.mapper.PaasConfigMapper;
import com.hirisun.cloud.paas.service.PaasConfigService;

@Service
public class PaasConfigServiceImpl implements PaasConfigService {

	@Autowired
	private FilesApi filesApi;
	@Autowired
	private PaasConfigMapper paasConfigMapper;
	@Autowired
	private OperateRecordApi operateRecordApi;
	@Autowired
	private SystemApi systemApi;
	
	@Transactional(rollbackFor = Exception.class)
	public String create(PaasConfig paas) {
		
		verifyParams(paas);
        paas.setId(UUIDUtil.getUUID());
        paas.setStatus(ReviewStatus.PRO_ONLINE.getCode());
        paasConfigMapper.insert(paas);
        
        SubpageParam param = new SubpageParam();
        param.setFiles(paas.getFileList());
        param.setRefId(paas.getId());
        filesApi.refFiles(param);
		return paas.getId();
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void serviceSort(String id, String ope) {
		
        PaasConfig entity = paasConfigMapper.selectById(id);
        PaasConfig change = null;
        QueryWrapper<PaasConfig> wrapper = new QueryWrapper<PaasConfig>().eq("status",entity.getStatus()).eq("SUB_TYPE",entity.getSubType());
        if ("down".equals(ope)) {
            wrapper.gt("sort", entity.getSort()).orderByAsc("sort");
        }else if ("up".equals(ope)){
            wrapper.lt("sort", entity.getSort()).orderByDesc("sort");
        }
        List<PaasConfig> pres =  paasConfigMapper.selectPage(new Page<PaasConfig>(1, 1),wrapper).getRecords();
        if (pres!=null&&pres.size()==1) {
            change = pres.get(0);
        }
        if (change!=null){
            Long sort = change.getSort();
            change.setSort(entity.getSort());
            entity.setSort(sort);
            paasConfigMapper.updateById(entity);
            paasConfigMapper.updateById(change);
        }
	}
	
	
	@Transactional(rollbackFor = Exception.class)
    public void publish(UserVO user, String id, Integer result, String remark) {
        PaasConfig paas = paasConfigMapper.selectById(id);
        if (result.equals(1)) { // 上线
            paas.setStatus(ReviewStatus.ONLINE.getCode());
            paasConfigMapper.updateById(paas);
            
            
            boolean isNotEmpty = operateRecordApi.isNotEmpty(id);
            OperateRecordVo vo = new OperateRecordVo();
            vo.setTargetId(id);
			vo.setOperator(user.getIdCard());
			vo.setOperate("上/下线");
			vo.setResult("上线");
			vo.setRemark(remark);
            
            if(isNotEmpty) {
            	operateRecordApi.save(vo);
            } else {
            	operateRecordApi.saveLatest(vo);
            }
        } else { // 下线
            paas.setStatus(ReviewStatus.PRO_ONLINE.getCode());
            paasConfigMapper.updateById(paas);

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
     * 校验参数
     */
	private void verifyParams(PaasConfig paas) {
        if (!"0".equals(paas.getCanApplication()) || "1".equals(paas.getCanApplication())) {
            throw new CustomException(CommonCode.INVALID_PARAM);
        }
    }

	@Override
	public IPage<PaasConfig> getPage(IPage<PaasConfig> page, UserVO user, Integer status, String name, String subType) {
		IPage<PaasConfig> paasConfigPage = paasConfigMapper.getPage(page, user, status, name, subType);
		return paasConfigPage;
	}

	@Transactional(rollbackFor = Exception.class)
	public void delete(UserVO user,String id) {
		
		PaasConfig paas = paasConfigMapper.selectById(id);
        paas.setStatus(ReviewStatus.DELETE.getCode());
        paasConfigMapper.updateById(paas);
        systemApi.saveLog(user.getIdCard(),"PaaS服务id："+id,"删除服务", IpUtil.getIp());
		
	}

	@Transactional(rollbackFor = Exception.class)
	public void edit(PaasConfig paas) {
		
		verifyParams(paas);
        if (StringUtils.isEmpty(paas.getCreator())) {
            paas.setCreator(null);
        }
        paas.setStatus(ReviewStatus.PRO_ONLINE.getCode());
        paasConfigMapper.updateById(paas);
        
        SubpageParam param = new SubpageParam();
        param.setFiles(paas.getFileList());
        param.setRefId(paas.getId());
        filesApi.refFiles(param);
        
	}
	
	@Override
    public PaasConfig getDetail(UserVO user, String id) {
		PaasConfig paas = paasConfigMapper.selectById(id);
        if (paas != null) {
            paas.setUser(user);
            
            SubpageParam param = new SubpageParam();
            param.setSubpageId(id);
            List<FilesVo> filesList = filesApi.find(param);
            paas.setFileList(filesList);
        }
        return paas;
    }

	@Override
	public PaasConfig setWorkflow(String id, String flowId) {
		PaasConfig saas = new PaasConfig();
        saas.setId(id);
        saas.setWorkFlowId(flowId);
        paasConfigMapper.updateById(saas);
		return saas;
	}

}
