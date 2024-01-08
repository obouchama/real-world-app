/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
package com.liferay.training.gradebook.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Brian Wing Shun Chan
 */
public class NoSuchAssignmentException extends NoSuchModelException {

	public NoSuchAssignmentException() {
	}

	public NoSuchAssignmentException(String msg) {
		super(msg);
	}

	public NoSuchAssignmentException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchAssignmentException(Throwable throwable) {
		super(throwable);
	}

}