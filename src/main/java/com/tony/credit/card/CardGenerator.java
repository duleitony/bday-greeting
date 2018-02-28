package com.tony.credit.card;

import java.util.List;
import java.util.Random;
import java.util.Stack;
import java.util.Vector;

/**
 * The class is used to generate credit card.
 * You can generate a credit card number based on brand or given digits.
 * 
 * @author lei.du
 *
 */
public class CardGenerator {

    public static void main(String[] args)    {
        generateSingleCardNumberWithDigits("411111111111111", 16);

        generateSingleCardNumbesrWithBrand(BINTable.JCB_PREFIX_LIST, 16);

        String[] cardNumberArray = generateMultipleCardNumbesrWithBrand(BINTable.MASTERCARD_PREFIX_LIST, 16, 3);
        for(int i=0; i<cardNumberArray.length; i++) {
            System.out.println("Validate pan number '" + cardNumberArray[i] + "' : " + "LuhnCheck result is " + LuhnCheck.isValidCreditCardNumber(cardNumberArray[i]));
        }
    }

    /**
     * Generate a card number by a given first digits
     * 
     * @param givenDigits
     * @param length
     * @return
     */
    public static String generateSingleCardNumberWithDigits(String givenDigits, int length) {
        length--; //Deduct the last checkdigit
        String padding = "";
        Random random = new Random();
        if (givenDigits.length()< length) {
            for(int j=0; j<length-givenDigits.length(); j++) {
                padding = padding + random.nextInt(10);
            }
        }
        System.out.println("Calculate check digit for: " + givenDigits + " The padding is: " + padding);
        String checkDigit = LuhnCheck.calculateCheckDigit(givenDigits + padding);
        System.out.println("Check digit: " + checkDigit);
        String pan = givenDigits + padding + checkDigit;
        System.out.println("Validate pan number '" + pan + "': " + "LuhnCheck result is " + LuhnCheck.isValidCreditCardNumber(pan));

        return pan;
    }

    /**
     * Generate multiple credit cards with a given brand
     * 
     * @param prefixList
     * @param length
     * @param howMany
     * @return
     */
    public static String[] generateMultipleCardNumbesrWithBrand(String[] prefixList, int length,
            int howMany) {

        Stack<String> result = new Stack<String>();
        for (int i = 0; i < howMany; i++) {
            int randomArrayIndex = (int) Math.floor(Math.random()
                    * prefixList.length);
            String ccnumber = prefixList[randomArrayIndex];
            result.push(completed_number(ccnumber, length));
        }

        return result.toArray(new String[result.size()]);
    }

    /**
     * Generate a single credit card with a given brand
     * 
     * @param prefixList
     * @param length
     * @return
     */
    public static String generateSingleCardNumbesrWithBrand(String[] prefixList, int length) {
            int randomArrayIndex = (int) Math.floor(Math.random()
                    * prefixList.length);
            String ccnumber = prefixList[randomArrayIndex];
            String pan = completed_number(ccnumber, length);
            System.out.println("Validate pan number '" + pan + "': " + "LuhnCheck result is " + LuhnCheck.isValidCreditCardNumber(pan));

            return pan;
    }

    /**
     * 'prefix' is the start of the CC number as a string, any number of digits.
     * 'length' is the length of the CC number to generate. Typically 13 or 16
     * 
     * @param prefix
     * @param length
     * @return
     */
    private static String completed_number(String prefix, int length) {

        String ccnumber = prefix;

        // generate digits
        while (ccnumber.length() < (length - 1)) {
            ccnumber += new Double(Math.floor(Math.random() * 10)).intValue();
        }

        // reverse number and convert to int
        String reversedCCnumberString = strrev(ccnumber);

        List<Integer> reversedCCnumberList = new Vector<Integer>();
        for (int i = 0; i < reversedCCnumberString.length(); i++) {
            reversedCCnumberList.add(new Integer(String
                    .valueOf(reversedCCnumberString.charAt(i))));
        }

        // calculate sum

        int sum = 0;
        int pos = 0;

        Integer[] reversedCCnumber = reversedCCnumberList
                .toArray(new Integer[reversedCCnumberList.size()]);
        while (pos < length - 1) {

            int odd = reversedCCnumber[pos] * 2;
            if (odd > 9) {
                odd -= 9;
            }

            sum += odd;

            if (pos != (length - 2)) {
                sum += reversedCCnumber[pos + 1];
            }
            pos += 2;
        }

        // calculate check digit

        int checkdigit = new Double(
                ((Math.floor(sum / 10) + 1) * 10 - sum) % 10).intValue();
        ccnumber += checkdigit;

        return ccnumber;

    }

    /**
     * 
     * 
     * @param str
     * @return
     */
    private static String strrev(String str) {
        if (str == null)
            return "";
        String revstr = "";
        for (int i = str.length() - 1; i >= 0; i--) {
            revstr += str.charAt(i);
        }

        return revstr;
    }
}
