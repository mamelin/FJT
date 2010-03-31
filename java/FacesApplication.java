import java.util.Date;

class FacesApplication {
	private int _loggedInUser = -1;
	private Date _previousLogin = null;
	private FacesConsole _console = null;
	private FacesDatabase _db = null;

	public FacesApplication() {
		_db = new FacesDatabase();
		_console = new FacesConsole();
	}
	
	public void run() {
		StringBuilder sb = new StringBuilder();

		if( _db.connect() ) {
			_loggedInUser = loginOrRegister();
			if( -1 != _loggedInUser ) {
				_console.println();
				_console.println( "Welcome to Faces@OU!" );
				_console.println( "Type \"help\" for a list of commands" );
				processCommands();
			} else {
				_console.println( "Login or register failed." );
			}
			_db.disconnect();
		}
	}
	
	int loginOrRegister() {
		int id = -1;
		_console.println( "Faces@OU - Login or Register" );
		_console.println( "(enter \"new\" if you are a new user)" );
		_console.println();

		String emailOrNew = _console.promptForString("E-mail address");

		if( emailOrNew.equalsIgnoreCase("new") ) {
			id = register();
		} else {
			id = login( emailOrNew );
		}
		
		if( -1 != id ) {
			_db.setLastLogin(id, new Date());
		}
		
		return id;
	}

	int login( String email ) {
		String password = _console.promptForString("Password");
		
		int id = _db.getUserId( email, password );
		
		if( -1 != id ) {
			_previousLogin = _db.getLastLogin(id);
		}
		
		return id;
	}

	int register() {
		String name		= _console.promptForString( "name" );
		String email	= _console.promptForString( "E-mail address" );
		String password	= _console.promptForString( "Password" );
		Date birth		= _console.promptForDate( "Birth date (MM DD, YYYY)" );
		String aboutme	= _console.promptForMultiline( "About me" );
		
		int id = _db.registerUser( name, email, password, birth, aboutme );
		
		if( -1 != id ) {
			_previousLogin = new Date();
		}
		
		return id;
	}
	
	void showHelp() {
		_console.println( "Faces@OU Commands" ); 
		_console.println();
		_console.println( "Command      Description");
		_console.println( "==========   ==============================");
		_console.println( "help         Show this help screen" ); 
		_console.println( "msg          Send message" ); 
		_console.println( "add-friend   Add a friend" ); 
		_console.println( "new-msgs     Display new messages" ); 
		_console.println( "all-msgs     Display all messages" ); 
		_console.println( "friends      Display friends" );
		_console.println( "find         Find user" );
		_console.println( "confirm      Confirm friend requests" ); 
		_console.println( "degrees      Three degrees of seperation" ); 
		_console.println( "join         Join a group" );
		_console.println( "drop         Drop account" );
		_console.println( "exit         Logout" );
	}
	
	void processCommands() {
		String command = null;
		boolean quit = false;
		
		while( false == quit ) {
			_console.println();
			_console.print( "Faces@OU>" );
			
			command = _console.readLine().trim();
			
			if( command.equalsIgnoreCase("help") ) {
				showHelp();
			} else if( command.equalsIgnoreCase("msg") ) {
				sendMessage();
			} else if( command.equalsIgnoreCase("add-friend") ) {
				addFriend();
			} else if( command.equalsIgnoreCase("new-msgs") ) {
				displayNewMessages();
			} else if( command.equalsIgnoreCase("all-msgs") ) {
				displayAllMessages();
			} else if( command.equalsIgnoreCase("friends") ) {
				displayFriends();
			} else if( command.equalsIgnoreCase("find") ) {
				findUser();
			} else if( command.equalsIgnoreCase("confirm") ) {
				confirmFriendRequests(); 
			} else if( command.equalsIgnoreCase("degrees") ) {
				threeDegreesOfSeperation();
			} else if( command.equalsIgnoreCase("join") ) {
				joinAGroup();
			} else if( command.equalsIgnoreCase("drop") ) {
				dropAccount();
			} else if( command.equalsIgnoreCase("exit") ) {
				quit = true;
			} else {
				_console.println( "Unknown command" );
			}
		}
	}

	void sendMessage() {
		_console.println( "send message" );
	}

	void addFriend() {
		_console.println( "add friend" );
	}

	void displayNewMessages() {
		_console.println( "display new messages" );
	}

	void displayAllMessages() {
		_console.println( "display all messages" );
	}

	void displayFriends() {
		_console.println( "display friends" );
	}

	void findUser() {
		_console.println( "find user" );
	}

	void confirmFriendRequests() {
		_console.println( "confirm friend requests" );
	}

	void threeDegreesOfSeperation() {
		_console.println( "three degrees of seperation" );
	}

	void joinAGroup() {
		_console.println( "join a group" );
	}

	void dropAccount() {
		_console.println( "drop account" );
	}
}