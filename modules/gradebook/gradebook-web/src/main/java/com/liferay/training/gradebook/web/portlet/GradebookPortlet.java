package com.liferay.training.gradebook.web.portlet;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.site.navigation.model.SiteNavigationMenu;
import com.liferay.site.navigation.model.SiteNavigationMenuItem;
import com.liferay.site.navigation.service.SiteNavigationMenuItemLocalServiceUtil;
import com.liferay.site.navigation.service.SiteNavigationMenuLocalServiceUtil;
import com.liferay.training.gradebook.web.constants.GradebookPortletKeys;
import org.osgi.service.component.annotations.Component;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
/**
 * @author Otmane.Bouchama
 */
@Component(
        property = {
                "com.liferay.portlet.display-category=category.training",
                "com.liferay.portlet.header-portlet-css=/css/main.scss",
                "com.liferay.portlet.instanceable=false",
                "javax.portlet.display-name=Gradebook",
                "javax.portlet.init-param.template-path=/",
                "javax.portlet.init-param.view-template=/view.jsp",
                "javax.portlet.name=" + GradebookPortletKeys.GRADEBOOK,
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.security-role-ref=power-user,user"
        },
        service = Portlet.class
)
public class GradebookPortlet extends MVCPortlet {
}