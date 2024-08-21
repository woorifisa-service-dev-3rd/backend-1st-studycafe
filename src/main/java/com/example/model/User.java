package com.example.model;

/**
 * 모델: Entity 정의
 * Entity는 DB와의 매핑을 담당함
 */

public class User {
	// 필드
	private int userUid; // PK, auto_increment
	private int password;
	private String name;
	private String id;
	private String phone;
	private int resttime;
	private int point;
	
	
	// 기본 생성자
	public User() {
		
	}
	
	public User(String id, int password) {
		super();
		this.id = id;
		this.password = password;
	}


	// 초기화 생성자
	public User(int userUid, String name, String phone, int resttime,  int point, String id, int password) {
		super();
		this.userUid = userUid;
		this.name = name;
		this.phone = phone;
		this.resttime = resttime;
		this.point = point;
		this.id = id;
		this.password = password;
	}

	// Getter, Setter 메서드	
	public int getUserUid() {
		return userUid;
	}

	public void setUserUid(int userUid) {
		this.userUid = userUid;
	}

	public int getPassword() {
		return password;
	}

	public void setPassword(int password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getResttime() {
		return resttime;
	}

	public void setResttime(int resttime) {
		this.resttime = resttime;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	@Override
	public String toString() {
		return "User [userUid=" + userUid + ", password=" + password + ", name=" + name + ", id=" + id + ", phone="
				+ phone + ", resttime=" + resttime + ", point=" + point + "]";
	}
	
}
