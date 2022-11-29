/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package com.tongfu.Util;

import java.io.BufferedReader;
import java.io.File;

import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import net.sf.ehcache.CacheManager;


import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.converters.ArrayConverter;
import org.apache.commons.beanutils.converters.DateConverter;


import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Value;
import org.w3c.dom.Document;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;



/**
 * Utils - 系统设置
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@SuppressWarnings("unchecked")
public final class SettingUtils {

	/** CacheManager */
	private static final CacheManager cacheManager = CacheManager.create();

	/** BeanUtilsBean */
	private static final BeanUtilsBean beanUtils;
	private static int HttpResult;


	static {
		ConvertUtilsBean convertUtilsBean = new ConvertUtilsBean() {
			@Override
			public String convert(Object value) {
				if (value != null) {
					Class<?> type = value.getClass();
					if (type.isEnum() && super.lookup(type) == null) {
						super.register(new EnumConverter(type), type);
					} else if (type.isArray() && type.getComponentType().isEnum()) {
						if (super.lookup(type) == null) {
							ArrayConverter arrayConverter = new ArrayConverter(type, new EnumConverter(type.getComponentType()), 0);
							arrayConverter.setOnlyFirstToString(false);
							super.register(arrayConverter, type);
						}
						Converter converter = super.lookup(type);
						return ((String) converter.convert(String.class, value));
					}
				}
				return super.convert(value);
			}

			@SuppressWarnings("rawtypes")
			@Override
			public Object convert(String value, Class clazz) {
				if (clazz.isEnum() && super.lookup(clazz) == null) {
					super.register(new EnumConverter(clazz), clazz);
				}
				return super.convert(value, clazz);
			}

			@SuppressWarnings("rawtypes")
			@Override
			public Object convert(String[] values, Class clazz) {
				if (clazz.isArray() && clazz.getComponentType().isEnum() && super.lookup(clazz.getComponentType()) == null) {
					super.register(new EnumConverter(clazz.getComponentType()), clazz.getComponentType());
				}
				return super.convert(values, clazz);
			}

			@SuppressWarnings("rawtypes")
			@Override
			public Object convert(Object value, Class targetType) {
				if (super.lookup(targetType) == null) {
					if (targetType.isEnum()) {
						super.register(new EnumConverter(targetType), targetType);
					} else if (targetType.isArray() && targetType.getComponentType().isEnum()) {
						ArrayConverter arrayConverter = new ArrayConverter(targetType, new EnumConverter(targetType.getComponentType()), 0);
						arrayConverter.setOnlyFirstToString(false);
						super.register(arrayConverter, targetType);
					}
				}
				return super.convert(value, targetType);
			}
		};

		DateConverter dateConverter = new DateConverter();
		dateConverter.setPatterns(CommonAttributes.DATE_PATTERNS);
		convertUtilsBean.register(dateConverter, Date.class);
		beanUtils = new BeanUtilsBean(convertUtilsBean);
	}

	/**
	 * 不可实例化
	 */
	private SettingUtils() {
	}


	/**
	 * 获取系统设置
	 * 
	 * @return 系统设置
	 */
	public static Map<String,Object> getReadXml(String url) {
		Map<String,Object> map=new HashMap<>();
		try {

			String path = url+CommonAttributes.shenzhou_XML_PATH;

			DocumentBuilderFactory fac= DocumentBuilderFactory.newInstance();

			//用上面的工厂创建一个文档解析器
			DocumentBuilder builder=fac.newDocumentBuilder();

			//用上面的文档解析器解析一个文件放到document对象里
			Document document= builder.parse(path);
			Element root = document.getDocumentElement();
			NodeList listenerList = root.getElementsByTagName("setting");

//			NodeList listenerList=document.getElementsByTagName("/tongfu/setting");
			System.out.println("一共有"+listenerList.getLength()+"个节点");

			for (int i = 0; i < listenerList.getLength(); i++) {
				Node node1=listenerList.item(i);
				NamedNodeMap namedNodeMap = node1.getAttributes();
				String name = String.valueOf(namedNodeMap.getNamedItem("name").getNodeValue());
				Object value = (Object) namedNodeMap.getNamedItem("value").getNodeValue();
				map.put(name,value);
			}

		} catch (Exception e) {
			System.out.println("解析xml文件出错:"+e.getMessage());
			e.printStackTrace();
		}
		return map;
	}


}