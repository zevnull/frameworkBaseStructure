package com.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

/**
 * Provides basic SSH functionality
 * 
 * @author Den
 * 
 */
public class SSH
{

  private int          PING_TRYS    = 5;
  private String       PING_COMMAND = "ping -c " + PING_TRYS + " -i 1 ";
  protected String     host, user, password;
  protected Connection connection   = null;
  protected Session    session      = null;
  protected int        conn_counter = 0;
  private Logger       logger       = Logger.getLogger( SSH.class );





  /**
   * Checks is node up during timeout seconds, immediately returns true if node up
   * 
   * @param ip - node IP
   * @param timeout - timeout in seconds
   * @return - true if node up, false if node down during timeout seconds
   * @throws Exception
   */
  public boolean waitForNodeUP( String ip, int timeout ) throws Exception
  {
    for( int time = 0; time < timeout; time++ )
    {
      if( ping( ip ) )
        return true;
      Thread.sleep( 1000 );
    }
    return false;
  }





  /**
   * Checks is node down during timeout seconds, immediately returns true if node down
   * 
   * @param ip - node ip
   * @param timeout - timeout in seconds
   * @return - true if node down, false if node up during timeout seconds
   * @throws Exception
   */
  public boolean waitForNodeDOWN( String ip, int timeout ) throws Exception
  {
    for( int time = 0; time < timeout; time++ )
    {
      if( !ping( ip ) )
        return true;
      Thread.sleep( 1000 );
    }
    return false;
  }





  /**
   * Execute ping command to the node with IP ip
   * 
   * @param ip - node ip
   * @return - true if all ping packets received successfully
   * @throws Exception
   */
  public boolean ping( String ip ) throws Exception
  {
    String patern = "^\\d+\\s+bytes\\s+from.*?:\\s+icmp_seq=\\d+\\s+ttl=\\d+\\s+time=\\d+\\.\\d+\\s+\\w+$";

    List<String> pingRes = runCmd( PING_COMMAND + ip );

    Pattern pat = Pattern.compile( patern );
    Matcher matcher = null;
    int success = 0;
    for( int i = 0; i < pingRes.size(); i++ )
    {
      matcher = pat.matcher( pingRes.get( i ) );
      if( matcher.matches() )
        success++;
    }

    return ( success == PING_TRYS );
  }





  /**
   * Runs command 'command' and returns result of that command as List of String
   * 
   * @param command - command to execute
   * @return - result of the 'command' as List of Strings
   * @throws Exception
   */
  public List<String> runCmd( String command ) throws Exception
  {
    connect();
    if( session == null )
    {
      throw new Exception( "Session isn't initialized" );
    }

    logger.debug( "Run command '" + command + "'" );
    session.execCommand( command );

    InputStream stdout = new StreamGobbler( session.getStdout() );

    BufferedReader br = new BufferedReader( new InputStreamReader( stdout ) );

    List<String> output = new ArrayList<String>();
    String line;
    while( true )
    {
      line = br.readLine();
      if( line == null )
        break;
      output.add( line );
    }
    disconnect();
    br.close();
    return output;
  }





  /**
   * Runs command 'command' and returns result of that command as String which is result of concatenation of all string
   * result of command with '\n' character
   * 
   * @param command - command to execute
   * @return - String concatenated with '\n' character
   * @throws Exception
   */
  public String runCommand( String command ) throws Exception
  {
    return runCommand( command, "\n" );
  }





  /**
   * Runs command 'command' and returns result of that command as String which is result of concatenation of all string
   * result of command with 'concatenation' character
   * 
   * @param command - command to execute
   * @param separator - separator for string concatenation
   * @return - String concatenated with '\n' character
   * @throws Exception
   */
  public String runCommand( String command, String separator ) throws Exception
  {
    List<String> cmdOtput = runCmd( command );
    String ret = "";
    for( int i = 0; i < cmdOtput.size(); ++i )
    {
      if( i > 0 )
        ret += separator + cmdOtput.get( i );
      else
        ret += cmdOtput.get( i );
    }
    return ret;
  }





  /**
   * Method for connecting to the node
   * 
   * @throws Exception
   */
  protected void connect() throws Exception
  {
    if( conn_counter > 0 )
    {
      conn_counter++;
      return;
    }

    logger.debug( "Start ssh connection to host " + host + ", user " + user + ", password " + password );
    if( null != connection )
      return;
    connection = new Connection( host );
    connection.connect();
    if( !connection.authenticateWithPassword( user, password ) )
    {
      throw new Exception( "Ssh authentiication failed" );
    }
    session = connection.openSession();
    conn_counter++;
  }





  /**
   * Method for disconnecting
   */
  protected void disconnect()
  {
    conn_counter--;
    if( conn_counter > 0 )
      return;
    conn_counter = 0;
    logger.debug( "Stop ssh connection" );
    if( null != session )
    {
      session.close();
      session = null;
    }

    if( null != connection )
    {
      connection.close();
      connection = null;
    }
  }





  /**
   * Method for setting IP of node
   * 
   * @param host - node IP
   */
  public void setHost( String host )
  {
    this.host = host;
  }





  /**
   * Method for setting SSH credentials
   * 
   * @param login - user name
   * @param password - user password
   */
  public void setCredentials( String login, String password )
  {
    this.user = login;
    this.password = password;
  }





  /**
   * Returns User name
   * 
   * @return - user name
   */
  public String getUserName()
  {
    return user;
  }





  /**
   * Returns password
   * 
   * @return - password
   */
  public String getPassword()
  {
    return password;
  }

}
