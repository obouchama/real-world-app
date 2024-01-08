package com.liferay.training.gradebook.web.portlet.action;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.training.gradebook.service.AssignmentService;
import com.liferay.training.gradebook.web.constants.GradebookPortletKeys;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import static com.liferay.training.gradebook.constants.GradebookConstants.ASSIGNMENT_ID;

/**
 * MVC Action Command for deleting assignments.
 *
 * @author Otmane.Bouchama
 */
@Component(
	immediate = true, 
	property = { 
		"javax.portlet.name=" + GradebookPortletKeys.GRADEBOOK,
		"mvc.command.name=/gradebook/assignment/delete" 
	}, 
	service = MVCActionCommand.class
)
public class DeleteAssignmentMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(ActionRequest actionRequest, ActionResponse actionResponse)
			throws Exception {
		// Get assignment id from request.
		long assignmentId = ParamUtil.getLong(actionRequest, ASSIGNMENT_ID);
		try {
			// Call service to delete the assignment.
			_assignmentService.deleteAssignment(assignmentId);
			// Set success message.
			SessionMessages.add(actionRequest, "assignmentDeleted");
			}catch (PortalException pe) {
				
				// Set error messages from the service layer.
				SessionErrors.add(actionRequest, "serviceErrorDetails", pe);
			}
	}
	
	@Reference
	protected AssignmentService _assignmentService;
}
