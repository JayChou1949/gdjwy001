package com.hirisun.cloud.order.service.shopping.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.hirisun.cloud.api.system.FilesApi;
import com.hirisun.cloud.common.contains.ResourceType;
import com.hirisun.cloud.model.app.param.SubpageParam;
import com.hirisun.cloud.model.file.FilesVo;
import com.hirisun.cloud.model.param.FilesParam;
import com.hirisun.cloud.model.shopping.vo.ShoppingCartVo;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.order.bean.shopping.ShoppingCart;
import com.hirisun.cloud.order.continer.FormNum;
import com.hirisun.cloud.order.continer.HandlerWrapper;
import com.hirisun.cloud.order.continer.IApplicationHandler;
import com.hirisun.cloud.order.continer.ShoppingCartStatus;
import com.hirisun.cloud.order.mapper.shopping.ShoppingCartMapper;
import com.hirisun.cloud.order.service.application.IApplicationInfoService;
import com.hirisun.cloud.order.service.shopping.ShoppingCartService;
import com.hirisun.cloud.order.vo.SubmitRequest;

@Service
public class ShoppingCartNewServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService{

	private static final Logger logger = LoggerFactory.getLogger(ShoppingCartServiceImpl.class);
	
	@Autowired
	private FilesApi filesApi;
	@Autowired
	private ShoppingCartMapper shoppingCartMapper;
	@Autowired
	private IApplicationInfoService applicationInfoService;
	@Autowired
	private ApplicationContext context;
	
	@Override
	public void create(UserVO user, String json, ShoppingCartVo shoppingCartVo) {
		
		String formNum = shoppingCartVo.getFormNum();
		
		HandlerWrapper hw = FormNum.getHandlerWrapperByName(formNum);
		shoppingCartVo.setResourceType(hw.getFormNum().getResourceType().getCode());
		shoppingCartVo.setFormNum(hw.getFormNum().name());
		shoppingCartVo.setStatus(ShoppingCartStatus.WAIT_SUBMIT.getCode());
		shoppingCartVo.setCreatorIdCard(user.getIdCard());
		shoppingCartVo.setCreatorName(user.getName());
		
		ShoppingCart shoppingCart = new ShoppingCart();
		BeanUtils.copyProperties(shoppingCartVo, shoppingCart);
		
		//保存购物车
		shoppingCartMapper.insert(shoppingCart);
		
		shoppingCartVo.setId(shoppingCart.getId());
		
		logger.debug("create json -> {}",json);
//        ShoppingCart shoppingCart = parseShoppingCart(json,hw.getApplicationType());
        logger.debug("parseShoppingCart -> {}",shoppingCart);

        //保存购物车购物项
        //applicationInfoService.addToShoppingCart(user, shoppingCart, hw.getHandler());
        saveShoppingCartItem(shoppingCartVo);
        
        //保存文件
        if(CollectionUtils.isNotEmpty(shoppingCart.getFileList())){
            logger.debug("ref file");
            refFiles(shoppingCart.getFileList(),shoppingCart.getId());
        }
	}

	private void saveShoppingCartItem(ShoppingCartVo shoppingCartVo) {
		
		String formNum = shoppingCartVo.getFormNum();
		if(formNum.startsWith("IAAS")) {
			
		}else if(formNum.startsWith("DAAS")) {
			
		}else if(formNum.startsWith("SAAS")) {
			
		}else if(formNum.startsWith("PAAS")) {
			
		}
		
	}
	
	private void refFiles(List<FilesVo> files, String refId) {
        if (StringUtils.isEmpty(refId)) {
            return;
        }
        
        SubpageParam param = new SubpageParam();
        param.setRefId(refId);
		filesApi.remove(param);
        
//        filesService.remove(new QueryWrapper<Files>().lambda().eq(Files::getRefId, refId));
        if (files != null && !files.isEmpty()) {
            for (FilesVo f : files) {
                f.setId(null);
                f.setRefId(refId);
            }
            FilesParam filesParam = new FilesParam();
            param.setFiles(files);
            filesApi.saveBatch(filesParam);
//            filesService.saveBatch(files);
        }
    }
	
	
	@Override
	public List<ShoppingCart> getShoppingCartList(String idCard, Long resourceType, String name) {

        List<ShoppingCart> shoppingCartList = Lists.newArrayList();
        if(StringUtils.isNotBlank(name)){
            if(ResourceType.SAAS_SERVICE.getCode().equals(resourceType) ||  ResourceType.DAAS.getCode().equals(resourceType)){
                shoppingCartList = this.list(new QueryWrapper<ShoppingCart>().lambda().eq(ShoppingCart::getCreatorIdCard,idCard)
                        .eq(ShoppingCart::getResourceType,resourceType)
                        .like(ShoppingCart::getDsName,name).orderByDesc(ShoppingCart::getModifiedTime));
            }else {
                shoppingCartList = this.list(new QueryWrapper<ShoppingCart>().lambda().eq(ShoppingCart::getCreatorIdCard,idCard)
                        .eq(ShoppingCart::getResourceType,resourceType)
                        .like(ShoppingCart::getServiceTypeName,name).orderByDesc(ShoppingCart::getModifiedTime));
            }
        }else {
            shoppingCartList = this.list(new QueryWrapper<ShoppingCart>().lambda().eq(ShoppingCart::getCreatorIdCard,idCard)
                    .eq(ShoppingCart::getResourceType,resourceType).orderByDesc(ShoppingCart::getModifiedTime));
        }
        shoppingCartList.forEach(shoppingCart -> {
        	
            HandlerWrapper hw = FormNum.getHandlerWrapperByName(context,shoppingCart.getFormNum());
            IApplicationHandler handler = hw.getHandler();
            if(handler != null){
                shoppingCart.setServerList(handler.getByShoppingCartId(shoppingCart.getId()));
                shoppingCart.setTotalNum(handler.getTotalNumInShoppingCart(shoppingCart.getId()));
            }else {
                shoppingCart.setTotalNum(1);
            }
        });
        return shoppingCartList;
    
	}

	@Override
	public ShoppingCart detail(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S> void update(String json) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void submit(UserVO user, SubmitRequest submitRequest) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteOldShoppingCart(Long resourceType) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String, Integer> getNumGroupByType(String idCard) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void distinct() {
		// TODO Auto-generated method stub
		
	}

}
