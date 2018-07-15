package com.rensanning.crawler.image;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BingImageCrawler extends ImageCrawler {

	//async=content
	//q=美女 搜索关键字
	//first=118 开始条数
	//count=35 显示数量
	private static final String BING_IMAGE_SEARCH_URL = "http://www.bing.com/images/async?async=content&q=%s&first=%d&count=%d";
	private static final int PAGE_SIZE = 35;
	private static final String IMAGE_URL_REG = "imgurl:&quot;(https?://[^,]+)&quot;";
	private static final Pattern IMAGE_PATTERN = Pattern.compile(IMAGE_URL_REG);
	
	@Override
	public String getSearchUrl(String keyword, int page) {
		int begin = page * PAGE_SIZE;
		return String.format(BING_IMAGE_SEARCH_URL, keyword, begin, PAGE_SIZE);
	}

	@Override
	public int parseImageUrl(ConcurrentLinkedQueue<String> queue, StringBuffer data) {
		int count = 0;
		Matcher matcher = IMAGE_PATTERN.matcher(data);
		while (matcher.find()) {
			queue.offer(matcher.group(1));
			count++;
		}
		return count;
	}

}
