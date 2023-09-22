package com.chainsys.core.handler;

import java.util.List;

import org.json.JSONObject;

import com.chainsys.appplatform.exception.AppException;
import com.chainsys.appplatform.exception.BusinessException;
import com.chainsys.appplatform.json.JSONUtils;
import com.chainsys.appplatform.vo.AppVO;
import com.chainsys.core.business.EmployeeDefinition;
import com.chainsys.core.vo.EmployeeDefinitionVO;
import com.chainsys.core.vo.OrganizationProfileVO;
import com.chainsys.core.vo.UserProfileVO;

/*
 * The Class EMployeeDefinitionHandler.
 */
public final class EmployeeDefinitionHandler {
	private static final String EMPLOYEE_DEFINITION_VO = "employeeDefinitionVO";	        
	       
	
	       public void delete(int employeeId,UserProfileVO userProfileVO)
	                throws AppException {
	        	   
	                EmployeeDefinition employeeDefinition = null;
	                try {
	                	employeeDefinition = new EmployeeDefinition();
	                	employeeDefinition.delete(employeeId, userProfileVO);
	                } catch (Exception exception) {
	                    throw new AppException(getClass().getName(), "delete", exception.getMessage(), exception);
	                }
	            }
	
	public String fetchAll(UserProfileVO userProfileVO) throws AppException {
		String jsonString = null;
		EmployeeDefinition employeeDefiniiton = null;
		List<AppVO> userProfileVOList = null;
		try {
			employeeDefiniiton = new EmployeeDefinition();
			userProfileVOList = employeeDefiniiton.fetchAll(userProfileVO);
			jsonString = new JSONUtils(userProfileVO).objectToJson(userProfileVOList);
		} catch (Exception exception) {
			throw new AppException(getClass().getName(), "fetchAll", exception.getMessage(), exception);
		}
		return jsonString;
	}

	public JSONObject fetch(JSONObject jsonObject, UserProfileVO userProfileVO) throws AppException {
		int employeeId = 0;
		EmployeeDefinitionVO employeeDefinitionVO = null;
		EmployeeDefinition employeeDefinition = null;
		JSONObject valueObjectJson = new JSONObject();
		JSONObject employeeDefinitionVOJsonObject = null;
		try {
			employeeDefinition = new EmployeeDefinition();
			employeeDefinitionVO = new EmployeeDefinitionVO();
			employeeId = jsonObject.getInt("employeeId");
			employeeDefinitionVO.setOrgId(userProfileVO.getOrgId());
			employeeDefinitionVO = (EmployeeDefinitionVO) employeeDefinition.fetch(employeeId, userProfileVO.getOrgId());
			employeeDefinitionVOJsonObject = new JSONObject(
					new JSONUtils(userProfileVO).objectToJson(employeeDefinitionVO));

			valueObjectJson = convertEmployeeDefinitionVoJsonToValueObjectJson(employeeDefinitionVOJsonObject);

		} catch (AppException appException) {
			throw appException;
		} catch (Exception exception) {
			throw new AppException(getClass().getName(), "fetch", exception.getMessage(), exception);
		}
		return valueObjectJson;
	}

	/**
	 * Save.
	 * 
	 * @param pageValueObject the page value object
	 * @param userProfileVO   the user profile vo
	 * @return the jSON object
	 * @throws AppException      the app exception
	 * @throws BusinessException the business exception
	 */
	public JSONObject save(JSONObject jsonObjectToValue, UserProfileVO userProfileVO)
			throws AppException {
		EmployeeDefinitionVO employeeDefinitionVO = null;
		EmployeeDefinition employeeDefinition = null;
		JSONObject valueObjectJson = null;
		JSONObject employeeDefinitionVOJsonObject = null;
		JSONObject jsonObject = null;
		try {
			employeeDefinition = new EmployeeDefinition();
			jsonObject = jsonObjectToValue.getJSONObject(EMPLOYEE_DEFINITION_VO);
			employeeDefinitionVO = (EmployeeDefinitionVO) new JSONUtils(userProfileVO)
					.jsonToObject(jsonObject.toString(), EmployeeDefinitionVO.class);
			employeeDefinitionVO.setLoggedUserId(userProfileVO.getLoggedUserId());
			employeeDefinitionVO.setCreatedBy(userProfileVO.getLoggedUserId());
			employeeDefinitionVO.setLastModifiedBy(userProfileVO.getLoggedUserId());
			employeeDefinitionVO.setOrgId(userProfileVO.getOrgId());
			employeeDefinitionVO = (EmployeeDefinitionVO) employeeDefinition.save(employeeDefinitionVO, userProfileVO);
			employeeDefinitionVOJsonObject = new JSONObject(
					new JSONUtils(userProfileVO).objectToJson(employeeDefinitionVO));
			valueObjectJson = convertEmployeeDefinitionVoJsonToValueObjectJson(employeeDefinitionVOJsonObject);
		} catch (AppException appException) {
			throw appException;
		} catch (Exception exception) {
			throw new AppException(getClass().getName(), "save", exception.getMessage(), exception);
		}
		return valueObjectJson;
	}

	public JSONObject convertEmployeeDefinitionVoJsonToValueObjectJson(JSONObject employeeDefinitionJsonObject)
			throws AppException {
		JSONObject employeeDefinitionVoJson = new JSONObject();
		JSONObject valueObjectJson = new JSONObject();
		try {
			employeeDefinitionVoJson.put(EMPLOYEE_DEFINITION_VO, employeeDefinitionJsonObject);
			valueObjectJson.put("valueObject", employeeDefinitionVoJson);
		} catch (Exception exception) {
			throw new AppException(getClass().getName(), "convertAppConnectionVoJsonToValueObjectJson",
					exception.getMessage(), exception);
		}
		return valueObjectJson;
	}

	/**
	 * Sets the default values.
	 *
	 * @param jsonObject    the json object
	 * @param userProfileVO the user profile vo
	 * @return the jSON object
	 * @throws AppException the app exception
	 */
	public JSONObject setDefaultValues(UserProfileVO userProfileVO) throws AppException {
		JSONObject employeeDefinitionVOJsonObject = null;
		EmployeeDefinitionVO employeeDefinitionVO = new EmployeeDefinitionVO();
		OrganizationProfileVO organizationProfileVO = new OrganizationProfileVO();
		try {
			employeeDefinitionVO.setLoggedUserId(userProfileVO.getLoggedUserId());
			employeeDefinitionVO.setOrgId(userProfileVO.getOrgId());
			organizationProfileVO.setOrganizationId(userProfileVO.getOrgId());
			employeeDefinitionVOJsonObject = new JSONObject(
					new JSONUtils(userProfileVO).objectToJson(employeeDefinitionVO));
		} catch (Exception exception) {
			throw new AppException(getClass().getName(), "setDefaultValues", exception.getMessage(), exception);
		}
		return employeeDefinitionVOJsonObject;
	}
}
