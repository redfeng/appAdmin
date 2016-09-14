package com.zqxx.common.dao.support;

import java.util.UUID;

public class CreateId {
  public static String getCodeId(){
	  String id=UUID.randomUUID().toString();
	  String codeId="-"+id.substring(0, 4);
	return codeId;
	  
  }
}
