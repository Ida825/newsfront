package cn.et.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import cn.et.model.MyNews;
import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

public class MyTimerTask extends TimerTask{

	MyNews my = new MyNews();
	/**
	 * 生成首页的方法
	 */
	@Override
	public void run() {	
		try {
			//生成HTML
			Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
			cfg.setDirectoryForTemplateLoading(new File("src/main/resources"));
			cfg.setObjectWrapper(new DefaultObjectWrapper(Configuration.VERSION_2_3_23));
			
			//从数据库中获取新闻数据，并存入Map集合
			List<Map> newsList = my.selectNews();
			Map map = new HashMap();
			map.put("newsList", newsList);
			
			//将数据放入模板生成HTML首页
			Template temp = cfg.getTemplate("index.ftl");
			//生成首页HTML的路径
			String htmlPath = "src/main/webapp/index.html";
			
			Writer out = new OutputStreamWriter(new FileOutputStream(htmlPath));
			temp.process(map, out);
			out.flush();
			out.close();
		} catch (Exception e) {		
			e.printStackTrace();
		} 
	}
	
}
