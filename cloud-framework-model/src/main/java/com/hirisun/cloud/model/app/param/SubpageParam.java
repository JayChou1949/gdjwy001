package com.hirisun.cloud.model.app.param;

import java.io.Serializable;
import java.util.List;

import com.hirisun.cloud.model.app.vo.AppSceneVo;
import com.hirisun.cloud.model.app.vo.FunChaVo;
import com.hirisun.cloud.model.app.vo.FunDetailVo;
import com.hirisun.cloud.model.file.FilesVo;

import lombok.Data;

@Data
public class SubpageParam implements Serializable{

	private static final long serialVersionUID = 4565678967855197955L;
	
	private List<AppSceneVo> appSceneList;
	private List<FunChaVo> funCha;
	private List<FunDetailVo> funDetails;
	private String masterId;
	private List<FilesVo> files;
	private String refId;
	private String subpageId;
	
}
