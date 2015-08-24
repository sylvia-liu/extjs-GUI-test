//时间选择组件
package org.extjs;

public class Datefield extends TextField {
	String JS;
	public Datefield(String componentQuery){
		super(componentQuery);
		setQuery();
	}
	
	/**
	 * 处理定位Datefield组件的query，_query=componentQuery
	 * 1）_query为null,根据xtype定位；
	 * 2）_query不包含[,代表输入标签文字
	 * 3）_query比较复杂，则不作处理直接传入gerQuery()中
	 */
	private void setQuery(){
		if(_query==null){
			_query = "datefield";
		}else if(_query.indexOf("[")==-1&&_query.indexOf("=")==-1){
			_query = String.format("datefield[fieldLabel='%s']",_query);
		}else if(_query.indexOf("[")==-1&&_query.indexOf("=")!=-1){
			_query = String.format("datefield[%s]",_query);
		}else{
			_query = String.format("%s",_query);
		}
	}
	/**
	 * 设置时间值,自定义时间格式
	 * @param strData 时间字符串 ,输入-表示取系统当前时间
	 * @param pattern 时间格式  ，值为null时，格式为'Y-m-d H:i:s'，
	 */
	public void setData(String strData,String pattern){
		if(strData==null){
			return;
		}
		if(strData.equals("-")){//-表示系统当前时间
			setCurData();
		}
		if(pattern==null){
			JS = "var dt = Ext.Date.parse(\'"+strData+"\','Y-m-d H:i:s');";
		}else{
			JS = "var dt = Ext.Date.parse(\'"+strData+"\',\'"+pattern+"\');";

		}
		JS = JS+"var dataTextField = "+getQuery()+";"
				+ "dataTextField.setValue(dt)";
		executeJS(JS);
	}
	/**
	 * 设置时间值,时间格式默认为'Y-m-d H:i:s'
	 * @param strData 时间字符串 ,输入-表示取系统当前时间
	 */
	public void setDataBySecond(String strData){
		setData(strData,"Y-m-d H:i:s");
	}
	/**
	 * 设置时间值,时间格式默认为'Y-m-d H:i:s'
	 * @param strData 时间字符串 ,输入-表示取系统当前时间
	 */
	public void setDataByData(String strData){
		setData(strData,"Y-m-d");
	}
	/**
	 * 设置时间值——系统当前时间
	 */
	public void setCurData(){
			JS = "var dataTextField = "+getQuery()+";"
				+ "dataTextField.setValue(new Data())";
		executeJS(JS);
	}
	
	/**
	 * 最近一段时间,例如 H=1
	 * @param type d：天；h：小时；i：分钟；s：秒
	 * @param duration
	 * @return 返回值类似 2015-01-29 11:15:07，可直接在setData()中调用
	 */
	public String lastData(char type,int duration){
		long length = 0;
		switch (type) {
		case 'd':
			length = duration*24*60*60*1000;
			break;
		case 'h':
			length = duration*60*60*1000;
			break;
		case 'i':
			length = duration*60*1000;
			break;
		case 's':
			length = duration*1000;
			break;
		default:
			break;
		}
		JS = "var lastData = new Date(new Date()-7*24*60*60*1000);"
				+ "return Ext.Date.format(lastData, 'Y-m-d H:i:s')";
		return (String) executeJS(JS);
	}
}
