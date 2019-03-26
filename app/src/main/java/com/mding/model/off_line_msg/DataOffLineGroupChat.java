package com.mding.model.off_line_msg;

import java.util.List;

public class DataOffLineGroupChat {


    /**
     * code : 200
     * msg : 成功
     * method : messageGroupPush
     * record : {"messageList":[{"groupId":"3","message":"123","messageStoId":13,"messageType":"1","memberId":"cb16ce45-854c-f553-9c9a-2e57a6addca3","requestTime":"123","groupName":"123","groupHeadImg":"123","memberName":"123","memberHeadImg":"123"}]}
     * api_key : 20180903
     * sign : 456F3B742124A3741AA83CF9EA9CA58B
     * timestamp : 1542768063
     */

    private int code;
    private String msg;
    private String method;
    private RecordBean record;
    private String api_key;
    private String sign;
    private int timestamp;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public RecordBean getRecord() {
        return record;
    }

    public void setRecord(RecordBean record) {
        this.record = record;
    }

    public String getApi_key() {
        return api_key;
    }

    public void setApi_key(String api_key) {
        this.api_key = api_key;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public static class RecordBean {
        private List<MessageListBean> messageList;

        public List<MessageListBean> getMessageList() {
            return messageList;
        }

        public void setMessageList(List<MessageListBean> messageList) {
            this.messageList = messageList;
        }

        public static class MessageListBean {
            /**
             * groupId : 3
             * message : 123
             * messageStoId : 13
             * messageType : 1
             * memberId : cb16ce45-854c-f553-9c9a-2e57a6addca3
             * requestTime : 123
             * groupName : 123
             * groupHeadImg : 123
             * memberName : 123
             * memberHeadImg : 123
             */

            private String groupId;
            private String message;
            private int messageStoId;
            private String messageType;
            private String memberId;
            private String requestTime;
            private String groupName;
            private String groupHeadImg;
            private String memberName;
            private String memberHeadImg;


            //        加入群助手1否 2是
            private String assistantType;

            private String bannedType;//是否被禁言
            private String disturbType;//消息免打扰
            private String operationType;//屏蔽该群
            private String topType;//置顶状态

            public String getAssistantType() {
                return assistantType;
            }

            public void setAssistantType(String assistantType) {
                this.assistantType = assistantType;
            }

            public String getBannedType() {
                return bannedType;
            }

            public void setBannedType(String bannedType) {
                this.bannedType = bannedType;
            }

            public String getDisturbType() {
                return disturbType;
            }

            public void setDisturbType(String disturbType) {
                this.disturbType = disturbType;
            }

            public String getOperationType() {
                return operationType;
            }

            public void setOperationType(String operationType) {
                this.operationType = operationType;
            }

            public String getTopType() {
                return topType;
            }

            public void setTopType(String topType) {
                this.topType = topType;
            }

            public String getGroupId() {
                return groupId;
            }

            public void setGroupId(String groupId) {
                this.groupId = groupId;
            }

            public String getMessage() {
                return message;
            }

            public void setMessage(String message) {
                this.message = message;
            }

            public int getMessageStoId() {
                return messageStoId;
            }

            public void setMessageStoId(int messageStoId) {
                this.messageStoId = messageStoId;
            }

            public String getMessageType() {
                return messageType;
            }

            public void setMessageType(String messageType) {
                this.messageType = messageType;
            }

            public String getMemberId() {
                return memberId;
            }

            public void setMemberId(String memberId) {
                this.memberId = memberId;
            }

            public String getRequestTime() {
                return requestTime;
            }

            public void setRequestTime(String requestTime) {
                this.requestTime = requestTime;
            }

            public String getGroupName() {
                return groupName;
            }

            public void setGroupName(String groupName) {
                this.groupName = groupName;
            }

            public String getGroupHeadImg() {
                return groupHeadImg;
            }

            public void setGroupHeadImg(String groupHeadImg) {
                this.groupHeadImg = groupHeadImg;
            }

            public String getMemberName() {
                return memberName;
            }

            public void setMemberName(String memberName) {
                this.memberName = memberName;
            }

            public String getMemberHeadImg() {
                return memberHeadImg;
            }

            public void setMemberHeadImg(String memberHeadImg) {
                this.memberHeadImg = memberHeadImg;
            }
        }
    }
}