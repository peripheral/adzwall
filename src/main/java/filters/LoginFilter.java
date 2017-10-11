package filters;

import java.io.IOException;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.*;
import javax.servlet.http.*;

import mBeans.SessionBean;

public class LoginFilter implements Filter{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		HttpSession session = request.getSession();
		SessionBean sessionB = (SessionBean) session.getAttribute("SessionBean"); 
		String url = request.getRequestURI();

		if(isValidUrl(url)&& sessionB == null){
			System.out.println("Attept to request restricted page without activ logedSession.");
			response.sendRedirect(request.getContextPath() + "/index.xhtml?redirect="+url); 
		}
		else if(isValidUrl(url)&& !sessionB.isLogged()){
			System.out.println("Not logged but has session, atempts to read restricted page");
			response.sendRedirect(request.getContextPath() + "/index.xhtml?redirect="+url); 
		}else{
			chain.doFilter(req, res); // Logged-in user found, so just continue request.
		}
	}
	//Pages that can be showed without login
	private boolean isValidUrl(String url){
		return (!url.contains("index.xhtml")&& !url.contains("Info.xhtml")
				&& !url.contains("Info.xhtml")&& !url.contains("Registrera.xhtml")
				&& !url.contains("Aboutus.xhtml"));
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

}
