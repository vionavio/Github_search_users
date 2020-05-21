package com.vio.githusearchtest.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class SearchData  {

	@SerializedName("login")
	private String login;

	@SerializedName("avatar_url")
	private String avatarUrl;

	public void setLogin(String login){
		this.login = login;
	}

	public String getLogin(){
		return login;
	}

	public void setAvatarUrl(String avatarUrl){
		this.avatarUrl = avatarUrl;
	}

	public String getAvatarUrl(){
		return avatarUrl;
	}

	@Override
 	public String toString(){
		return 
			"ItemsItem{" +
			",login = '" + login + '\'' +
			",avatar_url = '" + avatarUrl + '\'' +
			"}";
		}
}