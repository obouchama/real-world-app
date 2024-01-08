/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.training.gradebook.model.impl;

import com.liferay.training.gradebook.model.Assignment;
import com.liferay.training.gradebook.service.AssignmentLocalServiceUtil;

/**
 * The extended model base implementation for the Assignment service. Represents a row in the &quot;Gradebook_Assignment&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This class exists only as a container for the default extended model level methods generated by ServiceBuilder. Helper methods and all application logic should be put in {@link AssignmentImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssignmentImpl
 * @see Assignment
 * @generated
 */
public abstract class AssignmentBaseImpl
	extends AssignmentModelImpl implements Assignment {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a assignment model instance should use the <code>Assignment</code> interface instead.
	 */
	@Override
	public void persist() {
		if (this.isNew()) {
			AssignmentLocalServiceUtil.addAssignment(this);
		}
		else {
			AssignmentLocalServiceUtil.updateAssignment(this);
		}
	}

}