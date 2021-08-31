package org.ilmostro.websocket.handler;

public interface DistributedMessageHandler {

    /**
     * 接收远程的消息
     *
     * @param channelId 操作的channelId
     * @param message 消息
     */
    void onMessage(String channelId, Object message);
}
