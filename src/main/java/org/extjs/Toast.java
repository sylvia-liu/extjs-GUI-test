package org.extjs;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class Toast extends Component {
	protected String Msg = null;

	public Toast() {
		super("toast");
		setQuery();
	}

	public Toast(String componentQuery) {
		super(componentQuery);
		setQuery();
	}

	/**
	 * 获取定位toast组件的query语句
	 */
	private void setQuery() {
		if (_query == null || _query.equals("toast")) {
			_query = "toast";
		} else if (_query.indexOf("[") == -1) {
			_query = String.format("toast[title='%s']", _query);
		}
	}

	/******************************** 操作方法 ******************************************/
	/**
	 * 获取提示信息
	 * @return String 提示信息
	 * @EditBy:lql 2014年11月20日
	 */
	public String getMsg() {
		WebElement textEl =getElement().findElement(By.cssSelector("div:nth-child(2) div[data-ref='innerCt']"));
		int time = 0;
		while (time <= 1000) {
			if(textEl.getAttribute("innerHTML")!=null){
				Msg = textEl.getAttribute("innerHTML");
				break;
			}else{
				time = time + 100;
				waitForTime(time);
			}
		}
		return Msg;
	}
	
	public void close(){
		String JS= "var toast = "+ getQuery() +";"
				+ "toast.close()";
		executeJS(JS);
		waitForTime(1000);
	}
}
