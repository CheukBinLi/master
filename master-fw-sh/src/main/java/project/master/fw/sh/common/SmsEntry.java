package project.master.fw.sh.common;

public class SmsEntry {
	private String code;
	private long overTime;

	public String getCode() {
		return code;
	}

	public SmsEntry setCode(String code) {
		this.code = code;
		return this;
	}

	public long getOverTime() {
		return overTime;
	}

	public SmsEntry setOverTime(long overTime) {
		this.overTime = overTime;
		return this;
	}

	/***
	 * 
	 * @param code
	 * @param timeOut
	 *            秒
	 */
	public SmsEntry(String code, int timeOutSceond) {
		super();
		this.code = code;
		overTime = System.currentTimeMillis() + (timeOutSceond * 1000);
	}

	public boolean compareTo(long l) {
		return overTime <= l;
	}
}