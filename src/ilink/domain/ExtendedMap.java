package ilink.domain;

import java.util.HashMap;
import java.util.Set;
import java.util.Iterator;
import java.util.Map;


public class ExtendedMap extends HashMap {

    public ExtendedMap() {
        super();
    }

    public ExtendedMap(Map m) {
        super(m);
    }

    @Override
	    public String toString() {

		String result = "{";
		Set set = this.entrySet();
		boolean firstRecord = true;
		String delimeter = "";
		Iterator i = set.iterator();
		while(i.hasNext()){
		  Map.Entry me = (Map.Entry)i.next();
		  result += ( delimeter + me.getKey() + ":" + me.getValue());
		  if(firstRecord){
			  delimeter = ",";
			  firstRecord = false;
		  }

        }

		result += "}";
	    return result;
	}
    public String toStringStr() {

		String result = "{";
		Set set = this.entrySet();
		boolean firstRecord = true;
		String delimeter = "";
		Iterator i = set.iterator();
		while(i.hasNext()){
		  Map.Entry me = (Map.Entry)i.next();
		  Object value = me.getValue();
		  if(value == null || value.toString().equals("")){
			 value = "\"\""; 
		  }else{
			  value = "\"" + value +"\""; 
		  }
		  result += ( delimeter + me.getKey() + ":" + value);
		  if(firstRecord){
			  delimeter = ",";
			  firstRecord = false;
		  }

        }

		result += "}";
	    return result;
	}

}
