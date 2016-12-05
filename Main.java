package me.hupeng.os.demo1;

import java.util.Random;

public class Main {
	
	public static Integer S1, S2,s1,s2;
	static {
		S1 = 1;
		S2 = 2;
		s1 = 1;
		s2 = 1;
	}

	public static void main(String[] args) {
		// 开始执行并发操作
		System.out.println("开始执行。。。");
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				P1();
			}
		}).start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				P2();
			}
		}).start();;
		new Thread(new Runnable() {
			@Override
			public void run() {
				P3();
			}
		}).start();
		
		//System.out.println("。。。执行结束");
	}

	public static void P1() {
		
		
		wait(S2);
		wait(S1);

		// 主要的业务逻辑代码
		System.out.println("进程P1正在执行。。。");
		long beginTime = System.currentTimeMillis();
		Integer integer = new Random().nextInt(1000);
		try {
			Thread.sleep(integer);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		long endTime = System.currentTimeMillis();
		System.out.println("。。。进程P1执行结束,进程执行时间：" + (endTime - beginTime) +"ms");

		signal(S2);
		signal(S1);
	}

	public static void P2() {
		
		
		wait(S2);
		wait(S1);

		// 主要的业务逻辑代码
		System.out.println("进程P2正在执行。。。");
		long beginTime = System.currentTimeMillis();
		Integer integer = new Random().nextInt(1000);
		try {
			Thread.sleep(integer);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		long endTime = System.currentTimeMillis();
		System.out.println("。。。进程P2执行结束,进程执行时间：" + (endTime - beginTime)+ "ms");

		signal(S2);
		signal(S1);
		
		
	}

	public static void P3() {
		
		
		
		wait(S2);

		// 主要的业务逻辑代码
		System.out.println("进程P3正在执行。。。");
		long beginTime = System.currentTimeMillis();
		Integer integer = new Random().nextInt(1000);
		try {
			Thread.sleep(integer);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		long endTime = System.currentTimeMillis();
		System.out.println("。。。进程P3执行结束,进程执行时间：" + (endTime - beginTime) + "ms");

		signal(S2);
		
		
	}

	/**
	 * 原子操作，不可分割，使用synchronized保证
	 * */
	public static void signal(Integer arg) {
		synchronized (new Object()) {
			//控制台输出
			System.out.println("正在signal操作,signal:s" + arg + "。。。S1:" + s1 + ",S2:" + s2);
			
			if (arg == 1) {
				s1 ++;
			}else {
				if (arg == 2) {
					s2 ++;
				}
			}
			
			//控制台输出
			System.out.println("signal操作完成,signal:s" + arg + "。。。S1:" + s1 + ",S2:" + s2 );
		}
		
		
	}

	/**
	 * 原子操作，不可分割
	 * */
	public static void wait(Integer arg) {
		synchronized (new Object()) {
			//控制台输出
			System.out.println("正在wait操作,wait:s" + arg + "。。。S1:" + s1 + ",S2:" + s2);
			
			while ((arg==1?s1:s2 ) == 0) {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (arg == 1) {
				s1 --;
			}else {
				if (arg == 2) {
					s2 --;
				}
			}
			//控制台输出
			System.out.println("wait操作完成,wait:s" + arg + "完成。。。S1:" + s1 + ",S2:" + s2 );
		}
		
	}

}