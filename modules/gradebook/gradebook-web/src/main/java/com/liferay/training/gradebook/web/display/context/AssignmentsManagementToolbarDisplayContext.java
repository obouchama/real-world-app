package com.liferay.training.gradebook.web.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.BaseManagementToolbarDisplayContext;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.*;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.*;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.training.gradebook.web.constants.GradebookPortletKeys;
import com.liferay.training.gradebook.web.constants.MVCCommandNames;
import com.liferay.training.gradebook.web.internal.security.permission.resource.AssignmentTopLevelPermission;

import javax.portlet.PortletException;
import javax.portlet.PortletURL;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Assigments management toolbar display context.
 * <p>
 * This class passes contextual information to the user interface for the Clay
 * management toolbar.
 *
 * @author Otmane.Bouchama
 */
public class AssignmentsManagementToolbarDisplayContext extends BaseManagementToolbarDisplayContext {

    public AssignmentsManagementToolbarDisplayContext(LiferayPortletRequest liferayPortletRequest,
                                                      LiferayPortletResponse liferayPortletResponse, HttpServletRequest httpServletRequest) {
        super(liferayPortletRequest, liferayPortletResponse, httpServletRequest);
        _liferayPortletRequest = liferayPortletRequest;
        _liferayPortletResponse = liferayPortletResponse;
        _portalPreferences = PortletPreferencesFactoryUtil.getPortalPreferences(liferayPortletRequest);
        _themeDisplay = (ThemeDisplay) httpServletRequest.getAttribute(WebKeys.THEME_DISPLAY);
    }

    /**
     * Returns the creation menu for the toolbar (plus sign on the management
     * toolbar).
     *
     * @return creation menu
     */
    public CreationMenu getCreationMenu() {

        // Check if user has permissions to add assignments.
        if (!AssignmentTopLevelPermission.contains(
                _themeDisplay.getPermissionChecker(),
                _themeDisplay.getScopeGroupId(), "ADD_ENTRY")) {
            return null;
        }

        // Create the menu.
        return new CreationMenu() {
            {
                addDropdownItem(dropdownItem -> {
                    dropdownItem.setHref(liferayPortletResponse.createRenderURL(), "mvcRenderCommandName",
                            MVCCommandNames.EDIT_ASSIGNMENT, "redirect", currentURLObj.toString());
                    dropdownItem.setLabel(LanguageUtil.get(request, "add-assignment"));
                });
            }
        };
    }

    @Override
    public String getClearResultsURL() {
        return getSearchActionURL();
    }

    /**
     * Returns the assignment list display style.
     * <p>
     * Current selection is stored in portal preferences.
     *
     * @return current display style
     */
    public String getDisplayStyle() {

        String displayStyle = ParamUtil.getString(request, "displayStyle");

        if (Validator.isNull(displayStyle)) {
            displayStyle = _portalPreferences.getValue(GradebookPortletKeys.GRADEBOOK, "assignments-display-style", "descriptive");
        } else {
            _portalPreferences.setValue(GradebookPortletKeys.GRADEBOOK, "assignments-display-style", displayStyle);

            request.setAttribute(WebKeys.SINGLE_PAGE_APPLICATION_CLEAR_CACHE, Boolean.TRUE);
        }

        return displayStyle;
    }

    /**
     * Returns the sort order column.
     *
     * @return sort column
     */
    public String getOrderByCol() {

        return ParamUtil.getString(request, "orderByCol", "title");
    }

    /**
     * Returns the sort type (ascending / descending).
     *
     * @return sort type
     */
    public String getOrderByType() {

        return ParamUtil.getString(request, "orderByType", "asc");
    }

    /**
     * Returns the action URL for the search.
     *
     * @return search action URL
     */
    @Override
    public String getSearchActionURL() {

        return PortletURLBuilder.createRenderURL(
                _liferayPortletResponse
        ).setMVCRenderCommandName(
                MVCCommandNames.VIEW_ASSIGNMENTS
        ).setNavigation(
                ParamUtil.getString(request, "navigation", "entries")
        ).setParameter(
                "orderByCol", getOrderByCol()
        ).setParameter(
                "orderByType", getOrderByType()
        ).buildString();
    }

    /**
     * Returns the view type options (card, list, table).
     *
     * @return list of view types
     */
    @Override
    public List<ViewTypeItem> getViewTypeItems() {

        int delta = ParamUtil.getInteger(request, SearchContainer.DEFAULT_DELTA_PARAM);
        String deltaObj = (delta > 0) ? String.valueOf(delta) : null;

        int cur = ParamUtil.getInteger(request, SearchContainer.DEFAULT_CUR_PARAM);
        String curObj = (cur > 0) ? String.valueOf(cur) : null;

        String orderByCol = ParamUtil.getString(request, "orderByCol", "title");
        String orderByType = ParamUtil.getString(request, "orderByType", "asc");

        PortletURL portletURL = PortletURLBuilder.createRenderURL(
                _liferayPortletResponse
        ).setMVCRenderCommandName(
                MVCCommandNames.VIEW_ASSIGNMENTS
        ).setParameter(
                "delta", deltaObj, false
        ).setParameter(
                "cur", curObj, false
        ).setParameter(
                "orderByCol", orderByCol
        ).setParameter(
                "orderByType", orderByType
        ).buildPortletURL();

        return new ViewTypeItemList(portletURL, getDisplayStyle()) {
            {
                addCardViewTypeItem();

                addListViewTypeItem();

                addTableViewTypeItem();
            }
        };
    }

    /**
     * Return the option items for the sort column menu.
     *
     * @return options list for the sort column menu
     */
    @Override
    protected List<DropdownItem> getOrderByDropdownItems() {
        return new DropdownItemList() {
            {
                add(dropdownItem -> {
                    dropdownItem.setActive("title".equals(getOrderByCol()));
                    dropdownItem.setHref(_getCurrentSortingURL(), "orderByCol", "title");
                    dropdownItem.setLabel(LanguageUtil.get(request, "title"));
                });

                add(dropdownItem -> {
                    dropdownItem.setActive("createDate".equals(getOrderByCol()));
                    dropdownItem.setHref(_getCurrentSortingURL(), "orderByCol", "createDate");
                    dropdownItem.setLabel(LanguageUtil.get(request, "create-date"));
                });
            }
        };
    }

    /**
     * Returns the current sorting URL.
     *
     * @return current sorting portlet URL
     * @throws PortletException
     */
    private PortletURL _getCurrentSortingURL() throws PortletException {
        PortletURL sortingURL = PortletURLUtil.clone(currentURLObj, liferayPortletResponse);

        sortingURL.setParameter("mvcRenderCommandName", MVCCommandNames.VIEW_ASSIGNMENTS);

        // Reset current page.

        sortingURL.setParameter(SearchContainer.DEFAULT_CUR_PARAM, "0");

        String keywords = ParamUtil.getString(request, "keywords");

        if (Validator.isNotNull(keywords)) {
            sortingURL.setParameter("keywords", keywords);
        }

        return sortingURL;
    }

    private final LiferayPortletRequest _liferayPortletRequest;
    private final LiferayPortletResponse _liferayPortletResponse;
    private final PortalPreferences _portalPreferences;
    private final ThemeDisplay _themeDisplay;
}