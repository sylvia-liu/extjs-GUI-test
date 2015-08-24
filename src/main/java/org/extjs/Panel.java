package org.extjs;

public class Panel extends CompositeComponent {
	
	private Button BTN;
	private TextField TXT;
	private ComboBox COMBO;
	private Datefield DATA;
	private GridPanel GRID;
	private FormPanel FORM;
	private TabPanel TAB;
	
	/**
	 * Constructor 
	 * @param query 定位属性
	 * @param array 元素序列
	 */
	public Panel(String query,int array) {
		super(query,array);
		setQuery();
	}
	
	public Panel(String query) {
		super(query);
		setQuery();
	}

	public Panel() {
		super();
		setQuery();
	}

	public void setQuery() {
		if (_query == null || _query.equals("panel")) {
			_query = "panel";
		} else if (_query.indexOf("[") == -1&&_query.indexOf(".") == -1&&_query.indexOf("#") == -1) {
			_query = String.format("panel[title='%s']", _query);
		}
	}
	/***************************panel自带方法*******************************/
	  //展开panel
	   public void expand(){
			String js =getQuery()+".expand()";
			executeJS(js);
	   }	
	/***************************panel包含子组件*******************************/	
	//按钮控件
	public Button getButton(String text){
		BTN = new Button(text);
		addChild(BTN);
		return BTN;
	}
	//输入框控件
	public TextField getTextField(String query){
		TXT = new TextField(query);
		addChild(TXT);
		return TXT;
	}
	//下拉列表控件
	public ComboBox getCombo(String query){
		COMBO = new ComboBox(query);
		addChild(COMBO);
		return COMBO;
	}
	//日期控件
	public Datefield getDatefield(String query){
		DATA = new Datefield(query);
		addChild(DATA);
		return DATA;
	}
	
	//grid控件
	public GridPanel getGrid(String query){
		GRID = new GridPanel(query);
		addChild(GRID);
		return GRID;
	}
	
	//form控件
	public FormPanel getForm(String query){
		FORM = new FormPanel(query);
		addChild(FORM);
		return FORM;
	}
	
	//form控件
	public TabPanel getTab(String query){
		TAB = new TabPanel(query);
		addChild(TAB);
		return TAB;
	}
}
