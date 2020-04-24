package com.liuhao.cloud.activemq;

import javax.jms.Destination;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * Title: MessageProducer
 * Description: 
 * @Author: liuhao
 * @date: 2019年1月19日 下午10:40:18
 */
@Service
public class MessageProducer {
	
	@Autowired
	JmsMessagingTemplate jmsMessagingTemplate;
	
	public void sendMessage(final User user) {
		Destination destination = new ActiveMQQueue("com.ceair.message");                 
		jmsMessagingTemplate.convertAndSend(destination,user);
	}
	
	@JmsListener(destination = "com.ceair.back")
	public void back(String text) {
		System.out.println(text);
	}
}
