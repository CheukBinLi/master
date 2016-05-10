package project.master.fw.sh.common;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.ReentrantLock;

public abstract class AbstraceSmsFactory implements SmsFactory {

	protected ExecutorService executorService;

	protected final Object[] code = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 0 };

	protected final Random r = new Random();

	protected final StringBuffer randomStr = new StringBuffer();

	protected final Map<String, SmsEntry> sms = new ConcurrentHashMap<String, SmsEntry>();

	private int randomCodeMaxSize = 30;
	private int randomCodeLength = 6;
	private int randomCodeOftenSize = 5;// 长驻容量
	private int timeOutSceond = 5;// 短信过期时间
	protected ReentrantLock lock = new ReentrantLock();

	protected final BlockingDeque<String> randomCode = new LinkedBlockingDeque<String>(randomCodeMaxSize);

	protected volatile boolean interrupt = true;

	/***
	 * 发送实现
	 * 
	 * @param phone
	 * @param msg
	 * @throws Exception
	 */
	abstract void send(String phone, String msg) throws Exception;

	/*
	 * (non-Javadoc)
	 * 
	 * @see project.master.fw.sh.common.SmsService#sendCode(java.lang.String, java.lang.String, java.lang.String)
	 */
	public synchronized String sendCode(String phone, String msg, String codeTag) throws Exception {
		String code = randomCode.pollFirst();
		send(phone, msg.replace(codeTag, code));
		sms.put(phone, new SmsEntry(code, timeOutSceond));
		return code;
	}

	public SmsEntry getCode(String phone) {
		return sms.get(phone);
	}

	public SmsEntry getAndRemoveCode(String phone) {
		return sms.remove(phone);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see project.master.fw.sh.common.SmsService#start()
	 */
	public SmsFactory start() {
		if (interrupt) {
			executorService = Executors.newFixedThreadPool(5);
			interrupt = false;
			executorService.execute(new randomCodeService());
			executorService.execute(new timeOutCheck());
		}
		return this;
	}

	public SmsFactory interrupt() {
		if (!interrupt) {
			executorService.shutdownNow();
			interrupt = true;
		}
		return this;
	}

	protected void createRandom(int len) {
		try {
			if (lock.tryLock()) {
				int x;
				for (int i = 0; i < len; i++) {
					x = r.nextInt(code.length);
					randomStr.append(code[x]);
				}
				randomCode.offerLast(randomStr.toString());
				randomStr.setLength(0);
			}
		} finally {
			lock.unlock();
		}
	}

	protected String popRandomCode() {
		if (sms.size() < randomCodeOftenSize)
			synchronized (sms) {
				sms.notify();
			}
		return randomCode.pollFirst();
	}

	class randomCodeService implements Runnable {
		public void run() {
			try {
				while (!interrupt) {
					createRandom(randomCodeLength);
					synchronized (randomCode) {
						if (randomCode.size() == randomCodeMaxSize)
							randomCode.wait();
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	class timeOutCheck implements Runnable {
		public void run() {
			Iterator<Entry<String, SmsEntry>> it;
			Entry<String, SmsEntry> en;
			long now = System.currentTimeMillis();
			try {
				while (!interrupt) {
					it = sms.entrySet().iterator();
					while (it.hasNext()) {
						en = it.next();
						if (en.getValue().compareTo(now))
							sms.remove(en.getKey());
					}
					Thread.sleep(120000);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public int getRandomCodeMaxSize() {
		return randomCodeMaxSize;
	}

	public SmsFactory setRandomCodeMaxSize(int randomCodeMaxSize) {
		this.randomCodeMaxSize = randomCodeMaxSize;
		return this;
	}

	public int getRandomCodeLength() {
		return randomCodeLength;
	}

	public SmsFactory setRandomCodeLength(int randomCodeLength) {
		this.randomCodeLength = randomCodeLength;
		return this;
	}

	public int getRandomCodeOftenSize() {
		return randomCodeOftenSize;
	}

	public SmsFactory setRandomCodeOftenSize(int randomCodeOftenSize) {
		this.randomCodeOftenSize = randomCodeOftenSize;
		return this;
	}

	public int getTimeOutSceond() {
		return timeOutSceond;
	}

	public AbstraceSmsFactory setTimeOutSceond(int timeOutSceond) {
		this.timeOutSceond = timeOutSceond;
		return this;
	}

	public AbstraceSmsFactory() {
		super();
		this.start();
	}

}
