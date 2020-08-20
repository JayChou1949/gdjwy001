package com.hirisun.cloud.common.util;



import com.hirisun.cloud.model.common.Tree;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wuxiaoxing 2020-07-29
 *
 * 处理树形结构数据
 */
public class TreeUtils {

    /**
     * 将普通list数据转换成树形结构
     * 方法对对象内容有需求：
     * 1.对象需继承Tree对象
     * 2.pid，用于判断层级关系
     * 3.sort，用于排序
     * 4.children，用于存放子集
     * @return List<? extends Tree>
     */
    public static List<? extends Tree> listWithTree(List<? extends Tree> list) {

        /**
         * 1.过滤出父节点
         * 2.节点添加子集
         * 3.排序
         */
        List<? extends Tree> levelMenus= list.stream().filter(nodeEntity ->
                nodeEntity.getPid()==null
        ).map((menu)->{
            menu.setChildren(getChildrens(menu,list));
            return menu;
        }).sorted((menu1,menu2)->{
            return (int)((menu1.getSort()==null?999:menu1.getSort())-(menu2.getSort()==null?999:menu2.getSort()));
        }).collect(Collectors.toList());
        levelMenus.forEach(dict->{
            if (CollectionUtils.isEmpty(dict.getChildren())) {
                dict.setChildren(null);
            }
        });
        return levelMenus;
    }

    /**
     * 将普通list数据转换成树形结构,从指点节点开始
     */
    public static List<? extends Tree> listWithTreeByPid(List<? extends Tree> list,String pid) {

        /**
         * 1.过滤出父节点
         * 2.节点添加子集
         * 3.排序
         */
        List<? extends Tree> levelMenus= list.stream().filter(nodeEntity ->
                pid.equals(nodeEntity.getPid())
        ).map((menu)->{
            menu.setChildren(getChildrens(menu,list));
            return menu;
        }).sorted((menu1,menu2)->{
            return (int)((menu1.getSort()==null?999:menu1.getSort())-(menu2.getSort()==null?999:menu2.getSort()));
        }).collect(Collectors.toList());
        levelMenus.forEach(dict->{
            if (CollectionUtils.isEmpty(dict.getChildren())) {
                dict.setChildren(null);
            }
        });
        return levelMenus;
    }

    private static List<? extends Tree> getChildrens(Tree root,List<? extends Tree> all){
        List<? extends Tree> levelMenus= all.stream().filter(nodeEntity ->
                nodeEntity.getPid()!=null&&nodeEntity.getPid().equals(root.getId())
        ).map((menu)->{
            menu.setChildren(getChildrens(menu,all));
            return menu;
        }).sorted((menu1,menu2)->{
            return (int)((menu1.getSort()==null?999:menu1.getSort())-(menu2.getSort()==null?999:menu2.getSort()));
        }).collect(Collectors.toList());
        levelMenus.forEach(dict->{
            if (CollectionUtils.isEmpty(dict.getChildren())) {
                dict.setChildren(null);
            }
        });
        return levelMenus;
    }
}
