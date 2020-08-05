package com.cottonclub.interfaces;

import com.cottonclub.models.OrderItem;

public interface OrderListener
{
	public void sendOrder(OrderItem orderItem);
}
