package com.hirisun.cloud.model.iaas.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.hirisun.cloud.model.file.FilesVo;
import com.hirisun.cloud.model.pass.vo.PaasReportVo;
import com.hirisun.cloud.model.system.ReportFusionAccessVo;
import com.hirisun.cloud.model.system.ReportSpecialVo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("IAAS资源上报项目信息")
@Data
public class IaasZysbXmxxVo implements Serializable {

	private static final long serialVersionUID = 3906782452917568647L;

	@ApiModelProperty("ID")
    private String id;

	@ApiModelProperty("项目名称")
    private String projectName;

	@ApiModelProperty("项目说明")
    private String projectDesc;

	@ApiModelProperty("申请信息 id")
    private String masterId;

	@ApiModelProperty("创建日期")
    private Date createTime;

	@ApiModelProperty("修改日期")
    private Date modifiedTime;

	@ApiModelProperty("IaaS资源")
    private List<IaasReportVo> iaasList;

	@ApiModelProperty("PaaS资源")
    private List<PaasReportVo> paasList;

	@ApiModelProperty("桌面云资源")
    private List<ReportFusionAccessVo> fusionAccessList;

	@ApiModelProperty("特殊需求")
    private List<ReportSpecialVo> specialList;

	@ApiModelProperty("文件信息")
    private List<FilesVo> fileList;

}
