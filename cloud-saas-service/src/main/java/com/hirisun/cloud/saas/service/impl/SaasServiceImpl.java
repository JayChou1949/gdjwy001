package com.hirisun.cloud.saas.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hirisun.cloud.api.system.OperateRecordApi;
import com.hirisun.cloud.api.system.SystemApi;
import com.hirisun.cloud.common.contains.ReviewStatus;
import com.hirisun.cloud.common.exception.CustomException;
import com.hirisun.cloud.common.util.StringBooleanCheck;
import com.hirisun.cloud.common.util.UUIDUtil;
import com.hirisun.cloud.common.vo.CommonCode;
import com.hirisun.cloud.model.service.alter.vo.ServiceAlterVo;
import com.hirisun.cloud.model.service.publish.vo.ServicePublishVo;
import com.hirisun.cloud.model.system.OperateRecordVo;
import com.hirisun.cloud.model.system.SysDictVO;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.model.workbench.vo.QueryVO;
import com.hirisun.cloud.saas.bean.Saas;
import com.hirisun.cloud.saas.mapper.SaasMapper;
import com.hirisun.cloud.saas.service.SaasService;

@Service
public class SaasServiceImpl implements SaasService {

	@Autowired
	private SaasMapper saasConfigMapper;
	@Autowired
	private OperateRecordApi operateRecordApi;
	@Autowired
	private SystemApi systemApi;
	
	@Override
	public void publish(UserVO user, String id, Integer result, String remark) {

        Saas saas = saasConfigMapper.selectById(id);
        if (result.equals(1)) { // 上线
            saas.setStatus(ReviewStatus.ONLINE.getCode());
            saasConfigMapper.updateById(saas);
            
            boolean isNotEmpty = operateRecordApi.isNotEmpty(id);
            
            OperateRecordVo vo = new OperateRecordVo();
            vo.setTargetId(id);
			vo.setOperator(user.getIdcard());
			vo.setOperate("上/下线");
			vo.setResult("上线");
			vo.setRemark(remark);
			if(isNotEmpty) {
            	operateRecordApi.save(vo);
            } else {
            	operateRecordApi.saveLatest(vo);
            }
            
        } else { // 下线
        	
            saas.setStatus(ReviewStatus.PRO_ONLINE.getCode());
            saasConfigMapper.updateById(saas);
            OperateRecordVo vo = new OperateRecordVo();
            vo.setTargetId(id);
			vo.setOperator(user.getIdcard());
			vo.setOperate("上/下线");
			vo.setResult("下线");
			vo.setRemark(remark);
            
			operateRecordApi.save(vo);
        }
    
		
		

	}

	@Override
	public Map<String, Object> getFrontData(Integer serviceFlag) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Saas> getServiceFrontData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> getApplicationFrontData(String projectName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> getLabel(String typeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IPage<Saas> getPage(IPage<Saas> page, UserVO user, Integer status, String name, String subType,
			Integer serviceFlag, Integer pilotApp) {
		IPage<Saas> page2 = saasConfigMapper.getPage(page, user, status, 
				name, subType,serviceFlag,pilotApp);
		
		List<Saas> records = page2.getRecords();
		
		if(CollectionUtils.isNotEmpty(records)) {
			records.forEach(iaasConfig->{
				String configSubType = iaasConfig.getSubType();
				if(StringUtils.isNotBlank(configSubType)) {
					SysDictVO sysDictVO = systemApi.feignGetById(configSubType);
					if(sysDictVO != null)
						iaasConfig.setSubTypeName(sysDictVO.getName());
				}
			});
		}
		
		return page2;
	}

	@Override
	public IPage<Saas> getNewPage(IPage<Saas> page, QueryVO queryVO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Saas getDetail(UserVO user,String id) {

        Saas saas = saasConfigMapper.selectById(id);
        if (saas != null) {
            saas.setUser(user);
        }
        return saas;
    
	}

	@Override
	public IPage<Saas> getMorePage(IPage<Saas> page, String typeId, String keyword, String areaName,
			String policeCategory) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IPage<Saas> getServiceMorePage(IPage<Saas> page, String keyword) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Saas getDetailWithSubTypeName(String serviceId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> getLabelWithCount(String typeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IPage<Saas> getOneClickPage(IPage<Saas> page, String typeId, String label, String keyword) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Saas> saasList(String keyword) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateViewCountById(String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Saas> getAppName(String creator) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean servicePublish2SaaS(ServicePublishVo servicePublish, String serviceGuid) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean serviceAlter2SaaS(ServiceAlterVo serviceAlter, String serviceGuid) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Integer userCountOfSaasApplication(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer areaCountOfSaasApplication(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer policeCountOfSaasApplication(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void hotfix() {
		// TODO Auto-generated method stub

	}

	@Override
	public String create(UserVO user, Saas saasConfig) {
		
		saasConfig.setId(UUIDUtil.getUUID());
    	saasConfig.setCreator(user.getIdcard());
    	saasConfig.setStatus(ReviewStatus.PRO_ONLINE.getCode());
        verifyParams(saasConfig);
        saasConfigMapper.insert(saasConfig);
        return saasConfig.getId();
	}
	
	private void verifyParams(Saas saas) {
        if (!StringBooleanCheck.check(saas.getHome())) {
            throw new CustomException(CommonCode.INVALID_PARAM);
        }
        if (!StringBooleanCheck.check(saas.getCanApplication())) {
            throw new CustomException(CommonCode.INVALID_PARAM);
        }
        if (!StringBooleanCheck.check(saas.getHasDoc())) {
            throw new CustomException(CommonCode.INVALID_PARAM);
        }
        if (!StringBooleanCheck.check(saas.getSecret())) {
            throw new CustomException(CommonCode.INVALID_PARAM);
        }
    }

	@Transactional(rollbackFor = Exception.class)
    public void serviceSort(String id, String ope) {
        Saas entity = saasConfigMapper.selectById(id);
        Saas change = null;
        QueryWrapper<Saas> wrapper = new QueryWrapper<Saas>().eq("status",entity.getStatus()).eq("SUB_TYPE",entity.getSubType());
        if ("down".equals(ope)) {
           wrapper.gt("sort", entity.getSort()).orderByAsc("sort");
        }else if ("up".equals(ope)){
            wrapper.lt("sort", entity.getSort()).orderByDesc("sort");
        }
        List<Saas> pres = saasConfigMapper.selectPage(new Page<Saas>(1, 1),wrapper).getRecords();
        if (pres!=null&&pres.size()==1) {
            change = pres.get(0);
        }
        if (change!=null){
            Long sort = change.getSort();
            change.setSort(entity.getSort());
            entity.setSort(sort);
            saasConfigMapper.updateById(entity);
            saasConfigMapper.updateById(change);
        }
    }

	@Transactional(rollbackFor = Exception.class)
	public void delete(UserVO user, String id) {
		Saas saas = saasConfigMapper.selectById(id);
        saas.setStatus(ReviewStatus.DELETE.getCode());
        saasConfigMapper.updateById(saas);
        
        
        OperateRecordVo vo = new OperateRecordVo();
        vo.setTargetId(id);
		vo.setOperator(user.getIdcard());
		vo.setOperate("删除");
		vo.setResult("删除");
        operateRecordApi.save(vo);
	}

	@Transactional(rollbackFor = Exception.class)
	public void edit(Saas saas) {
		
        verifyParams(saas);
        if (StringUtils.isEmpty(saas.getCreator())) {
            saas.setCreator(null);
        }
        saas.setStatus(ReviewStatus.PRO_ONLINE.getCode());
        saasConfigMapper.updateById(saas);
		
	}

	@Transactional(rollbackFor = Exception.class)
	public Saas setflow(UserVO user, String id, String flowId) {
		
		Saas saas = saasConfigMapper.selectById(id);
		if(saas != null) {
			saas.setWorkFlowId(flowId);
			saasConfigMapper.updateById(saas);
		}
		return saas;
	}

	@Override
	public List<Saas> findSaasConfigByName(String name) {
		List<Saas> selectList = saasConfigMapper.selectList(new QueryWrapper<Saas>().lambda()
                .eq(Saas::getName, name)
                .or()
                .eq(Saas::getShortName, name));
		return selectList;
	}

	@Override
	public Saas getSaasConfigById(String saasConfigId) {
		return saasConfigMapper.selectById(saasConfigId);
	}

}
