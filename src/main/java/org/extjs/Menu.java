package org.extjs;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class Menu extends CompositeComponent {
	private String js;
	public Menu(String componentQuery) {
		super(componentQuery);
		setQuery();
	}
	
	public Menu() {
		super("menu");
		setQuery();
	}
	
	/**
	 * 获取定位treepanel组件的query语句
	 */
	private void setQuery(){
		if(_query==null||_query.equals("menu")){
			_query = "menu";
		}else if(_query.indexOf("[")==-1){
			_query = String.format("menu[title='%s']",_query);
		}
	}
	/**
	 * 点击菜单选项
	 */
	public void clickItem(String MenuItemText){
		String id = "var menu = "+getQuery()+";"
				+ " return menu.child('[text="+ MenuItemText+ "]').id";
		WebElement menuItem = _driver.findElement(By.id((String) executeJS(id)));
		menuItem.click();
	}
}
