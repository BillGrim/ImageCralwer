package com.rensanning.crawler.image;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.concurrent.ConcurrentLinkedQueue;

public abstract class ImageCrawler {

	private static final int CONN_TIME_OUT = 5 * 1000;
	private static final String USER_AGENT = "User-Agent:Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.63 Safari/537.36";
	private static final int DEFAULT_MAX_THREAD = 100;
	private static final String THREAD_NAME_STRING = "%1$-15s";
	private static final String DEFAULT_ENCODE = "UTF-8";

	public ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<String>();

	public void fetch(String keyword, String basePath) {
		fetch(keyword, basePath, DEFAULT_MAX_THREAD);
	}
	
	public void fetch(String keyword, String basePath, int maxThread) {
		
		String type = this.getClass().getSimpleName().replaceAll("ImageCrawler", "");
		
		new File(basePath + "\\md5\\").mkdirs();
		new File(basePath + "\\duplicate\\").mkdirs();
		File saveDir = new File(basePath);
		
		startThread(saveDir, maxThread, type);

		int page = 0;
		while (true) {
			try {
				String url = getSearchUrl(URLEncoder.encode(keyword, DEFAULT_ENCODE), page);

				System.out.println(type + " Crawler will start. URL：" + url);
				StringBuffer data = getPage(url);
	            int count = parseImageUrl(queue, data);
				System.out.println(type + " Crawler Completed (" + count + " images found). URL：" + url);
				
				if (count == 0) {
					break;
				}
				
				page++;
				if (!queue.isEmpty()) {
					Thread.sleep(1000);
				}
			} catch (Exception e) {
				System.err.println(type + " Crawler Failed. Error:" + e.getMessage());
				page++;
			}
		}

	}

	private StringBuffer getPage(String url) throws Exception {
		URL uri = new URL(url);
		URLConnection con = uri.openConnection();
		con.setConnectTimeout(CONN_TIME_OUT);
		con.setRequestProperty("User-Agent", USER_AGENT);
		InputStream is = con.getInputStream();
		BufferedReader in = new BufferedReader(new InputStreamReader(is, DEFAULT_ENCODE));  
		String line;
		StringBuffer data = new StringBuffer();
		while ((line = in.readLine()) != null) {  
			data.append(line);  
		}  
		in.close();
		return data;
	}

	private void startThread(File saveDir, int maxThread, String type) {
		int threadNum = 0;
		while (threadNum < maxThread) {
			Thread t = new Thread(new ImageWorker(queue, saveDir));
			t.setName(String.format(THREAD_NAME_STRING, type + "Thread-" + threadNum));
			t.start();
			threadNum++;
		}
	}
	
	public abstract String getSearchUrl(String keyword, int begin);
	public abstract int parseImageUrl(ConcurrentLinkedQueue<String> queue, StringBuffer data);
}
