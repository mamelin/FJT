import java.io.IOException;
import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

class FacesConsole {

	private PrintStream _out = System.out;
	private SimpleDateFormat _dateParser = new SimpleDateFormat("M d, yyyy");
	private BufferedReader _in = new BufferedReader(new InputStreamReader(System.in));;
	
	String readMultiLineValue() {
		StringBuilder sb = new StringBuilder();
		println("(enter \".\" to end entry)");

		boolean firstLine = true;
		while( true ) {
			String line = readLine();
			
			if( line.equalsIgnoreCase(".") ) {
				break;
			} else {
				if( firstLine ) {
					firstLine = false;
				} else {
					sb.append("\n");
				}
				sb.append(line);
			}
		}
		
		return sb.toString();
	}
	
	void print( String s ) {
		_out.print( s );
	}

	void println() {
		_out.println();
	}

	void println( String s ) {
		_out.println( s );
	}
	
	String readLine() {
		String line = null;
		try {
			line = _in.readLine();
		} catch( Exception ex ) {
			Err.err(ex);
		}
		return line;
	}
	
	void prompt( String prompt ) {
		print( prompt + ": " );
	}
	
	String promptForString( String prompt ) {
		prompt(prompt);
		return readLine();
	}
	
	Date promptForDate( String prompt ) {
		Date date = null;
		String str = promptForString(prompt);
		
		try {
			date = _dateParser.parse(str);
		} catch( Exception ex ) {
			Err.err(ex);
		}
		
		return date;
	}
	
	String promptForMultiline( String prompt ) {
		prompt(prompt);
		return readMultiLineValue();
	}
}