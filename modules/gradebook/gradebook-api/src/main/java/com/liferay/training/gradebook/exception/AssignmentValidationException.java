/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
package com.liferay.training.gradebook.exception;

import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class AssignmentValidationException extends PortalException {

	/**
	 * Custom constructor taking a list as a parameter.
	 *
	 * @param errors
	 */
	public AssignmentValidationException(List<String> errors) {
		super(String.join(",", errors));
		_errors = errors;
	}

	public AssignmentValidationException() {
	}

	public AssignmentValidationException(String msg) {
		super(msg);
	}

	public AssignmentValidationException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public AssignmentValidationException(Throwable throwable) {
		super(throwable);
	}

	public List<String> getErrors() {
		return _errors;
	}
	private List<String> _errors;

}