package com.kachiingapp.kachiing.api.controller.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class WebApiDemo {
	private static String url = "";
	private static String user = "";
	private static String pwd = "";
	private static String slnName = "";
	private static String dcName = "";
	private static String language = "";
	private static int dbType = 2;

	private static final byte[] pwdByte = { -82, -101, 127, 52, -8, -108, 2, 93 };

	public static String encrypt(String srcStr) throws Exception {
		SecretKey deskey = new SecretKeySpec(pwdByte, "DES");

		try {
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(1, deskey);

			byte[] resByte = cipher.doFinal(srcStr.getBytes());
			String res = byteArrayToBase64(resByte);

			return res;
		} catch (Exception e) {
			return "";
		}
	}

	private static String byteArrayToBase64(byte[] a) {
		int aLen = a.length;
		int numFullGroups = aLen / 3;
		int numBytesInPartialGroup = aLen - (3 * numFullGroups);
		int resultLen = 4 * (aLen + 2) / 3;
		StringBuffer result = new StringBuffer(resultLen);
		char[] intToAlpha = intToBase64;

		int inCursor = 0;
		for (int i = 0; i < numFullGroups; ++i) {
			int byte0 = a[(inCursor++)] & 0xFF;
			int byte1 = a[(inCursor++)] & 0xFF;
			int byte2 = a[(inCursor++)] & 0xFF;
			result.append(intToAlpha[(byte0 >> 2)]);
			result.append(intToAlpha[(byte0 << 4 & 0x3F | byte1 >> 4)]);
			result.append(intToAlpha[(byte1 << 2 & 0x3F | byte2 >> 6)]);
			result.append(intToAlpha[(byte2 & 0x3F)]);

		}

		if (numBytesInPartialGroup != 0) {
			int byte0 = a[(inCursor++)] & 0xFF;
			result.append(intToAlpha[(byte0 >> 2)]);
			if (numBytesInPartialGroup == 1) {
				result.append(intToAlpha[(byte0 << 4 & 0x3F)]);
				result.append("==");
			} else {
				int byte1 = a[(inCursor++)] & 0xFF;
				result.append(intToAlpha[(byte0 << 4 & 0x3F | byte1 >> 4)]);
				result.append(intToAlpha[(byte1 << 2 & 0x3F)]);
				result.append('=');
			}

		}

		return result.toString();
	}

	private static final char[] intToAltBase64 = { '!', '"', '#', '$', '%',
			'&', '\'', '(', ')', ',', '-', '.', ':', ';', '<', '>', '@', '[',
			']', '^', '`', '_', '{', '|', '}', '~', 'a', 'b', 'c', 'd', 'e',
			'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
			's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4',
			'5', '6', '7', '8', '9', '+', '?' };
	private static final char[] intToBase64 = { 'A', 'B', 'C', 'D', 'E', 'F',
			'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',
			'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f',
			'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
			't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', '+', '/' };

	public static void main(String[] args) {
		user = "user";
		pwd = "123456";
		url = "http://192.168.22.101:8080/easportal/openapi/";
		dcName = "uat202304";
		language = "L2";
		slnName = "eas";

		try {
			pwd = encrypt(pwd);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		String getToken = url + "login?authPattern=BaseDB&dcName=" + dcName
				+ "&isEncodePwd=1&language=" + language + "&password=" + pwd
				+ "&user=" + user;
		String jsonStr = HttpUtils.sendGet(getToken);
		System.out.println(jsonStr);

		JSONObject jsonObject = JSON.parseObject(jsonStr);
		String dataStr = (String) jsonObject.get("data");
		String errCode = String.valueOf(jsonObject.get("errCode"));

//		JsonParser parser = new JsonParser();
//		JsonObject jsonObj = parser.parse(jsonStr).getAsJsonObject();
//		String dataStr = jsonObj.get("data").getAsString();
//		String errCode = jsonObj.get("errCode").getAsString();
		if ("0".equals(errCode)) {
			JSONObject dataStrObj = JSON.parseObject(dataStr);
//			JsonObject dataStrObj = parser.parse(dataStr).getAsJsonObject();
			String token = (String) dataStrObj.get("token");
			try {
				token = URLEncoder.encode(token, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			String sendUrl = url + "api?token=" + token;
			Map map = new HashMap<String, Object>();
			List list = new LinkedList<String>();
			map.put("api", "OAFacade-addFYBill");

			// �����
			String json1Data = "{\"type\":\"loanPayment\",\"uid\":\"123\",\"company\":\"00000000-0000-0000-0000-000000000000CCE7AED4\",\"bill\":{"
					+ "\"bizDate\":\"2023-09-17\",\"title\":\"����\",\"processNumber\":\"���̱��\",\"company\":\"00000000-0000-0000-0000-000000000000CCE7AED4\","
					+ "\"dept\":\"����\",\"reimburser\":\"43100031\",\"jobNumber\":\"43100031\",\"post\":\"��λ\",\"deptNum\":\"���Ŵ���\",\"processDept\":\"������������\","
					+ "\"processCompany\":\"����������˾\",\"payType\":\"e09a62cd-00fd-1000-e000-0b32c0a8100dE96B2B8E\",\"phone\":\"��ϵ�绰\",\"subjectMatter\":\"����\","
					+ "\"use\":\"��;\",\"seq\":\"1\",\"expenseType\":\"FIwAAAAlaij+cBF9\",\"participants\":\"������Ա\","
					+ "\"loanApplicationAmount\":\"100\",\"loanApprovedAmount\":\"100\",\"borrower\":\"43100031\",\"payDept\":\"FIwAAAAAQJrM567U\","
					+ "\"expectedDate\":\"2023-09-17\",\"overreimbursement\":\"0\" ,\"cashAmount\":\"100\" ,\"amountDx\":\"Ҽ��Ԫ\" ,\"payee\":\"�տ���\" ,\"payeeBank\":\"�տ�����\","
					+ " \"payeeAccountBank\":\"�����˺�\",\"payeeProvince\":\"�տʡ\",\"payeeCity\":\"�տ����\",\"remarks\":\"����(��ע)\" }   "
					+ "}";
			// ���ñ��������
			String json2Data = "{\"type\":\"expensePayment\",\"uid\":\"123\",\"company\":\"00000000-0000-0000-0000-000000000000CCE7AED4\",\"bill\":{"
					+ "\"bizDate\":\"2023-09-17\",\"payThisMonth\":\"0\",\"title\":\"����\",\"processNumber\":\"���̱��\",\"company\":\"00000000-0000-0000-0000-000000000000CCE7AED4\","
					+ "\"dept\":\"����\",\"reimburser\":\"43100031\",\"jobNumber\":\"43100031\",\"post\":\"��λ\",\"processDept\":\"������������\",\"processCompany\":\"����������˾\","
					+ "\"payType\":\"e09a62cd-00fd-1000-e000-0b32c0a8100dE96B2B8E\",\"totleAmount\":\"100\",\"authorizedPerson\":\"ҵ���д��Ѷ����Ȩ��\",\"phone\":\"��ϵ�绰\""
					+ " ,\"subjectMatter\":\"����\","
					+ "\"use\":\"��;\",\"applicationAmount\":\"100\",\"cashAmount\":\"100\" ,\"amountDx\":\"Ҽ��Ԫ\" ,\"payee\":\"�տ���\" ,\"payeeBank\":\"�տ�����\","
					+ " \"payeeAccountBank\":\"�����˺�\",\"payeeProvince\":\"�տʡ\",\"payeeCity\":\"�տ����\",\"remarks\":\"����(��ע)\""
					+ " ,\"E1\":["
					+ " {\"isTickets\":\"1\",\"isDeduction\":\"1\",\"deductionTax\":\"5\",\"invoiceNum\":\"50\",\"expenseType\":\"FIwAAAAlaij+cBF9\",\"bizDate\":\"2023-09-18\",\"participants\":\"������Ա\",\"applicationAmount\":\"100\" ,\"approvedAmount\":\"100\" ,\"taxRate\":\"0.3\" ,\"approvedTaxAmount\":\"100\",\"payDept\":\"FIwAAAAAQJrM567U\",\"contractNumber\":\"FIwAAAABY5H5CwEz\",\"contractMajor\":\"001\",\"confirmation\":\"����ȷ�Ϸ�ʽ\",\"projects\":\"FIwAAAABY5H5CwEz\"},"
					+ " {\"isTickets\":\"0\",\"isDeduction\":\"0\",\"deductionTax\":\"10\",\"deductionTax\":\"100\",\"expenseType\":\"FIwAAAAlaij+cBF9\",\"bizDate\":\"2023-09-18\",\"participants\":\"������Ա\",\"applicationAmount\":\"100\" ,\"approvedAmount\":\"100\" ,\"taxRate\":\"0.3\" ,\"approvedTaxAmount\":\"100\",\"payDept\":\"FIwAAAAAQJrM567U\",\"contractNumber\":\"FIwAAAABY5H5CwEz\",\"contractMajor\":\"001\",\"confirmation\":\"����ȷ�Ϸ�ʽ\",\"projects\":\"FIwAAAABY5H5CwEz\"},"
					+ " ] "
					+ "  ,\"E2\":["
					+ "	 {\"invoiceType\":\"��Ʊ����\",\"invoiceCode\":\"��Ʊ����\",\"invoiceNum\":\"��Ʊ����\",\"invoiceDate\":\"2023-09-17\",\"totalValue\":\"100\",\"taxRate\":\"100\",\"tax\":\"100\",\"tradeName\":\"��Ʒ����\",\"ticketCompany\":\"��Ʊ��˾\",\"billingCompany\":\"��Ʊ��˾\",\"invoiceResults\":\"��ƱУ����\",\"ismodify\":\"0\",\"expenses\":\"����������ϸ\"} "
					+ "	 {\"invoiceType\":\"��Ʊ����\",\"invoiceCode\":\"��Ʊ����\",\"invoiceNum\":\"��Ʊ����\",\"invoiceDate\":\"2023-09-17\",\"totalValue\":\"100\",\"taxRate\":\"100\",\"tax\":\"100\",\"tradeName\":\"��Ʒ����\",\"ticketCompany\":\"��Ʊ��˾\",\"billingCompany\":\"��Ʊ��˾\",\"invoiceResults\":\"��ƱУ����\",\"ismodify\":\"1\",\"expenses\":\"����������ϸ\"} "
					+ "]"
					+ " ,\"E3\":["
					+ " 	{\"loanNumber\":\"����\",\"loanDate\":\"2023-09-18\",\"loanSubjectMatter\":\"�������\",\"approvedAmount\":\"100\",\"expenseType\":\"FIwAAAAlaij+cBF9\",\"amount\":\"100\"} "
					+ " 	{\"loanNumber\":\"����\",\"loanDate\":\"2023-09-18\",\"loanSubjectMatter\":\"�������\",\"approvedAmount\":\"100\",\"expenseType\":\"FIwAAAAlaij+cBF9\",\"amount\":\"100\"} "
					+ "]" + "}   " + "}";

			// ���÷ѱ��������
			String json3Data = "{\"type\":\"travelPayment\",\"uid\":\"123\",\"company\":\"00000000-0000-0000-0000-000000000000CCE7AED4\",\"bill\":{"
					+ "\"bizDate\":\"2023-09-17\",\"payThisMonth\":\"0\",\"title\":\"����\",\"processNumber\":\"���̱��\",\"company\":\"00000000-0000-0000-0000-000000000000CCE7AED4\",\"dept\":\"����\","
					+ "\"reimburser\":\"43100031\",\"jobNumber\":\"43100031\",\"post\":\"��λ\",\"deptNum\":\"���Ŵ���\",\"processDept\":\"������������\",\"processCompany\":\"����������˾\","
					+ "\"payType\":\"e09a62cd-00fd-1000-e000-0b32c0a8100dE96B2B8E\",\"phone\":\"��ϵ�绰\",\"tripProcess\":\"������������\",\"tripType\":\"��������\",\"tripDays\":\"12\","
					+ "\"totleAmount\":\"100\",\"subjectMatter\":\"����\",\"use\":\"��;\",\"cashAmount\":\"100\",\"amountDx\":\"Ҽ��Ԫ\",\"payee\":\"�տ���\",\"payeeBank\":\"�տ�����\","
					+ " \"payeeAccountBank\":\"�����˺�\",\"payeeProvince\":\"�տʡ\" ,\"payeeCity\":\"�տ����\",\"remarks\":\"��ע\","
					+ "\"E1\":["
					+ "		{\"taxRate\":\"0.03\",\"expenseType\":\"FIwAAAAlaij+cBF9\",\"bizDate\":\"2023-09-17\",\"amount\":\"100\",\"isTickets\":\"1\",\"approvedAmount\":\"100\",\"approvedTaxAmount\":\"100\",\"approvedNoTaxAmount\":\"100\",\"description\":\"˵��\",\"isDeduction\":\"1\",\"deductionTax\":\"10\",\"invoiceNum\":\"100\"},"
					+ "		{\"taxRate\":\"0.06\",\"expenseType\":\"FIwAAAAlaij+cBF9\",\"bizDate\":\"2023-09-17\",\"amount\":\"100\",\"isTickets\":\"0\",\"approvedAmount\":\"100\",\"approvedTaxAmount\":\"100\",\"approvedNoTaxAmount\":\"100\",\"description\":\"˵��\",\"isDeduction\":\"0\",\"deductionTax\":\"10\",\"invoiceNum\":\"100\"}"
					+ "	],"
					+ "\"E2\":["
					+ "		{\"people\":\"43100031\",\"startDate\":\"2023-09-17\",\"endDate\":\"2023-09-17\",\"expenseType\":\"FIwAAAAlaij+cBF9\",\"payDept\":\"FIwAAAAAQJrM567U\",\"projects\":\"FIwAAAABY5H5CwEz\",\"contractNumber\":\"FIwAAAABY5H5CwEz\",\"contractName\":\"��ͬ����\",\"contractMajor\":\"001\",\"confirmation\":\"����ȷ�Ϸ�ʽ\",\"approvedNoTaxAmount\":\"50\"  },"
					+ "		{\"people\":\"43100031\",\"startDate\":\"2023-09-17\",\"endDate\":\"2023-09-17\",\"expenseType\":\"FIwAAAAlaij+cBF9\",\"payDept\":\"FIwAAAAAQJrM567U\",\"projects\":\"FIwAAAABY5H5CwEz\",\"contractNumber\":\"FIwAAAABY5H5CwEz\",\"contractName\":\"��ͬ����\",\"contractMajor\":\"001\",\"confirmation\":\"����ȷ�Ϸ�ʽ\",\"approvedNoTaxAmount\":\"50\"  }"
					+ "	],"
					+ "\"E3\":["
					+ "		{\"invoiceType\":\"��Ʊ����\",\"invoiceCode\":\"��Ʊ����\",\"invoiceNum\":\"��Ʊ����\",\"invoiceDate\":\"2023-09-17\",\"totalValue\":\"100\",\"taxRate\":\"0.05\",\"tax\":\"10\",\"invoiceResults\":\"��ƱУ����\",\"ismodify\":\"0\",\"invoiceSource\":\"��Ʊ��Դ\","
					+ "		\"expenses\":\"����������ϸ\",\"passengers\":\"�ÿ�\",\"idCard\":\"���֤\",\"level\":\"��λ�ȼ�\",\"trainNumber\":\"�����/����\",\"from\":\"��������\",\"to\":\"Ŀ�ĳ���\",\"tradeName\":\"��Ʒ����\",\"taxClassificationCode\":\"˰�շ������\",\"remarks\":\"��ע\",\"collectionDate\":\"2023-09-18\","
					+ "		\"ticketCompany\":\"��Ʊ��˾\",\"billingCompany\":\"��Ʊ��˾\",\"airportConstructionFee\":\"10\",\"otherTaxesFees\":\"10\",\"insurancePremium\":\"10\",\"airportOther\":\"10\" },"
					+ "		{\"invoiceType\":\"��Ʊ����\",\"invoiceCode\":\"��Ʊ����\",\"invoiceNum\":\"��Ʊ����\",\"invoiceDate\":\"2023-09-17\",\"totalValue\":\"100\",\"taxRate\":\"0.05\",\"tax\":\"10\",\"invoiceResults\":\"��ƱУ����\",\"ismodify\":\"0\",\"invoiceSource\":\"��Ʊ��Դ\","
					+ "		\"expenses\":\"����������ϸ\",\"passengers\":\"�ÿ�\",\"idCard\":\"���֤\",\"level\":\"��λ�ȼ�\",\"trainNumber\":\"�����/����\",\"from\":\"��������\",\"to\":\"Ŀ�ĳ���\",\"tradeName\":\"��Ʒ����\",\"taxClassificationCode\":\"˰�շ������\",\"remarks\":\"��ע\",\"collectionDate\":\"2023-09-18\","
					+ "		\"ticketCompany\":\"��Ʊ��˾\",\"billingCompany\":\"��Ʊ��˾\",\"airportConstructionFee\":\"10\",\"otherTaxesFees\":\"10\",\"insurancePremium\":\"10\",\"airportOther\":\"10\" }"
					+ "	],"
					+ "\"E4\":["
					+ "		{\"loanNumber\":\"��ؽ���\",\"loanDate\":\"2023-09-17\",\"loanSubjectMatter\":\"�������\",\"loanBalance\":\"1\",\"expenseType\":\"FIwAAAAlaij+cBF9\",\"amount\":\"1\",\"residualBorrowings\":\"1\"},"
					+ "		{\"loanNumber\":\"��ؽ���\",\"loanDate\":\"2023-09-17\",\"loanSubjectMatter\":\"�������\",\"loanBalance\":\"1\",\"expenseType\":\"FIwAAAAlaij+cBF9\",\"amount\":\"1\",\"residualBorrowings\":\"1\"} "
					+ "	],"
					+ "\"E5\":["
					+ "		{\"type\":\"��ؽ���\",\"number\":\"���ݱ��\",\"amount\":\"100\",\"payAmount\":\"100\",\"payAmountDate\":\"2023-09-17\",\"remarks\":\"��ע\"},"
					+ "		{\"type\":\"��ؽ���\",\"number\":\"���ݱ��\",\"amount\":\"100\",\"payAmount\":\"100\",\"payAmountDate\":\"2023-09-17\",\"remarks\":\"��ע\"} "
					+ "	]" + "}}";

			list.add(json2Data);
			map.put("data", list);
			String sendPost = HttpUtils.sendPost(sendUrl,JSON.toJSONString(map) );
			System.out.println(sendPost);



			//��� ֻ���  ���ñ��������   ���÷ѱ��������
			map.put("api", "OAFacade-auditFyBill");
			list.clear();
			String auditData = "{\"type\":\"travelPayment\",\"uid\":\"FIwAAAAmAnukNgs7\"}";
			list.add(auditData);
			map.put("data", list);
			String sendPost1 = HttpUtils.sendPost(sendUrl,JSON.toJSONString(map));
			System.out.println(sendPost1);


		} else {
			String errMsg = (String) jsonObject.get("errMsg");
			System.out.println(errMsg);
		}
	}

}
