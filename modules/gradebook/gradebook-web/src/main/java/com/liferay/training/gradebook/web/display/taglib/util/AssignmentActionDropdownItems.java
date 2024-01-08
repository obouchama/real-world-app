package com.liferay.training.gradebook.web.display.taglib.util;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.*;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.training.gradebook.model.Assignment;
import com.liferay.training.gradebook.web.constants.MVCCommandNames;
import com.liferay.training.gradebook.web.internal.security.permission.resource.AssignmentPermission;

import javax.portlet.PortletURL;
import javax.portlet.WindowStateException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.liferay.training.gradebook.constants.GradebookConstants.ASSIGNMENT_MODEL_CLASS_NAME;
import static com.liferay.training.gradebook.constants.GradebookConstants.ASSIGNMENT_ID;

public class AssignmentActionDropdownItems {

    public AssignmentActionDropdownItems(
            Assignment assignment, LiferayPortletRequest portletRequest,
            LiferayPortletResponse portletResponse, PortletURL currentURLObj) {

        _assignment = assignment;
        _portletResponse = portletResponse;
        _currentURLObj = currentURLObj;

        _httpServletRequest = PortalUtil.getHttpServletRequest(portletRequest);

        _themeDisplay = (ThemeDisplay) _httpServletRequest.getAttribute(WebKeys.THEME_DISPLAY);
    }

    public List<DropdownItem> getActionDropdownItems() {

        PermissionChecker permissionChecker = _themeDisplay.getPermissionChecker();
        boolean hasViewPermission = false;
        try {
            hasViewPermission = AssignmentPermission.contains(permissionChecker, _assignment.getAssignmentId(), ActionKeys.VIEW);
        } catch (PortalException e) {
            throw new RuntimeException(e);
        }
        boolean hasUpdatePermission = false;
        try {
            hasUpdatePermission = AssignmentPermission.contains(permissionChecker, _assignment.getAssignmentId(), ActionKeys.UPDATE);
        } catch (PortalException e) {
            throw new RuntimeException(e);
        }

        boolean hasDeletePermission = false;
        try {
            hasDeletePermission = AssignmentPermission.contains(permissionChecker, _assignment.getAssignmentId(), ActionKeys.DELETE);
        } catch (PortalException e) {
            throw new RuntimeException(e);
        }

        boolean finalHasViewPermission = hasViewPermission;
        boolean finalHasUpdatePermission = hasUpdatePermission;
        boolean finalHasDeletePermission = hasDeletePermission;


        return DropdownItemListBuilder.addGroup(
                dropdownGroupItem -> dropdownGroupItem.setDropdownItems(
                        DropdownItemListBuilder.add(
                                () -> AssignmentPermission.contains(permissionChecker, _assignment.getAssignmentId(), ActionKeys.VIEW),
                                getUnsafeConsumer(PortletURLBuilder.createRenderURL(
                                        _portletResponse
                                ).setMVCRenderCommandName(MVCCommandNames.VIEW_ASSIGNMENT
                                ).setRedirect(
                                        _currentURLObj.toString()
                                ), "view")
                        ).add(
                                () -> AssignmentPermission.contains(permissionChecker, _assignment.getAssignmentId(), ActionKeys.UPDATE),
                                getUnsafeConsumer(PortletURLBuilder.createRenderURL(
                                        _portletResponse
                                ).setMVCRenderCommandName(MVCCommandNames.EDIT_ASSIGNMENT
                                ).setRedirect(
                                        _currentURLObj.toString()
                                ), "edit")
                        ).add(
                                () -> AssignmentPermission.contains(permissionChecker, _assignment.getAssignmentId(), ActionKeys.PERMISSIONS),
                                getPermissionsActionUnsafeConsumer(_assignment)
                        ).add(
                                () -> AssignmentPermission.contains(permissionChecker, _assignment.getAssignmentId(), ActionKeys.DELETE),
                                getUnsafeConsumer(PortletURLBuilder.createActionURL(
                                        _portletResponse
                                ).setActionName(
                                        MVCCommandNames.DELETE_ASSIGNMENT
                                ).setRedirect(
                                        _currentURLObj.toString()
                                ), "delete")
                        ).build()
                )
        ).build();
    }

    private UnsafeConsumer<DropdownItem, Exception> getUnsafeConsumer(PortletURLBuilder.AfterRedirectStep _portletResponse, String label) {
        return dropdownItem -> {
            dropdownItem.setHref(
                    _portletResponse.setParameter(
                            ASSIGNMENT_ID, _assignment.getAssignmentId()
                    ).buildString());
            dropdownItem.setLabel(
                    LanguageUtil.get(_httpServletRequest, label));
        };
    }

    private UnsafeConsumer<DropdownItem, Exception> getPermissionsActionUnsafeConsumer(Assignment assignment) {

        return dropdownItem -> {
            dropdownItem.putData("action", "permissions");
            dropdownItem.setHref(getPermissionsURL(assignment));
            dropdownItem.setLabel(LanguageUtil.get(_httpServletRequest, "permissions"));
        };
    }

    private String getPermissionsURL(Assignment assignment) {
        try {
            PortletURL portletURL = PortletProviderUtil.getPortletURL(_httpServletRequest, "com.liferay.portlet.configuration.kernel.util.PortletConfigurationApplicationType$PortletConfiguration", PortletProvider.Action.VIEW);
            portletURL.setParameter("mvcPath", "/edit_permissions.jsp");
            portletURL.setWindowState(LiferayWindowState.MAXIMIZED);
            portletURL.setParameter("redirect", _currentURLObj.toString());
            portletURL.setParameter("portletConfiguration", Boolean.TRUE.toString());
            PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();
            portletURL.setParameter("portletResource", portletDisplay.getId());
            portletURL.setParameter("modelResource", ASSIGNMENT_MODEL_CLASS_NAME);
            portletURL.setParameter("modelResourceDescription", assignment.getTitle(_themeDisplay.getLocale()));
            portletURL.setParameter("resourcePrimKey", String.valueOf(assignment.getAssignmentId()));

            return portletURL.toString();
        } catch (WindowStateException | PortalException e) {
            throw new RuntimeException(e);
        }
    }

    private final Assignment _assignment;
    private final HttpServletRequest _httpServletRequest;
    private final PortletURL _currentURLObj;
    private final LiferayPortletResponse _portletResponse;
    private final ThemeDisplay _themeDisplay;

}
