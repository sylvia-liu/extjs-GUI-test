package org.extjs;

public class RadioGroup extends CompositeComponent {
	private Component radio;
	
	/**
	 * 实例化RadioGroup组件
	 * @param componentQuery TextField的检索条件，可为null
	 */
	public RadioGroup(String componentQuery){
		super(componentQuery);
		setQuery();
	}
	public RadioGroup(){
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
			_query = "radiogroup";
		}else if(_query.indexOf("[")==-1){
			_query = String.format("radiogroup[fieldLabel='%s']",_query);
		}
	}
/**
 * 选中指定值
 * @param boxLabel 选项标签
 */
	public void setValue(String boxLabel){
		if(boxLabel==null){
			return;
		}
		String JS ="var radioGroup = "+getQuery()+";"
						+ "var radio = radioGroup.query('radiofield[boxLabel="+boxLabel+"]')[0];"
						+ "radio.setValue(true)";
		executeJS(JS);
	}
}



