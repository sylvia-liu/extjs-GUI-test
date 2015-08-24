package org.extjs;

public class FieldSet extends CompositeComponent {

	public FieldSet(String query) {
		super(query);
		setQuery();
	}

	public FieldSet() {
		super("fieldset");
		setQuery();
	}
	
	public void setQuery(){
		if(_query==null||_query.equals("fieldset")){
			_query = "fieldset";
		}else if(_query.indexOf("[")==-1){
			_query = String.format("fieldset[title='%s']",_query);
		}
	}

}
