package org.extjs;
/**
 * xtype=checkboxgroup 多选按钮组 
 * @param componentQuery CheckboxGroup的检索条件，可为null
 * _query=componentQuery
 * 1）_query为null,根据xtype定位；
 * 2）_query不包含[和等号,根据标签文字定位
 * 3）_query不包含[，但包含=，根据CheckboxGroup的属性定位
 * 4）_query比较复杂，则不作处理直接传入gerQuery()中
 * @author lql
 */
public class CheckboxGroup extends CompositeComponent {

	public CheckboxGroup(String componentQuery){
		super(componentQuery);
		setQuery();
	}
	public CheckboxGroup(){
		super();
		setQuery();
	}
	public CheckboxGroup(int array){
		super(null,array);
		setQuery();
	}
	private void setQuery(){
		if(_query==null){
			_query = "checkboxgroup";
		}else if(_query.indexOf("[")==-1&&_query.indexOf("=")==-1){
			_query = String.format("checkboxgroup[fieldLabel='%s']",_query);
		}else if(_query.indexOf("[")==-1&&_query.indexOf("=")!=-1){
			_query = String.format("checkboxgroup[%s]",_query);
		}else{
			_query = String.format("%s",_query);
		}
	}
	
	/**
	 * 选中指定选项,目前为单项，尚未实现多项选择
	 * @param boxLabels 选项标签,可以多个，用逗号隔开
	 * @return void
	 */
	public void setValue(String... boxLabels){
		reset();
		for(String boxLabel:boxLabels){
			Checkbox box = new Checkbox(boxLabel);
			this.addChild(box);
			box.setValue(true);
		}
/*		String JS ="var checkboxGroup = "+getQuery()+";"
						+ "checkboxGroup.setValue({name1: true, name2: false)";
		executeJS(JS);*/
	}
	
	/**
	 * 恢复默认值，一般用于取消所有项目的勾选
	 * @return void
	 */
	public void reset(){
		String JS ="var checkboxGroup = "+getQuery()+";"
				+ "checkboxGroup.reset()";
		
		executeJS(JS);
		
	}
}



