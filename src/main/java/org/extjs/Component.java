package org.extjs;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Component{

	protected static WebDriver _driver = null;//浏览器驱动

    protected String _query=null;//组件检索条件语句
    protected int _array=0;//第几个元素
    protected String _HTMLID = null;
    protected WebElement _element;//页面元素
    protected Component _parent=null;//父级组件
	protected static JavascriptExecutor executor =null;
	protected static Actions action = null;
    
	public static void setDriver(WebDriver driver){
		_driver = driver;//浏览器驱动
		executor =(JavascriptExecutor) _driver;
		action = new Actions(_driver);
	}
	
	public Component(String query,int array) {
		_array = array;
       	_query = query;
	}
	
	public Component(String query) {
       	_query = query;
	}
	
	public Component() {
	}
	
	/**
	 * 查找页面元素 
	 * @return WebElement
	 * @EditBy:lql 2014年12月4日
	 */
	public WebElement getElement() {
    	Wait<WebDriver> wait = new WebDriverWait(_driver,10);
    	_element = wait.until(
    				new ExpectedCondition<WebElement>() {
    					public WebElement apply(WebDriver driver) {
    						String id = getId();
	    					return driver.findElement(By.id(id));
    					};
    				}
    			);
        return _element;
    }
    
    /**
     * 获取页面元素id
     * @return String
     * @EditBy:lql 2014年12月4日
     */
	public String getId() {
		_HTMLID = (String) executeJS("return " + this.getQuery()+ ".id");
/*        if(isPresent(20000)){
        	_HTMLID = (String) executeJS("return " + this.getQuery()+ ".id");
        }*/
		return _HTMLID;
	}
	
	/**
	 * 获取定位组件的js语句
	 * @return String 定位组件的js语句
	 * @EditBy:lql 2014年12月4日
	 */
	public String getQuery() {
        //return StringUtils.EMPTY;
		return "Ext.ComponentQuery.query(\""+ getFullQuery() + "\")["+_array+"]";
    }

    protected String getInputQuery() {
    	return "Ext.ComponentQuery.query(\"" + getFullQuery()+ "\")[0].inputEl";
     }
    /**
     * 定位组件的检索条件(含父组件时有用)
     * @return String
     * @EditBy:lql 2014年12月4日
     */
	protected String getFullQuery() {
        String query = _query;
        Component parent = _parent;
        while (parent != null) {
            query = parent._query + " " + query;
            parent = parent._parent;
        }
        return query;
	}
	 
	/**>>>>>>>>>>>>>>>>>>>>>>>>>>>>等待时间处理>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>**/
    /**
     * 硬等待
     * @param millis 等待时间毫秒
     */
    public void waitForTime(long waitTimeInMillis){
    	try {
			Thread.sleep((waitTimeInMillis));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * 显性等待
     * @param timeOutInSeconds 超时时间
     */
    public void waitForPresent(long timeOutInSeconds){
		new WebDriverWait(_driver,timeOutInSeconds).until(ExpectedConditions.presenceOfElementLocated(By.id(getId())));
    }
    
    /**
     * 检查元素是否存在
     * @param OutTime 检查时间
     * @return 若存在返回true，否则返回false
     */
    public boolean isPresent(int OutTime){
		int time = 0;
		boolean b = false;
		while(time <= OutTime) {
			try{
				executor.executeScript("return "+this.getQuery()+".id");
				b = true;
				break;
			}catch(Exception e){
				time = time + 100;
				waitForTime(100);
			}
		}
		return b;
    }
    /**<<<<<<<<<<<<<<<<<<<<<<<<<<<<<等待时间处理<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<**/
    
	/**
	 * 执行js语句
	 * @param js
	 * @return
	 */
	public Object executeJS(String js){
		boolean b = isPresent(5000);
		if(b){
			return executor.executeScript(js);
		}
		return null;
	}

	/**
	 * 当前对象的兄弟对象
	 * @param i
	 * @return
	 */
	public String getNextSibling(int i){
		String JS = "var child = Ext.ComponentQuery.query(\""+ getFullQuery() + "\")["+i+"];"// 找到grid组件
				+ "return child.id";
		return (String) executeJS(JS);
	}
	/**
	 * 当前对象的父对象
	 * @param query
	 * @return
	 */
	public String getParentId(String query){
		String JS = "var child = " + getQuery() + ";"// 找到grid组件
						+ "var id = child.findParentByType('"+query+"').id;"
						+ "return id";// 
		return (String) executeJS(JS);
	}
	/************************WebElement方法重写*****************************/
//	@Override
	public void click() {
		getElement().click();
		waitForTime(100);
	}

//	@Override
	public void submit() {
        getElement().submit();		
	}

//	@Override
	public void sendKeys(CharSequence... keysToSend) {
	   getElement().sendKeys(keysToSend);		
	}    
	
//	@Override
    public void clear() {
        getElement().clear();
    }

//    @Override
    public String getTagName() {
        return getElement().getTagName();
    }

//    @Override
    public String getAttribute(String name) {
        return getElement().getAttribute(name);
    }

//    @Override
    public boolean isSelected() {
        return getElement().isSelected();
    }

//    @Override
    public boolean isEnabled() {
        return getElement().isEnabled();
    }

//    @Override
    public String getText() {
        return getElement().getText();
    }

//    @Override
    public boolean isDisplayed() {
        return getElement().isDisplayed();
    }

//    @Override
    public Point getLocation() {
        return getElement().getLocation();
    }

//    @Override
    public Dimension getSize() {
        return getElement().getSize();
    }

//    @Override
    public String getCssValue(String propertyName) {
        return getElement().getCssValue(propertyName);
    }
}
