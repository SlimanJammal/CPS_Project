package il.cshaifasweng.OCSFMediatorExample.entities;

import java.io.Serializable;
import java.time.LocalTime;

public class Message implements Serializable {

	private static final long serialVersionUID = -8224097662914849956L;
	
	private String message;
	private LocalTime time;
	private Object Object1;


	private Object Object2;
	private Object Object3;
	private Object Object4;
	private Object Object5;
	private Object Object6;
	private Object Object7;
	private Object Object8;
	private Object Object9;

	private  String Password;
	private  String ID;
	private  String LicenesPlate;
	private String  SubNum;

	public void setTime(LocalTime time) {
		this.time = time;
	}

	public Object getObject1() {
		return Object1;
	}

	public void setObject1(Object object1) {
		Object1 = object1;
	}

	public Object getObject2() {
		return Object2;
	}

	public void setObject2(Object object2) {
		Object2 = object2;
	}

	public Object getObject3() {
		return Object3;
	}

	public void setObject3(Object object3) {
		Object3 = object3;
	}

	public Object getObject4() {
		return Object4;
	}

	public void setObject4(Object object4) {
		Object4 = object4;
	}

	public Object getObject5() {
		return Object5;
	}

	public void setObject5(Object object5) {
		Object5 = object5;
	}

	public Object getObject6() {
		return Object6;
	}

	public void setObject6(Object object6) {
		Object6 = object6;
	}

	public Object getObject7() {
		return Object7;
	}

	public void setObject7(Object object7) {
		Object7 = object7;
	}

	public Object getObject8() {
		return Object8;
	}

	public void setObject8(Object object8) {
		Object8 = object8;
	}

	public Object getObject9() {
		return Object9;
	}

	public void setObject9(Object object9) {
		Object9 = object9;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public String getID() {
		return ID;
	}

	public void setID(String ID) {
		this.ID = ID;
	}

	public String getLicenesPlate() {
		return LicenesPlate;
	}

	public void setLicenesPlate(String licenesPlate) {
		LicenesPlate = licenesPlate;
	}

	public String getSubNum() {
		return SubNum;
	}

	public void setSubNum(String subNum) {
		SubNum = subNum;
	}


	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Message(String message) {
		this.message = message;
		this.time = LocalTime.now();
	}

	public LocalTime getTime() {
		return time;
	}
}
