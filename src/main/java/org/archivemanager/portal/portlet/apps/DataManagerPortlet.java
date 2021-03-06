package org.archivemanager.portal.portlet.apps;
import java.io.IOException;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletRequest;

import org.archivemanager.portal.portlet.PortletSupport;
import org.heed.openapps.User;

import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.util.PortalUtil;


public class DataManagerPortlet extends PortletSupport {

	
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {
				
		include("/jsp/apps/data_portlet.jsp", renderRequest, renderResponse);
	}
	
	public void serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws IOException, PortletException {
		HttpServletRequest httpReq = PortalUtil.getHttpServletRequest(resourceRequest);		
		HttpServletRequest httpReq2 = PortalUtil.getOriginalServletRequest(httpReq);
		String serviceUrl = PropsUtil.get("openapps.service.url");
		
		resourceRequest.setAttribute("serviceUrl", serviceUrl);
		
		User user = getSecurityService().getCurrentUser(httpReq2);
		if(user != null) {
			resourceRequest.setAttribute("openapps_user", user);
			resourceRequest.setAttribute("roles", getRolesString(user.getRoles()));
		}
		
		includeResource("/jsp/apps/data.jsp", resourceRequest, resourceResponse);
	}
}
