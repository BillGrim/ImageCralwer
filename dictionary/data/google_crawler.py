# -*- coding:utf-8 -*-
# !/usr/bin/python


import requests
from requests import RequestException

'''

	//tbm=isch
	//q=美女 搜索关键字
	//ijn=0 页码(***Google只提供到0到8页数据！)
	//start=0 开始条数
	//tbs=isz:l 搜索条件
	//	尺寸
	//	tbs=isz:l 大
	//	tbs=isz:m 中
	//	颜色
	//	tbs=ic:color 彩色
	//	tbs=ic:gray 黑白
	//	tbs=ic:trans 透明
	//	类型
	//	tbs=itp:face 脸部特写
	//	tbs=itp:photo 照片
	//	tbs=itp:clipart 剪贴画
	//	tbs=itp:lineart 素描画
	//	tbs=itp:animated 动画
	//	条件组合
	//	tbs=isz:l,ic:color,itp:face
	private static final String GOOGLE_IMAGE_SEARCH_URL = "https://www.google.com/search?tbm=isch&q=%s&ijn=%d&start=%d&tbs=isz:l";
'''

search_url = "https://www.google.com/search?tbm=isch&q=%s&ijn=%d&start=%d&tbs=isz:l"

# headers = {"Host": "mtl.ttsqgs.com",
#            "Connection": "keep-alive",
#            "Cache-Control": "max-age=0",
#            "Upgrade-Insecure-Requests": "1",
#            "User-Agent": "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36",
#            "Accept": "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8",
#            "Referer": "https://www.meitulu.com/item/3361.html",
#            "Accept-Encoding": "gzip, deflate, br",
#            "Accept-Language": "zh-CN,zh;q=0.9,en;q=0.8,en-US;q=0.7",
#            "If-None-Match": "1eb364c238f5d21:0",
#            "If-Modified-Since": "Sun, 02 Jul 2017 19:06:52 GMT"}

# headers = {
#     "User-Agent": "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36",
# }
headers = {
    ":authority": "www.google.com",
    ":method": "GET",
    ":path": "/search?hl=en&biw=646&bih=642&tbm=isch&sa=1&ei=gB5LW82WN4rv-QachLeQDQ&q=happy&oq=happy&gs_l=img.12...0.0.0.33504.0.0.0.0.0.0.0.0..0.0....0...1c..64.img..0.0.0....0.zesSisioGFQ",
    ":scheme": "https",
    "accept": "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8",
    "accept-encoding": "gzip, deflate, br",
    "accept-language": "zh-CN,zh;q=0.9,en;q=0.8,en-US;q=0.7",
    "cache-control": "max-age=0",
    # "cookie": "CGIC=IlV0ZXh0L2h0bWwsYXBwbGljYXRpb24veGh0bWwreG1sLGFwcGxpY2F0aW9uL3htbDtxPTAuOSxpbWFnZS93ZWJwLGltYWdlL2FwbmcsKi8qO3E9MC44; NID=134=5YWQldIg0F030hlt5LHX227ncGqXAbd4K_ZiY44NdVY4XAP2qFGHf7fGkyNTcPNVe-oomG7Esw1aCtlhgFok6moCoDHsSbOQryZCOdg2C0npxcK_zefjGnfsDdOjrMhXEQ4gHLuhgbo; 1P_JAR=2018-07-15-10",
    "referer": "https://www.google.com/",
    "upgrade-insecure-requests": "1",
    "user-agent": "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36",
    "x-client-data": "CKS1yQEIkrbJAQijtskBCMS2yQEIqZ3KAQjYncoBCKijygE="
}


def get_page_detail(url):
    try:
        print(url)
        response = requests.get(url, headers=headers)
        if response.status_code == 200:
            return response.content
        else:
            print(response.status_code)
            return None
    except RequestException:
        print("请求详情页出错")
        return None


def fetch_image(query):
    get_page_detail(search_url % (query, 0, 0))


if __name__ == '__main__':
    list = ['happy']
    for name in list:
        print(fetch_image(name))
