package cn.et.main;

import java.util.Timer;

public class GenernateIndex {
	public static void main(String[] args) {
		//执行一个定时任务
		Timer time = new Timer();
		time.schedule(new MyTimerTask(), 1000,10000);
	}
}
