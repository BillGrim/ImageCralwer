package com.rensanning.crawler.image;

import java.io.File;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ImageWorker implements Runnable {

	private ConcurrentLinkedQueue<String> queue;
	private File saveDir;

	public ImageWorker(ConcurrentLinkedQueue<String> queue, File saveDir) {
		this.queue = queue;
		this.saveDir = saveDir;
	}

	public void run() {
		while (true) {
			String url = queue.poll();
			while (url == null) {
				try {
					Thread.sleep(1000);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					url = queue.poll();
				}
			}
			new ImageDownloader(url, this.saveDir).download();
		}
	}
}
