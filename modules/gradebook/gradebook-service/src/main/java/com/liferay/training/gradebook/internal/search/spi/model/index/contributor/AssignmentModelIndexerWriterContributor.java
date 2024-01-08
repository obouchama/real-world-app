package com.liferay.training.gradebook.internal.search.spi.model.index.contributor;

import com.liferay.portal.search.batch.BatchIndexingActionable;
import com.liferay.portal.search.batch.DynamicQueryBatchIndexingActionableFactory;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.index.contributor.helper.IndexerWriterMode;
import com.liferay.portal.search.spi.model.index.contributor.helper.ModelIndexerWriterDocumentHelper;
import com.liferay.training.gradebook.model.Assignment;
import com.liferay.training.gradebook.service.AssignmentLocalService;

/**
 * @author Otmane.Bouchama
 */
public class AssignmentModelIndexerWriterContributor implements ModelIndexerWriterContributor<Assignment> {
    public AssignmentModelIndexerWriterContributor(
            AssignmentLocalService assignmentLocalService,
            DynamicQueryBatchIndexingActionableFactory dynamicQueryBatchIndexingActionableFactory) {
        _assignmentLocalService = assignmentLocalService;
        _dynamicQueryBatchIndexingActionableFactory = dynamicQueryBatchIndexingActionableFactory;
    }

    @Override
    public void customize(
            BatchIndexingActionable batchIndexingActionable,
            ModelIndexerWriterDocumentHelper modelIndexerWriterDocumentHelper) {

        batchIndexingActionable.setPerformActionMethod(
                (Assignment assignment) -> batchIndexingActionable.addDocuments(
                        modelIndexerWriterDocumentHelper.getDocument(assignment)));
    }

    @Override
    public BatchIndexingActionable getBatchIndexingActionable() {
        return _dynamicQueryBatchIndexingActionableFactory.
                getBatchIndexingActionable(
                        _assignmentLocalService.getIndexableActionableDynamicQuery());
    }

    @Override
    public long getCompanyId(Assignment assignment) {
        return assignment.getCompanyId();
    }

    @Override
    public IndexerWriterMode getIndexerWriterMode(Assignment assignment) {
        if (assignment.isApproved() || assignment.isDraft() ||
                assignment.isPending() ||
                assignment.isScheduled()) {

            return IndexerWriterMode.UPDATE;
        }

        if (!assignment.isApproved()) {
            return IndexerWriterMode.SKIP;
        }

        return IndexerWriterMode.DELETE;
    }

    private final AssignmentLocalService _assignmentLocalService;
    private final DynamicQueryBatchIndexingActionableFactory _dynamicQueryBatchIndexingActionableFactory;
}
