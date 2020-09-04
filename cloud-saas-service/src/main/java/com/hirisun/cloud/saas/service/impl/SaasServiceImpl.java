package com.hirisun.cloud.saas.service.impl;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hirisun.cloud.api.system.OperateRecordApi;
import com.hirisun.cloud.api.system.SystemApi;
import com.hirisun.cloud.common.constant.RedisKey;
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
import com.hirisun.cloud.model.workbench.vo.KeyAndValueVO;
import com.hirisun.cloud.model.workbench.vo.QueryVO;
import com.hirisun.cloud.saas.bean.Saas;
import com.hirisun.cloud.saas.mapper.SaasMapper;
import com.hirisun.cloud.saas.service.SaasService;

@Service
public class SaasServiceImpl implements SaasService {

	@Autowired
	private SaasMapper saasMapper;
	@Autowired
	private OperateRecordApi operateRecordApi;
	@Autowired
	private SystemApi systemApi;
	@Autowired
    private StringRedisTemplate stringRedisTemplate;
	
	
	@Override
	public void publish(UserVO user, String id, Integer result, String remark) {

        Saas saas = saasMapper.selectById(id);
        if (result.equals(1)) { // 上线
            saas.setStatus(ReviewStatus.ONLINE.getCode());
            saasMapper.updateById(saas);
            
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
            saasMapper.updateById(saas);
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

	/**
	 * 门户首页应用市场-通用应用/警种应用/地市应用/试点应用
	 */
	public List<Map<String, Object>> getApplicationFrontData(String projectName) {

		//统计门户访问量
		stringRedisTemplate.opsForValue().increment(RedisKey.KEY_APP_VIEW_COUNT, 1L);
		
        List<Saas> all = saasMapper.getOnlineList(0);
        List<Map<String, Object>> subType = Lists.newArrayList();
        if(all!=null){
        	
        	List<SysDictVO> saasType = systemApi.findDictByValue("saasType");
        	
            saasType.forEach(type -> {
                List<Saas> list;
                if("通用应用".equals(type.getName())){
                    currencyApplication(all, subType, type);
                }else if("专项应用".equals(type.getName())){
                    if(StringUtils.isNotBlank(projectName)){
                        specialApplication(projectName, all, subType, type);
                    }
                }else {
                    if(!"政府机构应用".equals(type.getName())){
                        governmentApplication(all, subType, type);
                    }
                }
            });
        }

        List<Saas> pilot = all.parallelStream().filter(saas -> saas.getPilotApp()==1).sorted(Comparator.comparing(Saas::getTop,Comparator.reverseOrder()).thenComparing(Saas::getSort)).collect(Collectors.toList());
        if (pilot == null) pilot = Lists.newArrayList();
        Map<String, Object> map = Maps.newHashMap();
        map.put("typeId", "");
        map.put("name", "试点应用");
        // 显示分类总数,和更多页面总数保持一致
        map.put("totalCount", pilot.size());
        map.put("list", pilot.size() > 15 ? pilot.subList(0, 15) : pilot);
        subType.add(map);
        return subType;
    
	}

	/**
	 * 政府机构
	 * @param all
	 * @param subType
	 * @param type
	 */
	private void governmentApplication(List<Saas> all, List<Map<String, Object>> subType, SysDictVO type) {
		List<Saas> list;
		list = all.parallelStream().filter(saas -> Objects.equals(type.getId(), saas.getSubType()))
		        .sorted(Comparator.comparing(Saas::getViewCount,Comparator.reverseOrder()).thenComparing(Saas::getSort))
		        .collect(Collectors.toList());

		List<Saas> mostView;
		Map<String,Long> resMap;
		List<KeyAndValueVO> keyAndValueVOList = Lists.newArrayList();

		if(list.size()>6){
		    mostView = list.subList(0,6);
		}else {
		    mostView = list;
		}
		if("警种应用".equals(type.getName())){
		     resMap =  list.parallelStream().filter(saas -> saas.getPoliceCategory() != null &&(saas.getAreaName() == null || "省厅".equals(saas.getAreaName())) ).collect(Collectors.groupingBy(Saas::getPoliceCategory,Collectors.counting()));
		     keyAndValueVOList = groupHandler(resMap);
		     if(keyAndValueVOList.size()>5){
		         keyAndValueVOList = keyAndValueVOList.subList(0,5);
		     }

		}else if("地市应用".equals(type.getName())){
		    resMap =  list.parallelStream().filter(saas -> saas.getAreaName() !=null && !"省厅".equals(saas.getAreaName())).collect(Collectors.groupingBy(Saas::getAreaName,Collectors.counting()));
		    keyAndValueVOList = groupHandler(resMap);
		}
		Map<String,Object> map = Maps.newHashMap();
		map.put("mostView",mostView);
		map.put("typeId", type.getId());
		map.put("name", type.getName());
		// 显示分类总数,和更多页面总数保持一致
		map.put("totalCount", list.size());
		map.put("list",keyAndValueVOList);
		subType.add(map);
	}

	/**
	 * 专项应用
	 * @param projectName
	 * @param all
	 * @param subType
	 * @param type
	 */
	private void specialApplication(String projectName, List<Saas> all, List<Map<String, Object>> subType,
			SysDictVO type) {
		List<Saas> list;
		list = all.parallelStream().filter(saas ->StringUtils.equals(type.getId(),saas.getSubType()) && StringUtils.equals(saas.getProject(),projectName))
		        .sorted(Comparator.comparing(Saas::getSort)).collect(Collectors.toList());
		if(list == null){
		    list = Lists.newArrayList();
		}
		Map<String, Object> map = Maps.newHashMap();
		map.put("typeId", type.getId());
		map.put("name", type.getName());
		map.put("projectName",projectName);
		// 显示分类总数,和更多页面总数保持一致
		map.put("totalCount", list.size());
		map.put("list", list.size() > 15 ? list.subList(0, 15) : list);
		subType.add(map);
	}

	/**
	 * 通用应用
	 * @param all
	 * @param subType
	 * @param type
	 */
	private void currencyApplication(List<Saas> all, List<Map<String, Object>> subType, SysDictVO type) {
		List<Saas> list;
		list = all.parallelStream().filter(saas -> Objects.equals(type.getId(), saas.getSubType()))
		        //重点应用降序后按排序值升序
		        .sorted(Comparator.comparing(Saas::getTop,Comparator.reverseOrder()).thenComparing(Saas::getSort))
		        .collect(Collectors.toList());
		if (list == null) list = Lists.newArrayList();
		Map<String, Object> map = Maps.newHashMap();
		map.put("typeId", type.getId());
		map.put("name", type.getName());
		// 显示分类总数,和更多页面总数保持一致
		map.put("totalCount", list.size());
		map.put("list", list.size() > 15 ? list.subList(0, 15) : list);
		subType.add(map);
	}

	/**
     * Map转首页警种和地区应用VO
     * @return
     */
    private List<KeyAndValueVO> groupHandler(Map<String,Long> map){
        List<KeyAndValueVO> keyAndValueVOList = Lists.newArrayList();
        map.forEach((k,v)->{
          KeyAndValueVO keyAndValueVO = new KeyAndValueVO(k,v);
          keyAndValueVOList.add(keyAndValueVO);
          });
        List<KeyAndValueVO>  sortResult = keyAndValueVOList.parallelStream().sorted(Comparator.comparing(KeyAndValueVO::getValue,Comparator.reverseOrder())).collect(Collectors.toList());
        return sortResult;

    }
	
	@Override
	public Set<String> getLabel(String typeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IPage<Saas> getPage(IPage<Saas> page, UserVO user, Integer status, String name, String subType,
			Integer serviceFlag, Integer pilotApp) {
		IPage<Saas> page2 = saasMapper.getPage(page, user, status, 
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

        Saas saas = saasMapper.selectById(id);
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
        saasMapper.insert(saasConfig);
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
        Saas entity = saasMapper.selectById(id);
        Saas change = null;
        QueryWrapper<Saas> wrapper = new QueryWrapper<Saas>().eq("status",entity.getStatus()).eq("SUB_TYPE",entity.getSubType());
        if ("down".equals(ope)) {
           wrapper.gt("sort", entity.getSort()).orderByAsc("sort");
        }else if ("up".equals(ope)){
            wrapper.lt("sort", entity.getSort()).orderByDesc("sort");
        }
        List<Saas> pres = saasMapper.selectPage(new Page<Saas>(1, 1),wrapper).getRecords();
        if (pres!=null&&pres.size()==1) {
            change = pres.get(0);
        }
        if (change!=null){
            Long sort = change.getSort();
            change.setSort(entity.getSort());
            entity.setSort(sort);
            saasMapper.updateById(entity);
            saasMapper.updateById(change);
        }
    }

	@Transactional(rollbackFor = Exception.class)
	public void delete(UserVO user, String id) {
		Saas saas = saasMapper.selectById(id);
        saas.setStatus(ReviewStatus.DELETE.getCode());
        saasMapper.updateById(saas);
        
        
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
        saasMapper.updateById(saas);
		
	}

	@Transactional(rollbackFor = Exception.class)
	public Saas setflow(UserVO user, String id, String flowId) {
		
		Saas saas = saasMapper.selectById(id);
		if(saas != null) {
			saas.setWorkFlowId(flowId);
			saasMapper.updateById(saas);
		}
		return saas;
	}

	@Override
	public List<Saas> findSaasConfigByName(String name) {
		List<Saas> selectList = saasMapper.selectList(new QueryWrapper<Saas>().lambda()
                .eq(Saas::getName, name)
                .or()
                .eq(Saas::getShortName, name));
		return selectList;
	}

	@Override
	public Saas getSaasConfigById(String saasConfigId) {
		return saasMapper.selectById(saasConfigId);
	}

}
