package org.extjs;

public class TextField extends Component {
	/**
	 * 实例化TextField组件
	 * @param componentQuery TextField的检索条件，可为null
	 */
	public TextField(String componentQuery){
		super(componentQuery);
		setQuery();
	}
	public TextField(String componentQuery,int array){
		super(componentQuery,array);
		setQuery();
	}
	public TextField(){
		super();
		setQuery();
	}
	
	/**
	 * 处理定位TextField组件的query，_query=componentQuery
	 * 1）_query为null,根据xtype定位；
	 * 2）_query不包含[,代表输入标签文字
	 * 3）_query比较复杂，则不作处理直接传入gerQuery()中
	 */
	private void setQuery(){
		if(_query==null){
			_query = "textfield";
		}else if(_query.indexOf("[")==-1&&_query.indexOf("=")==-1){
			_query = String.format("textfield[fieldLabel='%s']",_query);
		}else if(_query.indexOf("[")==-1&&_query.indexOf("=")!=-1){
			_query = String.format("textfield[%s]",_query);
		}else{
			_query = String.format("%s",_query);
		}
	}
	
	/**
	 * 先清空，后输入值;若输入值为null，则直接返回不执行操作
	 * @param keysToSend
	 */
	public void type(String keysToSend) {
    	if(keysToSend == null){
    		return;
    	}
		String JS = "var txt ="+getQuery()+";"
    			+ "txt.setValue('"+keysToSend+"')";
		executeJS(JS);
	}
	
	/**
	 * 清空组件值
	 */
	public void clear(){
		String JS = "var txt ="+getQuery()+";"
    			+ "txt.setValue()";
		executeJS(JS);
	}
	
	/**
	 * 获取Input当前值
	 * @return
	 */
	public String getValue(){
    	String JS = "var txt ="+getQuery()+";"
    			+ "return txt.getValue()";
		return (String) executeJS(JS);
	}
}



