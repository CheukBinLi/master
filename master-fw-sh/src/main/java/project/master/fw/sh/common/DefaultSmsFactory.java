package project.master.fw.sh.common;

public class DefaultSmsFactory extends AbstraceSmsFactory {

	@Override
	void send(String phone, String msg) throws Exception {
		System.out.println(String.format("phone:%s sms:%s", phone, msg));
	}

}
