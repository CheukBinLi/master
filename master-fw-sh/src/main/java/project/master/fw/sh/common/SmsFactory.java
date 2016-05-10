package project.master.fw.sh.common;

public interface SmsFactory {

	String sendCode(String phone, String msg, String codeTag) throws Exception;

	SmsEntry getCode(String phone);

	SmsEntry getAndRemoveCode(String phone);

	SmsFactory start();

	SmsFactory interrupt();

}
