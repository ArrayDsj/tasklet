package net.wisedream.hiapk.flow;

import java.util.Iterator;

import net.wisedream.hiapk.util.MHttpClient;
import net.wisedream.tasklet.Manager;

import org.apache.http.client.methods.HttpPost;
import org.json.JSONObject;

/**
 * 点击普通下载 <br/>
 * 1.http://m.apk.hiapk.com/wap/api.do?qt=8000取得下一步要post的信息
 * 
 * @author pseudo
 * 
 */
public class Step05 extends HiapkTask {
	int retry = 3;

	public void perform(Manager manager) {
		HttpPost req = new HttpPost("http://m.apk.hiapk.com/wap/api.do?qt=8000");
		// 设置请求头
		req.addHeader("X-Requested-With", "XMLHttpRequest");
		req.addHeader(
				"User-Agent",
				"Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.153 Safari/537.36");
		req.addHeader("Referer", "http://m.apk.hiapk.com/");
		req.addHeader("Origin", "http://m.apk.hiapk.com");
		req.addHeader("Accept", "text/plain");
		req.addHeader("Accept-Encoding", "gzip,deflate");
		req.addHeader("Accept-Language", "zh-CN, en-US");
		req.addHeader("Accept-Charset", "utf-8, utf-16, *;q=0.7");
		try {
			String resp = MHttpClient.request(req, "UTF-8");
			String info = parseContent(resp);
			context.putAttrib("info", info);
			manager.addTask(new Step06());
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

	// function abdcdr(){return
	// {
	// uid:'-6925260412960462941',signtime:'148c1282cb3',signsum:'sviptodoLTY5MjUyNjA0MTI5NjA0NjI5NDE6aHR0cDovL20uYXBrLmhpYXBrLmNvbS86MTQ4YzEyODJjYjM='};};
	// uid=-6925260412960462941&signtime=148c1282cb3&signsum=sviptodoLTY5MjUyNjA0MTI5NjA0NjI5NDE6aHR0cDovL20uYXBrLmhpYXBrLmNvbS86MTQ4YzEyODJjYjM%3D
	private String parseContent(String resp) {
		// 截取json数据
		String tmp = resp.split("return")[1];
		tmp = tmp.replace(";};", "");
		JSONObject data = new JSONObject(tmp.trim());
		Iterator<String> keys = data.keys();

		StringBuilder info = new StringBuilder(tmp.length());
		String key = null;
		while (keys.hasNext()) {
			key = keys.next();
			info.append(key).append('=').append(data.getString(key))
					.append('&');
		}
		return info.substring(0, info.length() - 1).toString();
	}

}
