package org.extjs;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class CompositeComponent extends Component {

	protected List <Component> _children = new ArrayList<Component> ();//CompositeComponent子对象，Component组件集合
	
	private GridPanel GRID;
	private FormPanel FORM;
	private TabPanel TAB;
	private Panel PANEL;
	private TreePanel TREE;
	private FieldSet FieldSet;

	private Button BTN;
	private TextField TXT;
	private ComboBox COMBO;
	private RadioGroup radioGroup;
	private CheckboxGroup ChkBoxGroup;
	private Checkbox ChkBox;
	private Datefield DATA;
	private Radio radio;

	public CompositeComponent(String query,int arrary) {
		super(query,arrary);
    }
	
	public CompositeComponent(String query) {
		super(query);
    }
	public CompositeComponent() {
		super();
    }
	/**
	 * 添加子组建
	 * @param component 
	 */
	public void addChild(Component component) {
	    this._children.add(component);
	    component._parent = this;
    }

	//grid控件
	public GridPanel getGrid(String query){
		GRID = new GridPanel(query);
		addChild(GRID);
		return GRID;
	}
	//grid控件
	public GridPanel getGrid(){
		GRID = new GridPanel();
		addChild(GRID);
		return GRID;
	}
	
	//form控件
	public FieldSet getFieldSet(String query){
		FieldSet = new FieldSet(query);
		addChild(FieldSet);
		return FieldSet;
	}
	
	//form控件
	public FieldSet getFieldSet(){
		FieldSet = new FieldSet();
		addChild(FieldSet);
		return FieldSet;
	}
	
	//form控件
	public FormPanel getForm(String query){
		FORM = new FormPanel(query);
		addChild(FORM);
		return FORM;
	}
	
	//form控件
	public FormPanel getForm(){
		FORM = new FormPanel();
		addChild(FORM);
		return FORM;
	}
	
	//Tab控件
	public TabPanel getTab(String query){
		TAB = new TabPanel(query);
		addChild(TAB);
		return TAB;
	}

	//Tab控件
	public TabPanel getTab(){
		TAB = new TabPanel();
		addChild(TAB);
		return TAB;
	}
	
	//panel控件
	public Panel getPanel(String query){
		PANEL = new Panel(query);
		addChild(PANEL);
		return PANEL;
	}
	//panel控件
	public Panel getPanel(){
		PANEL = new Panel();
		addChild(PANEL);
		return PANEL;
	}
	
	//panel控件
	public TreePanel getTree(String query){
		TREE = new TreePanel(query);
		addChild(TREE);
		return TREE;
	}
	//panel控件
	public TreePanel getTree(){
		TREE = new TreePanel();
		addChild(TREE);
		return TREE;
	}
	
	//输入框控件
	public TextField getTextField(String query){
		TXT = new TextField(query);
		addChild(TXT);
		return TXT;
	}

	//按钮控件
	public Button getButton(String query) {
		BTN = new Button(query);
		addChild(BTN);
		return BTN;
	}

	//下拉列表控件
	public ComboBox getCombo(String query){
		COMBO = new ComboBox(query);
		addChild(COMBO);
		return COMBO;
	}
	//单选框控件组
	public RadioGroup getRadioGroup(String query){
		radioGroup = new RadioGroup(query);
		addChild(radioGroup);
		return radioGroup;
	}
	//单选框控件
	public Radio getRadio(String query){
		radio = new Radio(query);
		addChild(radio);
		return radio;
	}
	//多选框控件组
	public CheckboxGroup getCheckBoxGroup(String query){
		ChkBoxGroup = new CheckboxGroup(query);
		addChild(ChkBoxGroup);
		return ChkBoxGroup;
	}
	//多选框控件
	public Checkbox getCheckBox(String query){
		ChkBox = new Checkbox(query);
		addChild(ChkBox);
		return ChkBox;
	}
	
	//日期控件
	public Datefield getDatefield(String label){
		DATA = new Datefield(label);
		addChild(DATA);
		return DATA;
	}

    public List<WebElement> findElements(By by) {
        return getElement().findElements(by);
    }

    public WebElement findElement(By by) {
        return getElement().findElement(by);
    }

}
