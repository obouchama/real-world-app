package com.liferay.training.gradebook.web.display.taglib.util;

import com.liferay.frontend.taglib.clay.servlet.taglib.BaseBaseClayCard;
import com.liferay.frontend.taglib.clay.servlet.taglib.VerticalCard;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.training.gradebook.model.Assignment;
import com.liferay.training.gradebook.web.constants.MVCCommandNames;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.liferay.training.gradebook.constants.GradebookConstants.ASSIGNMENT_ID;

public class AssignmentVerticalCard extends BaseBaseClayCard implements VerticalCard {

    public AssignmentVerticalCard(
            Assignment assignment, LiferayPortletRequest liferayPortletRequest,
            LiferayPortletResponse liferayPortletResponse,
            RowChecker rowChecker) {

        super(assignment, rowChecker);

        _assignment = assignment;
        _liferayPortletRequest = liferayPortletRequest;
        _liferayPortletResponse = liferayPortletResponse;
        _httpServletRequest = PortalUtil.getHttpServletRequest(liferayPortletRequest);
        _currentURLObj = PortletURLUtil.getCurrent(liferayPortletRequest, liferayPortletResponse);
        _themeDisplay = (ThemeDisplay) liferayPortletRequest.getAttribute(WebKeys.THEME_DISPLAY);
    }

    public AssignmentVerticalCard(
            Assignment assignment, PortletRequest renderRequest,
            PortletResponse renderResponse,
            RowChecker rowChecker) {

        this(assignment, PortalUtil.getLiferayPortletRequest(renderRequest), PortalUtil.getLiferayPortletResponse(renderResponse), rowChecker);
    }

    @Override
    public List<DropdownItem> getActionDropdownItems() {
        AssignmentActionDropdownItems assignmentActionDropdownItems =
                new AssignmentActionDropdownItems(
                        _assignment, _liferayPortletRequest, _liferayPortletResponse, _currentURLObj);

        return assignmentActionDropdownItems.getActionDropdownItems();
    }

    @Override
    public String getHref() {
        return PortletURLBuilder.createRenderURL(
                _liferayPortletResponse
        ).setMVCRenderCommandName(
                MVCCommandNames.VIEW_ASSIGNMENT
        ).setRedirect(
                _currentURLObj.toString()
        ).setParameter(
                ASSIGNMENT_ID, _assignment.getAssignmentId()
        ).buildString();
    }

    @Override
    public String getIcon() {
        return "cards2";
    }

    @Override
    public String getInputValue() {
        if (Validator.isNull(super.getInputValue())) {
            return null;
        }

        return String.valueOf(_assignment.getAssignmentId());
    }

    @Override
    public String getSubtitle() {
        String modifiedDateDescription =
                LanguageUtil.getTimeDescription(
                        _httpServletRequest, System.currentTimeMillis() - _assignment.getModifiedDate().getTime(), true);
        String pattern = LanguageUtil.get(_httpServletRequest, "x-modified-x-ago");

        return LanguageUtil.format(_httpServletRequest, pattern, new String[]{_assignment.getUserName(), modifiedDateDescription});
    }

    @Override
    public String getTitle() {
        return _assignment.getTitle(_themeDisplay.getLocale());
    }

    @Override
    public boolean isSelectable() {
        return false;
    }

    private static final Log _log = LogFactoryUtil.getLog(AssignmentVerticalCard.class);

    private final Assignment _assignment;
    private final HttpServletRequest _httpServletRequest;
    private final LiferayPortletRequest _liferayPortletRequest;
    private final LiferayPortletResponse _liferayPortletResponse;
    private final PortletURL _currentURLObj;
    private final ThemeDisplay _themeDisplay;
}
