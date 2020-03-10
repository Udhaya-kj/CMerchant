package life.corals.merchant.utils;

import java.util.HashMap;

public interface QrParserListener {

	void onSuccess(HashMap<String, String> parsedData, String outletId, String authToken, String campaignId);

	void onFailure(String result);
}
