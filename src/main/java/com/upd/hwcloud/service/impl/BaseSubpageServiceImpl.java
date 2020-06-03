package com.upd.hwcloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.upd.hwcloud.bean.entity.*;
import com.upd.hwcloud.common.utils.UUIDUtil;
import com.upd.hwcloud.dao.BaseSubpageMapper;
import com.upd.hwcloud.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 二级页面配置 服务实现类
 * </p>
 *
 * @author zwb
 * @since 2019-06-03
 */
public class BaseSubpageServiceImpl<M extends BaseSubpageMapper<T>, T extends BaseSubpage> extends ServiceImpl<M, T> implements IBaseSubpageService<T> {
    @Autowired
    IIaasAppSceneService appSceneService;
    @Autowired
    IIaasFunChaService funChaService;
    @Autowired
    IIaasFunDetailService detailService;
    @Autowired
    private IFilesService filesService;
    @Transactional
    @Override
    public void saveIaasPage(User user, T iaas, String iaasId) {
        iaas.setMasterId(iaasId);
        iaas.setId(UUIDUtil.getUUID());
        baseMapper.insert(iaas);
        List<Files> files = iaas.getFilesList();
        List<IaasAppScene> scenes = iaas.getAppScenes();
        if (scenes != null) {
            appSceneService.save(scenes,iaasId);
        }
        List<IaasFunCha> funChara = iaas.getFunChas();
        funChaService.save(funChara,iaasId);
        List<IaasFunDetail> funDetails = iaas.getFunDetails();
        detailService.save(funDetails,iaasId);
        filesService.refFiles(files,iaas.getId());
    }
@Transactional
    @Override
    public void updateIaasPage(User user, T iaas, String iaasId) {
        deleteSubpage(iaasId);
        saveIaasPage(user,iaas,iaasId);
    }

    private void deleteSubpage(String iaasId) {
        T page = baseMapper.selectOne(new QueryWrapper<T>().lambda().eq(T::getMasterId,iaasId));
        appSceneService.remove(new QueryWrapper<IaasAppScene>().lambda().eq(IaasAppScene::getIaasId,iaasId));
        funChaService.remove(new QueryWrapper<IaasFunCha>().lambda().eq(IaasFunCha::getIaasId,iaasId));
        detailService.remove(new QueryWrapper<IaasFunDetail>().lambda().eq(IaasFunDetail::getIaasId,iaasId));
        if (null!=page){
            filesService.remove(new QueryWrapper<Files>().lambda().eq(Files::getRefId,page.getId()));
        }
        this.remove(new QueryWrapper<T>().lambda().eq(T::getMasterId,iaasId));
    }

    @Override
    public T getDetail(String iaasId) {
        T subpage =  this.getOne(new QueryWrapper<T>().lambda().eq(T::getMasterId,iaasId));
        if (subpage != null) {
            List<Files> filesList = filesService.list(new QueryWrapper<Files>().lambda().eq(Files::getRefId, subpage.getId()));
            subpage.setFilesList(filesList);
            // 功能特点
            List<IaasFunCha> iaasFunChas = funChaService.list(new QueryWrapper<IaasFunCha>().lambda().eq(IaasFunCha::getIaasId, iaasId)
                    .orderByAsc(IaasFunCha::getRemark));
            subpage.setFunChas(iaasFunChas);
            // 应用场景
            List<IaasAppScene> appScenes = appSceneService.getByIaasId(iaasId);
            subpage.setAppScenes(appScenes);
            // 功能详情
            List<IaasFunDetail> details = detailService.getByIaasId(iaasId);
            subpage.setFunDetails(details);
        }
        return subpage;
    }
}
