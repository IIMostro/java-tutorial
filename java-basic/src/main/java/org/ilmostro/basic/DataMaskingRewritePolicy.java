package org.ilmostro.basic;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.rewrite.RewritePolicy;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.impl.Log4jLogEvent;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.ParameterizedMessage;
import org.apache.logging.log4j.message.SimpleMessage;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Plugin(name = "DataMaskingRewritePolicy", category = "Core",
        elementType = "rewritePolicy", printObject = true)
@Slf4j
public final class DataMaskingRewritePolicy implements RewritePolicy {

    private static final String OVERLAY = "***********";
    private static final int START_EXCLUDE = 3;
    private static final int END_EXCLUDE = 16;
    private static final int BAND_ACCOUNT_LEN = 19;

    private final static String ASTERISK = "*****";

    /*
     * 引号
     */
    private static final String QUOTATION_MARK = "\"";

    //银行卡正则匹配
    private final static Pattern BANK_CARD_PATTERN = Pattern.compile("(\\D)([3-6]\\d{3})(\\d{8,12})(\\d{4})(\\D)", Pattern.CASE_INSENSITIVE);

    //手机号正则匹配
    private final static Pattern PHONE_PATTERN = Pattern.compile("(?<!\\d)(1\\d{10})(?!\\d)", Pattern.CASE_INSENSITIVE);

    //身份证正则匹配
    private final static Pattern ID_CARD_PATTERN = Pattern.compile("([1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[\\dXx])|([1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3})", Pattern.CASE_INSENSITIVE);

    //姓名正则匹配
    //姓名、realName 暂时不加密
    private final static Pattern NAME_PATTERN = Pattern.compile("(phone|telephone|password|signature|usename|userName|receiveName)(=|=\\[|\\\":\\\"|:|：|=')([\\u4e00-\\u9fa5]{1})([\\u4e00-\\u9fa5]{1,3})(\\]|\\\"|'|)", Pattern.CASE_INSENSITIVE);

    //邮箱
    private final static Pattern EMAIL_PATTERN = Pattern.compile("(\\w{1})(\\w*)(\\w{1})@(\\w+)(.com)", Pattern.CASE_INSENSITIVE);

    //其他
    //分为四个捕获组，每个捕获组从左往右开始匹配，所以尽可能在最左边
    //1、匹配:username、secret、phone、telephone、password、密码、验证码、地址、address、app_key、appkey、appSecret、app_secret、signature
    //2、匹配中间的分隔字符，因为是从左往右匹配，尽可能复杂的在前："="、'='、='、="、=[、=、":"、":["、':'、:[、:'、:
    //3、也就是需加密的内容
    //4、结束字符：]、"、'、,、}
//    private final static Pattern OTHER_PATTERN = Pattern.compile("(ak|sk|token|sixerToken|ip|gwIp|gw_ip|username|secret|phone|telephone|password|密码|验证码|地址|address|app_key|appkey|appSecret|app_secret|signature|productSecret)(\\\"=\\\"|'='|='|=\\\"|=\\[|=|\\\":\\[\\\"|\\\":\\\"|':'|:\\[|:'|:)(.*?)(\\]|\\\"|'|\\,|\\})", Pattern.CASE_INSENSITIVE);
//
//    private final static List<String> sensitiveFields = Arrays.asList("ak","sk","password", "phone", "telephone", "username", "token", "sixerToken", "ip", "gwIp", "gw_ip", "productSecret");

    private final static Pattern OTHER_PATTERN = Pattern.compile("(&cloudToken|uid|userId|token|sixerToken|ip|gwIp|gw_ip|username|secret|phone|telephone|password|密码|验证码|地址|address|app_key|appkey|appSecret|app_secret|signature|productSecret|sig)(\\\"=\\\"|'='|='|=\\\"|=\\[|=|\\\":\\[\\\"|\\\":\\\"|':'|:\\[|:'|:)(.*?)(\\]|\\\"|'|\\,|\\}|&|$)", Pattern.CASE_INSENSITIVE);

    private final static List<String> sensitiveFields = Arrays.asList("entryPwd", "pass_word", "parameter", "password", "old_productKey", "new_pk_secert", "phone", "telephone", "username", "pass_id", "sign", "signature", "token", "newPk", "cloudToken", "uid", "userId", "sixerToken", "ip", "gwIp", "gw_ip", "productSecret", "sig");


    @Override
    public LogEvent rewrite(LogEvent logEvent) {
        if (!(logEvent instanceof Log4jLogEvent)) {
            return logEvent;
        }
        Log4jLogEvent log4jLogEvent = (Log4jLogEvent) logEvent;

        Message message = log4jLogEvent.getMessage();

        if (message instanceof SimpleMessage) {
            // 简单消息脱敏
            SimpleMessage newMessage = decryptionSimpleMessage((SimpleMessage) message);
            Log4jLogEvent.Builder builder = log4jLogEvent.asBuilder().setMessage(newMessage);
            return builder.build();

        } else if (!(message instanceof ParameterizedMessage)) {
            return logEvent;
        }
        ParameterizedMessage parameterizedMessage = (ParameterizedMessage) message;

        Object[] params = parameterizedMessage.getParameters();
        if (params == null || params.length <= 0) {
            return logEvent;
        }

        Object[] newParams = new Object[params.length];
        for (int i = 0; i < params.length; i++) {
            Object object = params[i];
            if (object instanceof String) {
                String obj = (String) object;
                if (StringUtils.isNumeric(obj) && obj.length() == BAND_ACCOUNT_LEN) {
                    newParams[i] = StringUtils.overlay(obj, OVERLAY, START_EXCLUDE, END_EXCLUDE);
                    continue;
                } else {
                    obj = convert(obj, parameterizedMessage.getFormat());
                }
                newParams[i] = obj;
            } else {
                if (!Objects.isNull(object)) {
                    // 获取对象的包地址
                    String packageAddress = object.getClass().getPackage().getName();
                    if (packageAddress.contains("com.closeli") || packageAddress.contains("java.util") || packageAddress.contains("okhttp")) {
                        String obj = JSONObject.toJSONString(object);
                        obj = desensitizeJson(obj);
                        newParams[i] = obj;
                    } else {
                        newParams[i] = object;
                    }
                } else {
                    newParams[i] = object;
                }
            }
        }
        ParameterizedMessage m = new ParameterizedMessage(parameterizedMessage.getFormat(), newParams, parameterizedMessage.getThrowable());
        Log4jLogEvent.Builder builder = log4jLogEvent.asBuilder().setMessage(m);
        return builder.build();
    }

    @PluginFactory
    public static DataMaskingRewritePolicy createPolicy() {
        return new DataMaskingRewritePolicy();
    }

    public String convert(String logMsg, String format) {
        try {
            final Set<String> list;
            list = validDate(logMsg);
            if (!CollectionUtil.isEmpty(list)) {
                for (String param : list) {
                    String convertMsg = logMsg;
                    logMsg = convertDate(convertMsg, param);
                }
            } else {
                if (isSensitiveField(format)) {
                    logMsg = ASTERISK;
                }
            }
            return logMsg;
        } catch (Exception e) {
            log.error("error={}", e);
        }
        return logMsg;
    }

    /**
     * 正则匹配是否包含脱敏数据
     */
    private static Set<String> validDate(String param) {
        Set<String> set = new HashSet<>();
        // 匹配手机号
        Matcher phoneMatcher = PHONE_PATTERN.matcher(param);
        while (phoneMatcher.find()) {
            set.add(phoneMatcher.group());
        }
        // 匹配身份证
        Matcher idCardMatcher = ID_CARD_PATTERN.matcher(param);
        while (idCardMatcher.find()) {
            set.add(idCardMatcher.group());
        }

        Matcher bankCardMatcher = BANK_CARD_PATTERN.matcher(param);
        while (bankCardMatcher.find()) {
            set.add(bankCardMatcher.group());
        }

        Matcher namePatternMatcher = NAME_PATTERN.matcher(param);
        while (namePatternMatcher.find()) {
            set.add(namePatternMatcher.group());
        }

        Matcher emailPatternMatcher = EMAIL_PATTERN.matcher(param);
        while (emailPatternMatcher.find()) {
            set.add(emailPatternMatcher.group());
        }

        Matcher otherPatternMatcher = OTHER_PATTERN.matcher(param);
        while (otherPatternMatcher.find()) {
            set.add(otherPatternMatcher.group());
        }
        return set;
    }

    /**
     * 数据脱敏
     */
    private static String convertDate(String logMsg, String param) {
        if (PHONE_PATTERN.matcher(param).find()) {
            return logMsg.replace(param, param.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));
        } else if (ID_CARD_PATTERN.matcher(param).find()) {
            String replaceContext = param.replaceAll(param, StringUtils.left(param, 6).concat(StringUtils.removeStart(StringUtils.leftPad(StringUtils.right(param, 4), StringUtils.length(param), "*"), "******")));
            return logMsg.replace(param, replaceContext);
        } else if (BANK_CARD_PATTERN.matcher(param).find()) {
            String replaceContext = BANK_CARD_PATTERN.matcher(param).replaceAll("$1$2********$4$5");
            return logMsg.replace(param, replaceContext);
        } else if (NAME_PATTERN.matcher(param).find()) {
            String replaceContext = NAME_PATTERN.matcher(param).replaceAll("$1$2$3**$5");
            return logMsg.replace(param, replaceContext);
        } else if (EMAIL_PATTERN.matcher(param).find()) {
            String replaceContext = EMAIL_PATTERN.matcher(param).replaceAll("$1****$3@$4.com");
            return logMsg.replace(param, replaceContext);
        } else if (OTHER_PATTERN.matcher(param).find()) {
            String replaceMent = "$1$2" + ASTERISK + "$4";
            String replaceContext = OTHER_PATTERN.matcher(param).replaceAll(replaceMent);
            return logMsg.replace(param, replaceContext);
        }
        return logMsg;
    }

    private boolean isSensitiveField(String format) {
        // 在这里根据实际情况判断是否需要脱敏
        if (format != null) {
            Boolean matchAnySensitiveField = false;
            for (String sensitiveField : sensitiveFields) {
                if (format.contains(sensitiveField)) {
                    matchAnySensitiveField = true;
                    break;
                }
            }
            return matchAnySensitiveField;
        }
        return false;
    }

    private boolean isJsonContainsSensitiveField(String jsonString) {
        try {
            JSONObject jsonObject = JSON.parseObject(jsonString);
            Boolean matchAnySensitiveField = false;
            for (String sensitiveField : sensitiveFields) {
                if (jsonObject.containsKey(sensitiveField)) {
                    matchAnySensitiveField = true;
                    break;
                }
            }
            return matchAnySensitiveField;
        } catch (Exception e) {
            return false;
        }
    }

    private String desensitizeJson(String jsonString) {
        try {
            JSONObject jsonObject = JSON.parseObject(jsonString);
            for (String sensitiveField : sensitiveFields) {
                if (jsonObject.containsKey(sensitiveField)) {
                    jsonObject.put(sensitiveField, ASTERISK);
                    continue;
                }
            }
            return jsonObject.toString();
        } catch (Exception e) {
            return jsonString;
        }
    }

    private SimpleMessage decryptionSimpleMessage(SimpleMessage simpleMessage) {
        String simpleMessageString = simpleMessage.toString();
        String newMessage = simpleMessageString;

        if (!StringUtils.isEmpty(simpleMessageString)) {

            boolean isContain = sensitiveFields.stream().anyMatch(key -> StringUtils.contains(simpleMessageString, key));

            // 包含敏感词
            if (isContain) {

                for (String key : sensitiveFields) {
                    int keyLength = key.length();
                    // 敏感词
                    String targetStr = new String("<" + key + ">");
                    StringBuffer targetSb = new StringBuffer(targetStr);
                    int startIndex = newMessage.indexOf(targetStr);

                    /*
                     * 如<password>123456</password>替换为 <password>****</password>
                     */
                    if (startIndex > -1 && newMessage.indexOf(targetSb.append(ASTERISK).append("</").append(key).append(">").toString()) == -1) {
                        int endIndex = newMessage.indexOf(targetStr);
                        if (endIndex > -1) {
                            newMessage = newMessage.substring(0, startIndex + keyLength + 2) + ASTERISK
                                    + newMessage.substring(endIndex, newMessage.length());
                        }
                    }

                    /*
                     * 如password:123456替换为password:****
                     */
                    targetStr = key + ":";
                    if (newMessage.indexOf(targetStr) > -1 && newMessage.indexOf(targetStr + ASTERISK) == -1) {

                        startIndex = newMessage.indexOf(targetStr) + keyLength + 1;
                        String endMessage = newMessage.substring(startIndex, newMessage.length());
                        int endIndex = endMessage.indexOf(",");
                        if (endIndex > -1) {
                            newMessage = newMessage.substring(0, startIndex) + ASTERISK
                                    + endMessage.substring(endIndex, endMessage.length());
                        } else if (endMessage.indexOf(",") == -1 && endMessage.indexOf("=") == -1) {
                            newMessage = newMessage.substring(0, startIndex) + ASTERISK;
                        }
                    }

                    /*
                     * 如password=123456替换为password=****
                     */
                    if (newMessage.indexOf(key + "=") > -1 && newMessage.indexOf(key + "=" + ASTERISK) == -1) {

                        startIndex = newMessage.indexOf(key + "=") + keyLength + 1;
                        String endMessage = newMessage.substring(startIndex, newMessage.length());
                        int endIndex = endMessage.indexOf("&");

                        if (endIndex > -1) {
                            newMessage = newMessage.substring(0, startIndex) + ASTERISK
                                    + endMessage.substring(endIndex, endMessage.length());
                        } else if (endMessage.indexOf("&") == -1 && endMessage.indexOf("=") == -1) {
                            newMessage = newMessage.substring(0, startIndex) + ASTERISK;
                        }
                    }

                    /*
                     * 如"password":"123456" 替换为"password":"****"
                     */
                    String qmKey = QUOTATION_MARK + key + QUOTATION_MARK + ":";
                    if (newMessage.indexOf(qmKey) > -1 && newMessage.indexOf(qmKey + ASTERISK) == -1) {

                        startIndex = newMessage.indexOf(qmKey) + keyLength + 3;
                        String endMessage = newMessage.substring(startIndex, newMessage.length());
                        int endIndex = endMessage.indexOf(",");
                        if (endIndex > -1) {
                            newMessage = newMessage.substring(0, startIndex) + QUOTATION_MARK + ASTERISK
                                    + QUOTATION_MARK + endMessage.substring(endIndex, endMessage.length());
                        } else if (endMessage.indexOf(",") == -1 && endMessage.indexOf("=") == -1) {
                            newMessage = newMessage.substring(0, startIndex) + QUOTATION_MARK + ASTERISK
                                    + QUOTATION_MARK;
                        }
                    }

                }
                return new SimpleMessage(newMessage);
            }

        }

        return simpleMessage;
    }
}
