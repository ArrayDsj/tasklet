package net.wisedream.hiapk.flow;

import net.wisedream.hiapk.util.MHttpClient;
import net.wisedream.hiapk.util.Uri;
import net.wisedream.tasklet.Manager;

import org.apache.http.client.methods.HttpGet;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * 搜索指定应用
 * 
 * @author pseudo
 * 
 */
public class Step01 extends HiapkTask {
	private int retry = 3;
	private int pageIndex = 1;// 页码从1开始
	private int maxPageIndex = 3;// 只请求前三页
	private String targetPkn = "mobi.rmmhpg.jthjjt.ktpuvs";// 要刷的包名

	private String concatURL() {
		return "http://m.apk.hiapk.com/wap/api.do?qt=1005" + "&pi=" + pageIndex // 要请求的页码
				+ "&ps=20"// 分页大小
				+ "&key=" + Uri.encode("日本美女和服里的秘密")// 搜索关键字
				+ "&type=1&pid=0";
	}

	public void perform(Manager manager) {
		HttpGet req = new HttpGet(concatURL());
		// 设置请求头
		req.addHeader("X-Requested-With", "XMLHttpRequest");
		req.addHeader(
				"User-Agent",
				"Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.153 Safari/537.36");
		req.addHeader("Referer", "http://m.apk.hiapk.com/");
		req.addHeader("Accept-Encoding", "gzip,deflate,sdch");
		req.addHeader("Accept-Language", "en-US,en;q=0.8");
		// 发起请求
		try {
			if (retry == 3)
				throw new Exception("retry test");
			String content = MHttpClient.request(req, "UTF-8");
			d("响应内容：" + content);
			JSONObject target = findApk(content, targetPkn);
			if (target == null) {
				pageIndex++;
				manager.addTask(pageIndex > maxPageIndex ? null : this);
			} else {
				context.putAttrib("target", target);
				i("发现目标：" + target.toString(2));
				manager.addTask(new Step02());
			}

		} catch (Exception e) {
			// 重试
			if (retry-- > 0) {
				w("发生异常, 3s后重试: "+e.getMessage());
				pause(3);
				manager.addTask(this);
			} else {
				e("不能恢复的异常，Worklet退出.");
				throw new RuntimeException("Step01 fatal error.");
			}
		}
	}

	private JSONObject findApk(String content, String pkn) {
		if (!content.contains(pkn))
			return null;
		try {
			JSONObject json = new JSONObject(content);
			JSONArray data = json.getJSONArray("data");
			JSONObject tmp = null;
			for (int i = 0; i < data.length(); i++) {
				tmp = data.getJSONObject(i);
				if (tmp.getString("pkn").equals(pkn))
					return tmp;
			}
		} catch (Exception e) {
		}
		return null;
	}

}
