package com.rensanning.crawler.image;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BaiduImageCrawler extends ImageCrawler {
	
	//tn:resultjsonavatarnew
	//ie:utf-8 字符编码（ie输入 oe输出）
	//word:美女 搜索关键字
	//pn:60 开始条数
	//rn:30 显示数量
	//z:0 尺寸（0全部尺寸 9特大 3大 2中 1小）
	//width:1024 自定义尺寸-宽
	//height:768 自定义尺寸-高
	//ic:0 颜色(0全部颜色 1红色 2黄色 4绿色 8青色 16蓝色 32紫色 64粉色 128棕色 256橙色 512黑色 1024白色 2048黑白)
	//s:0 3头像图片
	//face:0 1面部特写
	//st:-1 -1全部类型 1卡通画 2简笔画
	//lm:-1 (6动态图片 7静态图片)
	//gsm:3c pn值的十六进制数
	private static final String BAIDU_IMAGE_SEARCH_URL = "http://image.baidu.com/search/avatarjson?tn=resultjsonavatarnew&ie=utf-8&word=%s&pn=%d&rn=%d&z=3&ic=0&s=0&face=0&st=-1&lm=-1&gsm=%s";
	private static final int PAGE_SIZE = 60;
	private static final String IMAGE_URL_REG = "\"objURL\":\"(https?://[^\"]+)\"";
	private static final Pattern IMAGE_PATTERN = Pattern.compile(IMAGE_URL_REG);
	
	@Override
	public String getSearchUrl(String keyword, int page) {
		int begin = page * PAGE_SIZE;
		return String.format(BAIDU_IMAGE_SEARCH_URL, keyword, begin, PAGE_SIZE, Integer.toHexString(begin));
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
