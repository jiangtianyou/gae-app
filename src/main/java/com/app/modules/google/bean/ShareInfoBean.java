package com.app.modules.google.bean;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ShareInfoBean {
	public static final String SHANGZHEGN = "sh000001"; // 上证指数
	public static final String CYB = "sz399006"; // 创业板
	public static final String HS300 = "sh000300"; // 沪深300
	public static final String SZ = "sz399106"; // 深证指数
	public static final String SZ180 = "sh000010"; // 上证180
	public static final String ZZ500 = "sh000905"; // 中证500

	private String code;
	private String name;
	private Double open;
	private Double close;
	private Date date;
	private double point; // 点位


}
