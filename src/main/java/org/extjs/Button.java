package org.extjs;

import org.openqa.selenium.WebElement;

/**
 * xtype=Button 
 * @author lql
 */
public class Button extends Component {
	/**
	 * 实例化Button组件
	 * @param driver 浏览器驱动
	 * @param componentQuery 组件检索条件，为空时输入null
	 * @param parent 父级组件，为空时输入null
	 */
	public Button(String componentQuery) {
		super(componentQuery);
		setQuery();
	}

	/**
	 * 获取定位button组件的js语句
	 */
	private void setQuery(){
		if(_query==null){
			_query = "button";
		}else if(_query.indexOf("[")==-1&&_query.indexOf("=")==-1){
			_query = String.format("button[text='%s'][hidden = false]",_query);
		}else if(_query.indexOf("[")==-1&&_query.indexOf("=")!=-1){
			_query = String.format("button[%s][hidden = false]",_query);
		}else{
			_query = String.format("%s",_query);
		}
	}
	public void click(){
		getElement().click();
	}
}
