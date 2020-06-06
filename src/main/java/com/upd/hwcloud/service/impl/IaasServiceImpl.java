package com.upd.hwcloud.service.impl;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.upd.hwcloud.bean.contains.FormNum;
import com.upd.hwcloud.bean.contains.ReviewStatus;
import com.upd.hwcloud.bean.vo.iaasOrder.biangeng.BiangengAfterVo;
import com.upd.hwcloud.bean.vo.iaasOrder.biangeng.BiangengFrontVo;
import com.upd.hwcloud.bean.vo.iaasOrder.biangeng.BiangengVo;
import com.upd.hwcloud.bean.vo.iaasOrder.dashujuZujian.DashujuSqVo;
import com.upd.hwcloud.bean.vo.iaasOrder.dashujuZujian.DashujuVo;
import com.upd.hwcloud.bean.vo.iaasOrder.duixingCC.DuixiangCCImplVo;
import com.upd.hwcloud.bean.vo.iaasOrder.duixingCC.DuixiangCCSqVo;
import com.upd.hwcloud.bean.vo.iaasOrder.duixingCC.DuixiangCCVo;
import com.upd.hwcloud.bean.vo.iaasOrder.tanxingyun.TanxingyunVo;
import com.upd.hwcloud.bean.vo.iaasOrder.tanxingyun.TxyfwImplVo;
import com.upd.hwcloud.bean.vo.iaasOrder.tanxingyun.TxyfwSqVo;
import com.upd.hwcloud.bean.vo.iaasOrder.txfuzaijunheng.TxFuzaijunhengImplVo;
import com.upd.hwcloud.bean.vo.iaasOrder.txfuzaijunheng.TxFuzaijunhengVo;
import com.upd.hwcloud.bean.vo.iaasOrder.txfuzaijunheng.TxfuzaijunhengSqVo;
import com.upd.hwcloud.bean.vo.iaasOrder.wenjianCC.WenjianCCImplVo;
import com.upd.hwcloud.bean.vo.iaasOrder.wenjianCC.WenjianCCSqVo;
import com.upd.hwcloud.bean.vo.iaasOrder.wenjianCC.WenjianCCVo;
import com.upd.hwcloud.bean.vo.iaasOrder.zhuomianyun.ZhuomianyunImplVo;
import com.upd.hwcloud.bean.vo.iaasOrder.zhuomianyun.ZhuomianyunSqVo;
import com.upd.hwcloud.bean.vo.iaasOrder.zhuomianyun.ZhuomianyunVo;
import com.upd.hwcloud.bean.entity.*;
import com.upd.hwcloud.bean.entity.application.*;
import com.upd.hwcloud.common.utils.DateUtil;
import com.upd.hwcloud.common.utils.easypoi.ExcelStyleUtil;
import com.upd.hwcloud.common.utils.easypoi.ExportView;
import com.upd.hwcloud.dao.IaasMapper;
import com.upd.hwcloud.service.IFilesService;
import com.upd.hwcloud.service.IIaasService;
import com.upd.hwcloud.service.IOperateRecordService;
import com.upd.hwcloud.service.IUserService;
import com.upd.hwcloud.service.application.*;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * IaaS 表 服务实现类
 * </p>
 *
 * @author wuc
 * @since 2018-10-25
 */
@Service
public class IaasServiceImpl extends ServiceImpl<IaasMapper, Iaas> implements IIaasService {

    @Autowired
    private IOperateRecordService operateRecordService;
    @Autowired
    private IaasMapper iaasMapper;
    @Autowired
    private IUserService userService;
    @Autowired
    private IFilesService filesService;
    @Autowired
    private IApplicationInfoService applicationInfoService;
    @Autowired
    private IIaasTxyfwService iaasTxyfwService;
    @Autowired
    private IIaasTxyfwImplService iaasTxyfwImplService;
    @Autowired
    private IIaasTxyfwxgService iaasTxyfwxgService;
    @Autowired
    private IIaasTxyfwxgImplService iaasTxyfwxgImplService;
    @Autowired
    private IPaasGawdsjptService paasGawdsjptService;
    @Autowired
    private IIaasYzmyzyService iaasYzmyzyService;
    @Autowired
    private IIaasYzmyzyUserService iaasYzmyzyUserService;
    @Autowired
    private IIaasDxccExtService iaasDxccExtService;
    @Autowired
    private IIaasDxccService iaasDxccService;
    @Autowired
    private IIaasDxccImplService iaasDxccImplService;
    @Autowired
    private IIaasDxccImplExtService iaasDxccImplExtService;
    @Autowired
    private IIaasSfswjfwService iaasSfswjfwService;
    @Autowired
    private IIaasSfswjfwImplService iaasSfswjfwImplService;
    @Autowired
    private IIaasTxyfzjhExtService iaasTxyfzjhExtService;
    @Autowired
    private IIaasTxyfzjhImplService iaasTxyfzjhImplService;

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void hotfix() {
        List<Iaas> iaasList = this.list(new QueryWrapper<Iaas>().lambda().isNotNull(Iaas::getImage));

        for(Iaas p : iaasList){
            Files f = filesService.getOne(new QueryWrapper<Files>().lambda().eq(Files::getUrl,p.getImage()));
            if(f != null){
                p.setRealImage(f.getRealURL());
            }
        }

        if(CollectionUtils.isNotEmpty(iaasList)){
            this.updateBatchById(iaasList,50);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void publish(User user, String id, Integer result, String remark) {
        Iaas iaas = this.getById(id);
        if (result.equals(1)) { // 上线
            iaas.setStatus(ReviewStatus.ONLINE.getCode());
            iaas.updateById();

            operateRecordService.insertRecord(id, user.getIdcard(), "上/下线", "上线", remark);
        } else { // 下线
            iaas.setStatus(ReviewStatus.PRO_ONLINE.getCode());
            iaas.updateById();

            operateRecordService.insertRecord(id, user.getIdcard(), "上/下线", "下线", remark);
        }
    }

    @Override
    public IPage<Iaas> getPage(IPage<Iaas> page, User user, Integer status, String name, String subType) {
        return iaasMapper.getPage(page, user, status, name, subType);
    }

    @Override
    public Iaas getDetail(User user, String id) {
        Iaas iaas = this.getById(id);
        if (iaas != null) {
            User creator = userService.findUserByIdcard(iaas.getCreator());
            iaas.setUser(creator);
            List<Files> filesList = filesService.list(new QueryWrapper<Files>().lambda().eq(Files::getRefId, id));
            iaas.setFileList(filesList);
        }
        return iaas;
    }

    @Override
    public List<Map<String, Object>> getLabelWithCount() {
        List<Subpage> subpageList = iaasMapper.getLabel(null, 1);
        Map<String, Set<String>> labelMap = new HashMap<>();
        for (Subpage subpage : subpageList) {
            String serviceId = subpage.getServiceId();
            String label = subpage.getUse();
            if (labelMap.get(serviceId) == null) {
                Set<String> collect = Arrays.stream(label.split("[;；]")).collect(Collectors.toSet());
                labelMap.put(serviceId, collect);
            } else {
                Set<String> collect = Arrays.stream(label.split("[;；]")).collect(Collectors.toSet());
                labelMap.get(serviceId).addAll(collect);
            }
        }

        Set<String> labelSet = subpageList.stream().map(Subpage::getUse).collect(Collectors.toSet());
        Set<String> label = labelSet.stream().flatMap(s -> Arrays.stream(s.split("[;；]")))
                .collect(Collectors.toSet());

        Map<String, Integer> count = new HashMap<>();
        for (String s : label) {
            for (Map.Entry<String, Set<String>> entry : labelMap.entrySet()) {
                if (entry.getValue().contains(s)) {
                    if (count.get(s) == null) {
                        count.put(s, 1);
                    } else {
                        count.put(s, count.get(s) + 1);
                    }
                }
            }
        }
        ArrayList<Map.Entry<String, Integer>> collect = new ArrayList<>(count.entrySet());
        collect.sort(Comparator.comparing(Map.Entry::getValue));
        Collections.reverse(collect);

        List<Map<String, Object>> result = new ArrayList<>();
        for (Map.Entry<String, Integer> e : collect) {
            Map<String, Object> m = new HashMap<>(2);
            m.put("label", e.getKey());
            m.put("count", e.getValue());
            result.add(m);
        }

        return result;
    }

    @Override
    public List<Iaas> getOneClickPage(String label, String keyword) {
        return iaasMapper.getPageByCondition(label, null, 1, keyword);
    }

    @Transactional
    @Override
    public Boolean serviceSort(String id, String ope) {
        Iaas entity = baseMapper.selectById(id);
        Iaas change = null;
        if ("down".equals(ope)) {
            List<Iaas> nexts = baseMapper.selectPage(new Page<Iaas>(1, 1), new QueryWrapper<Iaas>().eq("status", entity.getStatus()).gt("sort", entity.getSort()).orderByAsc("sort")).getRecords();
            if (nexts != null && nexts.size() == 1) {
                change = nexts.get(0);
            }
        } else if ("up".equals(ope)) {
            List<Iaas> pres = baseMapper.selectPage(new Page<Iaas>(1, 1), new QueryWrapper<Iaas>().eq("status", entity.getStatus()).lt("sort", entity.getSort()).orderByDesc("sort")).getRecords();
            if (pres != null && pres.size() == 1) {
                change = pres.get(0);
            }
        }
        if (change != null) {
            Long sort = change.getSort();
            change.setSort(entity.getSort());
            entity.setSort(sort);
            baseMapper.updateById(entity);
            baseMapper.updateById(change);
        }
        return true;
    }

    /**
     * 解析存储配置
     * @param specification
     * @return
     */
    public String[] analysisSpecification(String specification){
        if (!StringUtils.isEmpty(specification)){
            if (specification.contains("/")){
                return specification.split("/");
            }
        }
        return null;
    }

    @Override
    public ExportView getTanxingyunData(Map<String, Object> params) throws InvocationTargetException, IllegalAccessException {
        params.put("form_num",FormNum.IAAS_TXYFW.name());
        List<ApplicationInfo> applicationInfoList = applicationInfoService.iaasOrderStatistics(params);
        List<TanxingyunVo> data = new ArrayList<>();
        for (int i = 0; i < applicationInfoList.size(); i++) {
            TanxingyunVo vo = new TanxingyunVo();
            ApplicationInfo info = applicationInfoList.get(i);
            BeanUtils.copyProperties(vo, info);
            vo.setId(i + 1);//ID
            vo.setApplicationTimeStart(DateUtil.formatDate(info.getApplicationTime()));//申请日期
            vo.setApplicationTimeEnd(DateUtil.formatDate(info.getBusinessHandlingTime()));//完成日期
            //申请配置信息
            String id = info.getId();
            List<IaasTxyfw> list1 = iaasTxyfwService.list(new QueryWrapper<IaasTxyfw>().eq("APP_INFO_ID", id).orderByDesc("CREATE_TIME"));
            List<TxyfwSqVo> txyfwSqVoList = new ArrayList<>();
            for (IaasTxyfw iaasTxyfw : list1) {
                TxyfwSqVo txyfwSqVo = new TxyfwSqVo();
                BeanUtils.copyProperties(txyfwSqVo, iaasTxyfw);
                //解析存储配置
                String[] strings =analysisSpecification(iaasTxyfw.getSpecification());
                if (strings != null){
                    txyfwSqVo.setCpu(strings[0]);
                    txyfwSqVo.setRam(strings[1]);
                }
                txyfwSqVoList.add(txyfwSqVo);
            }
            vo.setTxyfwSqVoList(txyfwSqVoList);
            //实施配置信息
            List<IaasTxyfwImpl> list2 = iaasTxyfwImplService.list(new QueryWrapper<IaasTxyfwImpl>().eq("APP_INFO_ID", id).orderByDesc("CREATE_TIME"));
            List<TxyfwImplVo> txyfwImplVoList = new ArrayList<>();
            for (IaasTxyfwImpl iaasTxyfwImpl : list2) {
                TxyfwImplVo txyfwImplVo = new TxyfwImplVo();
                BeanUtils.copyProperties(txyfwImplVo, iaasTxyfwImpl);
                //解析存储配置
                String[] strings =analysisSpecification(iaasTxyfwImpl.getSpecification());
                if (strings != null){
                    txyfwImplVo.setCpu(strings[0]);
                    txyfwImplVo.setRam(strings[1]);
                }
                txyfwImplVoList.add(txyfwImplVo);
            }
            vo.setTxyfwImplVoList(txyfwImplVoList);
            data.add(vo);
        }
        //设置title和sheet
        ExportParams exportParams = new ExportParams("弹性云申请发放情况表", "弹性云");
        exportParams.setStyle(ExcelStyleUtil.class);
        return new ExportView.Builder().exportParams(exportParams).cls(TanxingyunVo.class).dataList(data).create();
    }

    @Override
    public ExportView getBiangengData(Map<String, Object> params) throws InvocationTargetException, IllegalAccessException {
        params.put("form_num",FormNum.IAAS_TXYFWXG.name());
        List<ApplicationInfo> applicationInfoList = applicationInfoService.iaasOrderStatistics(params);
        List<BiangengVo> data = new ArrayList<>();
        for (int i = 0; i < applicationInfoList.size(); i++) {
            BiangengVo vo = new BiangengVo();
            ApplicationInfo info = applicationInfoList.get(i);
            BeanUtils.copyProperties(vo, info);
            vo.setId(i + 1);//ID
            vo.setApplicationTimeStart(DateUtil.formatDate(info.getApplicationTime()));//申请日期
            vo.setApplicationTimeEnd(DateUtil.formatDate(info.getBusinessHandlingTime()));//完成日期
            //申请配置信息
            String id = info.getId();
            List<IaasTxyfwxg> list1 = iaasTxyfwxgService.list(new QueryWrapper<IaasTxyfwxg>().eq("APP_INFO_ID", id).orderByDesc("CREATE_TIME"));
            List<BiangengFrontVo> frontVoList = new ArrayList<>();
            for (IaasTxyfwxg txyfwxg : list1) {
                BiangengFrontVo biangengFrontVo = new BiangengFrontVo();
                BeanUtils.copyProperties(biangengFrontVo, txyfwxg);
                //解析存储配置
                String[] strings =analysisSpecification(txyfwxg.getSpecificationOld());
                if (strings != null){
                    biangengFrontVo.setCpu(strings[0]);
                    biangengFrontVo.setRam(strings[1]);
                }
                frontVoList.add(biangengFrontVo);
            }
            vo.setFrontVoList(frontVoList);
            //实施配置信息
            List<IaasTxyfwxgImpl> list2 = iaasTxyfwxgImplService.list(new QueryWrapper<IaasTxyfwxgImpl>().eq("APP_INFO_ID", id).orderByDesc("CREATE_TIME"));
            List<BiangengAfterVo> biangengAfterVoList = new ArrayList<>();
            for (IaasTxyfwxgImpl iaasTxyfwxgImpl : list2) {
                BiangengAfterVo biangengAfterVo = new BiangengAfterVo();
                BeanUtils.copyProperties(biangengAfterVo, iaasTxyfwxgImpl);
                //解析存储配置
                String[] strings =analysisSpecification(iaasTxyfwxgImpl.getSpecificationOld());
                if (strings != null){
                    biangengAfterVo.setCpu(strings[0]);
                    biangengAfterVo.setRam(strings[1]);
                }
                biangengAfterVo.setExplanation(info.getExplanation());//变更后说明
                biangengAfterVoList.add(biangengAfterVo);
            }
            vo.setAfterVoList(biangengAfterVoList);
            data.add(vo);
        }
        //设置title和sheet
        ExportParams exportParams = new ExportParams("广东省警务云基础资源变更情况表", "变更");
        exportParams.setStyle(ExcelStyleUtil.class);
        return new ExportView.Builder().exportParams(exportParams).cls(BiangengVo.class).dataList(data).create();
    }

    @Override
    public ExportView getDashujuData(Map<String, Object> params) throws InvocationTargetException, IllegalAccessException {
        params.put("form_num",FormNum.PAAS_GAWDSJPT.name());
        List<ApplicationInfo> applicationInfoList = applicationInfoService.iaasOrderStatistics(params);
        List<DashujuVo> data = new ArrayList<>();
        for (int i = 0; i < applicationInfoList.size(); i++) {
            DashujuVo vo = new DashujuVo();
            ApplicationInfo info = applicationInfoList.get(i);
            BeanUtils.copyProperties(vo, info);
            vo.setApplicationTimeStart(DateUtil.formatDate(info.getApplicationTime()));//申请日期
            vo.setApplicationTimeEnd(DateUtil.formatDate(info.getBusinessHandlingTime()));//完成日期
            //申请配置信息
            String id = info.getId();
            List<PaasGawdsjpt> list1 = paasGawdsjptService.list(new QueryWrapper<PaasGawdsjpt>().eq("APP_INFO_ID", id).orderByDesc("CREATE_TIME"));
            List<DashujuSqVo> sqVoList = new ArrayList<>();
            for (PaasGawdsjpt paasGawdsjpt : list1) {
                DashujuSqVo dashujuSqVo = new DashujuSqVo();
                BeanUtils.copyProperties(dashujuSqVo, paasGawdsjpt);
                sqVoList.add(dashujuSqVo);
            }
            if (list1.size() > 0) BeanUtils.copyProperties(vo, list1.get(0));//配置信息
            vo.setId(i + 1);//ID
            vo.setSqVoList(sqVoList);
            //实施配置信息

            data.add(vo);
        }
        //设置title和sheet
        ExportParams exportParams = new ExportParams("广东省公安网大数据平台资源申请汇总表", "大数据组件");
        exportParams.setStyle(ExcelStyleUtil.class);
        return new ExportView.Builder().exportParams(exportParams).cls(DashujuVo.class).dataList(data).create();

    }

    @Override
    public ExportView getZhuomianyunData(Map<String, Object> params) throws InvocationTargetException, IllegalAccessException {
        params.put("form_num",FormNum.IAAS_YZMYZY.name());
        List<ApplicationInfo> applicationInfoList = applicationInfoService.iaasOrderStatistics(params);
        List<ZhuomianyunVo> data = new ArrayList<>();
        for (int i = 0; i < applicationInfoList.size(); i++) {
            ZhuomianyunVo vo = new ZhuomianyunVo();
            ApplicationInfo info = applicationInfoList.get(i);
            BeanUtils.copyProperties(vo, info);
            String id = info.getId();
            //用途、归属
            IaasYzmyzy iaasYzmyzy = iaasYzmyzyService.getOne(new QueryWrapper<IaasYzmyzy>().eq("APP_INFO_ID", id));
            BeanUtils.copyProperties(vo, iaasYzmyzy);
            vo.setId(i + 1);//ID
            vo.setApplicationTimeStart(DateUtil.formatDate(info.getApplicationTime()));//申请日期
            vo.setApplicationTimeEnd(DateUtil.formatDate(info.getBusinessHandlingTime()));//完成日期
            List<IaasYzmyzyUser> list1 = iaasYzmyzyUserService.list(new QueryWrapper<IaasYzmyzyUser>().eq("APP_INFO_ID", id).orderByDesc("CREATE_TIME").eq("APPLY_TYPE",0));
            List<ZhuomianyunSqVo> sqVoList = new ArrayList<>();//申请配置信息
            List<ZhuomianyunImplVo> implVoList = new ArrayList<>();//实施配置信息
            for (IaasYzmyzyUser iaasYzmyzyUser : list1) {
                ZhuomianyunSqVo zhuomianyunSqVo = new ZhuomianyunSqVo();
                BeanUtils.copyProperties(zhuomianyunSqVo, iaasYzmyzyUser);
                sqVoList.add(zhuomianyunSqVo);
                ZhuomianyunImplVo zhuomianyunImplVo = new ZhuomianyunImplVo();
                BeanUtils.copyProperties(zhuomianyunImplVo, iaasYzmyzyUser);
                implVoList.add(zhuomianyunImplVo);
            }
            vo.setSqVoList(sqVoList);
            //实施配置信息 和申请一样 需要判断是否实施
            vo.setImplVoList(implVoList);
            data.add(vo);
        }
        //设置title和sheet
        ExportParams exportParams = new ExportParams("广东省警务云平台桌面云申请汇总表", "桌面云");
        exportParams.setStyle(ExcelStyleUtil.class);
        return new ExportView.Builder().exportParams(exportParams).cls(ZhuomianyunVo.class).dataList(data).create();

    }

    @Override
    public ExportView getDuixiangCCData(Map<String, Object> params) throws InvocationTargetException, IllegalAccessException {
        params.put("form_num",FormNum.IAAS_DXCC.name());
        List<ApplicationInfo> applicationInfoList = applicationInfoService.iaasOrderStatistics(params);
        List<DuixiangCCVo> data = new ArrayList<>();
        for (int i = 0; i < applicationInfoList.size(); i++) {
            DuixiangCCVo vo = new DuixiangCCVo();
            ApplicationInfo info = applicationInfoList.get(i);
            BeanUtils.copyProperties(vo, info);
            vo.setApplicationTimeStart(DateUtil.formatDate(info.getApplicationTime()));//申请日期
            vo.setApplicationTimeEnd(DateUtil.formatDate(info.getBusinessHandlingTime()));//完成日期
            String id = info.getId();
            //同一用户账号
            IaasDxccExt iaasDxccExt = iaasDxccExtService.getOne(new QueryWrapper<IaasDxccExt>().eq("APP_INFO_ID", id));
            if (iaasDxccExt != null) {
                vo.setTyyhAccount(iaasDxccExt.getTyyhAccount());
            }
            //申请配置信息
            List<IaasDxcc> list1 = iaasDxccService.list(new QueryWrapper<IaasDxcc>().eq("APP_INFO_ID", id).orderByDesc("CREATE_TIME"));
            if(list1!=null){
                List<DuixiangCCSqVo> sqVoList = new ArrayList<>();
                for (IaasDxcc iaasDxcc : list1) {
                    DuixiangCCSqVo duixiangCCSqVo = new DuixiangCCSqVo();
                    BeanUtils.copyProperties(duixiangCCSqVo, iaasDxcc);
                    sqVoList.add(duixiangCCSqVo);
                }
                vo.setSqVoList(sqVoList);
            }
            //账号、访问域名、访问IP
            IaasDxccImpl iaasDxccImpl = iaasDxccImplService.getOne(new QueryWrapper<IaasDxccImpl>().eq("APP_INFO_ID", id));
            if(iaasDxccImpl != null){
                BeanUtils.copyProperties(vo, iaasDxccImpl);
            }
            vo.setId(i + 1);//ID
            //实施配置信息
            List<IaasDxccImplExt> list2 = iaasDxccImplExtService.list(new QueryWrapper<IaasDxccImplExt>().eq("APP_INFO_ID", id).orderByDesc("CREATE_TIME"));
            List<DuixiangCCImplVo> duixiangCCImplVoList = new ArrayList<>();
            for (IaasDxccImplExt iaasDxccImplExt : list2) {
                DuixiangCCImplVo duixiangCCImplVo = new DuixiangCCImplVo();
                BeanUtils.copyProperties(duixiangCCImplVo, iaasDxccImplExt);
                duixiangCCImplVoList.add(duixiangCCImplVo);
            }
            vo.setImplVoList(duixiangCCImplVoList);
            data.add(vo);
        }
        //设置title和sheet
        ExportParams exportParams = new ExportParams("广东省警务云平台对象存储资源申请汇总表", "对象存储");
        exportParams.setStyle(ExcelStyleUtil.class);
        return new ExportView.Builder().exportParams(exportParams).cls(DuixiangCCVo.class).dataList(data).create();
    }

    @Override
    public ExportView getWenjianCCData(Map<String, Object> params) throws InvocationTargetException, IllegalAccessException {
        params.put("form_num",FormNum.IAAS_SFSWJFW.name());
        List<ApplicationInfo> applicationInfoList = applicationInfoService.iaasOrderStatistics(params);
        List<WenjianCCVo> data = new ArrayList<>();
        for (int i = 0; i < applicationInfoList.size(); i++) {
            WenjianCCVo vo = new WenjianCCVo();
            ApplicationInfo info = applicationInfoList.get(i);
            BeanUtils.copyProperties(vo, info);
            vo.setId(i + 1);//ID
            vo.setApplicationTimeStart(DateUtil.formatDate(info.getApplicationTime()));//申请日期
            vo.setApplicationTimeEnd(DateUtil.formatDate(info.getBusinessHandlingTime()));//完成日期
            String id = info.getId();
            //申请配置信息
            List<IaasSfswjfw> list1 = iaasSfswjfwService.list(new QueryWrapper<IaasSfswjfw>().eq("APP_INFO_ID", id).orderByDesc("CREATE_TIME"));
            List<WenjianCCSqVo> sqVoList = new ArrayList<>();
            for (IaasSfswjfw iaasSfswjfw : list1) {
                WenjianCCSqVo wenjianCCSqVo = new WenjianCCSqVo();
                BeanUtils.copyProperties(wenjianCCSqVo, iaasSfswjfw);
                sqVoList.add(wenjianCCSqVo);
            }
            vo.setSqVoList(sqVoList);
            //实施配置信息
            List<IaasSfswjfwImpl> list2 = iaasSfswjfwImplService.list(new QueryWrapper<IaasSfswjfwImpl>().eq("APP_INFO_ID", id).orderByDesc("CREATE_TIME"));
            List<WenjianCCImplVo> wenjianCCImplVoList = new ArrayList<>();
            for (IaasSfswjfwImpl iaasSfswjfwImpl : list2) {
                WenjianCCImplVo wenjianCCImplVo = new WenjianCCImplVo();
                BeanUtils.copyProperties(wenjianCCImplVo, iaasSfswjfwImpl);
                wenjianCCImplVoList.add(wenjianCCImplVo);
            }
            vo.setImplVoList(wenjianCCImplVoList);
            data.add(vo);
        }
        //设置title和sheet
        ExportParams exportParams = new ExportParams("广东省警务云平台文件存储资源申请汇总表", "文件存储");
        exportParams.setStyle(ExcelStyleUtil.class);
        return new ExportView.Builder().exportParams(exportParams).cls(WenjianCCVo.class).dataList(data).create();

    }

    @Override
    public ExportView getTXFuzaijunhengData(Map<String, Object> params) throws InvocationTargetException, IllegalAccessException {
        params.put("form_num",FormNum.IAAS_TXYFZJH.name());
        List<ApplicationInfo> applicationInfoList = applicationInfoService.iaasOrderStatistics(params);
        List<TxFuzaijunhengVo> data = new ArrayList<>();
        for (int i = 0; i < applicationInfoList.size(); i++) {
            TxFuzaijunhengVo vo = new TxFuzaijunhengVo();
            ApplicationInfo info = applicationInfoList.get(i);
            BeanUtils.copyProperties(vo, info);
            vo.setId(i + 1);//ID
            vo.setApplicationTimeStart(DateUtil.formatDate(info.getApplicationTime()));//申请日期
            vo.setApplicationTimeEnd(DateUtil.formatDate(info.getBusinessHandlingTime()));//完成日期
            String id = info.getId();
            //申请配置信息
            List<IaasTxyfzjhExt> list1 = iaasTxyfzjhExtService.list(new QueryWrapper<IaasTxyfzjhExt>().eq("APP_INFO_ID", id).orderByDesc("CREATE_TIME"));
            List<TxfuzaijunhengSqVo> sqVoList = new ArrayList<>();
            for (IaasTxyfzjhExt iaasTxyfzjhExt : list1) {
                TxfuzaijunhengSqVo txfuzaijunhengSqVo = new TxfuzaijunhengSqVo();
                BeanUtils.copyProperties(txfuzaijunhengSqVo, iaasTxyfzjhExt);
                sqVoList.add(txfuzaijunhengSqVo);
            }
            vo.setSqVoList(sqVoList);
            //实施配置信息
            List<IaasTxyfzjhImpl> list2 = iaasTxyfzjhImplService.list(new QueryWrapper<IaasTxyfzjhImpl>().eq("APP_INFO_ID", id).orderByDesc("CREATE_TIME"));
            List<TxFuzaijunhengImplVo> txFuzaijunhengImplVoList = new ArrayList<>();
            for (IaasTxyfzjhImpl iaasTxyfzjhImpl : list2) {
                TxFuzaijunhengImplVo txFuzaijunhengImplVo = new TxFuzaijunhengImplVo();
                BeanUtils.copyProperties(txFuzaijunhengImplVo, iaasTxyfzjhImpl);
                txFuzaijunhengImplVoList.add(txFuzaijunhengImplVo);
            }
            vo.setImplVoList(txFuzaijunhengImplVoList);
            data.add(vo);
        }
        //设置title和sheet
        ExportParams exportParams = new ExportParams("广东省警务云平台弹性负载均衡资源申请汇总表", "弹性负载均衡");
        exportParams.setStyle(ExcelStyleUtil.class);
        return new ExportView.Builder().exportParams(exportParams).cls(TxFuzaijunhengVo.class).dataList(data).create();

    }
}
