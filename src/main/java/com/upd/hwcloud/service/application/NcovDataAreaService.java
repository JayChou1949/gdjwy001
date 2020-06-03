package com.upd.hwcloud.service.application;

import com.upd.hwcloud.bean.dto.cov.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author junglefisher
 * @date 2020/5/5 10:46
 */
public interface NcovDataAreaService {
    /**
     * 数据共享情况
     * @return
     */
    DataSharing dataSharingOverview() throws Exception;

    /**
     * 数据建模情况
     * @return
     */
    DataModeling dataModelingOverview() throws Exception;

    /**
     * 数据治理
     * @return
     */
    DataGovernance dataGovernance() throws Exception;

    /**
     * 数据治理表格下载
     * @return
     */
    void downloadFile(HttpServletResponse response,String tableName) throws IOException;

    /**
     * 数据接入
     * @return
     */
    DataAccess dataAccessOverview() throws Exception;

    /**
     * 数据接入-数据类型二级
     * @return
     * @param type 类型
     * @param from 来源
     */
    Map<String, Map<String,Long>> dataTypeLevel2(String type, String from) throws Exception;

    /**
     * 数据服务
     * @return
     */
    DataService dataService() throws Exception;

    /**
     * 首页
     * @return
     */
    HomePageData homePage() throws Exception;

    /**
     * 数据治理二级-根据采集单位查询表名
     * @param unitName 采集单位名称
     * @return
     */
    List<NameAndFileName> getTableNameByUnit(String unitName) throws Exception;

    /**
     * 数据列表及相关数据
     * @return
     */
    List<ServiceData> dataList();

    /**
     * 省直/地市防疫情况
     * @return
     */
    List<PreventionSituation> preventionSituation(String from) throws Exception;

    /**
     * 单位下载排名
     * @return
     */
    List<UnitDownloadExport> unitDownloadExport() throws Exception;

    /**
     * 高频使用资源排名
     * @return
     */
    List<HighFrequencyUseExport> highFrequencyUseExport() throws Exception;

    /**
     * 单位建模排名
     * @return
     */
    List<UnitModelingExport> unitModelingExport() throws Exception;

    /**
     * 公共模型建设单位排名
     * @return
     */
    List<UnitModelingExport> publicModelConstructionUnitExport() throws Exception;

    /**
     * 模型热度排名导出
     * @return
     */
    List<ModelPopularityExport> modelPopularityExport() throws Exception;

    /**
     * 模板文件上传
     * @param file
     */
    void uploadFile(MultipartFile file,String name) throws IOException;

    /**
     * 数据接入三级
     * @param unitName
     * @param from
     * @return
     */
    List<DataAccessLevel3> dataAccessLevel3(String unitName,String from,String type) throws Exception;
}
