package net.wisedream.hiapk.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.RedirectStrategy;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

/**
 * 请求工具类
 * 
 * @author pseudo
 * 
 */
public class MHttpClient {
	public static ThreadLocal<HttpContext> contexts = new ThreadLocal<HttpContext>() {

		@Override
		protected HttpContext initialValue() {
			return new BasicHttpContext();
		}

	};
	public static ThreadLocal<HttpClient> clients = new ThreadLocal<HttpClient>() {

		@Override
		protected HttpClient initialValue() {
			BasicHttpParams params = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(params, 60 * 1000);
			HttpConnectionParams.setSoTimeout(params, 40 * 1000);
			return new DefaultHttpClient(params);
		}

	};

	/**
	 * 为当前线程初始化资源
	 */
	public static final void init() {
		// release();
		BasicHttpParams params = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(params, 60 * 1000);
		HttpConnectionParams.setSoTimeout(params, 40 * 1000);
		clients.set(new DefaultHttpClient(params));
		contexts.set(new BasicHttpContext());
	}

	/**
	 * 设置httpclient默认代理
	 * 
	 * @param proxy
	 *            格式 127.0.0.1:8080
	 */
	public static void setProxy(String proxy) {
		if (proxy != null) {
			// TODO 代理格式验证
			String[] tmp = proxy.split(":");
			HttpHost proxyHost = new HttpHost(tmp[0], Integer.valueOf(tmp[1]));
			clients.get().getParams()
					.setParameter(ConnRouteParams.DEFAULT_PROXY, proxyHost);
		} else {
			clients.get().getParams()
					.setParameter(ConnRouteParams.DEFAULT_PROXY, null);
		}
	}

	/**
	 * 使用默认代理执行请求
	 * <p/>
	 * Note: 使用完后记得调用 {@link EntityUtils#consumeQuietly(HttpEntity)} 方法释放
	 * {@link HttpResponse#getEntity()}
	 * 
	 * @param req
	 * @return HttpResponse
	 * @throws Exception
	 */
	public static HttpResponse execute(HttpUriRequest req) throws Exception {
		return clients.get().execute(req, contexts.get());
	}

	/**
	 * 使用临时代理发送请求
	 * <p/>
	 * Note: 使用完后记得调用 {@link EntityUtils#consumeQuietly(HttpEntity)} 方法释放
	 * {@link HttpResponse#getEntity()}
	 * 
	 * @param req
	 * @param proxy
	 *            临时代理,如 127.0.0.1:8080
	 * @return
	 * @throws Exception
	 */
	public static HttpResponse execute(HttpUriRequest req, String proxy)
			throws Exception {
		HttpClient client = clients.get();
		Object origProxy = client.getParams().getParameter(
				ConnRouteParams.DEFAULT_PROXY);
		// 设置临时代理
		setProxy(proxy);
		HttpResponse resp = client.execute(req, contexts.get());
		// 恢复代理
		client.getParams().setParameter(ConnRouteParams.DEFAULT_PROXY,
				origProxy);
		return resp;
	}

	/**
	 * 使用临时代理请求并读取返回数据
	 * 
	 * @param req
	 * @param proxy
	 *            临时代理,如 127.0.0.1:8080
	 * @param defaultCharset
	 * @return
	 */
	public static String request(HttpUriRequest req, String proxy,
			String defaultCharset) {
		String content = null;
		try {
			HttpResponse resp = execute(req, proxy);
			content = readContent(resp, defaultCharset);
			EntityUtils.consumeQuietly(resp.getEntity());
		} catch (Exception e) {
			req.abort();
		}
		return content;
	}

	/**
	 * 使用默认代理请求并读取返回内容
	 * 
	 * @param req
	 * @param defaultCharset
	 * @return
	 * @throws Exception
	 */
	public static String request(HttpUriRequest req, String defaultCharset)
			throws Exception {
		String content = null;
		try {
			HttpResponse resp = execute(req);
			content = readContent(resp, defaultCharset);
			EntityUtils.consumeQuietly(resp.getEntity());
		} catch (Exception e) {
			req.abort();
		}
		return content;
	}

	private static String readContent(HttpResponse resp, String defaultCharset)
			throws IOException {
		HttpEntity entity = resp.getEntity();
		String charset = getCharset(entity, defaultCharset);
		String content = null;
		if (resp.containsHeader("Content-Encoding")
				&& resp.getHeaders("Content-Encoding")[0].getValue().equals(
						"gzip"))
			content = gzipDecompress(entity, charset);
		else
			content = EntityUtils.toString(entity, charset);
		return content;
	}

	/**
	 * 读取压缩数据
	 * 
	 * @param entity
	 * @param charset
	 * @return
	 * @throws IOException
	 */
	private static String gzipDecompress(HttpEntity entity, String charset)
			throws IOException {
		// 解压
		GZIPInputStream gzipIn = new GZIPInputStream(entity.getContent());
		byte[] buffer = new byte[512];
		int count = 0;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		while ((count = gzipIn.read(buffer)) > -1) {
			bos.write(buffer, 0, count);
		}
		bos.flush();
		bos.close();

		return bos.toString(charset);

	}

	/**
	 * 从响应实体中取得字符编码
	 * 
	 * @param entity
	 * @param defaultCharset
	 * @return
	 */
	public static String getCharset(HttpEntity entity, String defaultCharset) {
		String charset = null;
		try {
			charset = ContentType.get(entity).getCharset().name();
			System.out.println("取得编码： " + charset);
		} catch (Exception e) {
			System.out.println("取得编码失败，使用默认编码.");
			charset = defaultCharset;
		}
		return charset;
	}

	/**
	 * 释放资源
	 */
	public static void release() {
		System.out.println("release resource. ");
		HttpClient client = (HttpClient) clients.get();
		if (client != null)
			client.getConnectionManager().shutdown();
	}

	/**
	 * 是否自动跳转
	 * 
	 * @param flag
	 */
	public static void setFollowRedirect(final boolean flag) {
		RedirectStrategy strategy = new DefaultRedirectStrategy() {
			public boolean isRedirected(HttpRequest request,
					HttpResponse response, HttpContext context)
					throws ProtocolException {
				if (flag) {
					return super.isRedirected(request, response, context);
				}
				return false;
			}
		};
		((DefaultHttpClient) clients.get()).setRedirectStrategy(strategy);
	}

	/**
	 * 清理cookie
	 */
	public static void clearCookie() {
		DefaultHttpClient client = (DefaultHttpClient) clients.get();
		CookieStore cookie = client.getCookieStore();
		cookie.clear();
	}
}
