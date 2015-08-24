package org.extjs;

public class Window extends CompositeComponent {
	private Button btn;//操作按钮  一般为保存、取消
	private GridPanel Grid;
	private FormPanel Form;
	private TabPanel Tab;
	
	public Window(String componentQuery) {
		super(componentQuery);
		setQuery();
	}
	public Window() {
		super();
		setQuery();
	}
//	
//	public Window(WebDriver driver, String title) {
//		super(driver,title,null);
//		b = false;
//	    this.title = title;
//	}

	/**
	 * 获取定位Window组件的query语句
	 */
	private void setQuery(){
		if(_query==null||_query.equals("window")){
			_query = "window[[hidden=false]]";
		}else if(_query.indexOf("[")==-1){
			_query = String.format("window[title='%s'][hidden=false]",_query);
		}
	}	
	//关闭windows窗口
	public void close(){
		executeJS(getQuery()+".close( )");
	}
}
