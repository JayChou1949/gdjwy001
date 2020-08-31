package com.hirisun.cloud.workflow.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hirisun.cloud.model.param.WorkflowNodeParam;
import com.hirisun.cloud.model.workflow.WorkflowNodeVO;
import com.hirisun.cloud.workflow.bean.WorkflowNode;
import com.hirisun.cloud.workflow.mapper.WorkflowNodeMapper;
import com.hirisun.cloud.workflow.service.WorkflowNodeService;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 流程模型 服务实现类
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-08-05
 */
@Service
public class WorkflowNodeServiceImpl extends ServiceImpl<WorkflowNodeMapper, WorkflowNode> implements WorkflowNodeService {

    @Autowired
    private WorkflowNodeMapper workflowNodeMapper;

    @Override
    public WorkflowNode getNextNodeById(String nodeId) {
        return workflowNodeMapper.getNextNodeById(nodeId);
    }

	@Override
	public WorkflowNodeVO getNodeByParam(WorkflowNodeParam param) {
		
		WorkflowNode workflowNode = workflowNodeMapper.selectOne(new QueryWrapper<WorkflowNode>()
				.eq("WORKFLOW_ID",param.getWorkflowId())
				.eq("NODE_NAME", param.getNodeName())
				.eq("VERSION",param.getVersion()));
		
		if(workflowNode != null) {
			WorkflowNodeVO vo = new WorkflowNodeVO();
			BeanUtils.copyProperties(workflowNode, vo);
			return vo;
		}
		
		return null;
	}

	@Override
	public void syncOldData() {
		List<WorkflowNode> oldDataList = workflowNodeMapper.getOldData();

//		List<WorkflowNode> newList = new ArrayList<>();
		oldDataList.forEach(item->{
			if ("申请".equals(item.getNodeName())) {
				item.setNodeSort(1);
				item.setNodeFeature("5,9");
				syncNextNode(item.getNextmodelids(),oldDataList,1);
			}
			if(item.getModeltype()!=null&&item.getModeltype().equals(2)){
				item.setNodeFeature("3");
				return;
			}
		});

		this.updateBatchById(oldDataList);

	}

	void syncNextNode(String id,List<WorkflowNode> list,Integer sort) {
    	list.forEach(item->{
			if (id == null) {
				return;
			}
			if (id!=null&&id.equals(item.getId())) {
				item.setNodeSort(sort+1);
				syncNextNode(item.getNextmodelids(), list, sort + 1);
			}
		});
	}

}
