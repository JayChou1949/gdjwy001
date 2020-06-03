package com.upd.hwcloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.upd.hwcloud.bean.contains.OrgTreeNodeType;
import com.upd.hwcloud.bean.contains.UnifledUserType;
import com.upd.hwcloud.bean.dto.ZTreeNode;
import com.upd.hwcloud.bean.entity.Manufacturers;
import com.upd.hwcloud.bean.entity.Org;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.dao.OrgMapper;
import com.upd.hwcloud.service.IManufacturersService;
import com.upd.hwcloud.service.IOrgService;
import com.upd.hwcloud.service.IUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 机构表 服务实现类
 * </p>
 *
 * @author wuc
 * @since 2018-10-12
 */
@Service
public class OrgServiceImpl extends ServiceImpl<OrgMapper, Org> implements IOrgService {

    private static final String CS_ROOT_ID = "123456";
    private static final String CS_ROOT_NAME = "服务提供商";

    @Autowired
    private OrgMapper orgMapper;
    @Autowired
    private IUserService userService;
    @Autowired
    private IManufacturersService manufacturersService;

    @Override
    public List<ZTreeNode> tree(User user, Boolean orgOnly, Boolean unitOnly) {
        // 是否显示人员节点
        boolean showUser = !orgOnly;
        // 是否显示厂商节点
        boolean showManufacturer = !unitOnly;

        List<ZTreeNode> nodeList = new ArrayList<>();
        String userType = user.getUserType();
        // 用户类型为空,或者施工人员,只显示顶级节点
        if (StringUtils.isEmpty(userType) || UnifledUserType.WB.getCode().equals(userType)) {
            Org orgRoot = this.getOne(new QueryWrapper<Org>().lambda().isNull(Org::getUpGovId));
            nodeList.add(this.convert2Node4Org(orgRoot));
        } else {
            // 获取对应用户的树形结构
            List<Org> orgTree = orgMapper.queryParentTreeByOrgId(user.getOrgId());
            List<String> orgIds = orgTree.stream().map(Org::getId).collect(Collectors.toList());
            // 获取要展开的节点下的所有组织和根节点组织
            List<Org> allOrgList = this.list(new QueryWrapper<Org>().lambda()
                    .in(Org::getUpGovId, orgIds)
                    .or()
                    .isNull(Org::getUpGovId)
                    .orderByAsc(Org::getCode));
            // 将组织信息转换为节点信息,并缓存为map
            List<ZTreeNode> allOrgNodeList = new ArrayList<>();
            Map<String, ZTreeNode> orgMap = new HashMap<>();
            for (Org org : allOrgList) {
                ZTreeNode node = this.convert2Node4Org(org);
                allOrgNodeList.add(node);
                orgMap.put(node.getId(), node);
            }
            // 获取要展开的节点下的所有人员
            List<User> allUserList = null;
            if (showUser) {
                allUserList = userService.findUserByOrgIds(orgIds);
            }
            // 待展开节点,和待展开节点下面的人员信息
            for (String orgId : orgIds) {
                ZTreeNode node = orgMap.get(orgId);
                if (node != null) {
                    node.setOpen(true);
                    // pid != null表示直接在根节点下面的用户不显示
                    if (node.getPid() != null && showUser) {
                        List<User> userList = allUserList.stream().filter(u -> node.getId().equals(u.getOrgId())).collect(Collectors.toList());
                        node.getChildren().addAll(this.convert2Node4UserList(userList));
                    }
                }
            }
            // 组合节点
            ZTreeNode orgRootNode = null;
            for (ZTreeNode node : allOrgNodeList) {
                if (node.getPid() == null) {
                    orgRootNode = node;
                } else {
                    ZTreeNode parent = orgMap.get(node.getPid());
                    if (parent != null) {
                        parent.getChildren().add(node);
                    }
                }
            }
            if (orgRootNode != null) {
                nodeList.add(orgRootNode);
            }
        }
        // 服务提供商节点
        if (showManufacturer) {
            ZTreeNode mRootNode = this.buildManufacturerRootNode();
            nodeList.add(mRootNode);
        }
        return nodeList;
    }

    private ZTreeNode buildManufacturerRootNode() {
        ZTreeNode node = new ZTreeNode();
        node.setId(CS_ROOT_ID);
        node.setPid(null);
        node.setName(CS_ROOT_NAME);
        node.setParent(true);
        node.setNodeType(OrgTreeNodeType.ORG);
        return node;
    }

    @Override
    public List<ZTreeNode> getNodeDetail(String orgId, Boolean orgOnly, Boolean unitOnly) {
        // 是否显示人员节点
        boolean showUser = !orgOnly;
        // 是否显示厂商节点
        boolean showManufacturer = !unitOnly;

        // orgId可能为某个厂商,或某个应用,或某个组织,或自己构建的'服务提供商'节点
        List<ZTreeNode> nodeList = new ArrayList<>();
        if (showManufacturer) {
            if (CS_ROOT_ID.equals(orgId)) {
                // orgId为服务提供商节点
                List<Manufacturers> allManufacturers = manufacturersService.list(
                        new QueryWrapper<Manufacturers>().lambda().orderByAsc(Manufacturers::getCode));
                nodeList.addAll(this.convert2Node4ManufacturerList(allManufacturers));
                return nodeList;
            }
            Manufacturers manufacturers = manufacturersService.getById(orgId);
            if (manufacturers != null) {
                if (showUser) {
                    // orgId为厂商,查厂商下的人员
                    List<User> userByOrgIds = userService.findUserByManufacturerId(orgId);
                    if (userByOrgIds != null && !userByOrgIds.isEmpty()) {
//                        userByOrgIds.forEach(user -> {
//                            user.setOrgName(manufacturers.getManufacturerName());
//                        });
                        nodeList.addAll(this.convert2Node4UserList(userByOrgIds));
                    }
                }
                return nodeList;
            }
        }
        Org org = this.getById(orgId);
        if (org != null) {
            // orgId为组织
            if (showUser) {
                // 获取该组织下面的所有人员,并转换为节点
                List<User> userByOrgIds = userService.findUserByOrgIds(Collections.singletonList(orgId));
                nodeList.addAll(this.convert2Node4UserList(userByOrgIds));
            }
            // 获取该组织下面的所有组织,并转换为节点,和人员节点进行组合
            List<Org> orgList = this.list(
                    new QueryWrapper<Org>().lambda().eq(Org::getUpGovId, orgId).orderByAsc(Org::getCode));
            for (Org o : orgList) {
                ZTreeNode n = this.convert2Node4Org(o);
                nodeList.add(n);
            }
        }
        return nodeList;
    }

    @Override
    public void saveOrUpdateFromRemote(Org remoteOrg) {
        if(StringUtils.isNotEmpty(remoteOrg.getId())) {
            // 查询数据库
            Org dbOrg = this.getById(remoteOrg.getId());
            if (dbOrg == null) {
                // 本地不存在,插入
                remoteOrg.insert();
            } else {
                // 本地存在,更新
                remoteOrg.updateById();
            }
        }
    }

    /**
     * 搜索组织,包含机构和厂商
     *
     * @param keyword
     * @return
     */
    @Override
    public List<Org> search(String keyword) {
        List<Org> list = orgMapper.search(keyword);
        return list;
    }


    /**
     * 将组织转换为节点
     */
    private ZTreeNode convert2Node4Org(Org org) {
        ZTreeNode node = new ZTreeNode();
        node.setId(org.getId());
        node.setPid(org.getUpGovId());
        node.setName(org.getFullName());
        node.setParent(true);
        node.setNodeType(OrgTreeNodeType.ORG);
        return node;
    }

    /**
     * 将人员转换为节点
     */
    private List<ZTreeNode> convert2Node4UserList(List<User> users) {
        List<ZTreeNode> children = new ArrayList<>();
        if (users == null || users.isEmpty()) {
            return children;
        }

        for (User user : users) {
            ZTreeNode node = new ZTreeNode();
            node.setId(user.getIdcard());
            node.setPid(user.getOrgId());
            node.setName(user.getName());
            node.setMobileWork(user.getMobileWork());
            node.setOrgName(user.getOrgName());
            node.setParent(false);
            node.setOpen(false);
            node.setPerson(true);
            node.setNodeType(OrgTreeNodeType.USER);
            children.add(node);
        }
        return children;
    }

    /**
     * 将厂商转换为节点
     */
    private List<ZTreeNode> convert2Node4ManufacturerList(List<Manufacturers> manufacturers) {
        List<ZTreeNode> children = new ArrayList<>();
        if (manufacturers == null || manufacturers.isEmpty()) {
            return children;
        }
        for (Manufacturers manufacturer : manufacturers) {
            ZTreeNode node = new ZTreeNode();
            node.setId(manufacturer.getId());
            node.setPid(null);
            node.setName(manufacturer.getManufacturerName());
            node.setParent(true);
            node.setNodeType(OrgTreeNodeType.MANUFACTURER);
            node.setCode(manufacturer.getCode());
            children.add(node);
        }
        return children;
    }


}
