package com.liferay.training.gradebook.internal.search.spi.model.query.contributor;

import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.search.localization.SearchLocalizationHelper;
import com.liferay.portal.search.query.QueryHelper;
import com.liferay.portal.search.spi.model.query.contributor.KeywordQueryContributor;
import com.liferay.portal.search.spi.model.query.contributor.helper.KeywordQueryContributorHelper;
import com.liferay.training.gradebook.constants.GradebookConstants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import static com.liferay.training.gradebook.constants.GradebookConstants.ASSIGNMENT_MODEL_CLASS_NAME;

/**
 * @author Otmane.Bouchama
 */
@Component(
        property = "indexer.class.name=" + ASSIGNMENT_MODEL_CLASS_NAME,
        service = KeywordQueryContributor.class
)
public class AssignmentModelPreFilterContributor
        implements KeywordQueryContributor {

    @Override
    public void contribute(
            String keywords, BooleanQuery booleanQuery,
            KeywordQueryContributorHelper keywordQueryContributorHelper) {

        SearchContext searchContext =
                keywordQueryContributorHelper.getSearchContext();

        _queryHelper.addSearchLocalizedTerm(
                booleanQuery, searchContext, Field.DESCRIPTION, false);
        _queryHelper.addSearchLocalizedTerm(
                booleanQuery, searchContext, Field.TITLE, false);

        QueryConfig queryConfig = searchContext.getQueryConfig();

        queryConfig.addHighlightFieldNames(
                _searchLocalizationHelper.getLocalizedFieldNames(
                        new String[] {Field.DESCRIPTION, Field.TITLE}, searchContext));
    }

    @Reference
    private QueryHelper _queryHelper;

    @Reference
    private SearchLocalizationHelper _searchLocalizationHelper;
}
