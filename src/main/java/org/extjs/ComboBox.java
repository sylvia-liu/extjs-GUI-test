package org.extjs;

import java.util.List;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

public class ComboBox extends Component {

	public ComboBox(String componentQuery) {
		super(componentQuery);
		setQuery();
	}
	public ComboBox(String componentQuery,int array) {
		super(componentQuery,array);
		setQuery();
	}
	public ComboBox() {
		setQuery();
	}
	
	/**
	 * 定位combo的js语句
	 */
	private void setQuery(){
		if(_query==null||_query.equals("combo")){
			_query = "combo";
		}else if(_query.indexOf("[")==-1&&_query.indexOf("=")==-1){
			_query = String.format("combo[fieldLabel=%s]",_query);
		}else if(_query.indexOf("[")==-1&&_query.indexOf("=")!=-1){
			_query = String.format("combo[%s]",_query);
		}else{
			_query = String.format("%s",_query);
		}
	}	
	

//	/**
//	 * 获取combobox中的所有可选项——动态生成
//	 * @return void
//	 * @reference getComboItems 
//	 * @EditBy:lql 2014年12月9日
//	 */
//	public List<String> getComboDynamicItems(){
//		String js ="var comboItems = [];"
//				+ "var grid = Ext.ComponentQuery.query('grid')[0];"
//				+ "var store = grid.getViewModel().getStore('"+storeName+"');"
//				+ "var len =store.getData().length;"
//				+ "for(var i=0;i<len;i++){"
//				+ "comboItems[i] = store.getData().getAt(i).data.name;"
//				+ "}"
//				+ "return comboItems";
//		return (List<String>) executor.executeScript(js);
//	} 
	
	/**
	 * 获取combobox中的所有可选项_动态加载后页面显示选项
	 * @return void
	 * @reference getComboItems 
	 * @EditBy:lql 2014年12月19日
	 */
	public Object getComboStaticItems(String item){
		String js ="var comboItems = '{';"
				+ "var combo = "+getQuery()+";"//找到combo组件
				+ "var storeData = combo.store.getData();"
				+ "var len = storeData.length;"//组件可选项数
				+ "for(var i=0;i<len;i++){"
						+ "var display = storeData.getAt(i).get(combo.displayField);"
						+ "var value= storeData.getAt(i).get(combo.valueField);"
						+ "comboItems =comboItems+'\"'+display+'\":\"'+value+'\"';"
						+ "if(i<len-1){comboItems = comboItems+',';}"				
				+ "}"
				+ "comboItems=comboItems + '}';"
				+ "return comboItems";
		String items =(String) executor.executeScript(js);
		
		JSONObject jsonItem =null;
		Object objItem = null;
		try {
			jsonItem = new JSONObject(items);
			objItem = jsonItem.get(item);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return objItem;
	} 

	/**
	 * 选择指定项
	 * @param displayValue  下拉选项显示文字信息
	 * @return void
	 * @EditBy:lql 2014年12月19日
	 */
/*	public void selectByVisibleText(String displayValue){
    	if(displayValue == null){
    		return;
    	}
    	Pattern pattern = Pattern.compile("[0-9]*");
    	if(pattern.matcher(getComboStaticItems().get(displayValue).toString()).matches()){
    		executor.executeScript(getQuery()+".setValue("+getComboStaticItems().get(displayValue)+")");
    	}else{
    		executor.executeScript(getQuery()+".setValue('"+getComboStaticItems().get(displayValue)+"')");
    	}
	}*/
	
	/**
	 * 选择指定项_待调试
	 * @param value  下拉选项实际传递的值
	 * @return void
	 * @EditBy:lql 2015年4月14日
	 */
	public void selectByValue(String value){
    	if(value == null){
    		return;
    	}
    	Pattern pattern = Pattern.compile("[0-9]*");
    	if(pattern.matcher(getComboStaticItems(value).toString()).matches()){
    		executor.executeScript(getQuery()+".setValue("+value+")");
    	}else{
    		executor.executeScript(getQuery()+".setValue('"+value+"')");
    	}
		
	}
	
	/**
	 * 随机选择项
	 * @return void
	 * @EditBy:lql 2014年12月19日
	 */
	public void selectByRandom(){
		String js ="var combo = "+getQuery()+";"//找到combo组件
				+ "return combo.store.getData().length;";//组件可选项数
		if((Integer)executeJS(js) == 0){
			return;
		}
		String js_getvalue ="var combo = "+getQuery()+";"//找到combo组件
				+ "var storeData = combo.store.getData();"
				+ "var len = storeData.length;"//组件可选项数
				+ "if(len==0){"
				+ "	return;"
				+ "}else{"
				+ "var value= storeData.getAt(parseInt((Math.random()*len))).get(combo.valueField);"
				+ "combo.setValue(value)"
				+ "}";
		executor.executeScript(js_getvalue);
	}

	/**
	 * Select option that display text matching the argument.
	 * @param text The visible text to match against
	 */
	public void selectByVisibleText(String text) {
		if(text==null){
			return;
		}
		//click arrow-trigger to pop up the combo list
		getElement().findElement(By.className("x-form-arrow-trigger")).click();
		
//		_driver.findElement(By.xpath("//li[contains(@class, 'x-boundlist-item')][text()='"+text+"']")).click();
		
		try{
			WebElement boundlist = (WebElement) executor.executeScript("return "+getQuery()+".getPicker().getEl().dom");//get the element of show option
			List<WebElement> options = boundlist.findElements(By.className("x-boundlist-item"));
			for(WebElement option : options){
				if(option.getAttribute("innerText").trim().equals(text)){
					option.click();
					break;
				}
			}
		}catch(Exception e){
			e.getMessage();
			e.printStackTrace();
		}
	}
	
	/**
	 * Select the option at the given index
	 * @param index: The option at this index will be selected
	 */
	public void selectByIndex(int index) {
		//click arrow-trigger to pop up the combo list
		getElement().findElement(By.className("x-form-arrow-trigger")).click();
		
//	    _driver.findElement(By.cssSelector(".x-boundlist-item:nth-child("+index+")")).click();
	    
		WebElement boundlist = (WebElement) executor.executeScript("return "+getQuery()+".getPicker().getEl().dom");//get the element of show option
		boundlist.findElement(By.cssSelector(".x-boundlist-item:nth-child("+index+")")).click();
	}
	
	/**
	 * 设置选项值_当组件可输入值时，即editable:true 或没有readonly属性
	 * @param option
	 * @return void
	 * @EditBy:lql 2014年12月2日
	 */
	public void sendVisibleText(String text){
		if(text==null){
			return;
		}
		clearValue();
		getElement().findElement(By.tagName("input")).sendKeys(text);
		action.sendKeys(Keys.ENTER).perform();//输入回车键
	}
	
	//清空选项
	public void clearValue(){
		executor.executeScript(getQuery()+".clearValue()");
	}
	
	//获取当前选中项
	public String getValue(){
		return (String) executor.executeScript("return "+getQuery()+".getRawValue()");
	}
}
