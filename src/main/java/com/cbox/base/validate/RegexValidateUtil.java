package com.cbox.base.validate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @ClassName: RegexValidateUtil
 * @Function: Java表单验证工具类
 * 
 * @author cbox
 * @date 2020年2月27日 下午3:12:05
 * @version 1.0
 */
public class RegexValidateUtil {
	
//	/**
//	 * 只能包含中文、英文、数字、下划线、逗号
//	 */
//	public static final String STRING = "stringCheck";
//	public static final String STRING_TIP = "只能包含中文、英文、数字、下划线、逗号";
//	/**
//	 * 匹配非负整数（正整数+0）
//	 */
//	public static final String INT = "isInteger";
//	public static final String INT_TIP = "只能非负整数";
//	/**
//	 * 匹配URL地址
//	 */
//	public static final String URL = "isUrl";
//	public static final String URL_TIP = "http或https开头的网址";
//	/**
//	 * 匹配密码，以字母开头，长度在6-12之间，只能包含字符、数字
//	 */
//	public static final String PWD = "isPwd";
//	public static final String PWD_TIP = "以字母开头，长度在6-12之间，只能包含字符、数字";
//	/**
//	 * 匹配Email地址
//	 */
//	public static final String EMAIL = "isEmail";
//	public static final String EMAIL_TIP = "邮箱地址：example@qq.com";
//	/**
//	 * 判断数值类型，包括整数和浮点数
//	 */
//	public static final String NUM = "isNumeric";
//	public static final String NUM_TIP = "数值类型，包括整数和浮点数";
//	/**
//	 * 只能输入数字
//	 */
//	public static final String DIGITS = "isDigits";
//	public static final String DIGITS_TIP = "只能数字";
//	/**
//	 * 匹配浮点数
//	 */
//	public static final String FLOAT = "isFloat";
//	public static final String FLOAT_TIP = "只能浮点数";
//	/**
//	 * 联系电话(手机/电话皆可)验证
//	 */
//	public static final String TEL = "isTel";
//	public static final String TEL_TIP = "联系电话(手机/固话皆可)";
//	/**
//	 * 电话号码(区号)验证
//	 */
//	public static final String PHONE = "isPhone";
//	public static final String PHONE_TIP = "只能固话号码需加区号";
//	/**
//	 * 手机号码验证
//	 */
//	public static final String MOBILE = "isMobile";
//	public static final String MOBILE_TIP = "只能11位数字手机号码";
//	/**
//	 * 身份证号码验证
//	 */
//	public static final String IDCARDNO = "isIdCardNo";
//	public static final String IDCARDNO_TIP = "18位身份证号码";
//	/**
//	 * 邮政编码验证
//	 */
//	public static final String ZIPCODE = "isZipCode";
//	public static final String ZIPCODE_TIP = "只能6位的数字邮政编码";
//	/**
//	 * 英文 数字 下划线
//	 */
//	public static final String RIGHTFULSTRING = "isRightfulString";
//	public static final String RIGHTFULSTRING_TIP = "只能英文、数字、下划线";
//	/**
//	 * 判断英文字符
//	 */
//	public static final String ENGLISH = "isEnglish";
//	public static final String ENGLISH_TIP = "只能英文字符";
//	/**
//	 * 判断中文字符(包括汉字和符号)
//	 */
//	public static final String CHINESECHAR = "isChineseChar";
//	public static final String CHINESECHAR_TIP = "只能中文字符(包括汉字和符号)";
//	/**
//	 * 只能汉字
//	 */
//	public static final String CHINESE = "isChineseChar";
//	public static final String CHINESE_TIP = "只能汉字";
//	/**
//	 * 日期验证
//	 */
//	public static final String DATE = "date";
//	public static final String DATE_TIP = "年月日以-分隔符，时间以:为分隔符的日期";
//	/**
//	 * 只能是数字和逗号验证
//	 */
//	public static final String STRINGNUMCHECK = "stringNumCheck";
//	public static final String STRINGNUMCHECK_TIP = "只能数字和逗号";
	
	/**
	 * 匹配日期
	 * 
	 * @param str
	 * @return
	 */
	public final static boolean Date(String str) {
		return match(str, "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
	}
	public final static String DateTip(){
		return "年月日以-分隔符，时间以:为分隔符的日期";
	}
	/**
	 * 匹配URL地址
	 * 
	 * @param str
	 * @return
	 */
	public final static boolean Url(String str) {
		return match(str, "^(http|ftp|https)://([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?$");
	}
	public final static String UrlTip(){
		return "http或https开头的网址";
	} 
	/**
	 * 匹配密码，以字母开头，长度在6-12之间，只能包含字符、数字和下划线。
	 * 
	 * @param str
	 * @return
	 */
	public final static boolean Pwd(String str) {
		return match(str, "^[a-zA-Z]\\w{6,12}$");
	}
	public final static String PwdTip(){
		return "以字母开头，长度在6-12之间，只能包含字符、数字";
	} 
	/**
	 * 验证字符，只能包含中文、英文、数字、下划线、逗号 字符。
	 * 
	 * @param str
	 * @return
	 */
	public final static boolean String(String str) {
		return match(str, "^[a-zA-Z0-9_,\u4e00-\u9fa5]+$");
	}
	public final static String StringTip(){
		return "只能包含中文、英文、数字、下划线、逗号";
	} 
	/**
	 * 验证字符，只能包含数字 和逗号。
	 * 
	 * @param str
	 * @return
	 */
	public final static boolean DigitsComma(String str) {
		return match(str, "^^[0-9,]+$");
	}
	public final static String DigitsCommaTip(){
		return "只能数字和逗号";
	} 
	/**
	 * 匹配Email地址
	 * 
	 * @param str
	 * @return
	 */
	public final static boolean Email(String str) {
		return match(str,
				"^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
	}
	public final static String EmailTip(){
		return "邮箱地址：example@qq.com";
	} 
	/**
	 * 匹配非负整数（正整数+0）
	 * 
	 * @param str
	 * @return
	 */
	public final static boolean Integer(String str) {
		return match(str, "^[+]?\\d+$");
	}
	public final static String IntegerTip(){
		return "只能非负整数";
	}
	/**
	 * 判断数值类型，包括整数和浮点数
	 * 
	 * @param str
	 * @return
	 */
	public final static boolean Numeric(String str) {
		if (Float(str) || Integer(str))
			return true;
		return false;
	}
	public final static String NumericTip(){
		return "数值类型，包括整数和浮点数";
	}
	/**
	 * 只能输入数字
	 * 
	 * @param str
	 * @return
	 */
	public final static boolean Digits(String str) {
		return match(str, "^[0-9]*$");
	}
	public final static String DigitsTip(){
		return "只能数字";
	}
	/**
	 * 匹配正浮点数
	 * 
	 * @param str
	 * @return
	 */
	public final static boolean Float(String str) {
		return match(str, "^[-\\+]?\\d+(\\.\\d+)?$");
	}
	public final static String FloatTip(){
		return "只能浮点数";
	}
	/**
	 * 联系电话(手机/电话皆可)验证
	 * 
	 * @param text
	 * @return
	 */
	public final static boolean Tel(String text) {
		if (Mobile(text) || Phone(text))
			return true;
		return false;
	}
	public final static String TelTip(){
		return "联系电话(手机/固话皆可)";
	}
	/**
	 * 电话号码验证
	 * 
	 * @param text
	 * @return
	 */
	public final static boolean Phone(String text) {
		return match(text, "^(\\d{3,4}-?)?\\d{7,9}$");
	}
	public final static String PhoneTip(){
		return "只能区号加固话号码";
	}
	/**
	 * 手机号码验证
	 * 
	 * @param text
	 * @return
	 */
	public final static boolean Mobile(String text) {
		if (text.length() != 11)
			return false;
		return match(text,"^1[123456879]\\d{9}$");
	}
	public final static String MobileTip(){
		return "只能11位数字手机号码";
	}
	/**
	 * 身份证号码验证
	 * 
	 * @param text
	 * @return
	 */
	public final static boolean IdCard(String text) {
		return match(text, "^(\\d{6})()?(\\d{4})(\\d{2})(\\d{2})(\\d{3})(\\w)$");
	}
	public final static String IdCardTip(){
		return "18位身份证号码";
	}
	/**
	 * 邮政编码验证
	 * 
	 * @param text
	 * @return
	 */
	public final static boolean ZipCode(String text) {
		return match(text, "^[0-9]{6}$");
	}
	public final static String ZipCodeTip(){
		return "只能6位的数字邮政编码";
	}
	/**
	 * 判断是否为合法字符(a-zA-Z0-9-_)
	 * 
	 * @param text
	 * @return
	 */
	public final static boolean EnglishDigits(String text) {
		return match(text, "^[A-Za-z0-9_-]+$");
	}
	public final static String EnglishDigitsTip(){
		return "只能英文、数字、下划线、横线";
	}
	/**
	 * 判断英文字符(a-zA-Z)
	 * 
	 * @param text
	 * @return
	 */
	public final static boolean English(String text) {
		return match(text, "^[A-Za-z]+$");
	}
	public final static String EnglishTip(){
		return "只能英文字符";
	}
	/**
	 * 判断中文字符(包括汉字和符号)
	 * 
	 * @param text
	 * @return
	 */
	public final static boolean ChineseChar(String text) {
		return match(text, "^[\u0391-\uFFE5]+$");
	}
	public final static String ChineseCharTip(){
		return "只能中文字符(包括汉字和符号)";
	}
	/**
	 * 匹配汉字
	 * 
	 * @param text
	 * @return
	 */
	public final static boolean Chinese(String text) {
		return match(text, "^[\u4e00-\u9fa5]+$");
	}
	public final static String ChineseTip(){
		return "只能汉字";
	}
	/**
	 * 是否包含中英文特殊字符，除英文"-_"字符外
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isContainsSpecialChar(String text) {
		if (StringUtils.isBlank(text))
			return false;
		String[] chars = { "[", "`", "~", "!", "@", "#", "$", "%", "^", "&",
				"*", "(", ")", "+", "=", "|", "{", "}", "'", ":", ";", "'",
				",", "[", "]", ".", "<", ">", "/", "?", "~", "！", "@", "#",
				"￥", "%", "…", "&", "*", "（", "）", "—", "+", "|", "{", "}",
				"【", "】", "‘", "；", "：", "”", "“", "’", "。", "，", "、", "？", "]" };
		for (String ch : chars) {
			if (text.contains(ch))
				return true;
		}
		return false;
	}

	/**
	 * 过滤中英文特殊字符，除英文"-_"字符外
	 * 
	 * @param text
	 * @return
	 */
	public static String stringFilter(String text) {
		String regExpr = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		Pattern p = Pattern.compile(regExpr);
		Matcher m = p.matcher(text);
		return m.replaceAll("").trim();
	}

	/**
	 * 过滤html代码
	 * 
	 * @param inputString
	 *            含html标签的字符串
	 * @return
	 */
	public static String htmlFilter(String inputString) {
		String htmlStr = inputString; // 含html标签的字符串
		String textStr = "";
		java.util.regex.Pattern p_script;
		java.util.regex.Matcher m_script;
		java.util.regex.Pattern p_style;
		java.util.regex.Matcher m_style;
		java.util.regex.Pattern p_html;
		java.util.regex.Matcher m_html;
		java.util.regex.Pattern p_ba;
		java.util.regex.Matcher m_ba;

		try {
			String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
			// }
			String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
			// }
			String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
			String patternStr = "\\s+";

			p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
			m_script = p_script.matcher(htmlStr);
			htmlStr = m_script.replaceAll(""); // 过滤script标签

			p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
			m_style = p_style.matcher(htmlStr);
			htmlStr = m_style.replaceAll(""); // 过滤style标签

			p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
			m_html = p_html.matcher(htmlStr);
			htmlStr = m_html.replaceAll(""); // 过滤html标签

			p_ba = Pattern.compile(patternStr, Pattern.CASE_INSENSITIVE);
			m_ba = p_ba.matcher(htmlStr);
			htmlStr = m_ba.replaceAll(""); // 过滤空格

			textStr = htmlStr;

		} catch (Exception e) {
			System.err.println("Html2Text: " + e.getMessage());
		}
		return textStr;// 返回文本字符串
	}

	/**
	 * 正则表达式匹配
	 * 
	 * @param text 待匹配的文本
	 * @param reg 正则表达式
	 * @return
	 */
	private final static boolean match(String text, String reg) {
		if (StringUtils.isBlank(text) || StringUtils.isBlank(reg))
			return false;
		return Pattern.compile(reg).matcher(text).matches();
	}
	
	public final static String validateParams(String paramValue, String is_nullable, String data_length, String pattern, String comment) throws Exception {
        if (Boolean.valueOf(is_nullable)) {
            if (StringUtils.isBlank(paramValue.toString()) || "null".equals(paramValue)) {
                return comment + "不能为空值";
            }
        }
        if (StringUtils.isNotBlank(paramValue.toString()) && !"null".equals(paramValue)) {
            if (StringUtils.isNotBlank(data_length) && (paramValue.length() > Integer.valueOf(data_length))) {
                return comment + "长度需小于" + comment;
            }
            if (StringUtils.isNotBlank(pattern)) {
                Class<?> clz = Class.forName("com.cbox.base.validate.RegexValidateUtil");
                Object o = clz.newInstance();
                boolean b = (Boolean) clz.getMethod(pattern, String.class).invoke(o, paramValue);
                if (!b) {
                    String t_comment = (String) clz.getMethod(pattern + "Tip").invoke(o);
                    return comment + "格式不符，格式要求：" + t_comment;
                }
            }
        }
        return "";
    }
	// 参考地址：http://www.cnblogs.com/yansheng/archive/2010/05/07/1730188.html

	// 附 ： 常用的正则表达式：
	// 匹配特定数字：
	// ^[1-9]d*$　 　 //匹配正整数
	// ^-[1-9]d*$ 　 //匹配负整数
	// ^-?[1-9]d*$　　 //匹配整数
	// ^[1-9]d*|0$　 //匹配非负整数（正整数 + 0）
	// ^-[1-9]d*|0$　　 //匹配非正整数（负整数 + 0）
	// ^[1-9]d*.d*|0.d*[1-9]d*$　　 //匹配正浮点数
	// ^-([1-9]d*.d*|0.d*[1-9]d*)$　 //匹配负浮点数
	// ^-?([1-9]d*.d*|0.d*[1-9]d*|0?.0+|0)$　 //匹配浮点数
	// ^[1-9]d*.d*|0.d*[1-9]d*|0?.0+|0$　　 //匹配非负浮点数（正浮点数 + 0）
	// ^(-([1-9]d*.d*|0.d*[1-9]d*))|0?.0+|0$　　//匹配非正浮点数（负浮点数 + 0）
	// 评注：处理大量数据时有用，具体应用时注意修正
	//
	// 匹配特定字符串：
	// ^[A-Za-z]+$　　//匹配由26个英文字母组成的字符串
	// ^[A-Z]+$　　//匹配由26个英文字母的大写组成的字符串
	// ^[a-z]+$　　//匹配由26个英文字母的小写组成的字符串
	// ^[A-Za-z0-9]+$　　//匹配由数字和26个英文字母组成的字符串
	// ^w+$　　//匹配由数字、26个英文字母或者下划线组成的字符串
	//
	// 在使用RegularExpressionValidator验证控件时的验证功能及其验证表达式介绍如下:
	//
	// 只能输入数字：“^[0-9]*$”
	// 只能输入n位的数字：“^d{n}$”
	// 只能输入至少n位数字：“^d{n,}$”
	// 只能输入m-n位的数字：“^d{m,n}$”
	// 只能输入零和非零开头的数字：“^(0|[1-9][0-9]*)$”
	// 只能输入有两位小数的正实数：“^[0-9]+(.[0-9]{2})?$”
	// 只能输入有1-3位小数的正实数：“^[0-9]+(.[0-9]{1,3})?$”
	// 只能输入非零的正整数：“^+?[1-9][0-9]*$”
	// 只能输入非零的负整数：“^-[1-9][0-9]*$”
	// 只能输入长度为3的字符：“^.{3}$”
	// 只能输入由26个英文字母组成的字符串：“^[A-Za-z]+$”
	// 只能输入由26个大写英文字母组成的字符串：“^[A-Z]+$”
	// 只能输入由26个小写英文字母组成的字符串：“^[a-z]+$”
	// 只能输入由数字和26个英文字母组成的字符串：“^[A-Za-z0-9]+$”
	// 只能输入由数字、26个英文字母或者下划线组成的字符串：“^w+$”
	// 验证用户密码:“^[a-zA-Z]\\w{5,17}$”正确格式为：以字母开头，长度在6-18之间，
	//
	// 只能包含字符、数字和下划线。
	// 验证是否含有^%&’,;=?$”等字符：“[^%&’,;=?$x22]+”
	// 只能输入汉字：“^[u4e00-u9fa5],{0,}$”
	// 验证Email地址：“^w+[-+.]w+)*@w+([-.]w+)*.w+([-.]w+)*$”
	// 验证InternetURL：“^http://([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?$”
	// 验证电话号码：“^((d{3,4})|d{3,4}-)?d{7,8}$”
	//
	// 正确格式为：“XXXX-XXXXXXX”，“XXXX-XXXXXXXX”，“XXX-XXXXXXX”，
	//
	// “XXX-XXXXXXXX”，“XXXXXXX”，“XXXXXXXX”。
	// 验证身份证号（15位或18位数字）：“^d{15}|d{}18$”
	// 验证一年的12个月：“^(0?[1-9]|1[0-2])$”正确格式为：“01”-“09”和“1”“12”
	// 验证一个月的31天：“^((0?[1-9])|((1|2)[0-9])|30|31)$” 正确格式为：“01”“09”和“1”“31”。
	//
	// 匹配中文字符的正则表达式： [u4e00-u9fa5]
	// 匹配双字节字符(包括汉字在内)：[^x00-xff]
	// 匹配空行的正则表达式：n[s| ]*r
	// 匹配HTML标记的正则表达式：/< (.*)>.*|< (.*) />/
	// 匹配首尾空格的正则表达式：(^s*)|(s*$)
	// 匹配Email地址的正则表达式：w+([-+.]w+)*@w+([-.]w+)*.w+([-.]w+)*
	// 匹配网址URL的正则表达式：^http://([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?$
//	public static void main(String[] args) {
		// System.out.println("过滤中英文特殊字符: "+RegexUtil.stringFilter("中国~~!#$%%."));
		// System.out.println("是否包含中英文特殊字符: "+RegexUtil.isContainsSpecialChar("12"));
		// System.out.println("过滤html代码: "+RegexUtil.htmltoText("<JAVASCRIPT>12</JAVASCRIPT>DDDDD"));
		// System.out.println("判断中文字符: "+RegexUtil.isChineseChar("中国！"));
//		System.out.println("匹配汉字: " + RegexHelper.isChinese("中国！"));
		// System.out.println("判断英文字符: "+RegexUtil.isEnglish("abc!"));
		// System.out.println("判断合法字符: "+RegexUtil.isRightfulString("abc_-11AAA"));
		// System.out.println("邮政编码验证: "+RegexUtil.isZipCode("162406"));
		// System.out.println("身份证号码验证: "+RegexUtil.isIdCardNo("35052419880210133e"));
		// System.out.println("手机号码验证: "+RegexUtil.isMobile("18918611111"));
		// System.out.println("电话号码验证: "+RegexUtil.isPhone("8889333"));
		// System.out.println("电话号码验证: "+RegexUtil.isNumeric("888.9333"));
		// System.out.println("匹配密码: "+RegexUtil.isPwd("d888d_ddddd"));
		// System.out.println("匹配密码: "+RegexUtil.isUrl("http://baidu.com"));
//		System.out.println("验证字符: " + RegexHelper.stringCheck("中文aabc001_-"));
		// System.out.println(isEmail("416501600@qq.com"));
		// http://baidu.com www.baidu.com baidu.com
		// System.out.println(NumberUtils.toInt("-0000000002"));
//	}
}
