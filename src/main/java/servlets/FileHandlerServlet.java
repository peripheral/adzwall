package servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;

import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import db.SqlDb;
import mBeans.SessionBean;

/**
 * Servlet implementation class FileHandlerServlet
 */
@WebServlet("/FileHandlerServlet")
@MultipartConfig
public class FileHandlerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@ManagedProperty(value="#{SessionB}")
	private SessionBean sessionData;
	
	@Inject
	private SqlDb db;

	public void setSessionData(SessionBean session){
		sessionData = session;
	}
	public SessionBean getSessionData(){
		return sessionData;
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FileHandlerServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//sessionData = (SessionBean) request.getSession().getAttribute("SessionBean");
		if(sessionData == null){
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			return;
		}
		if(sessionData != null && !sessionData.isLogged()){
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			return;
		}
		try{
		response.setStatus(HttpServletResponse.SC_OK);
		String basePath = System.getProperty("jboss.server.data.dir");
		OutputStream outStream = response.getOutputStream();
		System.out.println(request.getRequestURI());
		InputStream inStream = new FileInputStream(new File(basePath+"/"+request.getRequestURI()));
		byte[] buffer = new byte[512];
		int read = inStream.read(buffer);
		while(read>0){
			outStream.write(buffer);
			read = inStream.read(buffer);
		}
		outStream.close();
		inStream.close();
		}catch(Exception e){
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		System.out.println("File upload finished");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		sessionData = (SessionBean) request.getSession().getAttribute("SessionBean");
		if(sessionData == null){
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			return;
		}
		if(sessionData != null && !sessionData.isLogged()){
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			return;
		}
		String basePath = System.getProperty("jboss.server.data.dir");
		Part p = request.getPart("userfile");
		String adId = request.getParameter("id");
		if(p==null || adId == null){
			System.out.println("id"+adId);
			System.out.println("part was null");
			return;
		}
		Long id = -1l;
		try{
			id =Long.valueOf(adId.trim());
		}catch(Exception e){
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		

		db.newMediaFile(id, p.getSubmittedFileName());
		System.out.println("id"+ adId);
		File newFile = new File(basePath+"/media/"+sessionData.getEmail()+"/"+adId+"/"+p.getSubmittedFileName());
		File theDir = new File(basePath+"/media");
		//Make directory if it doesn't esists
		if (!theDir.exists()) theDir.mkdir();
		theDir = new File(basePath+"/media/"+sessionData.getEmail());
		if (!theDir.exists()) theDir.mkdir();
		theDir = new File(basePath+"/media/"+sessionData.getEmail()+"/"+adId);
		if (!theDir.exists()) theDir.mkdir();
		System.out.println(newFile.getAbsolutePath());
		Collection<Part> parts =  request.getParts();
		System.out.println("Parts"+parts.size());
		System.out.println(request.getContentType()+request.getParameterMap().keySet());
		for(Part pa:parts){
			System.out.println("Part:"+pa.getSubmittedFileName());
		}
		System.out.println(request.getRequestURI());
		InputStream inStream = p.getInputStream();
		OutputStream outStream = new FileOutputStream(newFile);
		byte[] buffer = new byte[512];
		int read = inStream.read(buffer);
		while(read>0){
			outStream.write(buffer);
			read = inStream.read(buffer);
		}
		outStream.close();
		inStream.close();
		System.out.println("File upload finished");
	}

}
