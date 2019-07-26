/**
 * Created by user on 2019/07/01.
 */
package jp.ac.ncc.system.se1007.hurumatch;

public class User {

	public String email;
	public String password;
	public static int point;

	public User(){

	}

	public User(String _email,String _password, int _point){
		email = _email;
		password = _password;
		point = _point;
	}
	public String getEmail(){
		return email;
	}
	public String getPassword(){
		return password;
	}
	public int getPoint(){ return point; }

}