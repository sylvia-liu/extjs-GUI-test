/**
 * Ext.form.Panel
 * xtype: form
 */

package org.extjs;

public class FormPanel extends CompositeComponent{
	
	private Button btn;
	private TextField txt;
	private ComboBox combo;
	private Datefield data;
	private CheckboxGroup ChkBoxGroup;
	private Checkbox ChkBox;
	private RadioGroup radioGroup;
	
	
	//构造函数
	public FormPanel(String componentQuery) {
		super(componentQuery);
		setQuery();
	}
	
	public FormPanel() {
		super("form");
		setQuery();
	}
	
	/**
	 * 获取定位FormPanel组件的query语句
	 */
	private void setQuery(){
		if(_query==null||_query.equals("form")){
			_query = "form";
		}else if(_query.indexOf("[")==-1){
			_query = String.format("form[title='%s']",_query);
		}
	}	

}
