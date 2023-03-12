/*
 * Copyright 2004, 2005, 2006 Acegi Technology Pty Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.security.authentication;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * Indicates a class can process a specific
 * {@link org.springframework.security.core.Authentication} implementation.
 * 实现AuthenticationProvider接口，可以实现特定的认证方式
 * eg: {@link RememberMeAuthenticationProvider}
 *
 * 各种不同的Authentication，由对应的AuthenticationProvider来处理，所以需要support方法，
 * 用来分辨当前的AuthenticationProvider是否支持对应的Authentication。
 * 可以同时存在多个AuthenticationProvide，，由ProviderManger同一管理。
 * 如果所有的AuthenticationProvider都认证失败，就会调用parent进行认证，来兜底。
 * 例如：同时支持form表单登录和短信验证码登录。
 *
 * @author Ben Alex
 */
public interface AuthenticationProvider {
	// ~ Methods
	// ========================================================================================================

	/**
	 * Performs authentication with the same contract as
	 * {@link org.springframework.security.authentication.AuthenticationManager#authenticate(Authentication)}
	 * .
	 *
	 * @param authentication the authentication request object.
	 *
	 * @return a fully authenticated object including credentials. May return
	 * <code>null</code> if the <code>AuthenticationProvider</code> is unable to support
	 * authentication of the passed <code>Authentication</code> object. In such a case,
	 * the next <code>AuthenticationProvider</code> that supports the presented
	 * <code>Authentication</code> class will be tried.
	 *
	 * @throws AuthenticationException if authentication fails.
	 */
	Authentication authenticate(Authentication authentication)
			throws AuthenticationException;

	/**
	 * Returns <code>true</code> if this <Code>AuthenticationProvider</code> supports the
	 * indicated <Code>Authentication</code> object.
	 * <p>
	 * Returning <code>true</code> does not guarantee an
	 * <code>AuthenticationProvider</code> will be able to authenticate the presented
	 * instance of the <code>Authentication</code> class. It simply indicates it can
	 * support closer evaluation of it. An <code>AuthenticationProvider</code> can still
	 * return <code>null</code> from the {@link #authenticate(Authentication)} method to
	 * indicate another <code>AuthenticationProvider</code> should be tried.
	 * </p>
	 * <p>
	 * Selection of an <code>AuthenticationProvider</code> capable of performing
	 * authentication is conducted at runtime the <code>ProviderManager</code>.
	 * </p>
	 * <p>
	 *     返回true表示支持该认证
	 *     返回true并不能保证AuthenticationProvider能够对呈现的Authentication类实例进行身份验证。
	 *     它只是表示它可以支持对它进行更仔细的评估。
	 *     AuthenticationProvider从authenticate(Authentication authentication)方法返回null，
	 *     表示应该尝试下一个AuthenticationProvider是否支持认证。
	 *     在运行的时候可以通过ProviderManger来选择能够执行身份验证的AuthenticationProvider。
	 * </p>
	 * @param authentication
	 *
	 * @return <code>true</code> if the implementation can more closely evaluate the
	 * <code>Authentication</code> class presented
	 */
	boolean supports(Class<?> authentication);
}
