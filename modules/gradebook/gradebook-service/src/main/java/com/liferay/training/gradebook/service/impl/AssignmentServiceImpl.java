/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.training.gradebook.service.impl;

import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.training.gradebook.constants.GradebookConstants;
import com.liferay.training.gradebook.model.Assignment;
import com.liferay.training.gradebook.service.base.AssignmentServiceBaseImpl;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
        property = {
                "json.web.service.context.name=gradebook",
                "json.web.service.context.path=Assignment"
        },
        service = AopService.class
)
public class AssignmentServiceImpl extends AssignmentServiceBaseImpl {

    /*
     * NOTE FOR DEVELOPERS:
     *
     * Never reference this class directly. Always use <code>com.liferay.training.gradebook.service.AssignmentServiceUtil</code> to access the assignment remote service.
     */
    public Assignment addAssignment(
            long groupId, Map<Locale, String> titleMap,
            Map<Locale, String> descriptionMap,
            Date dueDate, ServiceContext serviceContext)
            throws PortalException {

        // Check permissions.
        _portletResourcePermission.check(
                getPermissionChecker(), serviceContext.getScopeGroupId(),
                ActionKeys.ADD_ENTRY);

        return assignmentLocalService.addAssignment(
                groupId, titleMap, descriptionMap, dueDate, serviceContext);
    }

    public Assignment deleteAssignment(long assignmentId)
            throws PortalException {

        // Check permissions.
        _assignmentModelResourcePermission.check(
                getPermissionChecker(), assignmentId, ActionKeys.DELETE);

        Assignment assignment = assignmentLocalService.getAssignment(assignmentId);

        return assignmentLocalService.deleteAssignment(assignment);
    }

    public Assignment getAssignment(long assignmentId)
            throws PortalException {
        Assignment assignment =
                assignmentLocalService.getAssignment(assignmentId);

        // Check permissions.
        _assignmentModelResourcePermission.check(
                getPermissionChecker(), assignment, ActionKeys.VIEW);

        return assignment;
    }

    public List<Assignment> getAssignmentsByGroupId(long groupId) {
        return assignmentPersistence.filterFindByGroupId(groupId);
    }

    public List<Assignment> getAssignmentsByKeywords(
            long groupId, String keywords, int start, int end,
            OrderByComparator<Assignment> orderByComparator) {
        return assignmentLocalService.getAssignmentsByKeywords(
                groupId, keywords, start, end, orderByComparator);
    }

    public long getAssignmentsCountByKeywords(long groupId, String keywords) {
        return assignmentLocalService.getAssignmentsCountByKeywords(
                groupId, keywords);
    }

    public Assignment updateAssignment(
            long assignmentId, Map<Locale, String> titleMap,
            Map<Locale, String> descriptionMap,
            Date dueDate, ServiceContext serviceContext)
            throws PortalException {

        // Check permissions.
        _assignmentModelResourcePermission.check(
                getPermissionChecker(), assignmentId, ActionKeys.UPDATE);

        return assignmentLocalService.updateAssignment(
                assignmentId, titleMap, descriptionMap, dueDate, serviceContext);
    }


    @Reference(
            policy = ReferencePolicy.DYNAMIC,
            policyOption = ReferencePolicyOption.GREEDY,
            target = "(model.class.name=com.liferay.training.gradebook.model.Assignment)"
    )
    private volatile ModelResourcePermission<Assignment> _assignmentModelResourcePermission;

    @Reference(
            policy = ReferencePolicy.DYNAMIC,
            policyOption = ReferencePolicyOption.GREEDY,
            target = "(resource.name=" + GradebookConstants.RESOURCE_NAME + ")"
    )
    private volatile PortletResourcePermission _portletResourcePermission;
}