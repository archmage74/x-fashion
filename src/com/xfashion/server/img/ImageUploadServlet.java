package com.xfashion.server.img;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

public class ImageUploadServlet extends HttpServlet {

	private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

	private static final long serialVersionUID = 1L;

//	private static final boolean PRODUCTION_MODE = SystemProperty.environment.value() == SystemProperty.Environment.Value.Production;
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(request);
		BlobKey blobKey = blobs.get("upload").get(0);
		String key = blobKey.getKeyString();
		
//		ImagesService is = ImagesServiceFactory.getImagesService();
//		String url = is.getServingUrl(blobKey);
//		if (!PRODUCTION_MODE) {
//			url = url.replaceAll("http://0.0.0.0:8888", "http://127.0.0.1:8888");
//		}
		
		response.sendRedirect("/img/imageupload?blobkey=" + key);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String imageUrl = req.getParameter("blobkey");
		resp.setHeader("Content-Type", "text/html");
		resp.getWriter().print(imageUrl);
	}

	// private void sendToBlobStore(String id, String cmd, byte[] imageBytes)
	// throws IOException {
	// String urlStr = URL_PREFIX +
	// BlobstoreServiceFactory.getBlobstoreService().createUploadUrl("/blobimage");
	// URLFetchService urlFetch = URLFetchServiceFactory.getURLFetchService();
	// HTTPRequest req = new HTTPRequest(new URL(urlStr), HTTPMethod.POST,
	// FetchOptions.Builder.withDeadline(10.0));
	//
	// String boundary = makeBoundary();
	//
	// req.setHeader(new HTTPHeader("Content-Type",
	// "multipart/form-data; boundary=" + boundary));
	//
	// ByteArrayOutputStream baos = new ByteArrayOutputStream();
	//
	// write(baos, "--" + boundary + "\r\n");
	// writeParameter(baos, "id", id);
	// write(baos, "--" + boundary + "\r\n");
	// writeImage(baos, cmd, imageBytes);
	// write(baos, "--" + boundary + "--\r\n");
	//
	// req.setPayload(baos.toByteArray());
	// try {
	// urlFetch.fetch(req);
	// } catch (IOException e) {
	// // Need a better way of handling Timeout exceptions here - 10 second
	// // deadline
	// }
	// }
	//
	// private static Random random = new Random();
	//
	// private static String randomString() {
	// return Long.toString(random.nextLong(), 36);
	// }
	//
	// private String makeBoundary() {
	// return "---------------------------" + randomString() + randomString() +
	// randomString();
	// }
	//
	// private void write(OutputStream os, String s) throws IOException {
	// os.write(s.getBytes());
	// }
	//
	// private void writeParameter(OutputStream os, String name, String value)
	// throws IOException {
	// write(os,
	// "Content-Disposition: form-data; name=\""+name+"\"\r\n\r\n"+value+"\r\n");
	// }
	//
	// private void writeImage(OutputStream os, String name, byte[] bs) throws
	// IOException {
	// write(os,
	// "Content-Disposition: form-data; name=\""+name+"\"; filename=\"image.jpg\"\r\n");
	// write(os, "Content-Type: image/jpeg\r\n\r\n");
	// os.write(bs);
	// write(os, "\r\n");
	// }
	// private ArticleTypeImage createArticleTypeImage(String filename, byte[]
	// content) {
	// ImagesService is = ImagesServiceFactory.getImagesService();
	// Image img = ImagesServiceFactory.makeImage(content);
	// Transform resize = ImagesServiceFactory.makeResize(400, 400, 200, 200);
	// is.applyTransform(resize, img);
	//
	// ArticleTypeImage ati = new ArticleTypeImage();
	// return ati;
	// }
	//
	// private byte[] readBytes(FileItemStream item) throws IOException {
	// InputStream stream = item.openStream();
	//
	// ArrayList<byte[]> l = new ArrayList<byte[]>();
	// int sumLen = 0;
	// int len = 0;
	// int chunkSize = 8192;
	// while (len != -1) {
	// byte[] buffer = new byte[chunkSize];
	// len = stream.read(buffer, 0, buffer.length);
	// if (len != -1) {
	// l.add(buffer);
	// sumLen += len;
	// }
	// }
	// return listToBytes(l, sumLen, 8192);
	// }
	//
	// private byte[] listToBytes(ArrayList<byte[]> l, int sumLen, int
	// chunkSize) {
	// byte[] buffer = new byte[sumLen];
	// int pos = 0;
	// for (byte[] b : l) {
	// int len = chunkSize;
	// if (sumLen - pos < chunkSize) {
	// len = sumLen - pos;
	// }
	// System.arraycopy(b, 0, buffer, pos, len);
	// pos += chunkSize;
	// }
	// return buffer;
	// }

}
