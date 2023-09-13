package com.chainsys.core.dao;

import java.util.List;
import java.util.Map;

import com.chainsys.appplatform.exception.AppException;
import com.chainsys.base.db.JDBCTransaction;
import com.chainsys.core.util.AppConnectWrapper;
import com.chainsys.core.vo.EmployeeDefinitionVO;

public class EmployeeDefinitionDAO {
	public EmployeeDefinitionVO save(JDBCTransaction jdbcTransaction, EmployeeDefinitionVO employeeDefinitionVO)
			throws AppException {
		try {
			if (employeeDefinitionVO.getEmployeeId() > 0) {
				jdbcTransaction.update(employeeDefinitionVO, false);
			} else {
				jdbcTransaction.insert(employeeDefinitionVO);
			}
		} catch (Exception exception) {
			throw new AppException(getClass().getName(), "save", exception.getMessage(), exception);
		}
		return employeeDefinitionVO;
	}
	public List<Map<String, Object>> fetchAll(int loggedUserId, int organizationId) throws AppException {
		StringBuilder fetchEmployeeNameQuery = null;
		List<Map<String, Object>> employeeDefinitionWithPrivilegeList = null;
		JDBCTransaction jdbcTransaction = null;
		try {
			jdbcTransaction = new JDBCTransaction(organizationId, false);
			fetchEmployeeNameQuery = new StringBuilder();
			fetchEmployeeNameQuery.append(" SELECT EMPLOYEE_NAME  AS \"EMPLOYEE_NAME\", ");
			fetchEmployeeNameQuery.append(" EMPLOYEE_AGE  AS \"EMPLOYEE_AGE\", ");
			fetchEmployeeNameQuery.append(" ADDRESS  AS \"ADDRESS\", ");
			fetchEmployeeNameQuery.append(" EMPLOYEE_ID  AS \"EMPLOYEE_ID\" ");
			fetchEmployeeNameQuery.append(" FROM EMPLOYEE_3458 ORDER BY EMPLOYEE_NAME ASC");
			
			employeeDefinitionWithPrivilegeList = jdbcTransaction.getSpringUtils().queryForList(fetchEmployeeNameQuery.toString());
		} catch (Exception exception) {
			throw new AppException(getClass().getName(), "fetchAll", exception.getMessage(), exception);
		} finally {
			AppConnectWrapper.close(jdbcTransaction);
		}
		return employeeDefinitionWithPrivilegeList;
	}

	public EmployeeDefinitionVO fetch(int employeeId, int organizationId) throws AppException {
		EmployeeDefinitionVO employeeDefinitionVO = null;
		JDBCTransaction jdbcTransaction = null;
		try {
			employeeDefinitionVO = new EmployeeDefinitionVO();
			jdbcTransaction = new JDBCTransaction(organizationId, false);
			employeeDefinitionVO.setEmployeeId(employeeId);
			employeeDefinitionVO = (EmployeeDefinitionVO) jdbcTransaction.fetchById(employeeDefinitionVO);
		} catch (Exception exception) {
			throw new AppException(getClass().getName(), "fetch", exception.getMessage(), exception);
		} finally {
			AppConnectWrapper.close(jdbcTransaction);
		}
		return employeeDefinitionVO;
	}

}
