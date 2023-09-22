package com.chainsys.core.controller;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.chainsys.appdatacore.constants.AppDataLayoutConstants;
import com.chainsys.appplatform.enumeration.MessageType;
import com.chainsys.appplatform.exception.AppException;
import com.chainsys.appplatform.exception.BusinessException;
import com.chainsys.appplatform.vo.MessageVO;
import com.chainsys.core.constants.CoreConstants;
import com.chainsys.core.handler.EmployeeDefinitionHandler;
import com.chainsys.core.vo.UserProfileVO;
import com.chainsys.core.web.util.MessageUtils;
import com.chainsys.web.interceptor.Layout;

/**
 * The Class EmployeeDefinitionController.
 */
@Controller
@RequestMapping("/core/employeedefinition")
public final class EmployeeDefinitionController 
{
	
	private static final String USER_PROFILE = "USER_PROFILE";
	private static final String VALUE_OBJECT = "valueObject";

	@PostMapping("delete")
	public void delete(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			ModelAndView view) throws BusinessException, AppException {
		String employeeId = null;
		UserProfileVO userProfileVO = null;
		EmployeeDefinitionHandler employeeDefinitionHandler = null;
		try {
			employeeDefinitionHandler = new EmployeeDefinitionHandler();
			userProfileVO = (UserProfileVO) httpServletRequest.getSession(false).getAttribute(USER_PROFILE);
			employeeId = httpServletRequest.getParameter("employeeId");
			int employeeId1 = Integer.parseInt(employeeId);
			employeeDefinitionHandler.delete(employeeId1, userProfileVO);
			this.fetchall(httpServletRequest, httpServletResponse);
		} catch (Exception exception) {
			throw new AppException(getClass().getName(), "delete", exception.getMessage(), exception);
		}
	}

	@PostMapping("/fetch")
	public void fetch(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
			throws AppException {
		JSONObject employeeDefinitionVOJsonObject = null;
		UserProfileVO userProfileVO = null;
		EmployeeDefinitionHandler employeeDefinitionHandler = null;
		PrintWriter printWriter = null;
		try {
			userProfileVO = (UserProfileVO) httpServletRequest.getSession(false).getAttribute(USER_PROFILE);
			employeeDefinitionHandler = new EmployeeDefinitionHandler();
			/* If request parameter is not null, assign the value to the JSONObject */
			if (httpServletRequest.getParameter(CoreConstants.STRINGIFIED_PROPERTY_VALUE_JSON) != null) {
				employeeDefinitionVOJsonObject = new JSONObject(URLDecoder.decode(
						httpServletRequest.getParameter(CoreConstants.STRINGIFIED_PROPERTY_VALUE_JSON),
						StandardCharsets.UTF_8.toString()));
				employeeDefinitionVOJsonObject = employeeDefinitionHandler.fetch(employeeDefinitionVOJsonObject,
						userProfileVO);
				printWriter = httpServletResponse.getWriter();
				printWriter.print(employeeDefinitionVOJsonObject.toString());
				printWriter.flush();
			}
		} catch (Exception exception) {
			throw new AppException(this.getClass().getName(), "fetch", exception.getMessage(), exception);
		}
	}

	@PostMapping("/fetchall")
	public void fetchall(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
			throws AppException {
		String appConnectionDetail = null;
		JSONObject employeeDefinitionVORootJsonObject = null;
		UserProfileVO userProfileVO = null;
		EmployeeDefinitionHandler employeeDefinitionHandler = null;
		PrintWriter printWriter = null;
		try {
			userProfileVO = (UserProfileVO) httpServletRequest.getSession(false).getAttribute(USER_PROFILE);
			employeeDefinitionVORootJsonObject = new JSONObject();
			employeeDefinitionHandler = new EmployeeDefinitionHandler();
			appConnectionDetail = employeeDefinitionHandler.fetchAll(userProfileVO);
			employeeDefinitionVORootJsonObject.put("content", new JSONArray(appConnectionDetail));
			printWriter = httpServletResponse.getWriter();
			printWriter.print(employeeDefinitionVORootJsonObject);
			printWriter.flush();
		} catch (Exception exception) {
			throw new AppException(this.getClass().getName(), "fetchall", exception.getMessage(), exception);
		}
	}

	@PostMapping("/save")
	public void save(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, ModelAndView view)
			throws AppException {
		JSONObject employeeDefinitionVOJsonObject = null;
		JSONObject employeeDefinitionVORootJsonObject = null;
		UserProfileVO userProfileVO = null;
		EmployeeDefinitionHandler employeeDefinitionHandler = null;
		MessageVO messageVO = null;
		try {
			employeeDefinitionVORootJsonObject = new JSONObject();
			userProfileVO = (UserProfileVO) httpServletRequest.getSession(false).getAttribute(USER_PROFILE);
			employeeDefinitionVORootJsonObject = new JSONObject();
			employeeDefinitionHandler = new EmployeeDefinitionHandler();
			if (httpServletRequest.getParameter(CoreConstants.PAGE_VALUE_OBJECT) != null) {
				employeeDefinitionVOJsonObject = new JSONObject(
						httpServletRequest.getParameter(CoreConstants.PAGE_VALUE_OBJECT));
				employeeDefinitionVORootJsonObject = employeeDefinitionHandler.save(employeeDefinitionVOJsonObject,
						userProfileVO);
				view.addObject(CoreConstants.PAGE_VALUE_OBJECT,
						employeeDefinitionVORootJsonObject.get(VALUE_OBJECT).toString());
			}
			messageVO = new MessageVO();
			messageVO.setType(MessageType.INFO);
			messageVO.setMessage("key.message.Succeeded");
			MessageUtils.setContentAndTransactionMessage(httpServletRequest, httpServletResponse, messageVO, null,
					employeeDefinitionVORootJsonObject);
		} catch (AppException appException) {
			throw appException;
		} catch (Exception exception) {
			throw new AppException(this.getClass().getName(), "save", exception.getMessage(), exception);
		}
	}

	@GetMapping("/launch")
	@Layout(value = AppDataLayoutConstants.APPDATA_TEMPLATE_OUTLOOK)
	public ModelAndView launch(HttpServletRequest httpServletRequest, HttpServletResponse response, ModelAndView view)
			throws AppException {
		try {
			setDefaultValues(httpServletRequest, response, view);
		} catch (AppException appException) {
			throw appException;
		} catch (Exception exception) {
			throw new AppException(this.getClass().getName(), "launch", exception.getMessage(), exception);
		}
		return view;
	}

	@PostMapping("/redirect")
	@Layout(value = AppDataLayoutConstants.APPDATA_TEMPLATE_OUTLOOK)
	public ModelAndView redirect(HttpServletRequest httpServletRequest, HttpServletResponse response, ModelAndView view)
			throws AppException {
		try {
			setDefaultValues(httpServletRequest, response, view);
		} catch (AppException appException) {
			throw appException;
		} catch (Exception exception) {
			throw new AppException(this.getClass().getName(), "redirect", exception.getMessage(), exception);
		}
		return view;
	}

	private ModelAndView setDefaultValues(HttpServletRequest httpServletRequest, HttpServletResponse response,
			ModelAndView view) throws AppException {
		JSONObject employeeDefinitionVOJsonObject = null;
		JSONObject employeeDefinitionVORootJsonObject = null;
		UserProfileVO userProfileVO = null;
		EmployeeDefinitionHandler employeeDefinitionHandler = null;
		try {
			userProfileVO = (UserProfileVO) httpServletRequest.getSession(false).getAttribute(USER_PROFILE);
			employeeDefinitionVORootJsonObject = new JSONObject();
			employeeDefinitionHandler = new EmployeeDefinitionHandler();
			employeeDefinitionVOJsonObject = employeeDefinitionHandler.setDefaultValues(userProfileVO);
			employeeDefinitionVORootJsonObject.put("employeeName", httpServletRequest.getParameter("employeeName"));
			employeeDefinitionVORootJsonObject.put("employeeDefinitionVO", employeeDefinitionVOJsonObject);
			view.setViewName("/core/employeedefinitionedit.html");
			view.addObject(CoreConstants.PAGE_VALUE_OBJECT, employeeDefinitionVORootJsonObject.toString());
		} catch (AppException appException) {
			throw appException;
		} catch (Exception exception) {
			throw new AppException(this.getClass().getName(), "setDefaultValues", exception.getMessage(), exception);
		}
		return view;
	}
}
