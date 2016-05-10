package project.master.dbmaamger;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Scan {

	private static final ExecutorService executorService = Executors.newFixedThreadPool(2);

	public static final Set<String> doScan(String path) throws IOException, InterruptedException, ExecutionException {
		Set<String> result = new HashSet<String>();

		Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources("");
		Set<URL> scanResult = new LinkedHashSet<URL>();
		while (urls.hasMoreElements()) {
			scanResult.add(urls.nextElement());
		}
		result.addAll(classMatchFilter(path, scanResult));
		//		}
		try {
			return result;
		} finally {
		}
	}

	protected static final Set<String> classMatchFilter(String path, Set<URL> paths) throws InterruptedException, ExecutionException {
		String[] pathPattern = null;
		path = path.replace("*", ".*").replace("/**", "(/.*)?").replace(File.separator, "/");
		pathPattern = path.split(",");
		for (int i = 0, len = pathPattern.length; i < len; i++) {
			pathPattern[i] = String.format("^(/|.*/|.*)?%s$", pathPattern[i]);
		}

		final int startIndex = (new File(Thread.currentThread().getContextClassLoader().getResource("").getPath())).getPath().replace(File.separator, "/").length() + 1;
		Set<URL> jarClassPaths = new HashSet<URL>();
		Set<URL> fileClassPaths = new HashSet<URL>();
		Set<String> result = new HashSet<String>();
		List<Future<Set<String>>> futures = new ArrayList<Future<Set<String>>>();
		final CountDownLatch countDownLatch = new CountDownLatch(2);
		Iterator<URL> urls = paths.iterator();
		URL u;
		while (urls.hasNext()) {
			u = urls.next();
			if ("jar".equals(u.getProtocol()))
				jarClassPaths.add(u);
			else
				fileClassPaths.add(u);

		}
		futures.add(executorService.submit(new Scan.FileFilter(jarClassPaths, pathPattern, 0, countDownLatch) {
			@Override
			public Set<String> doFilter(Set<URL> url, String[] pathPattern, int startIndex) throws IOException {
				return jarTypeFilter(pathPattern, url);
			}
		}));
		futures.add(executorService.submit(new Scan.FileFilter(fileClassPaths, pathPattern, startIndex, countDownLatch) {
			@Override
			public Set<String> doFilter(Set<URL> url, String[] pathPattern, int startIndex) {
				Iterator<URL> it = url.iterator();
				Set<String> result = new HashSet<String>();
				while (it.hasNext())
					result.addAll(fileTypeFilter(new File(it.next().getPath()), pathPattern, startIndex));
				return result;
			}
		}));
		countDownLatch.await();

		result.addAll(futures.get(0).get());
		result.addAll(futures.get(1).get());

		return result;
	}

	@SuppressWarnings("resource")
	protected static final Set<String> jarTypeFilter(String[] pathPattern, Set<URL> urls) throws IOException {
		Set<String> result = new HashSet<String>();
		Iterator<URL> it = urls.iterator();
		URL u;
		while (it.hasNext()) {
			u = it.next();
			JarFile jarFile = new JarFile(new File(u.getPath().substring(0, u.getPath().lastIndexOf("!")).replaceAll("file:", "")));
			Enumeration<JarEntry> jars = jarFile.entries();
			while (jars.hasMoreElements()) {
				JarEntry jarEntry = jars.nextElement();
				for (int i = 0, len = pathPattern.length; i < len; i++)
					if (jarEntry.getName().matches(pathPattern[i])) {
						result.add(jarEntry.getName());
					}
			}
		}
		return result;
	}

	protected static final Set<String> fileTypeFilter(File file, String[] pathPattern, int startIndex) {
		Set<String> result = new HashSet<String>();
		String tempPath = null;
		if (file.isFile()) {
			tempPath = file.getPath().replace(File.separator, "/");
			for (int i = 0, len = pathPattern.length; i < len; i++)
				if (tempPath.matches(pathPattern[i]))
					result.add(file.getPath().substring(startIndex));
			return result;
		}
		else if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (File f : files) {
				result.addAll(fileTypeFilter(f, pathPattern, startIndex));
			}
		}
		return result;
	}

	static abstract class FileFilter implements Callable<Set<String>> {

		private String[] pathPattern;
		private Set<URL> urls;
		private int startIndex;
		private final CountDownLatch countDownLatch;

		public FileFilter(Set<URL> urls, String[] pathPattern, final CountDownLatch countDownLatch) {
			super();
			this.pathPattern = pathPattern;
			this.urls = urls;
			this.countDownLatch = countDownLatch;
		}

		public FileFilter(Set<URL> urls, String[] pathPattern, int startIndex, final CountDownLatch countDownLatch) {
			super();
			this.pathPattern = pathPattern;
			this.startIndex = startIndex;
			this.urls = urls;
			this.countDownLatch = countDownLatch;
		}

		public abstract Set<String> doFilter(Set<URL> url, String[] pathPattern, int startIndex) throws Exception;

		public Set<String> call() throws Exception {
			Set<String> result = doFilter(urls, pathPattern, startIndex);
			if (null != countDownLatch)
				countDownLatch.countDown();
			return result;
		}

	}

	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
		Object o = doScan("/**/*.query.xml,/**/*.query2.xml");
		System.out.println("X");

	}
}
