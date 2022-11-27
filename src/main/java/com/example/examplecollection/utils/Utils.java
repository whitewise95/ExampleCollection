package com.example.examplecollection.utils;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.*;

/**
 * Null Check 및 공통적으로 사용할 Util
 */
public class Utils {

    private Utils() {
    }

    //region ==============================isEmpty==============================

    /**
     * 문자열 체크(빈 문자열 존재)
     *
     * @param obj 문자열
     * @return true/false
     */
    public static boolean isEmpty(String obj) {
        return StringUtils.isEmpty(obj);
    }

    /**
     * Collection 체크
     *
     * @param obj Collection
     * @return true/false
     */
    public static boolean isEmpty(Collection<?> obj) {
        return CollectionUtils.isEmpty(obj);
    }

    /**
     * Map 체크
     *
     * @param obj Map
     * @return true/false
     */
    public static boolean isEmpty(Map<?, ?> obj) {
        return CollectionUtils.isEmpty(obj);
    }
    //endregion

    //region ==============================isNotEmpty==============================

    /**
     * 문자열 체크(빈 문자열 존재)
     *
     * @param obj 문자열
     * @return true/false
     */
    public static boolean isNotEmpty(String obj) {
        return StringUtils.isNotEmpty(obj);
    }

    /**
     * Collection 체크
     *
     * @param obj Collection
     * @return true/false
     */
    public static boolean isNotEmpty(Collection<?> obj) {
        return !CollectionUtils.isEmpty(obj);
    }

    /**
     * Map 체크
     *
     * @param obj Map
     * @return true/false
     */
    public static boolean isNotEmpty(Map<?, ?> obj) {
        return !CollectionUtils.isEmpty(obj);
    }
    //endregion

    //region ==============================isBlank==============================

    /**
     * 문자열 체크(빈 문자열 미존재)
     *
     * @param obj 문자열
     * @return true/false
     */
    public static boolean isBlank(String obj) {
        return StringUtils.isBlank(obj);
    }
    //endregion

    //region ==============================isNotBlank==============================

    /**
     * 문자열 체크(빈 문자열 미존재)
     *
     * @param obj 문자열
     * @return true/false
     */
    public static boolean isNotBlank(String obj) {
        return StringUtils.isNotBlank(obj);
    }
    //endregion

    //region ==============================패스워드 검증==============================

    /**
     * 패스워드 검증
     *
     * @param password     패스워드문자열
     * @param minLength    최소 자리수
     * @param maxLength    최대 자리수
     * @param isContinuous 3자리 이상 연속되는 문자/숫자 ex) 123,321, abc, cba
     * @param isSame       3자리 이상 같은 문자/숫자 ex) 111, aaa
     * @param isSpecialMix 특수문자 혼합 체크 여부 ex) 문자,숫자,특수문자 혼합 체크 (특수문자 범위 !,@,#,$,%,^,&,*,(,) )
     * @return
     */
    public static Map<String, String> passwordValidation(String password,
                                                         int minLength,
                                                         int maxLength,
                                                         boolean isContinuous,
                                                         boolean isSame,
                                                         boolean isSpecialMix) {

        Map<String, String> returnValue = Maps.newHashMap();

        // 정규식 객체
        Matcher matcher;

        //region 공백 체크

        //공백 체크
        if (isBlank(password)) {
            returnValue.put("status", "false");
            returnValue.put("message", "비밀번호가 존재하지 않습니다.");
            return returnValue;
        }

        // 공백 정규식
        String blankReg = "(\\s)";

        //공백 정규식 객체 삽입
        matcher = Pattern.compile(blankReg).matcher(password);

        //공백 체크
        if (matcher.find()) {
            returnValue.put("status", "false");
            returnValue.put("message", "비밀번호가 존재하지 않습니다.");
            return returnValue;
        }

        //endregion

        //패스워드 Upper
        String upperPwd = password.toUpperCase();

        //region 문자열 자리수 체크
        int pwdLength = upperPwd.length();

        if (minLength > maxLength)
            maxLength = minLength;

        if (pwdLength < minLength || pwdLength > maxLength) {
            returnValue.put("status", "false");
            returnValue.put("message", "비밀번호는 " + minLength + "~" + maxLength + "자리 까지만 가능합니다.");
            return returnValue;
        }
        //endregion

        //region 3자리 이상 연속되는 문자/숫자 체크

        if (isContinuous) {
            int[] tempArray = new int[pwdLength];

            //아스키코드 Array 삽입
            for (int i = 0; i < pwdLength; i++) {
                tempArray[i] = upperPwd.charAt(i);
            }

            //체크
            for (int i = 0; i < pwdLength - 2; i++) {
                //첫번째 문자가 0-9 또는 A-Z
                if ((tempArray[i] > 47 && tempArray[i + 2] < 58)
                        || (tempArray[i] > 64 && tempArray[i + 2] < 91)) {
                    // 배열의 연속된 수 검사
                    // 3번째 글자 - 2번째 글자 = 1, 3번째 글자 - 1번째 글자 = 2

                    //아스키코드로 3번째 문자 - 1번째 문자가 2이고 3번째 문자 - 2번째 문자가 1이면 연속된 숫자
                    if (Math.abs(tempArray[i + 2] - tempArray[i]) == 2
                            && Math.abs(tempArray[i + 2] - tempArray[i + 1]) == 1) {
                        returnValue.put("status", "false");
                        returnValue.put("message", "continuous");
                        return returnValue;
                    }
                }
            }
        }

        //endregion

        //region 3자리 같은 문자/숫자 체크

        if (isSame) {
            // 3자리 같은 문자 정규식
            String sameReg = "(\\w)\\1\\1";

            matcher = Pattern.compile(sameReg).matcher(upperPwd);

            //체크
            if (matcher.find()) {
                returnValue.put("status", "false");
                returnValue.put("message", "same");
                return returnValue;
            }
        }

        //endregion

        //region 특수문자 혼합 체크
        if (isSpecialMix) {
            // 영어, 숫자, 특수문자 포함한 정규식
            String pwdReg = "^((?=.*\\d)(?=.*[a-zA-Z])(?=.*[\\W]).{" + minLength + "," + maxLength + "})$";

            matcher = Pattern.compile(pwdReg).matcher(upperPwd);

            //체크
            if (!matcher.find()) {
                returnValue.put("status", "false");
                returnValue.put("message", "비밀번호는 영어, 숫자, 특수문자 포함해야 합니다.");
                return returnValue;
            }

        }
        //endregion

        returnValue.put("status", "true");
        returnValue.put("message", "success");
        return returnValue;
    }
    //endregion

    /**
     * 기준에 대한 월 차이
     */
    public static boolean isMonthDiffCheck(LocalDate startAt, LocalDate endAt, int checkMonth) {
        return !endAt.minusMonths(checkMonth).isAfter(startAt);
    }

    /**
     * 문자 인코딩
     */
    public static String encodeValue(String value) {
        try {
            return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException ex) {
            return null;
        }
    }

    /**
     * 한글 조사 (이,가) 붙여주는 서비스 한글 조사 연결 (을/를,이/가,은/는,로/으로) 1. 종성에 받침이 있는 경우 '을/이/은/으로/과' 2. 종성에 받침이 없는 경우 '를/가/는/로/와' 3. '로/으로'의 경우 종성의 받침이 'ㄹ' 인경우 '로'
     */
    public static String getPostWord(String str, String firstVal, String secondVal) {

        try {
            char laststr = str.charAt(str.length() - 1);
            // 한글의 제일 처음과 끝의 범위밖일 경우는 오류
            if (laststr < 0xAC00 || laststr > 0xD7A3) {
                return str;
            }

            int lastCharIndex = (laststr - 0xAC00) % 28;

            // 종성인덱스가 0이상일 경우는 받침이 있는경우이며 그렇지 않은경우는 받침이 없는 경우
            if (lastCharIndex > 0) {
                // 받침이 있는경우
                // 조사가 '로'인경우 'ㄹ'받침으로 끝나는 경우는 '로' 나머지 경우는 '으로'
                if (firstVal.equals("으로") && lastCharIndex == 8) {
                    str += secondVal;
                } else {
                    str += firstVal;
                }
            } else {
                // 받침이 없는 경우
                str += secondVal;
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }

        return str;
    }

    //region ==============================UUID_v4==============================
    public static String getUUID_V4() {
        return UUID.randomUUID().toString();
    }
    //endregion

}
