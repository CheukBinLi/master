package project.master.fw.sh.common;

import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/***
 * *
 * 
 * Copyright 2016 CHEUK.BIN.LI Individual All
 * 
 * ALL RIGHT RESERVED
 * 
 * CREATE ON 2016年5月16日
 * 
 * EMAIL:20796698@QQ.COM
 * 
 * 
 * @author CHEUK.BIN.LI
 * 
 * @see 对象转换
 *
 */
public final class JsonObjectFactory {

	private int max = 15;

	private transient BlockingDeque<Gson> GSONS = new LinkedBlockingDeque<Gson>(max);

	private transient BlockingDeque<Gson> GSONS_NULL = new LinkedBlockingDeque<Gson>(max);

	private static JsonObjectFactory newInstance = new JsonObjectFactory();

	private ReentrantLock lock = new ReentrantLock();

	public static JsonObjectFactory newInstance() {
		return newInstance;
	}

	public <T> T toObject(String json, Class<T> t, boolean serializeNulls) {
		BlockingDeque<Gson> temp = serializeNulls ? GSONS_NULL : GSONS;
		Gson g = temp.pollFirst();
		try {
			return g.fromJson(json, t);
		} finally {
			temp.offerLast(g);
		}
	}

	public <T> List<T> toListObject(String json, boolean serializeNulls) {
		BlockingDeque<Gson> temp = serializeNulls ? GSONS_NULL : GSONS;
		Gson g = temp.pollFirst();
		try {
			return g.fromJson(json, new TypeToken<List<T>>() {
			}.getType());
		} finally {
			temp.offerLast(g);
		}
	}

	public String toJson(Object o, boolean serializeNulls) {
		BlockingDeque<Gson> temp = serializeNulls ? GSONS_NULL : GSONS;
		Gson g = temp.pollFirst();
		try {
			return g.toJson(o);
		} finally {
			temp.offerLast(g);
		}
	}

	private void init() {
		for (int i = 0; i < max; i++) {
			GSONS.offerLast(new Gson());
			GSONS_NULL.offerLast(new GsonBuilder().serializeNulls().create());

		}
	}

	public void setPoolMax(int max) throws InterruptedException {
		if (lock.tryLock(1000, TimeUnit.SECONDS)) {
			GSONS = new LinkedBlockingDeque<Gson>((this.max = max));
			GSONS_NULL = new LinkedBlockingDeque<Gson>(max);
			init();
		}
	}

	public JsonObjectFactory() {
		super();
		init();
	}

	public static void main(String[] args) {
		new JsonObjectFactory();
	}

}
