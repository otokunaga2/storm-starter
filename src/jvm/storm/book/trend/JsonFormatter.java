package storm.book.trend;


import java.util.logging.LogRecord;

import org.omg.CORBA.FieldNameHelper;

import ch.qos.logback.classic.spi.ILoggingEvent;


public class JsonFormatter implements Formatter{

	private static final String QUOTE = "\"";
	private static final String COLON = ":";
	private static final String COMMA = ",";
	
	
	private boolean expectJson = false;
	
	
	
	
	
	public String format(ILoggingEvent event) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		fieldName("level", sb);
		quote(event.getFormattedMessage(),sb);
		sb.append(COMMA);
		fieldName("timestamp", sb);
		sb.append(event.getTimeStamp());
		sb.append(COMMA);
		fieldName("message", sb);
		if(this.expectJson){
			sb.append(event.getFormattedMessage());
		}else{
			quote(event.getFormattedMessage(),sb);
		}
		sb.append("}");
		return sb.toString();
	}
	private static void fieldName(String name, StringBuilder sb){
		quote(name,sb);
		sb.append(COLON);
	}
	private static void quote(String value, StringBuilder sb){
		sb.append(QUOTE);
		sb.append(value);
		
	}

}
