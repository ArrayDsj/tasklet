package net.wisedream.hiapk.flow;

import net.wisedream.hiapk.util.MHttpClient;
import net.wisedream.hiapk.util.StrTools;
import net.wisedream.tasklet.Manager;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

/**
 * 请求下载链接
 * 
 * @author pseudo
 * 
 */
public class Step06 extends HiapkTask {
	int retry = 3;
	private String info;
	private JSONObject target;
	private String url;

	public Step06() {
		target = context.getAttrib("target");
		info = context.getAttrib("info");
		url = "http://m.apk.hiapk.com/wap/api.do?qt=8001&id="
				+ target.getString("id");
	}

	public void perform(Manager manager) {
		HttpPost req = new HttpPost(url);
		// 设置请求头
		req.addHeader("X-Requested-With", "XMLHttpRequest");
		req.addHeader(
				"User-Agent",
				"Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.153 Safari/537.36");
		req.addHeader("Referer", "http://m.apk.hiapk.com/");
		req.addHeader("Origin", "http://m.apk.hiapk.com");
		req.addHeader("Accept", "application/json");
		req.addHeader("Accept-Encoding", "gzip,deflate");
		req.addHeader("Accept-Language", "zh-CN, en-US");
		req.addHeader("Accept-Charset", "utf-8, utf-16, *;q=0.7");
		// 设置请求体
		HttpEntity entity = new StringEntity(info,
				ContentType.APPLICATION_FORM_URLENCODED);
		req.setEntity(entity);
		// 发起请求
		try {
			String resp = MHttpClient.request(req, "UTF-8");
			String downUrl = parseContent(resp);
			if (!StrTools.isEmpty(downUrl)) {
				context.putAttrib("downUrl", downUrl);
				i("应用下载地址：" + downUrl);
				manager.addTask(new Step07());
			}
		} catch (Exception e) {
			// 重试
			if (retry-- < 0) {
				w("发生异常, 3s后重试");
				pause(3);
				manager.addTask(this);
			} else {
				e("不能恢复的异常，Worklet退出.");
				throw new RuntimeException("Step01 fatal error.");
			}
		}
	}

	private String parseContent(String content) {
		JSONObject json = new JSONObject(content);
		return json.getJSONObject("data").getString("downurl");
	}

}
