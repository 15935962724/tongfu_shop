/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package com.tongfu.Util;


import java.util.Date;
import java.util.UUID;

/**
 * Utils - Web
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
public final class UUIDUtil {

	public static String getUUID(){
		String uuid = UUID.randomUUID().toString();
		//去掉“-”符号
		return uuid.replaceAll("-", "");
	}


			public static void main(String[] args) {

				System.out.println(getUUID());
				System.out.println(new Date().getTime());

	}





}