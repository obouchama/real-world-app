package com.liferay.training.gradebook.internal.search.spi.model.result.contributor;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.search.spi.model.result.contributor.ModelVisibilityContributor;
import com.liferay.training.gradebook.model.Assignment;
import com.liferay.training.gradebook.service.AssignmentLocalService;

/**
 * @author Otmane.Bouchama
 */
public class AssignmentModelVisibilityContributor implements ModelVisibilityContributor {
    public AssignmentModelVisibilityContributor(
            AssignmentLocalService assignmentLocalService) {

        _assignmentLocalService = assignmentLocalService;
    }

    @Override
    public boolean isVisible(long classPK, int status) {
        try {
            Assignment entry = _assignmentLocalService.getAssignment(classPK);

            return isVisible(entry.getStatus(), status);
        }
        catch (PortalException portalException) {
            if (_log.isWarnEnabled()) {
                _log.warn(
                        "Unable to check visibility for assignment ",
                        portalException);
            }

            return false;
        }
    }

    private static final Log _log = LogFactoryUtil.getLog(
            AssignmentModelVisibilityContributor.class);

    private final AssignmentLocalService _assignmentLocalService;
    
}
