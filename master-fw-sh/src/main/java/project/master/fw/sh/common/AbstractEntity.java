package project.master.fw.sh.common;

import java.io.Serializable;

public abstract class AbstractEntity implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public String generatedValue() {
		return Long.toString(GeneratedIDService.newInstance().nextID());
	}

}
