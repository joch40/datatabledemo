package biz.urios.helper;

import java.io.Serializable;

/**
*
*/
public class Person implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String DT_RowId;
	public String DT_RowClass;
	public String firstName;
	public String lastName;
	public int age;
	public long number;

	Person(String firstName, String lastName, int age, long number) {

		DT_RowId = "PK:" + number;
		DT_RowClass = "custom";

		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.number = number;
	}
	
	
}
