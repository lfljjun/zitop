package com.zitop.security.service.extend;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.TextEscapeUtils;

import com.zitop.appsetting.ConfigNameConstants;
import com.zitop.util.EncryptUtil;

/**
 * <li>带验证码校验功能的用户名、密码认证过滤器</li>
 * <p>
 * 支持不输入验证码；支持验证码忽略大小写。
 * 
 * @author SteveYang
 * 
 */
public class ValidateCodeUsernamePasswordAuthenticationFilter extends
		UsernamePasswordAuthenticationFilter {

	private boolean postOnly = true;
	private boolean authenticationChannelOn = false;
	private boolean allowEmptyValidateCode = false;
	private String sessionvalidateCodeField = ConfigNameConstants.CAPTCHA_NAME;
	private String validateCodeParameter = DEFAULT_VALIDATE_CODE_PARAMETER;
	private String channelKeyParameter = SPRING_SECURITY_FORM_CHANNEL_KEY;
	public static final String DEFAULT_VALIDATE_CODE_PARAMETER = "validateCode";
	public static final String SPRING_SECURITY_FORM_CHANNEL_KEY = "j_channel_key";
	private String authenticationChannelKey ;

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException {
		String username = obtainUsername(request);
		String password = obtainPassword(request);
		
		//隧道模式
		if(authenticationChannelOn && obtainChannelKey(request) != null)
		{
			try {
				checkAuthenticationChannelKey(request);
				password = EncryptUtil.decryptDES(password, authenticationChannelKey);
			} catch (Exception e) {
				throw new AuthenticationServiceException("Channel Encrypt Method exception!");
			}
		}
		//页面模式
		else
		{
			if (postOnly && !request.getMethod().equals("POST")) {
				throw new AuthenticationServiceException(
						"Authentication method not supported: "
								+ request.getMethod());
			}
			
			// check validate code
			if (!isAllowEmptyValidateCode())
				checkValidateCode(request);
		}
	

		if (username == null) {
			username = "";
		}

		if (password == null) {
			password = "";
		}

		username = username.trim();

		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
				username, password);

		// Place the last username attempted into HttpSession for views
		HttpSession session = request.getSession(false);

		if (session != null || getAllowSessionCreation()) {
			request.getSession().setAttribute(
					SPRING_SECURITY_LAST_USERNAME_KEY,
					TextEscapeUtils.escapeEntities(username));
		}

		// Allow subclasses to set the "details" property
		setDetails(request, authRequest);
		
		return this.getAuthenticationManager().authenticate(authRequest);
	}

	/**
	 * 
	 * <li>比较session中的验证码和用户输入的验证码是否相等</li>
	 * 
	 */
	protected void checkValidateCode(HttpServletRequest request) {
		String sessionValidateCode = obtainSessionValidateCode(request);
		String validateCodeParameter = obtainValidateCodeParameter(request);
		if (StringUtils.isEmpty(validateCodeParameter)
				|| !sessionValidateCode.equalsIgnoreCase(validateCodeParameter)) {
			throw new AuthenticationServiceException(
					"validateCode.notEquals");
		}
	}
	
	protected void checkAuthenticationChannelKey(HttpServletRequest request) throws Exception
	{
		String username = obtainUsername(request);
		String postFormChannelKey = obtainChannelKey(request);
		if(authenticationChannelKey == null )
		{
			throw new AuthenticationServiceException("channelKey.isNull");
		}
		if(postFormChannelKey == null || !EncryptUtil.encryptHMAC(username,authenticationChannelKey).equalsIgnoreCase(postFormChannelKey))
		{
			throw new AuthenticationServiceException("channelKey.notEquals");
		}
	}

	private String obtainValidateCodeParameter(HttpServletRequest request) {
		return request.getParameter(validateCodeParameter);
	}

	protected String obtainSessionValidateCode(HttpServletRequest request) {
		Object obj = request.getSession()
				.getAttribute(sessionvalidateCodeField);
		return null == obj ? "" : obj.toString();
	}
	
	 protected String obtainChannelKey(HttpServletRequest request) {
	    return request.getParameter(channelKeyParameter);
	 }

	public boolean isPostOnly() {
		return postOnly;
	}

	@Override
	public void setPostOnly(boolean postOnly) {
		this.postOnly = postOnly;
	}

	public String getValidateCodeName() {
		return sessionvalidateCodeField;
	}

	public void setValidateCodeName(String validateCodeName) {
		this.sessionvalidateCodeField = validateCodeName;
	}

	public boolean isAllowEmptyValidateCode() {
		return allowEmptyValidateCode;
	}

	public void setAllowEmptyValidateCode(boolean allowEmptyValidateCode) {
		this.allowEmptyValidateCode = allowEmptyValidateCode;
	}

	public boolean isAuthenticationChannelOn() {
		return authenticationChannelOn;
	}

	public void setAuthenticationChannelOn(boolean authenticationChannelOn) {
		this.authenticationChannelOn = authenticationChannelOn;
	}

	public void setAuthenticationChannelKey(String authenticationChannelKey) {
		this.authenticationChannelKey = authenticationChannelKey;
	}



	

}
