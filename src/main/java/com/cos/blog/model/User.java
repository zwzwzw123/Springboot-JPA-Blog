package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity //User클래스가 MySQL에 테이블 생성
public class User {
	
	@javax.persistence.Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //프로젝트에서 연결된 db의 넘버링 전략을 따라간다
	private int id; //시퀀스, auto_increment
	
	@Column(nullable = false, length = 100, unique = true)
	private String username; //아이디
	
	@Column(nullable = false, length = 100)
	private String password; 
	
	@Column(nullable = false, length = 50)
	private String email;
	
	//@ColumnDefault("'user'")
	//db는 RoleType이라는게 없음
	@Enumerated(EnumType.STRING)
	private RoleType role; //나중에Enum으로 변경 할 것   ADMIN, USER, MANAGER
	
	private String oauth;//kakao,google
	
	@CreationTimestamp //시간이 자동으로 입력됨
	private Timestamp createDate;
}
