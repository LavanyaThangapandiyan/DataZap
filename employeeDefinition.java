package com.chainsys.core.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.chainsys.appplatform.exception.AppException;
import com.chainsys.appplatform.util.RecordSharingUtil;
import com.chainsys.appplatform.vo.AppVO;
import com.chainsys.appplatform.vo.RecordPrivilegeVO;
import com.chainsys.base.db.JDBCTransaction;
import com.chainsys.core.constants.PfmObjectNameConstants;
import com.chainsys.core.dao.EmployeeDefinitionDAO;
import com.chainsys.core.dao.OrganizationProfileDAO;
import com.chainsys.core.util.AppConnectWrapper;
import com.chainsys.core.vo.EmployeeDefinitionVO;
import com.chainsys.core.vo.OrganizationProfileVO;
import com.chainsys.core.vo.UserProfileVO;

public final class EmployeeDefinition {


	public AppVO fetch(int employeeId, int orgId) throws AppException {
		EmployeeDefinitionVO employeeDefinitionVO = null;
		EmployeeDefinitionDAO employeeDefinitionDAO = null;
		try {
			employeeDefinitionDAO = new EmployeeDefinitionDAO();
			employeeDefinitionVO = employeeDefinitionDAO.fetch(employeeId, orgId);
		} catch (AppException appException) {
			throw appException;
		} catch (Exception exception) {
			throw new AppException(getClass().getName(), "fetch", exception.getMessage(), exception);
		}
		return employeeDefinitionVO;
	}

	public List<AppVO> fetchAll(UserProfileVO userProfileVO) throws AppException {
		List<AppVO> employeeDefinitionVOList = null;
		List<Map<String, Object>> employeeDefinitionWithPrivilegeList = null;
		EmployeeDefinitionDAO employeeDefinitionDAO =null;
		try {
			employeeDefinitionDAO = new EmployeeDefinitionDAO();
			employeeDefinitionWithPrivilegeList = employeeDefinitionDAO.fetchAll(userProfileVO.getLoggedUserId(),
					userProfileVO.getOrgId());
			employeeDefinitionVOList = formEmployeeDefinitionVOList(employeeDefinitionWithPrivilegeList,
					userProfileVO.getLoggedUserId());
		} catch (AppException appException) {
			throw appException;
		} catch (Exception exception) {
			throw new AppException(getClass().getName(), "fetchAll", exception.getMessage(), exception);
		}
		return employeeDefinitionVOList;
	}
	
	private List<AppVO> formEmployeeDefinitionVOList(List<Map<String, Object>> employeeDefinitionWithPrivilegeList,
	        int loggedUserId) throws AppException {
	        List<AppVO> employeeDefinitionVOList = null;
	        EmployeeDefinitionVO employeeDefinitionVO = null;
	        try {
	            employeeDefinitionVOList = new ArrayList<>();
	            for (Map<String, Object> employeeDefinitionWithPrivilegeMap : employeeDefinitionWithPrivilegeList) {
	                employeeDefinitionVO = new EmployeeDefinitionVO();
	                employeeDefinitionVO.setEmployeeName(employeeDefinitionWithPrivilegeMap.get("EMPLOYEE_NAME").toString());
	                employeeDefinitionVO.setEmployeeId(
	                    Integer.parseInt(employeeDefinitionWithPrivilegeMap.get("EMPLOYEE_ID").toString()));

	                employeeDefinitionVO.setLoggedUserId(loggedUserId);
	                employeeDefinitionVOList.add(employeeDefinitionVO);
	            }
	        } catch (Exception exception) {
	            throw new AppException(getClass().getName(), "formEmployeeDefinitionVOList", exception.getMessage(),
	                exception);
	        }
	        return employeeDefinitionVOList;

	    }
	
	

	public AppVO save(EmployeeDefinitionVO employeeDefinitionVO, UserProfileVO userProfileVO)
			throws AppException {
		int organizationId = 0;
		EmployeeDefinitionDAO employeeDefinitionDAO = null;
		JDBCTransaction jdbcTransaction = null;
		try {
			employeeDefinitionDAO = new EmployeeDefinitionDAO();
			organizationId = userProfileVO.getOrgId();
			jdbcTransaction = new JDBCTransaction(organizationId, true);
			employeeDefinitionVO = employeeDefinitionDAO.save(jdbcTransaction, employeeDefinitionVO);
			AppConnectWrapper.commit(jdbcTransaction);
			
		} catch (AppException appException) {
			throw appException;
		} catch (Exception exception) {
			throw new AppException(getClass().getName(), "save", exception.getMessage(), exception);
		} finally {
			AppConnectWrapper.rollback(jdbcTransaction);
		}
		return employeeDefinitionVO;
	}

	public EmployeeDefinitionVO setDefaultValues(EmployeeDefinitionVO employeeDefinitionVO, UserProfileVO userProfileVO,
			OrganizationProfileVO organizationProfileVO) throws AppException {
		RecordPrivilegeVO recordPrivilegeVO = null;
		try {
			organizationProfileVO = new OrganizationProfileDAO().fetch(userProfileVO.getOrgId(),
					organizationProfileVO.getOrganizationId());
			recordPrivilegeVO = RecordSharingUtil.fetchObjectSharingPrivilege(PfmObjectNameConstants.EMPLOYEE,
					employeeDefinitionVO.getOrgId(), employeeDefinitionVO.getLoggedUserId());
			employeeDefinitionVO.setRecordPrivilegeVO(recordPrivilegeVO);

		} catch (Exception exception) {
			throw new AppException(getClass().getName(), "setDefaultValues", exception.getMessage(), exception);
		}
		return employeeDefinitionVO;
	}
}
