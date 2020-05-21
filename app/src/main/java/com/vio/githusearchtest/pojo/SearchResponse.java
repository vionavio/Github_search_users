package com.vio.githusearchtest.pojo;

import com.google.gson.annotations.SerializedName;
import com.vio.githusearchtest.model.SearchData;
import java.util.List;

public class SearchResponse{

	@SerializedName("items")
	private List<SearchData> items;

	public void setItems(List<SearchData> items){
		this.items = items;
	}
	public List<SearchData> getItems(){
		return items;
	}

	@Override
 	public String toString(){
		return 
			"SearchResponse{" +
			",items = '" + items + '\'' + 
			"}";
		}
}