package com.liferay.training.gradebook.internal.security.permission;

import com.liferay.portal.kernel.security.permission.PermissionUpdateHandler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.training.gradebook.constants.GradebookConstants;
import com.liferay.training.gradebook.model.Assignment;
import com.liferay.training.gradebook.service.AssignmentLocalService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.Date;

import static com.liferay.training.gradebook.constants.GradebookConstants.ASSIGNMENT_MODEL_CLASS_NAME;

@Component(
        property = "model.class.name=" + ASSIGNMENT_MODEL_CLASS_NAME,
        service = PermissionUpdateHandler.class
)
public class AssignmentPermissionUpdateHandler implements PermissionUpdateHandler {

    @Override
    public void updatedPermission(String primKey) {
        Assignment assignment = _assignmentLocalService.fetchAssignment(
                GetterUtil.getLong(primKey));

        if (assignment == null) {
            return;
        }

        assignment.setModifiedDate(new Date());

        _assignmentLocalService.updateAssignment(assignment);
    }

    @Reference
    private AssignmentLocalService _assignmentLocalService;
}
