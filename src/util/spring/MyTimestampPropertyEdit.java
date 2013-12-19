
package util.spring;

import java.beans.PropertyEditorSupport;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * <br>
 * <b>功能：</b>类功能描述<br>
 */
public class MyTimestampPropertyEdit extends PropertyEditorSupport {
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if(text.length()==19){
			try {
				setValue(new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(text).getTime()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return;
	}
  
}