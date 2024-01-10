package com.liferay.training.gradebook.web.portlet.action;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
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
import java.text.DateFormat;

import static com.liferay.training.gradebook.constants.GradebookConstants.ASSIGNMENT_ID;

/**
 * MVC Command for showing the assignment submissions list view.
 *
 * @author Otmane.Bouchama
 */
@Component(
        immediate = true,
        property = {
                "javax.portlet.name=" + GradebookPortletKeys.GRADEBOOK,
                "mvc.command.name=" + MVCCommandNames.VIEW_ASSIGNMENT
        },
        service = MVCRenderCommand.class
)
public class ViewSingleAssignmentMVCRenderCommand implements MVCRenderCommand {

    @Override
    public String render(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException {

        ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
        long assignmentId = ParamUtil.getLong(renderRequest, ASSIGNMENT_ID, 0);

        try {

            // Call the service to get the assignment.

            Assignment assignment = _assignmentService.getAssignment(assignmentId);

            DateFormat dateFormat = DateFormatFactoryUtil.getSimpleDateFormat("EEEEE, MMMMM dd, yyyy",
                    renderRequest.getLocale());

            // Set attributes to the request.
            renderRequest.setAttribute("assignment", assignment);
            renderRequest.setAttribute("dueDate", dateFormat.format(assignment.getDueDate()));
            renderRequest.setAttribute("createDate", dateFormat.format(assignment.getCreateDate()));

            // Set back icon visible.
            PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();
            String redirect = renderRequest.getRenderParameters().getValue("redirect");
            portletDisplay.setShowBackIcon(true);
            portletDisplay.setURLBack(redirect);

            return "/assignment/view_assignment.jsp";

        } catch (PortalException pe) {
            throw new PortletException(pe);
        }
    }

    @Reference
    private AssignmentService _assignmentService;

}