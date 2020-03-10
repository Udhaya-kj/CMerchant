package life.corals.merchant.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;

public class GenererateTopupQR {

    public String generateTopupQR(String topUp_pay, String mer_id, String outletId, String campaignID, String token) {

        StringBuffer _codeString = new StringBuffer();
  _codeString.append("0001");
        if (topUp_pay !=null && topUp_pay == "T") _codeString.append("T");
        else if (topUp_pay !=null && topUp_pay == "P") _codeString.append("P");
        else _codeString.append("X");

        campaignID = (topUp_pay !=null && topUp_pay == "P") ?"":campaignID;

        _codeString.append("26250011life.corals0106")
                .append(token);
  _codeString.append("92930014");
        StringBuffer _timeStamp = new StringBuffer();
        Calendar c = Calendar.getInstance();

        _timeStamp.append(c.get(Calendar.YEAR));
     _timeStamp
                .append((c.get(Calendar.MONTH) + 1) < 10 ? "0" + (c.get(Calendar.MONTH) + 1)
                        : (c.get(Calendar.MONTH) + 1))
                .append(c.get(Calendar.DATE) < 10 ? "0" + c.get(Calendar.DATE) : c.get(Calendar.DATE))
                .append(c.get(Calendar.HOUR) < 10 ? "0" + c.get(Calendar.HOUR) : c.get(Calendar.HOUR))
                .append(c.get(Calendar.MINUTE) < 10 ? "0" + c.get(Calendar.MINUTE) : c.get(Calendar.MINUTE))
                .append(c.get(Calendar.SECOND) < 10 ? "0" + c.get(Calendar.SECOND) : c.get(Calendar.SECOND));

        _codeString.append(_timeStamp)
                .append("0103999")
                .append("0264");


        try {
            String _tokenHash = getHash(mer_id, campaignID);
            if (_tokenHash != null)
                _codeString.append(_tokenHash);

        } catch (Exception e) {
            throw new IllegalArgumentException("Error");
        }
        _codeString.append("9318")
                .append("0009")
                .append(outletId)
                .append("0101");

        try {
            _codeString.append(getChkDigit(_timeStamp.toString()));

        } catch (Exception e) {
            throw new IllegalArgumentException("Error");
        }

        return _codeString.toString();
    }

    public String getHash(String _s1, String _s2) {
        StringBuffer sb = new StringBuffer();
        String computedHash = "";
        if (_s1 != null)
            sb.append(_s1);
        if (_s2 != null)
            sb.append(_s2);
        try {

            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] messageDigest = md.digest(sb.toString().getBytes());

            BigInteger no = new BigInteger(1, messageDigest);

            computedHash = no.toString(16);

            while (computedHash.length() < 64) {
                computedHash = "0" + computedHash;

            }


        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("Error");


        }

        return computedHash;
    }

    public String getChkDigit(String _s1) {

        long _vsum = 0;

        try {

            long inputVal = Long.parseLong(_s1);

            while (inputVal > 0) {
                _vsum = _vsum + inputVal % 10;
                inputVal = inputVal / 10;

                if (inputVal == 0 && _vsum >= 10) {
                    inputVal = _vsum;
                    _vsum = 0;
                }
            }

        } catch (Exception e) {
            throw new IllegalArgumentException();
        }


        return Long.toString(_vsum);
    }


}
