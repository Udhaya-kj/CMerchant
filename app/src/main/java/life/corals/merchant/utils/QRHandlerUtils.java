package life.corals.merchant.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class QRHandlerUtils {

	private static final String TAG = "QRHandlerUtils";

	private static final String node1 = "26";

	private static final String node2 = "92";

	public static final String OUTLET_ID = "93";

	public static final String MERCHANT_ID = "mer-id";
	public static final String CUSTOMER_ID = "cust_id";
	public static final String REDEEM_TYPE = "redeem-type";
	public static final String OUTLET_Id = "outlet-id";
	public static final String REDEEM_ID = "redeem-id";
	public static final String LEDGER_ID = "ledger-id";
	public static final String COUNTRY_CODE = "contry-code";

	private QrParserListener parserListener;

	public QRHandlerUtils() {

	}

	public void setParserListener(QrParserListener parserListener) {
		this.parserListener = parserListener;
	}



	private static List<String> nodeList = Arrays.asList(node1, node2, OUTLET_ID);

	private String outletId = "";
	private boolean isTopupQRValidated = false;
	private String authToken = "";
	private boolean isQRValidated = false;
	private String campaignId = "";

	private List<String> errors = Arrays.asList("", "Invalid QR code - 1", "Invalid QR code - 2",
			"Incorrect top up selected by you or the merchant", "Invalid QR code - 4", "Invalid QR code - 5");

	public void parseTopUpQR(String scannedString, String mer_id) {
		try {
			int resultCode = isTopupQRvalid(scannedString, mer_id);

			if (resultCode == 0 && parserListener != null) {
				parserListener.onSuccess(getQRParsed(scannedString), getOutletID(), getAuthTokenFromQR(),
						getCampaignID());
			} else if (resultCode == 1 || resultCode == 2 || resultCode == 3 || resultCode == 4
					|| resultCode == 5 && parserListener != null) {
				parserListener.onFailure(errors.get(resultCode));
			}
		} catch (Exception e) {
			parserListener.onFailure("Invalid QR code - 9");
		}
	}

	public void parseMyIssueQR(String stringQR) {
		if (parserListener != null) {

			parserListener.onSuccess(parseCustMyQR(stringQR), "", "", "");
		}
	}

	public void parseRedeemQR(String stringQR) {
		if (parserListener != null) {

			parserListener.onSuccess(parseRedeemProductQR(stringQR), "", "", "");
		}
	}

	public int isTopupQRvalid(String scannedString, String mer_id) {

		HashMap<String, String> hm = new HashMap<String, String>();
		boolean chk1 = false, chk2 = false, chk3 = false, chk4 = false, chk5 = false, chk6 = false, chk7 = false,
				chk8 = false;

		hm = getQRParsed(scannedString);

		/* QR instruction code check chk1 */
		if ((hm.get("00")) != null && (hm.get("00").equals("T") || hm.get("00").equals("P"))) {

			chk1 = true;
			// campaign_id = ( hm.get("00").equals("P")) ?"":campaign_id;
		}
		/* QR ownership check chk2 */

		if (hm.get("26-00") != null && hm.get("26-00").contentEquals("life.corals"))
			chk2 = true;
		/* Check if token present */
		if (hm.get("26-01") != null && hm.get("26-01").length() == 6) {
			chk7 = true;
			authToken = hm.get("26-01");
		}

		/* QR time expiry check chk3 */
		if (hm.get("92-00") != null && hm.get("92-00").length() > 5 && hm.get("92-01") != null
				&& hm.get("92-01").length() == 3) {

			String pattern = hm.get("92-00");

			try {

				Calendar c = (Calendar) Calendar.getInstance();
				Calendar cNow = (Calendar) Calendar.getInstance();

				c.set(Integer.parseInt(pattern.substring(0, 4)), Integer.parseInt(pattern.substring(4, 6)) - 1,
						Integer.parseInt(pattern.substring(6, 8)), Integer.parseInt(pattern.substring(8, 10)),
						Integer.parseInt(pattern.substring(10, 12)), Integer.parseInt(pattern.substring(12, 14)));
				c.getTime();
				c.add(Calendar.SECOND, 180);
				c.getTime();
				cNow.getTime();
				StringBuffer s = new StringBuffer();
				StringBuffer snow = new StringBuffer();
				s.append(c.get(Calendar.YEAR))
						.append(c.get(Calendar.MONTH) < 10 ? "0" + c.get(Calendar.MONTH) : c.get(Calendar.MONTH))
						.append(c.get(Calendar.DATE) < 10 ? "0" + c.get(Calendar.DATE) : c.get(Calendar.DATE))
						.append(c.get(Calendar.HOUR) < 10 ? "0" + c.get(Calendar.HOUR) : c.get(Calendar.HOUR))
						.append(c.get(Calendar.MINUTE) < 10 ? "0" + c.get(Calendar.MINUTE) : c.get(Calendar.MINUTE))
						.append(c.get(Calendar.SECOND) < 10 ? "0" + c.get(Calendar.SECOND) : c.get(Calendar.SECOND));
				snow.append(cNow.get(Calendar.YEAR))
						.append(cNow.get(Calendar.MONTH) < 10 ? "0" + cNow.get(Calendar.MONTH)
								: cNow.get(Calendar.MONTH))
						.append(cNow.get(Calendar.DATE) < 10 ? "0" + cNow.get(Calendar.DATE) : cNow.get(Calendar.DATE))
						.append(cNow.get(Calendar.HOUR) < 10 ? "0" + cNow.get(Calendar.HOUR) : cNow.get(Calendar.HOUR))
						.append(cNow.get(Calendar.MINUTE) < 10 ? "0" + cNow.get(Calendar.MINUTE)
								: cNow.get(Calendar.MINUTE))
						.append(cNow.get(Calendar.SECOND) < 10 ? "0" + cNow.get(Calendar.SECOND)
								: cNow.get(Calendar.SECOND));

				BigInteger bi1 = new BigInteger(s.toString());
				BigInteger bi2 = new BigInteger(snow.toString());
				if (bi1.compareTo(bi2) == 1) {
					chk3 = true;

				} else {
					System.out.println("current time greater:" + chk3);
				}

			} catch (Exception e) {
				chk3 = false;
			}
		}

		if (hm.get("93-02") != null) {
			long _vsum = 0;

			try {

				int chkdgt = Integer.parseInt(hm.get("93-02"));
				long inputVal = Long.parseLong(hm.get("92-00"));

				while (inputVal > 0) {
					_vsum = _vsum + inputVal % 10;
					inputVal = inputVal / 10;

					if (inputVal == 0 && _vsum >= 10) {
						inputVal = _vsum;
						_vsum = 0;
					}
				}

				if (_vsum == chkdgt)
					chk4 = true;
				else
					chk4 = false;

			} catch (Exception e) {
				chk4 = false;
			}
		} // chk4

//		check merchant and campaign is the same as selected by customer
		if (hm.get("92-02").length() >= 64 && mer_id != null) {

			String _inClear = mer_id, _hash = hm.get("92-02");
			String computedHash = "";

			try {

				MessageDigest md = MessageDigest.getInstance("SHA-256");
				byte[] messageDigest = md.digest(_inClear.getBytes());

				BigInteger no = new BigInteger(1, messageDigest);
				computedHash = no.toString(16);

				while (computedHash.length() < 64) {
					computedHash = "0" + computedHash;
				}

			} catch (NoSuchAlgorithmException e) {
				chk5 = false;
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (computedHash.length() == 64 && computedHash.equalsIgnoreCase(_hash)) {

				chk5 = true;
			}

		} else

			chk5 = false;

//		Extract outlet ID
		if (hm.get("93-00") != null && hm.get("93-00").length() > 5) {
			chk6 = true;
			outletId = hm.get("93-00");
		} else
			chk6 = false;

//		Extract CampaignID
		if (hm.get("93-01") != null && hm.get("93-01").length() > 10) {
			chk8 = true;
			campaignId = hm.get("93-01");
		} else
			chk8 = false;

		if (chk1 && chk2 && chk3 && chk4 && chk5 && chk6 && chk7 && chk8)
			isTopupQRValidated = true;

		if (!chk1)
			return 1; // Invalid QR code
		else if (!chk2)
			return 1; // Invalid QR code
		else if (!chk3)
			return 2; // QR Expired
		else if (!chk4)
			return 1; // Invalid QR code
		else if (!chk5)
			return 3; // wrong Merchant / Campaign selected
		else if (!chk6)
			return 4; // Outlet Missing / Incorrect
		else if (!chk7)
			return 5; // AuthCode Missing
		else if (!chk8)
			return 8; // Campaign Code missing
		else
			return 0; // QR OK Proceed with Txn
	}

	private HashMap<String, String> getQRParsed(String scannedString) {

		HashMap<String, String> hm = new HashMap<String, String>();

		hm = getHashList(hm, null, scannedString);

		if (hm.get("26") != null && hm.get("26").length() > 4) {
			hm = getHashList(hm, "26", hm.get("26"));
		}
		if (hm.get("92") != null && hm.get("92").length() > 4) {
			hm = getHashList(hm, "92", hm.get("92"));
		}
		if (hm.get("93") != null && hm.get("93").length() > 4) {
			hm = getHashList(hm, "93", hm.get("93"));
		}

		return hm;
	}

	/*
	 *
	 */
	private HashMap<String, String> getHashList(HashMap<String, String> hm, String Mastercode, String parsevalue) {

		String code = null, tranLength, tranValue = null;
		int position = 4, tLen = 0, newPointerPosition = 0;

		while (parsevalue.length() > 4) {

			code = parsevalue.substring(0, 2);

			tranLength = parsevalue.substring(2, 4);

			tLen = Integer.parseInt(tranLength);
			newPointerPosition = position + tLen;

			tranValue = parsevalue.substring(4, 4 + tLen);

			parsevalue = parsevalue.substring(newPointerPosition);

			if (Mastercode != null)
				code = Mastercode + "-" + code;

			hm.put(code, tranValue);
			code = tranValue = tranLength = null;

		} // while
		return hm;
	}

	public String getOutletID() {
		if (isTopupQRValidated) {
			return outletId;
		} else
			return null;
	}

	/**
	 *
	 */
	public String getCampaignID() {
		if (isTopupQRValidated) {

			return campaignId;
		} else
			return null;

	}

	public String getAuthTokenFromQR() {
		if (isTopupQRValidated)
			return authToken;
		else
			return "";
	}

	public HashMap<String, String> getOutletKeyFromQR(String QRString) {

		HashMap<String, String> hm = new HashMap<String, String>();
		HashMap<String, String> hmout = new HashMap<String, String>();

		hm = getQRParsed(QRString);

		if (hm.get("26") != null && hm.get("26").length() > 4) {
			hmout.put("qr_mer_inf_node", "26");
			hmout.put("qr_mer_inf_unique_cd", hm.get("26"));
		}
		if (hm.get("52") != null && hm.get("52").length() > 3) {
			hmout.put("qr_category_code", hm.get("52"));
		}
		if (hm.get("53") != null && hm.get("53").length() > 2) {
			hmout.put("qr_txn_currency", hm.get("53"));
		}

		return hmout;
	}

	public HashMap<String, String> parseCustMyQR(String myQRstring) {

		if (myQRstring.length() > 100) {
			String qrwip = myQRstring;
			String valueeval = "", errormsg = "";
			int lengtheval = 0;
			boolean isError = false;

			HashMap<String, String> hm = new HashMap<String, String>();

			if (!"PE".contentEquals(qrwip.substring(4, 6))) {
				isError = true;
				errormsg = "Invalid QR Code";
			}

			if (isError == false) {

				qrwip = qrwip.substring(6);

				if (!"26400011life.corals0102".equals(qrwip.substring(0, 23))) {
					isError = true;
					errormsg = "Invalid QR Code";
				} else
					qrwip = qrwip.substring(23);
			}

			if (isError == false) {
				hm.put("country_code", qrwip.substring(0, 2));
				qrwip = qrwip.substring(4);
				lengtheval = Integer.parseInt(qrwip.substring(0, 2));
				lengtheval = lengtheval + 2;
				valueeval = qrwip.substring(2, lengtheval);

				if (lengtheval < 14) {
					isError = true;
					errormsg = "Invalid Customer ID";
				} else {
					hm.put("cust_id", valueeval);
					qrwip = qrwip.substring(lengtheval);
				}

			}
			if (isError == false) {

				if ("27".equals(qrwip.substring(0, 2))) {

					lengtheval = Integer.parseInt(qrwip.substring(2, 4));
					valueeval = qrwip.substring(4, lengtheval + 4);
					hm.put("mobile_no", valueeval);
					qrwip = qrwip.substring(lengtheval + 4);
				} else {
					isError = true;
					errormsg = "Invalid QR Code";
				}

			}
			if (isError == false) {
				if ("9286".equals(qrwip.substring(0, 4))) {
					qrwip = qrwip.substring(90);
				} else {
					isError = true;
					errormsg = "Invalid QR Code";
				}
			}
			if (isError == false) {
				if ("94".equals(qrwip.substring(0, 2))) {

					lengtheval = Integer.parseInt(qrwip.substring(2, 4));
					valueeval = qrwip.substring(4, lengtheval + 4);
					hm.put("device_id", valueeval);

				} else {
					isError = true;
					errormsg = "Invalid QR Code";
				}

			}

			// country_code
			// cust_id
			// mobile_no
			// device_id
			if (!isError)
				hm.put("status", "0");
			else
				hm.put("status", "1");
			hm.put("error", errormsg);

			return hm;
		} else
			return null;
	}

	public HashMap<String, String> parseRedeemProductQR(String redeemQRString) {

		if (redeemQRString.length() > 100) {
			String qrwip = redeemQRString;
			String errormsg = "";
			int lengtheval = 0;
			boolean isError = false;
			HashMap<String, String> hm = new HashMap<String, String>();

			if (!"R".equals(qrwip.substring(4, 5))) {
				isError = true;
				errormsg = "Invalid QR Code";
			}

			if (isError == false) {

				qrwip = qrwip.substring(5);
				if (!"26580011life.corals0116".equals(qrwip.substring(0, 23))) {
					isError = true;
					errormsg = "Invalid QR Code";
				} else
					qrwip = qrwip.substring(23);
			}

			if (isError == false) {
				hm.put("redeem_id", qrwip.substring(0, 16));
				qrwip = qrwip.substring(16);

				if (!"0215".equals(qrwip.substring(0, 4))) {
					isError = true;
					hm.clear();
					errormsg = "Invalid QR Code";
				} else {

					hm.put("cust_id", qrwip.substring(4, 19));

					qrwip = qrwip.substring(19);

				}
			}

			if (isError == false) {

				if (!"0301".equals(qrwip.substring(0, 4))) {
					isError = true;
					hm.clear();
					errormsg = "Invalid QR Code";
				} else {
					hm.put("redemption_type", qrwip.substring(4, 5));
					qrwip = qrwip.substring(5);
				}

			}
			if (isError == false) {

				if (!"91".equals(qrwip.substring(0, 2))) {
					isError = true;
					hm.clear();
					errormsg = "Invalid QR Code";
				} else {
					lengtheval = Integer.parseInt(qrwip.substring(3, 4));
					hm.put("points", qrwip.substring(4, lengtheval + 4));
					qrwip = qrwip.substring(lengtheval + 4);
				}

			}

			if (isError == false) {

				if (!"92".equals(qrwip.substring(0, 2))) {
					isError = true;
					hm.clear();
					errormsg = "Invalid QR Code";
				} else {
					lengtheval = Integer.parseInt(qrwip.substring(2, 4));
					qrwip = qrwip.substring(lengtheval + 4);
				}
			}

			if (isError == false) {

				if (!"93".equals(qrwip.substring(0, 2))) {
					isError = true;
					hm.clear();
					errormsg = "Invalid QR Code";
				} else {
					qrwip = qrwip.substring(4);
					if (!"00".equals(qrwip.substring(0, 2))) {
						isError = true;
						hm.clear();
						errormsg = "Invalid QR Code";

					} else {
						lengtheval = Integer.parseInt(qrwip.substring(2, 4));
						hm.put("mer_id", qrwip.substring(4, lengtheval + 4));
					}
				}
			}

			if (!isError)
				hm.put("status", "0");
			else
				hm.put("status", "1");
			hm.put("error", errormsg);
			return hm;
		} else
			return null;
	}

	public void parseVoucherQR(String QRS, QrParserListener listener) {

		if (QRS.length() > 100) {
			String ERROR_MSG = "";
			boolean isError = false;
			HashMap<String, String> hm = new HashMap<String, String>();

			String QR_TYPE = QRS.substring(4, 5);
			System.out.println(QR_TYPE);

			if (!QR_TYPE.equalsIgnoreCase("R")) {
				isError = true;
				ERROR_MSG = "Invalid QR type";
			}

			QRS = QRS.substring(5);

			if (!"26".equalsIgnoreCase(QRS.substring(0, 2)) && Integer.parseInt(QRS.substring(2, 4).toString()) != 58) {
				isError = true;
				ERROR_MSG = "Invalid QR Code";
			}

			QRS = QRS.substring(8);

			if (!isError && !"life.corals".equalsIgnoreCase(QRS.substring(0, 11))) {
				isError = true;
				ERROR_MSG = "Invalid Domain Name";
			}

			QRS = QRS.substring(11);

			if (!isError) {
				if (!"01".equalsIgnoreCase(QRS.substring(0, 2))
						&& Integer.parseInt(QRS.substring(2, 4).toString()) != 16) {
					isError = true;
					ERROR_MSG = "Invalid QR Code";
					hm.clear();
				} else {
					QRS = QRS.substring(4);
					String redeemID = QRS.substring(0, 16);
					System.out.println("redeem_id" + redeemID);
					hm.put(REDEEM_ID, redeemID);
					QRS = QRS.substring(16);
				}
			}

			if (!isError) {
				if (!"02".equalsIgnoreCase(QRS.substring(0, 2))
						&& Integer.parseInt(QRS.substring(2, 4).toString()) != 15) {
					isError = true;
					ERROR_MSG = "Invalid QR Code";
					hm.clear();
				} else {
					QRS = QRS.substring(4);
					String custID = QRS.substring(0, 15);
					System.out.println("cust_id" + custID);
					hm.put(CUSTOMER_ID, custID);
					QRS = QRS.substring(15);
				}
			}

			if (!isError) {
				if (!"03".equalsIgnoreCase(QRS.substring(0, 2))
						&& Integer.parseInt(QRS.substring(2, 4).toString()) != 1) {
					isError = false;
					ERROR_MSG = "Invalid QR Code";
					hm.clear();
				} else {
					QRS = QRS.substring(4);
					String redeemType = QRS.substring(0, 1);
					hm.put(REDEEM_TYPE, redeemType);
					System.out.println("redeem_type" + redeemType);
					QRS = QRS.substring(1);
				}
			}

			// if the QR is shared -- NODE 28 will be allocated
			if ("28".equalsIgnoreCase(QRS.substring(0, 2))) {

				QRS = QRS.substring(4);

				if (!isError) {
					if (!"00".equalsIgnoreCase(QRS.substring(0, 2))
							&& Integer.parseInt(QRS.substring(2, 4).toString()) != 16) {
						isError = true;
						ERROR_MSG = "Invalid QR Code";
						hm.clear();
					} else {
						QRS = QRS.substring(4);
						String ledgerID = QRS.substring(0, 16);
						System.out.println("cb_ledger_id" + ledgerID);
						hm.put(LEDGER_ID, ledgerID);
						QRS = QRS.substring(16);
					}
				}
			}

			if (!isError) {
				if (!"52".equalsIgnoreCase(QRS.substring(0, 2))
						&& Integer.parseInt(QRS.substring(2, 4).toString()) != 2) {
					isError = false;
					ERROR_MSG = "Invalid QR Code";
					hm.clear();
				} else {
					QRS = QRS.substring(4);
					String country = QRS.substring(0, 2);
					hm.put(COUNTRY_CODE, country);
					System.out.println("contry_code" + country);
					QRS = QRS.substring(2);
				}
			}

			System.out.println(QRS);

			if (!"92".equalsIgnoreCase(QRS.substring(0, 2)) && Integer.parseInt(QRS.substring(2, 4).toString()) != 25) {
				isError = true;
				ERROR_MSG = "Invalid QR Code";
			}

			QRS = QRS.substring(4);
			System.out.println(QRS);

			if (!isError) {
				if (!"00".equalsIgnoreCase(QRS.substring(0, 2))
						&& Integer.parseInt(QRS.substring(2, 4).toString()) != 14) {
					isError = true;
					ERROR_MSG = "Invalid QR Code";
					hm.clear();
				} else {
					QRS = QRS.substring(4);
					String timestamp = QRS.substring(0, 14);
					System.out.println("timestamp" + timestamp);
					hm.put("timestamp", timestamp);
					QRS = QRS.substring(14);
				}
			}
			System.out.println(QRS);
			if (!isError) {
				if (!"01".equalsIgnoreCase(QRS.substring(0, 2))
						&& Integer.parseInt(QRS.substring(2, 4).toString()) != 3) {
					isError = true;
					ERROR_MSG = "Invalid QR Code";
					hm.clear();
				} else {
					QRS = QRS.substring(4);
					String expDuration = QRS.substring(0, 3);
					System.out.println("exp_duration" + expDuration);
					hm.put("exp_duration", expDuration);
					QRS = QRS.substring(3);
				}
			}

			if (!"93".equalsIgnoreCase(QRS.substring(0, 2)) && Integer.parseInt(QRS.substring(2, 4).toString()) != 13) {
				isError = true;
				ERROR_MSG = "Invalid QR Code";
			}

			QRS = QRS.substring(4);

			if (!isError) {
				if (!"00".equalsIgnoreCase(QRS.substring(0, 2))
						&& Integer.parseInt(QRS.substring(2, 4).toString()) != 9) {
					isError = true;
					ERROR_MSG = "Invalid QR Code";
					hm.clear();
				} else {
					QRS = QRS.substring(4);
					String merID = QRS.substring(0, 9);
					System.out.println(MERCHANT_ID + merID);
					hm.put(MERCHANT_ID, merID);
				}
			}

			listener.onSuccess(hm, QRS, ERROR_MSG, QR_TYPE);
		} else {
			listener.onFailure("Invalid QR code");
		}
	}
}