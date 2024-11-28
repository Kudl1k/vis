package cz.kudladev.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataParser {

    public static String parseAndFormatDate(String inputDate) {
        String inputFormat = "EEE MMM dd HH:mm:ss zzz yyyy";
        String outputFormat = "yyyy-MM-dd HH:mm:ss";

        try {
            SimpleDateFormat inputDateFormat = new SimpleDateFormat(inputFormat);
            Date date = inputDateFormat.parse(inputDate);

            SimpleDateFormat outputDateFormat = new SimpleDateFormat(outputFormat);
            return outputDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
