package com.xfashion.pdf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.oauth.OAuthService;
import com.google.appengine.api.oauth.OAuthServiceFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.tools.admin.OAuth2ServerConnection;
import com.google.appengine.tools.admin.AppAdminFactory.ConnectOptions;
import com.google.gdata.aeuploader.Uploader;
import com.google.gdata.client.AuthTokenFactory.AuthToken;
import com.google.gdata.client.DocumentQuery;
import com.google.gdata.client.GoogleAuthTokenFactory;
import com.google.gdata.client.docs.DocsService;
import com.google.gdata.client.media.ResumableGDataFileUploader;
import com.google.gdata.data.TextConstruct;
import com.google.gdata.data.docs.DocumentEntry;
import com.google.gdata.data.docs.DocumentListEntry;
import com.google.gdata.data.media.MediaByteArraySource;
import com.google.gdata.data.media.MediaFileSource;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;

public class StickerTest extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private final String URL_FEED = "/feeds";
	private final String URL_DOWNLOAD = "/download";
	private final String URL_DOCLIST_FEED = "/private/full";

	private final String URL_DEFAULT = "/default";
	private final String URL_FOLDERS = "/contents";
	private final String URL_ACL = "/acl";
	private final String URL_REVISIONS = "/revisions";

	private final String URL_CATEGORY_DOCUMENT = "/-/document";
	private final String URL_CATEGORY_SPREADSHEET = "/-/spreadsheet";
	private final String URL_CATEGORY_PDF = "/-/pdf";
	private final String URL_CATEGORY_PRESENTATION = "/-/presentation";
	private final String URL_CATEGORY_STARRED = "/-/starred";
	private final String URL_CATEGORY_TRASHED = "/-/trashed";
	private final String URL_CATEGORY_FOLDER = "/-/folder";
	private final String URL_CATEGORY_EXPORT = "/Export";

	private final String PARAMETER_SHOW_FOLDERS = "showfolders=true";
	
	private String authToken;

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) {
		try {
			service2(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void service2(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, MalformedURLException,
			IOException, ServiceException, InterruptedException, OAuthRequestException {
		ServletOutputStream out = response.getOutputStream();
		DocsService service = auth();
		
		out.print("DocsService, version=" + service.getServiceVersion());
		out.println("<br />");

		URL url;

		url = new URL("https://docs.google.com/feeds/default/private/full");
		DocumentQuery query = new DocumentQuery(url);

		byte[] bytes = (new String("das ist text.\nnoch mehr text\n")).getBytes();

		ByteArrayBlobProvider bp = new ByteArrayBlobProvider(bytes, "text/text");
		Uploader uploader = new Uploader(url, authToken, bp);
		String result = uploader.Create();
		System.out.println(result);

		// DocumentEntry entry = srvice.insert(url, de);

		// DocumentListFeed feed = service.query(query, DocumentListFeed.class);
		// DocumentListEntry entry = feed.getEntries().get(0);

		// DocumentEntry newEntry = new DocumentEntry();
		// newEntry.setTitle(new PlainTextConstruct("barcode2"));
		// DocumentEntry entry = service.insert(url, newEntry);
		//out.print("content-type=" + uploadedEntry.getContent().getType());

	}

	private DocsService auth() throws AuthenticationException {
		String USERNAME = "archmage74@gmail.com";
		String PASSWORD = "%necro7!";

		DocsService service = new DocsService("MyDocumentsListIntegration-v1");
		service.setUserCredentials(USERNAME, PASSWORD);
		
		GoogleAuthTokenFactory.UserToken at = (GoogleAuthTokenFactory.UserToken) service.getAuthTokenFactory().getAuthToken();
		authToken = at.getValue();
		return service;
	}

}
