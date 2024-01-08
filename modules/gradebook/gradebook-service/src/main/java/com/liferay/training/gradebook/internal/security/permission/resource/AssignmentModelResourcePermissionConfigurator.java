package com.liferay.training.gradebook.internal.security.permission.resource;

import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionLogic;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.training.gradebook.constants.GradebookConstants;
import com.liferay.training.gradebook.model.Assignment;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.Set;
import java.util.function.Consumer;

import static com.liferay.training.gradebook.constants.GradebookConstants.ASSIGNMENT_MODEL_CLASS_NAME;

@Component(
        property = "model.class.name=" + ASSIGNMENT_MODEL_CLASS_NAME,
        service = ModelResourcePermissionFactory.ModelResourcePermissionConfigurator.class
)
public class AssignmentModelResourcePermissionConfigurator
        implements ModelResourcePermissionFactory.ModelResourcePermissionConfigurator<Assignment> {

    /**
     * @param modelResourcePermission
     * @param consumer
     */
    @Override
    public void configureModelResourcePermissionLogics(
            ModelResourcePermission<Assignment> modelResourcePermission,
            Consumer<ModelResourcePermissionLogic<Assignment>> consumer) {

        consumer.accept(
                (permissionChecker, name, assignment, actionId) -> {
                    if (!_delegableActionIds.contains(actionId)) {
                        return null;
                    }
                    if (assignment == null) {
                        return null;
                    }

                    return _portletResourcePermission.contains(
                            permissionChecker, assignment.getGroupId(),
                            ActionKeys.ADD_ENTRY);
                });
    }

    private static final Set<String> _delegableActionIds =
            SetUtil.fromArray(ActionKeys.DELETE, ActionKeys.UPDATE);

    @Reference(target = "(resource.name=" + GradebookConstants.RESOURCE_NAME + ")")
    private PortletResourcePermission _portletResourcePermission;

}
