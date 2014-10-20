package net.wisedream.hiapk;

import net.wisedream.hiapk.flow.HiLauncher;

/**
 * <h1>Tasklet示例</h1>
 * <p/>
 * 功能：模拟用户下载行为，从hiapk的wap网下载一个特定的应用<br/>
 * Step01 : 执行搜索并从中选择目标apk<br/>
 * Step02-04: 点击打开应用详情页面<br/>
 * Step05-07: 点击“普通下载” <br/>
 * 
 * @author pseudo
 * 
 */
public class App {
	public static void main(String[] args) {
		final HiLauncher launcher = new HiLauncher();
//		for (int i = 0; i < 10; i++)
			new Thread() {

				@Override
				public void run() {
					launcher.launch();
				}

			}.start();

	}
}
