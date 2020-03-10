package life.corals.merchant.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
public class BusinessQRGenerator {

    private static final String DOMAIN_NAME = "life.corals";

    private String UNIQUE_ID = null;

    private String QR_CODE_STRING = null;

    public BusinessQRGenerator(String COUNTRY_NAME, String COUNTRY_CODE, String BUSINESS_NAME, String OUTLET_ID, String UNIQUE_ID)
            throws Exception {

        StringBuffer builder = new StringBuffer();

//        UNIQUE_ID = getUniqueInfCode();
        String VAL_NODE_53 = "5303702";
        String SUB_62_NODE_07 = getNODE07(OUTLET_ID);

        builder.append("000201010212")

                .append("26")
                .append(String.valueOf(UNIQUE_ID.length() < 10 ? "0" + UNIQUE_ID.length() : UNIQUE_ID.length()))
                .append(UNIQUE_ID)

                .append("52045812")

                .append("53")
                .append(String.valueOf(VAL_NODE_53.length() < 10 ? "0" + VAL_NODE_53.length() : VAL_NODE_53.length()))
                .append(VAL_NODE_53)

                .append("58")
                .append(String
                        .valueOf(COUNTRY_CODE.length() < 10 ? "0" + COUNTRY_CODE.length() : COUNTRY_CODE.length()))
                .append(COUNTRY_CODE)

                .append("59")
                .append(String
                        .valueOf(BUSINESS_NAME.length() < 10 ? "0" + BUSINESS_NAME.length() : BUSINESS_NAME.length()))
                .append(BUSINESS_NAME)

                .append("60")
                .append(String
                        .valueOf(COUNTRY_NAME.length() < 10 ? "0" + COUNTRY_NAME.length() : COUNTRY_NAME.length()))
                .append(COUNTRY_NAME)

                .append("62")
                .append(String.valueOf(
                        SUB_62_NODE_07.length() < 10 ? "0" + SUB_62_NODE_07.length() : SUB_62_NODE_07.length()))
                .append(SUB_62_NODE_07)

                .append("63042EA4");

        QR_CODE_STRING = builder.toString();
    }

    public String getUNIQUE_ID() {
        return UNIQUE_ID;
    }

    public void setUNIQUE_ID(String uNIQUE_ID) {
        UNIQUE_ID = uNIQUE_ID;
    }

    public String getQR_CODE_STRING() {
        return QR_CODE_STRING;
    }

    public void setQR_CODE_STRING(String qR_CODE_STRING) {
        QR_CODE_STRING = qR_CODE_STRING;
    }

    private String getUniqueInfCode() throws Exception {

        String HASH = UUID.randomUUID().toString();

        StringBuffer buffer = new StringBuffer();
        buffer.append("00")
                .append(String.valueOf(DOMAIN_NAME.length() < 10 ? "0" + DOMAIN_NAME.length() : DOMAIN_NAME.length()))
                .append(DOMAIN_NAME).append("01")
                .append(String.valueOf(HASH.length() < 10 ? "0" + HASH.length() : HASH.length())).append(HASH);

        return buffer.toString();
    }

    private String getNODE07(String OUTLET_ID) {
        StringBuffer buffer = new StringBuffer();

        buffer.append("07")
                .append(String.valueOf(OUTLET_ID.length() < 10 ? "0" + OUTLET_ID.length() : OUTLET_ID.length()))
                .append(OUTLET_ID);

        return buffer.toString();
    }

    public static String hashToSha256(String originalValue) throws NoSuchAlgorithmException {
        UUID uuid = UUID.randomUUID();
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedhash = digest.digest(originalValue.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(encodedhash);
    }

    private static String bytesToHex(byte[] hash) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1)
                hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

}