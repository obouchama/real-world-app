package com.liferay.training.gradebook.internal.search.spi.model.index.contributor;

import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.HtmlParser;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Localization;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;
import com.liferay.training.gradebook.constants.GradebookConstants;
import com.liferay.training.gradebook.model.Assignment;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.Locale;

import static com.liferay.training.gradebook.constants.GradebookConstants.ASSIGNMENT_MODEL_CLASS_NAME;

/**
 * @author Otmane.Bouchama
 */
@Component(
        property = "indexer.class.name=" + ASSIGNMENT_MODEL_CLASS_NAME,
        service = ModelDocumentContributor.class
)
public class AssignmentModelDocumentContributor
        implements ModelDocumentContributor<Assignment> {

    @Override
    public void contribute(Document document, Assignment assignment) {

        String description = _htmlParser.extractText(assignment.getDescription());
        document.addText(Field.DESCRIPTION, description);

        String title = _htmlParser.extractText(assignment.getTitle());
        document.addText(Field.TITLE, title);

        document.addDate(Field.MODIFIED_DATE, assignment.getModifiedDate());

        // Handle localized fields.
        for (Locale locale :
                _language.getAvailableLocales(assignment.getGroupId())) {

            String languageId = LocaleUtil.toLanguageId(locale);

            document.addText(
                    _localization.getLocalizedName(Field.DESCRIPTION, languageId),
                    description);
            document.addText(
                    _localization.getLocalizedName(Field.TITLE, languageId),
                    title);
        }
    }

    @Reference
    private HtmlParser _htmlParser;

    @Reference
    private Language _language;

    @Reference
    private Localization _localization;

}
