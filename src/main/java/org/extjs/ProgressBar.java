package org.extjs;


public class  ProgressBar extends Component {

	public ProgressBar(String query, int array) {
		super(query, array);
	}

	public ProgressBar(String query) {
		super(query);
	}

	public ProgressBar() {
		super();
		setQuery();
	}
	/**
	 * 获取定位toast组件的query语句
	 */
	private void setQuery() {
		if (_query == null || _query.equals("progressbar")) {
			_query = "progressbar";
		}
	}
	/**
	 * 检查进度条是否在页面显示
	 * @return true-不显示；false-显示
	 */
	public boolean isHidden(){
		return (Boolean) executeJS("return "+getQuery()+".isHidden()");
	}
	
	/**
	 * 检查进度条所在弹出窗是否在页面显示
	 * @return true-显示；false-不显示
	 */
	public boolean isVisible(){

		return (Boolean) executeJS("return "+getQuery()+".up().isVisible()");
	}
}
