package com.upd.hwcloud.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.upd.hwcloud.bean.dto.ZTreeNode;
import com.upd.hwcloud.bean.entity.Org;
import com.upd.hwcloud.bean.entity.User;

import java.util.List;

/**
 * <p>
 * 机构表 服务类
 * </p>
 *
 * @author wuc
 * @since 2018-10-12
 */
public interface IOrgService extends IService<Org> {

    /**
     * 获取组织树形菜单
     */
    List<ZTreeNode> tree(User user, Boolean orgOnly, Boolean unitOnly);

    /**
     * 获取某个节点菜单
     * @param orgId 组织 id
     * @param orgOnly
     */
    List<ZTreeNode> getNodeDetail(String orgId, Boolean orgOnly, Boolean unitOnly);

    /**
     * 保存/更新,远程组织数据
     * @param remoteOrg 获取的远程组织
     */
    void saveOrUpdateFromRemote(Org remoteOrg);

    /**
     * 搜索组织,包含机构和厂商
     * @param keyword
     * @return
     */
    List<Org> search(String keyword);

}
