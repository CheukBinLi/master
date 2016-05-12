package project.master.fw.sh.common;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChuangLanSmsFactory extends AbstraceSmsFactory {

	private String account;
	private String passwrod;
	private String url;

	@Override
	void send(String phone, String msg) throws Exception {
		URL u = new URL(String.format("%s/msg/HttpBatchSendSM", url));
		String params = String.format("needstatus=false&account=%s&pswd=%s&mobile=%s&msg=%s", account, passwrod, phone, msg);
		// http://222.73.117.156/msg/HttpBatchSendSM?account=gz_xhkj&pswd=Xhkj168888&mobile=13829386497&msg=您的注册验证码是：1131.请完成注册&needstatus=true
		HttpURLConnection con = null;
		OutputStreamWriter out = null;
		BufferedReader br = null;
		String line;
		try {
			con = (HttpURLConnection) u.openConnection();
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setConnectTimeout(5000);
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			con.connect();
			out = new OutputStreamWriter(con.getOutputStream(), "utf-8");
			out.write(params);
			out.flush();
			// InputStream inputStream = con.getInputStream();
			br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
			while (null != (line = br.readLine())) {
				break;
			}
		} finally {
			if (null != out)
				out.close();
			if (null != br)
				br.close();
			if (null != con)
				con.disconnect();
		}
		isOK(line.replaceAll("/n|/r", ""));
	}

	boolean isOK(String msg) throws Exception {
		Pattern pattern = Pattern.compile("^.*[/,](.*)$");
		Matcher matcher = pattern.matcher(msg);
		Integer code = -1;
		while (matcher.find()) {
			code = Integer.valueOf(matcher.group(1).toString());
			//System.out.println(matcher.group(1).toString());
		}
		switch (code) {
		case 0:
			return true;
		case -1:
			throw new Exception("末知错误");
		case 101:
			throw new Exception("101:无此用户");
		case 102:
			throw new Exception("102:密码错");
		case 103:
			throw new Exception("103:提交过快（提交速度超过流速限制）");
		case 104:
			throw new Exception("104:系统忙（因平台侧原因，暂时无法处理提交的短信）");
		case 105:
			throw new Exception("105:敏感短信（短信内容包含敏感词）");
		case 106:
			throw new Exception("106:消息长度错（>536或<=0）");
		case 107:
			throw new Exception("107:包含错误的手机号码");
		case 108:
			throw new Exception("108:手机号码个数错（群发>50000或<=0;单发>200或<=0）");
		case 109:
			throw new Exception("109:无发送额度（该用户可用短信数已使用完）");
		case 110:
			throw new Exception("110:不在发送时间内");
		case 111:
			throw new Exception("111:超出该账户当月发送额度限制");
		case 112:
			throw new Exception("112:无此产品，用户没有订购该产品");
		case 113:
			throw new Exception("113:extno格式错（非数字或者长度不对）");
		case 115:
			throw new Exception("115:自动审核驳回");
		case 116:
			throw new Exception("116:签名不合法，未带签名（用户必须带签名的前提下）");
		case 117:
			throw new Exception("117:IP地址认证错,请求调用的IP地址不是系统登记的IP地址");
		case 118:
			throw new Exception("118:用户没有相应的发送权限");
		case 119:
			throw new Exception("119:用户已过期");
		case 120:
			throw new Exception("120:测试内容不是白名单");
		default:
			break;
		}

		return false;
	}

	public String getAccount() {
		return account;
	}

	public ChuangLanSmsFactory setAccount(String account) {
		this.account = account;
		return this;
	}

	public String getPasswrod() {
		return passwrod;
	}

	public ChuangLanSmsFactory setPasswrod(String passwrod) {
		this.passwrod = passwrod;
		return this;
	}

	public String getUrl() {
		return url;
	}

	public ChuangLanSmsFactory setUrl(String url) {
		this.url = url;
		return this;
	}

	public static void main(String[] args) {

		String a = "123123123123,123/n123/r".replaceAll("/n|/r", "");
		Pattern pattern = Pattern.compile("^.*[/,](.*)$");
		Matcher matcher = pattern.matcher(a);
		while (matcher.find()) {
			System.out.println(matcher.group(1).toString());
		}

	}

}
