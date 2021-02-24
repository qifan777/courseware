package com.jarcheng.mbg.common;


import lombok.Getter;

@Getter
public enum QueueEnum {
    /**
     * 消息通知队列
     */
    QUEUE_ORDER_CANCEL("canteen.order.direct", "canteen.order.cancel", "canteen.order.cancel"),
    QUEUE_ORDER_RECORD("canteen.order.direct", "canteen.order.record", "canteen.order.record"),

    /**
     * 消息通知ttl队列
     */
    QUEUE_TTL_ORDER_CANCEL("canteen.order.direct", "canteen.order.cancel.ttl", "canteen.order.cancel.ttl"),
    QUEUE_TTL_ORDER_RECORD("canteen.order.direct", "canteen.order.record.ttl", "canteen.order.record.ttl");


    /**
     * 交换名称
     */
    private String exchange;
    /**
     * 队列名称
     */
    private String name;
    /**
     * 路由键
     */
    private String routeKey;

    QueueEnum(String exchange, String name, String routeKey) {
        this.exchange = exchange;
        this.name = name;
        this.routeKey = routeKey;
    }
}
