package com.liuhao.cloud.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Title: SimpleDateFormatTest
 * Description: 日期字符串工具类
 * @Author: liuhao
 * @date: 2019年3月22日 下午3:27:17
 */
public class SimpleDateFormatUtil {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SimpleDateFormat.class);
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private static final DateTimeFormatter datef = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	
	public static String formatDate(Date date) throws ParseException {
		synchronized (sdf) {
			return sdf.format(date);
		}
	}
	
	public static Date parse(String date) throws ParseException {
		synchronized (sdf) {
			return sdf.parse(date);
		}
	}
	
	public static String formatLocaleDate(LocalDateTime date) throws ParseException {
		return datef.format(date);
	}
	
	public static LocalDate parseLocaleDate(String date) throws ParseException {
		return LocalDate.parse(date, datef);
	}
	
	public static void main(String args[]) throws ParseException,InterruptedException {
		ExecutorService service = Executors.newFixedThreadPool(20);
		for (int i=0;i<10;i++) {
			service.execute(() -> {
				for (int j = 0; j < 10; j++) {
					try {
						System.out.println(parse("2019-03-22 15:42:00"));
					} catch (ParseException e) {
						LOGGER.info(e.getMessage());
					}
				}
			});
		}
		service.shutdown();
		service.awaitTermination(1, TimeUnit.DAYS);
	}
}
