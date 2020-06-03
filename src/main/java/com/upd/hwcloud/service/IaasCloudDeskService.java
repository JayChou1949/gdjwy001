package com.upd.hwcloud.service;

import com.upd.hwcloud.bean.dto.cov.CloudDeskDetail;
import com.upd.hwcloud.bean.dto.cov.CloudDeskHomePage;
import com.upd.hwcloud.bean.dto.cov.CloudDeskNumByAreaOrPolice;

import java.util.List;

/**
 * @author junglefisher
 * @date 2020/3/2 17:57
 */
public interface IaasCloudDeskService {

    CloudDeskHomePage cloudDeskHomePage();

    List<CloudDeskNumByAreaOrPolice> cloudDeskByArea();

    List<CloudDeskDetail> detailByUnit();

    List<CloudDeskNumByAreaOrPolice> cloudDeskByPolice();
}
