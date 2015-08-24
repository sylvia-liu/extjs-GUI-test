package org.extjs;

public class TreePanel extends CompositeComponent {
	private String js;
	public TreePanel(String componentQuery) {
		super(componentQuery);
		setQuery();
	}
	
	public TreePanel() {
		super("treepanel");
	}
	
	/**
	 * 获取定位treepanel组件的query语句
	 */
	private void setQuery(){
		if(_query==null||_query.equals("treepanel")){
			_query = "treepanel";
		}else if(_query.indexOf("[")==-1){
			_query = String.format("treepanel[title='%s']",_query);
		}
	}
	/**
	 * 逐层展开tree节点
	 * @param String数组 menuText，以逗号分隔
	 * @return void
	 * @EditBy:lql 2014年11月24日
	 */
   public void clickNode(String... menuTexts){
	   //将menuTexts转换为js数组
	   js = "var menuTexts = new Array(\""+menuTexts[0]+"\"";
	   for (int i=1;i<menuTexts.length;i++) {
		    js = js+",\""+menuTexts[i]+"\"";
		}
	   js = js +");";
	   
	   js = js+"var tree = "+ getQuery()+ ";"
		   		+ "var rootnode = tree.getRootNode();"//获取主节点
				+ "for(var x in menuTexts){"
			   		+ "var nodeArray = rootnode.childNodes;"
			   		+ "for (var y in nodeArray) { "  // 单纯的遍历数组   
						+ "if(nodeArray[y].get('text')==menuTexts[x]){"
							+ "if(nodeArray[y].hasChildNodes()){"//不是子节点，展开
								+ "nodeArray[y].expand(false, true);"
								+ "rootnode = nodeArray[y];"
							+ "	}else{"//是子节点，打开页面
								+ "var nodeDom = tree.getView().getRow(nodeArray[y]);"
								+ "nodeDom.click();"//点击打开页面
							+ "}"
						+ "}"
					+ "}"
	   			+ "}";
		executeJS(js);
   }
   /**
    * 展开所有节点
    */
   public void expandAll(){
	   executeJS(getQuery()+".expandAll()");
   }
   /**
    * 展开指定节点
    * @param text 节点文字
    */
   public void expandNode(String text){
		js = "var tree = "+ getQuery()+ ";"
				+ "var record = tree.getStore().findRecord('text','" + text +"');"
				+ "tree.expandNode(record)";
		executeJS(js);
   }
   /**
    * 折叠指定节点
    * @param text 节点文字
    */
   public void collapseNode(String text){
		js = "var tree = "+ getQuery()+ ";"
				+ "var record = tree.getStore().findRecord('text','" + text +"');"
				+ "tree.collapseNode(record)";
		executeJS(js);
   }
   /**
    * 折叠所有节点
    */
   public void collapseAll(){
	   js = "var tree = "+ getQuery()+ ";"
	   		+ "tree.collapseAll()";
	   executeJS(js);
   }
   
   /**
    * 选中指定节点
    * @param object 
    */
	public void select(Object... object) {
		for (Object o:object) {
			js = "var tree = "+ getQuery()+ ";"
					+ "tree.getStore().findRecord('text','" + o + "'"
					+ ").set('checked',true)";
			executeJS(js);
		}
	}
   
}
