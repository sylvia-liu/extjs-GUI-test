package org.extjs;


public class Radio extends Component {

	public Radio(String componentQuery) {
		super(componentQuery);
		setQuery();
	}
	/**
	 * 实例化TextField组件
	 * @param componentQuery TextField的检索条件，可为null
	 */
	public Radio(){
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
			_query = "radiofield";
		}else if(_query.indexOf("[")==-1){
			_query = String.format("radiofield[boxLabel='%s']",_query);
		}
	}
	/**
	 * 选中单选项
	 * @param isChecked boolean类型值，false表示不选中，true表示选中
	 */
	public void setSelected(){

		String JS ="var radio = "+getQuery()+";"
				+ "radio.setValue(true)";
		executeJS(JS);
	}

}
