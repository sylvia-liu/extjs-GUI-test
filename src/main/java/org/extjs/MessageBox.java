package org.extjs;

/**
 * 提示信息——confirm
 * @author lql
 */
public class MessageBox extends CompositeComponent {
	
	private Button btnAct; //MessageBox上的按钮
	
	protected String Msg;
	
	public MessageBox(String componentQuery) {
		super(componentQuery);	
		setQuery();
	}
	public MessageBox() {
		super("messagebox");	
		setQuery();
	}
	/**
	 * 获取定位MessageBox组件的js语句
	 */
/*	@Override
	public String getQuery(){
		String queryJS=null;
		if(_query==null){
			 //根据显示文字定位Button的js语句
			 queryJS = "Ext.MessageBox";
		}else if(_query.indexOf("[")==-1){
			 queryJS = super.getQuery();
		}
		 return queryJS;
   }  */
	/**
	 * 获取定位Window组件的query语句
	 */
	private void setQuery(){
		if(_query==null){
			_query = "messagebox";
		}else if(_query.indexOf("[")==-1){
			_query = String.format("messagebox[title='%s']",_query);
		}
	}
/********************************操作方法******************************************/
    /**
     * 获取提示信息
     * @return String 提示信息
     * @EditBy:lql 2014年11月20日
     */
    public String getMsg(){
    	String JS = "var msgbox ="+getQuery()+";"
    			+ "return msgbox.msg.value";
		int time = 0;
		while(time != 20000) {
			try{
	    		Msg = (String) executeJS(JS);
				break;
			}catch(Exception e){
				time = time + 100;      
				waitForTime(time);
			}
		}
    	return Msg;
    }
    
    /**
     * 按文字点击MessageBox上的按钮，例如“是”、“否”、“确定”、“取消”等
     * @param text 按钮文字
     */
    public void comfirm(String text){
    	btnAct = new Button(text);
    	this.addChild(btnAct);
    	btnAct.click();
    }
    
    /**
     * 关闭弹出提示窗口
     */
    public void close(){
    	String JS = "var msgbox ="+getQuery()+";"
    			+ "msgbox.close()";
    	executeJS(JS);
    }
    
}
