package com.stock.data.config.auth.dto;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

@Getter
public enum OAuthProviderType {
	GOOGLE("GOOGLE", 1){
		@Override
		public boolean equals(String oauthTypeNm) {
			String googleTypeNm = this.getProviderTypeNm();
			if(googleTypeNm.equalsIgnoreCase(oauthTypeNm)){
				return true;
			}
			else{
				return false;
			}
		}
	},
	NAVER("NAVER", 2){
		@Override
		public boolean equals(String oauthTypeNm) {
			String naverTypeNm = this.getProviderTypeNm();
			if(naverTypeNm.equalsIgnoreCase(oauthTypeNm)){
				return true;
			}
			else{
				return false;
			}
		}
	};

	private String providerTypeNm;
	private int providerTypeCd;
	private static Map innerMap = new HashMap();

	public abstract boolean equals(String oauthTypeNm);

	OAuthProviderType(String providerTypeNm, int providerTypeCd){
		this.providerTypeNm = providerTypeNm;
		this.providerTypeCd = providerTypeCd;
	}

	static{
		for(OAuthProviderType providerType : OAuthProviderType.values()){
			innerMap.put(providerType.providerTypeCd, providerType);
		}
	}

	public static OAuthProviderType valueOf(int providerTypeCd){
		return (OAuthProviderType) innerMap.get(providerTypeCd);
	}
}
