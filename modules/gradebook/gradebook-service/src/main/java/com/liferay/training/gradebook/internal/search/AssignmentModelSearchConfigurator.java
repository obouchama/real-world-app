package com.liferay.training.gradebook.internal.search;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.Localization;
import com.liferay.portal.search.batch.DynamicQueryBatchIndexingActionableFactory;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchConfigurator;
import com.liferay.portal.search.spi.model.result.contributor.ModelSummaryContributor;
import com.liferay.portal.search.spi.model.result.contributor.ModelVisibilityContributor;
import com.liferay.training.gradebook.internal.search.spi.model.index.contributor.AssignmentModelIndexerWriterContributor;
import com.liferay.training.gradebook.internal.search.spi.model.result.contributor.AssignmentModelSummaryContributor;
import com.liferay.training.gradebook.internal.search.spi.model.result.contributor.AssignmentModelVisibilityContributor;
import com.liferay.training.gradebook.model.Assignment;
import com.liferay.training.gradebook.service.AssignmentLocalService;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;


/**
 * @author Otmane.Bouchama
 */
@Component(service = ModelSearchConfigurator.class)
public class AssignmentModelSearchConfigurator implements ModelSearchConfigurator<Assignment> {

    @Override
    public String getClassName() {
        return Assignment.class.getName();
    }

    @Override
    public String[] getDefaultSelectedFieldNames() {
        return new String[]{
                Field.ASSET_TAG_NAMES, Field.COMPANY_ID, Field.DESCRIPTION,
                Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK, Field.GROUP_ID,
                Field.MODIFIED_DATE, Field.SCOPE_GROUP_ID, Field.TITLE, Field.UID
        };
    }

    @Override
    public String[] getDefaultSelectedLocalizedFieldNames() {
        return new String[]{Field.DESCRIPTION, Field.TITLE};
    }

    @Override
    public ModelIndexerWriterContributor<Assignment>
    getModelIndexerWriterContributor() {

        return _modelIndexWriterContributor;
    }

    @Override
    public ModelSummaryContributor getModelSummaryContributor() {
        return _modelSummaryContributor;
    }

    @Override
    public ModelVisibilityContributor getModelVisibilityContributor() {
        return _modelVisibilityContributor;
    }

    @Activate
    protected void activate() {
        _modelIndexWriterContributor =
                new AssignmentModelIndexerWriterContributor(
                        _assignmentLocalService,
                        _dynamicQueryBatchIndexingActionableFactory);
        _modelSummaryContributor = new AssignmentModelSummaryContributor(
                _localization);
        _modelVisibilityContributor = new AssignmentModelVisibilityContributor(
                _assignmentLocalService);
    }

    @Reference
    private AssignmentLocalService _assignmentLocalService;

    @Reference
    private DynamicQueryBatchIndexingActionableFactory _dynamicQueryBatchIndexingActionableFactory;

    @Reference
    private Localization _localization;

    private ModelIndexerWriterContributor<Assignment> _modelIndexWriterContributor;

    private ModelSummaryContributor _modelSummaryContributor;

    private ModelVisibilityContributor _modelVisibilityContributor;

}