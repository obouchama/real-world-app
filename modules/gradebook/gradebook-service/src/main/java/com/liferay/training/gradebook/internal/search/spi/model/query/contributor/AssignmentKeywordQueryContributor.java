package com.liferay.training.gradebook.internal.search.spi.model.query.contributor;

import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.search.query.QueryHelper;
import com.liferay.portal.search.spi.model.query.contributor.KeywordQueryContributor;
import com.liferay.portal.search.spi.model.query.contributor.helper.KeywordQueryContributorHelper;
import com.liferay.training.gradebook.constants.GradebookConstants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import static com.liferay.training.gradebook.constants.GradebookConstants.ASSIGNMENT_MODEL_CLASS_NAME;

@Component(
        immediate = true,
        property = "indexer.class.name=" + ASSIGNMENT_MODEL_CLASS_NAME,
        service = KeywordQueryContributor.class
)
public class AssignmentKeywordQueryContributor
        implements KeywordQueryContributor {
    @Override
    public void contribute(
            String keywords, BooleanQuery booleanQuery,
            KeywordQueryContributorHelper keywordQueryContributorHelper) {

        SearchContext searchContext =
                keywordQueryContributorHelper.getSearchContext();
        queryHelper.addSearchLocalizedTerm(
                booleanQuery, searchContext, Field.DESCRIPTION, false);
        queryHelper.addSearchLocalizedTerm(
                booleanQuery, searchContext, Field.TITLE, false);
    }

    @Reference
    protected QueryHelper queryHelper;
}
