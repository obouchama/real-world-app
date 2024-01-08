package com.liferay.training.gradebook.internal.search.spi.model.result.contributor;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Localization;
import com.liferay.portal.search.spi.model.result.contributor.ModelSummaryContributor;

import java.util.Locale;

/**
 * @author Otmane.Bouchama
 */
public class AssignmentModelSummaryContributor implements ModelSummaryContributor {
    public AssignmentModelSummaryContributor(Localization localization) {
        _localization = localization;
    }

    @Override
    public Summary getSummary(
            Document document, Locale locale, String snippet) {

        String languageId = LocaleUtil.toLanguageId(locale);

        return _createSummary(
                document, _localization.getLocalizedName(Field.DESCRIPTION, languageId),
                _localization.getLocalizedName(Field.TITLE, languageId));
    }

    private Summary _createSummary(
            Document document, String descriptionField, String titleField) {

        String prefix = Field.SNIPPET + StringPool.UNDERLINE;

        Summary summary = new Summary(
                document.get(prefix + titleField, titleField),
                document.get(prefix + descriptionField, descriptionField));

        summary.setMaxContentLength(200);

        return summary;
    }

    private final Localization _localization;
}
