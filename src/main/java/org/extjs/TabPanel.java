package org.extjs;

import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

public class TabPanel extends CompositeComponent{

	public CompositeComponent tabBody;
	private String JS;

	public TabPanel(String query,int array) {
		super(query,array);
		setQuery();
	}
	
	public TabPanel(String query) {
		super(query);
		setQuery();
	}
	public TabPanel() {
		super();
		setQuery();
	}
	/**
	 * 获取定位TabPanel组件的query
	 */
	public void setQuery(){
		if(_query==null||_query.equals("tabpanel")){
			_query = "tabpanel";
		}else if(_query.indexOf("[") == -1&&_query.indexOf(".") == -1&&_query.indexOf("#") == -1&&_query.indexOf("=") == -1){
			_query = String.format("tabpanel[text='%s']",_query);
		}else if(_query.indexOf("[") == -1&&_query.indexOf("=") != -1){
			_query = String.format("tabpanel['%s']",_query);
		}else{
			_query = String.format("%s",_query);
		}
	}

	/**
	 * 关闭所有标签页
	 * @return void
	 * @reference closeAllTab 
	 * @EditBy:lql 2014年12月2日
	 */
	public void closeAllTab(){
		JS = "var tab ="+getQuery()+";"
				+"tab.removeAll()";
		executeJS(JS);
	}
	
	/**
	 * 关闭指定标签页-按照标签显示文字
	 */
	public void closeTabByText(String text){
		String index = getTabIndex(text);
		JS = "var tab ="+getQuery()+";"
				+ "tab.setActiveTab("+index+");"
				+ "var actTab=tab.getActiveTab();"
				+ "tab.remove(actTab)";
		executeJS(JS);
	}
	/**
	 * 关闭当前显示标签页(当前显示也是已激活tab)
	 */
	public void closeActiveTab(){
		JS = "var tab ="+getQuery()+";"
				+ "var actTab=tab.getActiveTab( );"
				+ "tab.remove(actTab)";
		executeJS(JS);
	}
	
	/**
	 * 激活/切换到指定标签页——按照标签文字
	 * @param title tab标签页文字
	 * @return String tab内容区id
	 * 2014年12月2日
	 */
	public String setActiveTab(String title){
		String index = getTabIndex(title);
		JS = getQuery()+".setActiveTab("+index+")";
        executeJS(JS);
        return getTabId(title);
	}

	
	public CompositeComponent getTabBody(){
		JS = getQuery()+".getActiveTab().id";
        String id = (String) executeJS("return "+JS);
        tabBody = new CompositeComponent("[id ="+ id +"]");
        this.addChild(tabBody);
        return tabBody;
	}
	
	/**
	 * 读取tab的标签文字，tab页面组件id，index
	 * @return map方式存储{"title":{"index":"","id":""}[,....]}
	 */
	public JSONObject Items(String title){
		JS = "var Items ='{';"
				+ "var tab = "+getQuery()+";"
				+ "var itemsValue = tab.items.valueOf();"
				+ "var len = itemsValue.length;"
				+ "for(var index=0;index<len;index++){"
					+ "var title = itemsValue.items[index].title;"
					+ "var tabID = itemsValue.items[index].id;"
					+ "Items =Items+'\"'+title+'\":{\"index\":\"'+index+'\",\"id\":\"'+tabID+'\"}';"
					+ "if(index<len-1){Items = Items+',';}"
				+ "}"
				+ "Items=Items + '}';"
				+ "return Items";
		String strItems = (String)executeJS(JS);
		JSONObject jsonItem =null;
		JSONObject item = null;
		try {
			jsonItem = new JSONObject(strItems);
			item = jsonItem.getJSONObject(title);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return item;

	}
	/**
	 * 按照tab标签文字获取tab页id
	 * @param title
	 * @return tab页id
	 */
	public String getTabId(String title){
		String tabId = null;
		try {
			tabId = Items(title).getString("id");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tabId;
	}
	/**
	 * 按照tab标签文字获取tab页index
	 * @param title
	 * @return index
	 */
	public String getTabIndex(String title){
		String index = null;
		try {
			index = Items(title).getString("index");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return index;
	}	
}
