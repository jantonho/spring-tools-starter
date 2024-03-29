package com.carteira.response;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Response<T> {

	private T data;
	private List<String> errors;
	
	public List<String> getErrors(){
		
		if(errors == null) {
			errors = new ArrayList<String>();
		}
		
		return errors;
	}
	
}
