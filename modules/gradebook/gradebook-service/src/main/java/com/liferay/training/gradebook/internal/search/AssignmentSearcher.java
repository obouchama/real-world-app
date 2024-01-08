package com.liferay.training.gradebook.internal.search;

import com.liferay.portal.kernel.search.BaseSearcher;
import com.liferay.portal.kernel.search.Field;
import com.liferay.training.gradebook.constants.GradebookConstants;
import com.liferay.training.gradebook.model.Assignment;
import org.osgi.service.component.annotations.Component;

import static com.liferay.training.gradebook.constants.GradebookConstants.ASSIGNMENT_MODEL_CLASS_NAME;

/**
 * @author Otmane.Bouchama
 */
@Component(
        property = "model.class.name=" + ASSIGNMENT_MODEL_CLASS_NAME,
        service = BaseSearcher.class
)
public class AssignmentSearcher extends BaseSearcher {

    public AssignmentSearcher() {
        setDefaultSelectedFieldNames(
                Field.ASSET_TAG_NAMES,
                Field.COMPANY_ID,
                Field.ENTRY_CLASS_NAME,
                Field.ENTRY_CLASS_PK,
                Field.GROUP_ID,
                Field.MODIFIED_DATE,
                Field.SCOPE_GROUP_ID,
                Field.UID
        );
        setFilterSearch(true);
        setPermissionAware(true);
    }

    @Override
    public String getClassName() {
        return _CLASS_NAME;
    }

    private static final String _CLASS_NAME = Assignment.class.getName();

}
