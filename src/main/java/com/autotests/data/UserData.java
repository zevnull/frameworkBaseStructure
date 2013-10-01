package com.autotests.data;

/**
 * Entity of users data. E.g. using this entity for Login
 * 
 * @author Andrei_Tsiarenia
 *
 */
public class UserData 
{
	
	public String name;
	public String password;

	public UserData(String name, String password) 
	{
		this.name = name;
		this.password = password;
	}
}
