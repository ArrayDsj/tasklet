package net.wisedream.hiapk.flow;

import net.wisedream.hiapk.util.MHttpClient;
import net.wisedream.tasklet.Manager;
import net.wisedream.tasklet.Task;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
/**
 * 点击选定的应用，打开详情页面
 * 
 * @author pseudo
 *
 */
public class Step02 extends Task {
	private JSONObject target;
	private String url;

	public Step02() {
		target = context.getAttrib("target");
		url = "http://m.apk.hiapk.com/wap/api.do?qt=1010&id="+target.getString("id");
	}


	/**
	 * 这一步只是获取页面信息，内容没有用。
	 */
	@Override
	public void perform(Manager manager){
		HttpGet req = new HttpGet(url);
		// 设置请求头
		req.addHeader("X-Requested-With", "XMLHttpRequest");
		req.addHeader(
				"User-Agent",
				"Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.153 Safari/537.36");
		req.addHeader("Referer", "http://m.apk.hiapk.com/");
		req.addHeader("Accept-Encoding", "gzip,deflate,sdch");
		req.addHeader("Accept-Language", "en-US,en;q=0.8");
		// 发起请求
		try{
			HttpResponse resp = MHttpClient.execute(req);
			if(resp.getStatusLine().getStatusCode()==200)
				d("请求成功");
			EntityUtils.consume(resp.getEntity());
		} catch(Exception e){
			req.abort();
		}
		manager.addTask(new Step03());
	}

}
