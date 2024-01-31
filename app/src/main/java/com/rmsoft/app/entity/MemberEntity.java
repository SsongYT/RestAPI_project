package com.rmsoft.app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name="member")
public class MemberEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="member_pk", unique=true, nullable=false)
	private long memberPk;
	
	@Column(name="user_id", unique=true, nullable=false)
	private String userId;
	
	@Column(name="user_password", nullable=false)
	private String userPassword;
	
	@Column(name="user_name", nullable=false)
	private String userName;
	
	@Column(name="user_phone_no", nullable=false)
	private String userPhoneNo;
	
	@Column(name="user_email", nullable=false)
	private String userEmail;
}
