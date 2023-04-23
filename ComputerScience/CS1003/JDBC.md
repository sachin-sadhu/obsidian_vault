Java Database Connectivity

##Driver

Code that knows how to communicate with a Database server, allows you to send SQL queries to a remote database server

//make connection 
'''java
Connection conn = DriverManager.getConnection(dbURL,password);

//create SQL statement
Statement stmt = conn.createStatement();

ResultSet rs = stmt.executeQuery(query);

##Statements
**Statement** : used for simple SQL with no parameters
**PreparedStatement** : precompiling SQL statements that may contain parameters

'''java
PreparedStatement statement = connection.prepareStatement("INSERT INTO person VALUES (?,?)");

building relational database to store publication information, create relational database from dblp, make queries from that database
 find publication of certain authors, check out StringBuilder class
 