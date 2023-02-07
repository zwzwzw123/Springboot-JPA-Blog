package com.cos.blog.model;

import lombok.Data;

//@Generated("jsonschema2pojo")
@Data
public class KakaoProfile {

	public Long id;
	public String connected_at;
	public Properties properties;
	public KakaoAccount kakao_account;

	// @Generated("jsonschema2pojo")
	@Data
	public class Properties {
		public String nickname;
	}

	// @Generated("jsonschema2pojo")
	@Data
	public class KakaoAccount {
		public Boolean profile_nickname_needs_agreement;
		public Profile profile;
		public Boolean has_email;
		public Boolean email_needs_agreement;
		public Boolean is_email_valid;
		public Boolean is_email_verified;
		public String email;
		
		// @Generated("jsonschema2pojo")
		@Data
		 class Profile {
			public String nickname;

		}
	}
}
