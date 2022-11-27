package com.example.examplecollection.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.regex.*;

@RequiredArgsConstructor
@Service
public class MaskingUtils {

	/**
	 * 휴대폰번호 마스킹
	 */
	public String maskingCallNumber(String str) {
		String replaceString = str;
		String matchedStr = "";

		String pattern = "(\\d{2,3})-?(\\d{3,4})-?(\\d{3,4})";
		Matcher matcher = Pattern.compile(pattern).matcher(str);

		if (matcher.find()) {
			StringBuilder br = new StringBuilder();
			for (int i = 1; i <= matcher.groupCount(); i++) {
				matchedStr = matcher.group(i);
				for (int j = 0; j < matchedStr.length(); j++) {
					br.append("*");
				}
				if (i != matcher.groupCount()) {
					br.append("-");
				}
			}
			replaceString = matcher.replaceAll(br.toString());
		}
		return replaceString;
	}

	/**
	 * 이름 마스킹
	 */
	public String maskingName(String str) {
		StringBuilder replaceString = new StringBuilder(str);

		String pattern = "";
		if (str.length() == 2) {
			pattern = "^(.)(.+)$";
		} else {
			pattern = "^(.)(.+)(.)$";
		}

		Matcher matcher = Pattern.compile(pattern).matcher(str);

		if (matcher.matches()) {
			replaceString = new StringBuilder("");

			for (int i = 1; i <= matcher.groupCount(); i++) {
				String replaceTarget = matcher.group(i);
				if (i == 2) {
					char[] c = new char[replaceTarget.length()];
					Arrays.fill(c, '*');

					replaceString.append(String.valueOf(c));
				} else {
					replaceString.append(replaceTarget);
				}

			}
		}
		return replaceString.toString();
	}


}
