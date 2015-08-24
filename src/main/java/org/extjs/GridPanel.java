package org.extjs;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

/**
 * 页面元素class包含x-grid
 * 
 * @author liuquanling
 * 
 */
public class GridPanel extends CompositeComponent {

	private String JS;// js语句

	// gridcolum 列
	private CompositeComponent Column;

	// 行编缉组件
	private CompositeComponent RowEditor;// 行编缉区，包括编辑区和按钮区
	private Button btnRowEdit;// 操作按钮 一般为保存、取消
	private TextField txtRowEdit;// 行编缉输入textfield组件
	private ComboBox comboRowEdit;// 行编辑下拉选项combo

	// 工具栏
	private CompositeComponent Toolbar;// 显示按钮的工具栏
	private Button btnInToolbar;
	private TextField txtInToolbar;

	private ComboBox comboFilter;// 列筛选用
	private TextField txtFilter;// 列筛选用

	private ComboBox pageSize;// 每页显示多少条
	private TextField curPage;// 第几页

	/**
	 * Constructor
	 * 
	 * @param query
	 *            定位属性
	 * @param array
	 *            元素序列
	 */
	public GridPanel(String query, int array) {
		super(query, array);
		setQuery();
	}

	/**
	 * Constructor
	 * 
	 * @param query
	 *            定位属性
	 */
	public GridPanel(String query) {
		super(query);
		setQuery();
	}

	/**
	 * Constructor
	 */
	public GridPanel() {
		setQuery();
	}

	public void setQuery() {
		if (_query == null) {
			_query = "grid";
		} else if (_query.indexOf("[") == -1 && _query.indexOf("=") == -1) {
			_query = String.format("grid[title='%s']", _query);
		} else if (_query.indexOf("[") == -1 && _query.indexOf("=") != -1) {
			_query = String.format("grid['%s']", _query);
		} else {
			_query = String.format("%s", _query);
		}
	}

	/**
	 * 检查指定数据是否在grid中存在
	 * 
	 * @param title
	 *            表头文字 Table header text
	 * @param value
	 * @return boolean true -存在；false-不存在
	 */
	public boolean isExistInGrid(String title, Object value) {
		long index = this.getRowIndex(title, value);
		if (index == -1) {
			return false;
		} else {
			return true;
		}
	}

	/*******************************************单元格操作**********************************************/

	/**
	 * 设置单元格数据
	 * 
	 * @param rowIndex
	 *            行号
	 * @param title
	 *            列名
	 * @param value
	 *            单元格值
	 */
	public void setData(long rowIndex, String title, Object value) {
		JS = "var grid = " + getQuery() + ";"// 找到grid组件
				+"var record = grid.getStore().getAt(" + rowIndex + ");";
		if (value instanceof Boolean) {
			JS = JS + "return record.set(\'" + getHead(title) + "\',"
					+ value + ")";
		} else {
			JS = JS + "return record.set(\'" + getHead(title) + "\','"
					+ value + "')";
		}
		executeJS(JS);
	}

	/**
	 * 获取指定数据的行号,注意title=getHead().text
	 * 
	 * @param title 列名
	 * @param object 值
	 * @return long 行号
	 */
	public long getRowIndex(String title, Object value) {
		JS = "var grid = " + getQuery() + ";"// 找到grid组件
			+"return grid.getStore().find('" + getHead(title)
				+ "','" + value + "')";// 根据指定值获取index
		return (Long)executeJS(JS);
	}

	/**
	 * Return the index of this column by header text 列序号 
	 * 
	 * @param headerText 列名
	 *            : The header text to be used as innerHTML (html tags are
	 *            accepted) to display in the Grid
	 * @return index，Returns the index of this column in the list of visible
	 *         columns ，start form 0
	 */
	public long getColIndex(String headerText) {
		JS = "return grid.down('[text=" + headerText
				+ "]').getVisibleIndex()";// 获取标题为headerText的列的页面可见列号
		return (Long)load(JS);
	}

	/**
	 * Return the display text of this cell by index
	 * 
	 * @param rowIndex
	 *            start from 0
	 * @param colIndex
	 *            start from 0
	 * @return the display text
	 */
	public String getData(long rowIndex, long colIndex) {
		String selector = String.format(
				"table[data-recordindex='%s'] td:nth-child(%s) div", rowIndex,
				colIndex + 1);
		WebElement cell = getElement().findElement(By.cssSelector(selector));
		executor.executeScript("arguments[0].scrollIntoView();", cell);
		return cell.getAttribute("innerHTML");
	}

	/**
	 * 获取指定单元格值——页面显示值
	 * 
	 * @param rowIndex
	 *            行号 ::rowIndex start from 0
	 * @param headerText
	 *            列名
	 * @return 单元格页面显示值
	 */
	public String getDataInText(long rowIndex, String headerText) {
		long colIndex = getColIndex(headerText);
		String selector = String.format(
				"table[data-recordindex='%s'] td:nth-child(%s) div", rowIndex,
				colIndex + 1);
		WebElement cell = getElement().findElement(By.cssSelector(selector));
		executor.executeScript("arguments[0].scrollIntoView();", cell);
		return cell.getAttribute("innerHTML");
	}

	/**
	 * 获取指定单元格值——后台传值
	 * 
	 * @param rowIndex
	 *            rowIndex start from 0
	 * @param headerText
	 * @return 单元格后台传值
	 */
	public String getDataInValue(long rowIndex, String headerText) {
		getColIndex(headerText);
		JS = "var grid = " + getQuery()+ ";"// 找到grid组件
				+ "var record = grid.getStore().getAt(" + rowIndex + ");"
				+ "return record.get(\'" + getHead(headerText) + "\')";// 根据
		Object value = executeJS(JS);
		if (value instanceof java.lang.Long) {
			return String.valueOf(value);
		} else {
			return (String) value;
		}
	}

	/****************************************** 行操作 ***********************************************/
	
	/**
	 * 按照行号获取一行数据元素
	 * 
	 * @param row
	 *            行号 从1开始::start with 1
	 * @return
	 */
	public WebElement getRecord(long row) {
		String selector = String.format("table[data-recordindex='%s']", row);
		WebElement record = getElement().findElement(By.cssSelector(selector));
		return record;
	}
	/**
	 * 获取当前页记录数，即行数
	 * 
	 * @return long
	 */
	public long getCount() {
		JS = "return grid.getStore().getCount();";// 得到当前页记录数
		return (Long) load(JS);
	}

	/**
	 * 获取所有数据记录数，即跨页总行数
	 * 
	 * @return long
	 */
	public long getTotalCount() {
		JS = " return grid.getStore().getTotalCount();"; // 得到记录总数，这个对于翻页信息还是很有用的
		return (Long)load(JS);
	}
	/**
	 * 双击指定行
	 * @param row 行号
	 */
	public void clickDbl(long row) {
		WebElement record = getRecord(row);
		action.doubleClick(record).perform();// 执行双击操作
	}

	/**
	 * 单击指定行
	 * @param row  行号
	 */
	public void clickRecord(long row) {
		if (row < 0) {
			return;
		}
		getRecord(row).click();
	}

	/**
	 *  单击指定行
	 * @param title 列名
	 * @param object 值
	 */
	public void clickRecord(String title, Object value) {
		long row = getRowIndex(title, value);
		if (row == -1) {
			Assert.fail("不存在数据{" + title + "=" + value + "}");
		}
		clickRecord(row);
	}
	
	/**
	 *  单击选中多行
	 * @param title 列名
	 * @param values 值
	 */
	public void clickRecord(String title, Object... values) {
		ArrayList<Long> rows = new ArrayList<Long>();
		for (int i = 0;i<values.length; i++) {
			if(getRowIndex(title, values[i])!=-1){
				rows.add(i,getRowIndex(title,values[i]));
			}
		}
		if(rows.size()>1){//选择多行
			action.keyDown(Keys.CONTROL).perform();
			for(int i=0;i<rows.size();i++){
				clickRecord((long) rows.get(i));
			}
			action.keyUp(Keys.CONTROL).perform();
		}else if(rows.size()==1){//选择一行
			clickRecord((long) rows.get(0));
		}else{//选择为空
			return;
		}
	}

	/**
	 * 全选
	 */
	public void selectAllRows() {
		JS = "grid.getSelectionModel().selectAll();";
		load(JS);
	}

	/**
	 * 取消全选
	 */
	public void deselectAllRows() {
		JS = "grid.getSelectionModel().deselectAll();";
		load(JS);
	}
	/**
	 * 选择指定范围的记录行
	 * @param startRow
	 *            开始行号
	 * @param endRow
	 *            结束行号
	 */
	public void selectRangeRows(long startRow, long endRow) {
		JS = "grid.getSelectionModel().selectRange(" + startRow + ","+ endRow + ");";
		load(JS);
	}
	/**
	 * 选择指定范围的记录行
	 * @param startRow
	 *            开始行号
	 * @param endRow
	 *            结束行号
	 */
	public void deSelectRangeRows(long startRow, long endRow) {
		JS = "grid.getSelectionModel().deselectRange(" + startRow + ","+ endRow + ");";
		load(JS);
	}
	/**
	 * 勾选指定单行
	 * @param rowIndex   行号
	 */
	public void selectRow(long rowIndex) {
		JS = "grid.getSelectionModel().select(" + rowIndex + ");";
		load(JS);
	}
	
	/**
	 * 取消勾选指定行
	 * @param rowIndex   行号
	 */
	public void deselectRow(long rowIndex) {
		JS = "grid.getSelectionModel().deselect(" + rowIndex + ");";
		load(JS);
	}

	/**
	 * 勾选指定多行
	 * @param rows   行号
	 */
	public void selectRow(ArrayList<Long> rows) {
		JS =  "var rowIndexArray = new Array("+rows.get(0);
		   for (int i=1;i<rows.size();i++) {
			   JS = JS+","+rows.get(i);
			}
		JS = JS +");"; 
		
		JS = JS + "grid.getSelectionModel().select(rowIndexArray);";
		load(JS);
	}
	/**
	 *  选中多行
	 * @param title 列名
	 * @param values 值
	 */
	public void selectRow(String title, Object... values) {
		ArrayList<Long> rows = new ArrayList<Long>();
		for (int i = 0;i<values.length; i++) {
			if(getRowIndex(title, values[i])!=-1){
				rows.add(i,getRowIndex(title,values[i]));
			}
		}
		if(rows.size()>1){//选择多行
			selectRow(rows);
		}else if(rows.size()==1){//选择一行
			selectRow(rows.get(0));
		}else{//选择为空
			return;
		}
	}
	/**
	 * 按照行号点击每行数据右侧操作图标
	 * 
	 * @param row
	 *            行号
	 * @param tip
	 *            提示文字
	 */
	public void operateRecord(long row, String tip) {
//		record.click();// 确保有滚动条时，待删除数据已显示在窗口中
		selectRow(row);
		WebElement record = getRecord(row);
		WebElement btnImg = record.findElement(By.cssSelector("img[data-qtip='"
				+ tip + "']"));// 找到操作按钮
		btnImg.click();// 点击删除
	}

	/*** >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>筛选功能>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>**/
	/**
	 * 按照列筛选——combo
	 * 
	 * @param title
	 *            列名
	 * @param displayValue
	 *            筛选值
	 */
	public void filterByCombo(String title, String displayValue) {
		Column = new CompositeComponent("gridcolumn[text=" + title + "]");
		comboFilter = new ComboBox();
		addChild(Column);
		Column.addChild(comboFilter);
		executor.executeScript("arguments[0].scrollIntoView();",
				comboFilter.getElement());
		comboFilter.selectByVisibleText(displayValue);

	}

	/**
	 * 按照列筛选——TextField
	 * 
	 * @param title
	 *            列名
	 * @param value
	 *            筛选值
	 */
	public void filterByText(String title, String value) {
		Column = new CompositeComponent("gridcolumn[text=" + title + "]");
		txtFilter = new TextField();
		addChild(Column);
		Column.addChild(txtFilter);
		executor.executeScript("arguments[0].scrollIntoView();",
				txtFilter.getElement());
		txtFilter.type(value);

	}

	/***************************************** RowEditor-行编辑 ***************************************/
	/**
	 * 获取行编辑容器
	 * 
	 * @return
	 */
	public CompositeComponent getRowEditor() {
		String rowEditorID = getElement().findElement(
				By.cssSelector("div[id^=roweditor]")).getAttribute("id");
		RowEditor = new CompositeComponent("#" + rowEditorID);
		return RowEditor;
	}

	/**
	 * 定位行编缉操作按钮
	 * 
	 * @param text
	 *            按钮文字：buttonText
	 * @return 按钮对象
	 */
	public Button getRowEditorButton(String text) {
		btnRowEdit = new Button(text);
		if (RowEditor == null) {
			getRowEditor();
		}
		RowEditor.addChild(btnRowEdit);
		return btnRowEdit;
	}

	/**
	 * 行编缉输入框textfield,按照name定位
	 * 
	 * @param name
	 *            输入框name
	 * @return textfield组件
	 */
	public TextField getRowEditorTextField(String name) {
		txtRowEdit = new TextField("textfield[name=" + name + "]");
		if (RowEditor == null) {
			getRowEditor();
		}
		RowEditor.addChild(txtRowEdit);
		return txtRowEdit;
	}

	/**
	 * 行编缉选择框ComboBox
	 * 
	 * @param name
	 * @return ComboBox组件
	 */
	public ComboBox getRowEditorComboBox(String name) {
		comboRowEdit = new ComboBox("combo[name=" + name + "]");
		if (RowEditor == null) {
			getRowEditor();
		}
		RowEditor.addChild(comboRowEdit);
		return comboRowEdit;
	}

	/*********************************** 工具栏 *************************************************/
	/**
	 * 获取工具栏容器
	 * 
	 * @return
	 */
	public CompositeComponent getToolbar() {
		Toolbar = new CompositeComponent("toolbar");
		addChild(Toolbar);
		return Toolbar;
	}

	/**
	 * 获取工具栏按钮
	 * 
	 * @param text
	 *            按钮文字
	 * @return 按钮组件
	 */
	public Button getToolbarButton(String text) {
		btnInToolbar = new Button(text);
		getToolbar().addChild(btnInToolbar);
		return btnInToolbar;
	}

	/**
	 * 获取工具栏输入框
	 * 
	 * @param text
	 *            按钮文字
	 * @return 按钮组件
	 */
	public TextField getToolbarTextField(String query) {
		txtInToolbar = new TextField(query);
		getToolbar().addChild(txtInToolbar);
		return txtInToolbar;
	}

	/*********************************** 备用——翻页 *************************************************/

	/**
	 * 获取翻页容器
	 * 
	 * @return 翻页组件
	 */
	public CompositeComponent getPagingtoolbar() {
		CompositeComponent pagingtoolbar = new CompositeComponent(
				"pagingtoolbar");
		this.addChild(pagingtoolbar);
		return pagingtoolbar;
	}

	/**
	 * 翻到下一页
	 */
	public void nextPage() {

		JS = "var pagingtoolbar = " + getPagingtoolbar().getQuery() + ";"// 找到grid下翻页组件
				+ "pagingtoolbar.moveNext()";
		executeJS(JS);
	}

	/**
	 * 翻到上一页
	 */
	public void previousPage() {
		JS = "var pagingtoolbar = " + getPagingtoolbar().getQuery() + ";"// 找到grid下翻页组件
				+ "pagingtoolbar.movePrevious()";
		executeJS(JS);
	}

	/**
	 * 跳转到第一页
	 */
	public void firstPage() {
		JS = "var pagingtoolbar = " + getPagingtoolbar().getQuery() + ";"// 找到grid下翻页组件
				+ "pagingtoolbar.moveFirst()";
		executeJS(JS);
	}

	/**
	 * 跳转到最后一页
	 */
	public void lastPage() {
		JS = "var pagingtoolbar = " + getPagingtoolbar().getQuery() + ";"// 找到grid下翻页组件
				+ "pagingtoolbar.moveLast()";
		executeJS(JS);
	}

	/**
	 * 跳转到指定页面页
	 * 
	 * @param pageNum
	 *            指定页码
	 */
	public void moveTopage(int pageNum) {
		curPage = new TextField("textfield[name='inputItem']");// 第几页
		getPagingtoolbar().addChild(curPage);
		curPage.type(String.valueOf(pageNum));
		action.sendKeys(curPage.getElement(), Keys.ENTER).build().perform();// 输入回车键
	}

	/**
	 * 设置每页显示数据记录数
	 * 
	 * @param itemsPrePage
	 */
	public void setNumPrePage(int itemsPrePage) {
		if (itemsPrePage != 0) {
			pageSize = new ComboBox("combo");
			getPagingtoolbar().addChild(pageSize);
			pageSize.sendVisibleText(String.valueOf(itemsPrePage));
		}
	}

	/**
	 * 刷新页
	 */
	public void refreshPage() {
		JS = "var pagingtoolbar = " + getPagingtoolbar().getQuery() + ";"// 找到grid下翻页组件
				+ "pagingtoolbar.doRefresh()";
		executeJS(JS);
		waitForTime(1000);
	}

	/************************************** 设置布局 ******************************************/
	/**
	 * hide or show this column by Boolean.显示/隐藏列-----未调适
	 * 
	 * @param header
	 *            ：tring， 列标题文字
	 * @param visible
	 *            : Boolean， true to show, false to hide. 设置列隐藏/显示的js：
	 *            grid.columns[i].setVisible(false/true);或grid.down(
	 *            'columnQuery').setVisible(false/true);
	 */
	public void setColVisible(String header, boolean visible) {
		JS = "var grid = " + getQuery()+ ";"// 找到grid组件
				+ "grid.down('[dataIndex=" + getHead(header)
				+ "]').setVisible(" + visible + ")";

		executeJS(JS);
	}

	/**
	 * Sets the width of the component(this is column)-----未调适
	 * 
	 * @param header
	 *            :String 列标题文字
	 * @param width
	 *            :int The new width to set this column
	 * @return boolean true to success, false to failed.
	 */
	public void setColWidth(String header, int width) {
		JS = "var grid = " + getQuery() + ";";// 找到grid组件
		String JS_set = JS + "grid.down('[dataIndex=" + getHead(header)
				+ "]').setWidth(" + width + ")";// 设置列宽
		executeJS(JS_set);
	}

	/**
	 * gets the width of the component(this is column)-----未调适
	 * 
	 * @param header
	 *            :String 列标题文字
	 * @return The width of this column
	 */
	public int getColWidth(String header) {
		JS = "var grid = " + getQuery() + ";"// 找到grid组件
				+ "return grid.down('[dataIndex=" + getHead(header)
				+ "]').getWidth()";// 获取列宽度
		return (Integer) executeJS(JS);
	}

	/**
	 * Moves the move-column into this container in front of reference-colnmn .
	 * 把某列移动到指定列前-----未调适
	 * 
	 * @param moveHeader
	 *            ：the column to move
	 * @param referenceHeader
	 *            ：The reference column
	 */
	public void moveColumn(String moveHeader, String referenceHeader) {
		// 找到moveColumn
		String moveColumn_JS = "var grid = " + getQuery()+ ";"// 找到grid组件
				+ "return grid.down('[dataIndex=" + getHead(moveHeader)
				+ "]').id";
		WebElement moveColumn = getElement().findElement(
				By.id((String) executeJS(moveColumn_JS)));

		// 找到referenceColumn
		String referenceHeader_JS = "var grid = " + getQuery()+ ";"// 找到grid组件
				+ "return grid.down('[dataIndex=" + getHead(moveHeader)
				+ "]').id";
		WebElement referenceColumn = getElement().findElement(
				By.id((String) executeJS(referenceHeader_JS)));

		action.dragAndDrop(moveColumn, referenceColumn).perform();// 执行拖拽
	}

	/**
	 * 等待grid记录加载完成
	 * 
	 * @param outTime
	 *            最长等待时间。outTime=0：检查grid记录是否为空；outTime〉0：grid等待记录加载
	 * @return
	 */
	public long waitForLoad(long outTime) {
		
		int time = 0;
		while (time < outTime && this.getCount() == 0) {
			time = time + 100;
			waitForTime(100);
		}
		return this.getCount();

	}
	/**
	 * 等待grid加载完成
	 * @param str 操作js；
	 * @return 
	 */
	public <T>Object load(String strOperate) {
		JS = "var grid = " + getQuery()+ ";"// 找到grid组件
				+"grid.store.on('load',function(store) {"
				+ strOperate +"},grid)";
		return executeJS(JS);
	}

	/**
	 * 获取标题文字
	 * 
	 * @return Map 标题文字
	 */
	public Object getHead(String titleText) {
		JS = "var head ='{';"
				+ "var grid = " + getQuery() + ";"// 找到grid组件
				+ "var len = grid.columns.length;" + "for(var i=0;i<len;i++){"
				+ "var text = grid.columns[i].text;"
				+ "var dataIndex = grid.columns[i].dataIndex;"
				+ "head =head+'\"'+text+'\":\"'+dataIndex+'\"';"
				+ "if(i<len-1){head = head+',';}" + "}" + "head=head + '}';"
				+ "return head";
		String title = (String) executeJS(JS);
		
		JSONObject jsonTitle =null;
		Object titleValue = null;
		try {
			jsonTitle = new JSONObject(title);
			titleValue = jsonTitle.get(titleText);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return titleValue;
	}
}
