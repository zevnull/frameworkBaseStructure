package com.utils;

import java.util.*;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import com.autotests.data.primitives.SolutionsContainer;

/**
 * Helper class for SolutionsOverview on the Solutions Overview page
 * 
 * work with system-config.json file
 * 
 * @author Andrei_Tsiarenia
 * 
 */
public class SolutionsOverviewUtils
{
  SSH ssh = new SSH();
  {
    // TODO add property
    ssh.setHost( "192.168.111.151" );
    ssh.setCredentials( "root", "password" );

    // ssh.setHost(Settings.getProperty(SettingsKeys.NODE_IP));
    // ssh.setCredentials(Settings.getProperty(SettingsKeys.NODE_SSH_USER),
    // Settings.getProperty(SettingsKeys.NODE_SSH_PASSWORD));
  }

  /**
   * Get SolutionsOverview count from config file
   * 
   * @return int - Solutions Overview count
   * @throws Exception
   */
  public int getSolutionsOverviewCountFromConfig() throws Exception
  {
    DMLogger.methodStarted();
    int result = getSolutionsOverviewNamesFromConfig().size();
    DMLogger.methodFinished( result );
    return result;
  }

  /**
   * Get Map of Solutions names by solution type from config file
   * 
   * @return List<String> - Solutions Overview names
   * @throws Exception
   */
  public Map<SolutionsContainer.SOLUTION_TYPE, List<String>> getSolutionsMapFromConfig() throws Exception
  {
    DMLogger.methodStarted();
    // TODO add path for QH DM2Web config (system-config.json)
    // String jsonTxt = ssh.runCommand( "less /opt/ddn/directmon/web/etc/ddn/directmon/system-config.json" );
    String jsonTxt = ssh.runCommand( "less /etc/ddn/directmon/system-config.json" );

    JSONObject json = ( JSONObject ) JSONSerializer.toJSON( jsonTxt );
    Set<?> s = json.keySet();

    List<String> result = new ArrayList<String>();

    Map<SolutionsContainer.SOLUTION_TYPE, List<String>> mapResult =
      new HashMap<SolutionsContainer.SOLUTION_TYPE, List<String>>();

    for( Object value : s )
    {
      Set<String> keySet = json.getJSONObject( value.toString() ).keySet();
      result.addAll( keySet );
    }

    List<String> sfaSolutions = new ArrayList<String>();
    List<String> gsSolutions = new ArrayList<String>();
    for( String aResult : result )
    {
      String element = aResult.toString();
      String typeOfElement = element.substring( element.indexOf( ":" ) + 1, element.length() );

      if( SolutionsContainer.SOLUTION_TYPE.SFA.getType().equals( typeOfElement ) )
      {
        sfaSolutions.add( element.substring( 0, element.indexOf( ":" ) ) );
      }

      if( SolutionsContainer.SOLUTION_TYPE.GRID_SCALER.getType().equals( typeOfElement ) )
      {
        gsSolutions.add( element.substring( 0, element.indexOf( ":" ) ) );
      }

    }
    mapResult.put( SolutionsContainer.SOLUTION_TYPE.SFA, sfaSolutions );
    mapResult.put( SolutionsContainer.SOLUTION_TYPE.GRID_SCALER, gsSolutions );

    DMLogger.methodFinished( result );
    return mapResult;
  }

  /**
   * Get list of all solutions names from config file  ( EXCEPT es solutions )
   * 
   * @return List<String> - Solutions Overview names
   * @throws Exception
   */
  public List<String> getSolutionsOverviewNamesFromConfig() throws Exception
  {
    DMLogger.methodStarted();
    // TODO add path for QH DM2Web config (system-config.json)
    String jsonTxt = ssh.runCommand( "less /etc/ddn/directmon/system-config.json" );

    JSONObject json = ( JSONObject ) JSONSerializer.toJSON( jsonTxt );
    Set<?> s = json.keySet();

    List<String> result = new ArrayList<String>();
    for( Object value : s )
    {
      Set<String> keySet = json.getJSONObject( value.toString() ).keySet();
      result.addAll( keySet );
    }

    for( int i = 0; i < result.size(); i++ )
    {
      String element = result.get( i ).toString();
      element = element.substring( 0, element.indexOf( ":" ) );
        //skip ES solutions
      if( !"es".equals( element.indexOf( ":" ) ) )
      {
        result.set( i, element );
      }

    }
    DMLogger.methodFinished( result );
    return result;
  }

  /**
   * Get set of NSDs from config file
   * 
   * @return set of NSDs from config file
   */
  public Set<String> getNSDsSetFromConfig()
  {
    DMLogger.methodStarted();

    String jsonTxt = "";
    try
    {
      jsonTxt = ssh.runCommand( "less /etc/ddn/directmon/system-config.json" );
    }
    catch( Exception e )
    {
      String errorText = " Error with 'ssh.runCommand( \"less /etc/ddn/directmon/system-config.json\" );' , e";
      DMLogger.errorInMethod( errorText );
      throw new RuntimeException( errorText );
    }

    JSONObject json = ( JSONObject ) JSONSerializer.toJSON( jsonTxt );
    Set<String> s = json.keySet();

    List<String> solutionsWithNSD = new ArrayList<String>();

    List<String> solutionTypes = new ArrayList<String>();
    for( Object value : s )
    {

      if( !value.toString().contains( ":sfa" ) )
      {
        Set<String> keySet = json.getJSONObject( value.toString() ).keySet();
        solutionsWithNSD.addAll( keySet );
        solutionTypes.add( value.toString() );
      }

    }

    List<Collection> nsdAndStorageList = new ArrayList<Collection>();
    for( Object value : s )
    {

      if( !value.toString().contains( ":sfa" ) )
      {
        for( String nsdSolution : solutionsWithNSD )
        {
          nsdAndStorageList.add( json.getJSONObject( value.toString() ).getJSONObject( nsdSolution ).values() );
        }
      }
    }

    List<String> nsdList = new ArrayList<String>();
    for( Collection aNsdAndStorageList : nsdAndStorageList )
    {
      int currentNdsCount = aNsdAndStorageList.toArray()[0].toString().split( "]," ).length;

      for( int j = 0; j < currentNdsCount; j++ )
      {
        // [["qa-nsd1"
        String tmp = aNsdAndStorageList.toArray()[0].toString().split( "]," )[j].split( ",\"nsd\"," )[0];
        tmp = tmp.substring( tmp.indexOf( "\"" ) + 1, tmp.length() - 1 );
        nsdList.add( tmp );
      }
    }

    Set<String> result = new TreeSet<String>( nsdList );

    DMLogger.methodFinished( result );
    return result;
  }
}
