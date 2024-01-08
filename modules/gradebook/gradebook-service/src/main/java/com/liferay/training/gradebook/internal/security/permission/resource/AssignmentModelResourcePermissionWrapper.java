package com.liferay.training.gradebook.internal.security.permission.resource;

import com.liferay.exportimport.kernel.staging.permission.StagingPermission;
import com.liferay.portal.kernel.security.permission.resource.*;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.sharing.security.permission.resource.SharingModelResourcePermissionConfigurator;
import com.liferay.training.gradebook.constants.GradebookConstants;
import com.liferay.training.gradebook.model.Assignment;
import com.liferay.training.gradebook.service.AssignmentLocalService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import static com.liferay.training.gradebook.constants.GradebookConstants.ASSIGNMENT_MODEL_CLASS_NAME;

@Component(
        property = "model.class.name=" + ASSIGNMENT_MODEL_CLASS_NAME,
        service = ModelResourcePermission.class
)
public class AssignmentModelResourcePermissionWrapper extends BaseModelResourcePermissionWrapper<Assignment> {
    /**
     * @return
     */
    @Override
    protected ModelResourcePermission<Assignment> doGetModelResourcePermission() {

        return ModelResourcePermissionFactory.create(
                Assignment.class, Assignment::getAssignmentId,
                _assignmentLocalService::getAssignment, _portletResourcePermission,
                (modelResourcePermission, consumer) -> {
                    consumer.accept(
                            new StagedModelPermissionLogic<>(
                                    _stagingPermission, "com_liferay_training_gradebook_web_portlet_GradebookPortlet",
                                    Assignment::getAssignmentId));
//                    consumer.accept(
//                            new WorkflowedModelPermissionLogic<>(
//                                    modelResourcePermission, _groupLocalService,
//                                    Assignment::getAssignmentId));
//
//                    _sharingModelResourcePermissionConfigurator.configure(
//                            modelResourcePermission, consumer);
                });
    }

    @Reference
    private AssignmentLocalService _assignmentLocalService;

    @Reference
    private GroupLocalService _groupLocalService;

    @Reference(target = "(resource.name=" + GradebookConstants.RESOURCE_NAME + ")")
    private PortletResourcePermission _portletResourcePermission;

    @Reference
    private SharingModelResourcePermissionConfigurator
            _sharingModelResourcePermissionConfigurator;

    @Reference
    private StagingPermission _stagingPermission;
}
