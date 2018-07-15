package com.rensanning.crawler.image;

public class Startup {

	public static void main(String[] args) {
		//new BaiduImageCrawler().fetch("美女", "D:\\data\\baidu\\");
		// new BingImageCrawler().fetch("美女", "D:\\data\\bing\\");
		new GoogleImageCrawler().fetch("happy", "data\\google\\");
	}

}
