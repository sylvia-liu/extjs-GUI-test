package org.extjs;
/**
 * xtype=checkboxfield 多选按钮 
 * @author lql
 */
public class Checkbox extends Component {
	/**
	 * 实例化TextField组件
	 * @param componentQuery checkboxfield的检索条件，可为null
	 * _query=componentQuery
	 * 1）_query为null,根据xtype定位；
	 * 2）_query不包含[和等号,根据标签文字定位
	 * 3）_query不包含[，但包含=，根据checkboxfield的属性定位
	 * 4）_query比较复杂，则不作处理直接传入gerQuery()中
	 */
	public Checkbox(String componentQuery){
		super(componentQuery);
		setQuery();
	}
	public Checkbox(){
		super();
		setQuery();
	}
	
	private void setQuery(){
		if(_query==null){
			_query = "checkboxfield";
		}else if(_query.indexOf("[")==-1&&_query.indexOf("=")==-1){
			_query = String.format("checkboxfield[boxLabel='%s']",_query);
		}else if(_query.indexOf("[")==-1&&_query.indexOf("=")!=-1){
			_query = String.format("checkboxfield[%s]",_query);
		}else{
			_query = String.format("%s",_query);
		}
	}
	/**
	 * 设置多选框是否被选中
	 * @param isChecked boolean类型值，false表示不选中，true表示选中
	 */
	public void setValue(boolean isChecked){
		if(isChecked!=false && isChecked!=true){
			return;
		}
		String JS ="var checkbox = "+getQuery()+";"
				+ "checkbox.setValue("+isChecked+")";
		executeJS(JS);
	}
}



