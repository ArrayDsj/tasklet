package net.wisedream.hiapk.flow;

import net.wisedream.hiapk.util.MHttpClient;
import net.wisedream.tasklet.Manager;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

/**
 * 下载
 * 
 * @author pseudo
 * 
 */
public class Step07 extends HiapkTask {
	public void perform(Manager manager){
		String url = context.getAttrib("downUrl");
		HttpGet req = new HttpGet(url);
		// 设置请求头
		req.addHeader("X-Requested-With", "com.android.browser");
		req.addHeader(
				"User-Agent",
				"Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.153 Safari/537.36");
		req.addHeader("Referer", "http://m.apk.hiapk.com/");
		req.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		req.addHeader("Accept-Encoding", "gzip,deflate");
		req.addHeader("Accept-Language", "zh-CN, en-US");
		req.addHeader("Accept-Charset", "utf-8, utf-16, *;q=0.7");
		req.addHeader("Range", "bytes=0-4096");//因为只是模拟下载，所以只下载前4K
		try {
			MHttpClient.setFollowRedirect(true);
			HttpResponse resp = MHttpClient.execute(req);
			pause(10);
			EntityUtils.consumeQuietly(resp.getEntity());
		} catch (Exception e) {
			req.abort();
		}
		i("任务完成");
	}
}
