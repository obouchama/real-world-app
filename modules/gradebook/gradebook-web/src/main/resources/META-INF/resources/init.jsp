<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>

<%@ taglib prefix="clay" uri="http://liferay.com/tld/clay" %>

<%@ taglib prefix="liferay-item-selector" uri="http://liferay.com/tld/item-selector" %>
<%@ taglib prefix="liferay-frontend" uri="http://liferay.com/tld/frontend" %>
<%@ taglib prefix="liferay-security" uri="http://liferay.com/tld/security" %>

<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.asset.kernel.model.AssetRenderer"%>
<%@ page import="com.liferay.portal.kernel.util.WebKeys"%>

<%@ page import="com.liferay.portal.kernel.servlet.SessionErrors"%>
<%@ page import="com.liferay.portal.kernel.language.LanguageUtil"%>
<%@ page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>
<%@ page import="com.liferay.portal.kernel.util.HtmlUtil"%>

<%@ page import="java.util.Date"%>
<%@ page import="javax.portlet.WindowState"%>

<%@ page import="com.liferay.training.gradebook.model.Assignment"%>
<%@ page import="com.liferay.training.gradebook.web.constants.MVCCommandNames"%>
<%@ page import="com.liferay.training.gradebook.web.display.taglib.util.AssignmentVerticalCard"%>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />