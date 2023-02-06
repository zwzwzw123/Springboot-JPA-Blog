package com.cos.blog.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.model.OAuthToken;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

//인증이 안된 사용자들이 출입할 수 있는 경로 /auth/** 허용
// 주소가 /이면 index.jsp허용
//static이하에 있는 /js/**, /css/**, /image/**
@Controller
public class UserController {

	@GetMapping("/auth/joinForm")
	public String joinForm() {
		return "user/joinForm";
	}

	@GetMapping("/auth/loginForm")
	public String loginForm() {
		return "user/loginForm";
	}

	@GetMapping("/user/updateForm")
	public String updateForm(@AuthenticationPrincipal PrincipalDetail principal) {
		return "user/updateForm";
	}

	@GetMapping("/auth/kakao/callback")
	public @ResponseBody String kakaoCallback(String code) {

		// post방식으로 key-value데이터를 요청(카카오쪽으로)
		// 라이브러리 : Retrofit2, OkHttp, RestTemplate

		RestTemplate rt = new RestTemplate();

		// HttHeader 오브젝트 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		// HttpBody 오브젝트 생성
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", "8df17247d55783bd03518a5c7e50d6e4");
		params.add("redirect_uri", "http://localhost:8000/auth/kakao/callback");
		params.add("code", code);

		// HttpHeader와 HttpBody를 하나의 오브젝트에 담기
		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

		// Http요청하기 - Post방식으로 - response변수의 응답을 받음
		ResponseEntity<String> response = rt.exchange("https://kauth.kakao.com/oauth/token", HttpMethod.POST,
				kakaoTokenRequest, String.class);

		// Gson, Json Simple, ObjectMapper
		ObjectMapper objectMapper = new ObjectMapper();
		OAuthToken oauthToken = null;

		try {
			oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		System.out.println("카카오 엑세스 토큰 : " + oauthToken.getAccess_token());

		/*
		 * RestTemplate rt2 = new RestTemplate();
		 * 
		 * // HttHeader 오브젝트 생성 HttpHeaders headers2 = new HttpHeaders();
		 * headers2.add("Authorization",
		 * "Bearer 1lObMpqVOeKPXIrpQUYtz89SO0rNck9qVEQxIzC9CiolDwAAAYYkNYZ4/KakaoAK ${APP_ADMIN_KEY}"
		 * ); headers2.add("Content-type",
		 * "application/x-www-form-urlencoded;charset=utf-8");
		 * 
		 * // HttpHeader와 HttpBody를 하나의 오브젝트에 담기 HttpEntity<MultiValueMap<String,
		 * String>> kakaoProfileRequest2 = new HttpEntity<>(headers2);
		 * 
		 * // Http요청하기 - Post방식으로 - response변수의 응답을 받음 ResponseEntity<String> response2
		 * = rt2.exchange( "https://kapi.kakao.com", HttpMethod.POST,
		 * kakaoProfileRequest2, String.class);
		 */

		return response.getBody();

	}
}
