package com.liferay.training.gradebook.web.portlet.action;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.training.gradebook.model.Assignment;
import com.liferay.training.gradebook.service.AssignmentService;
import com.liferay.training.gradebook.web.constants.GradebookPortletKeys;
import com.liferay.training.gradebook.web.constants.MVCCommandNames;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import static com.liferay.training.gradebook.constants.GradebookConstants.ASSIGNMENT_ID;

/**
 * MVC Command for showing edit assignment view.
 *
 * @author Otmane.Bouchama
 */
@Component(
        immediate = true,
        property = {
                "javax.portlet.name=" + GradebookPortletKeys.GRADEBOOK,
                "mvc.command.name=" + MVCCommandNames.EDIT_ASSIGNMENT
        },
        service = MVCRenderCommand.class
)
public class EditAssignmentMVCRenderCommand implements MVCRenderCommand {
    @Override
    public String render(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException {
        try {
            long assignmentId = ParamUtil.getLong(renderRequest, ASSIGNMENT_ID, 0);

            // Call the service to get the assignment for editing.
            Assignment assignment = _assignmentService.getAssignment(assignmentId);

            ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);

            // Set back icon visible.
            PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();
            portletDisplay.setShowBackIcon(true);
            String redirect = renderRequest.getRenderParameters().getValue("redirect");
            portletDisplay.setURLBack(redirect);

            // Set assignment to the request attributes.
            renderRequest.setAttribute("assignment", assignment);
            renderRequest.setAttribute("assignmentClass", Assignment.class);

            return "/assignment/edit_assignments.jsp";
        } catch (PortalException pe) {
            throw new PortletException(pe);
        }
    }

    @Reference
    private AssignmentService _assignmentService;
}