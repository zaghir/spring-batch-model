package com.zaghir.batch.batchspringmodel.common;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtils {

    private static Logger logger = LoggerFactory.getLogger(DateUtils.class);

    /** Regex de controle format JJ/MM/AAAA **/
    public static final String REGEX_DATE_5 = "^(\\d{2})([\\/])(\\d{2})([\\/])(\\d{4})$";
    private static final Pattern PATTERN_DATE_5 = Pattern.compile(REGEX_DATE_5);
    private static final String DATE_FORMAT_YEAR_2CHAR_MONTH = "yyMM";
    public final static String DATE_FORMAT_DB2_IG = "yyyy-MM-dd";

  

    public static Date convertToDate(String dateInString, DatePattern pattern) {

        SimpleDateFormat formatter = new SimpleDateFormat(pattern.getPattern());

        try {
            return formatter.parse(dateInString);

        } catch (ParseException e) {
            logger.error("convertToDate", e);
        }

        return null;
    }

    /**
     * Renvoie la date sous forme de String en respectant le pattern passé en paramètre.
     * 
     * @param aDate
     *            La date
     * @param aPattern
     *            Le pattern à respecter
     * @return La date formatées
     */
    public static String getDateAsStringFromPattern(Date aDate, String aPattern) {
        if (aDate != null) {
            SimpleDateFormat lSimpleDateFormat = new SimpleDateFormat(aPattern);
            return lSimpleDateFormat.format(aDate);
        } else {
            return "";
        }
    }

    public static int getTempsEcoule(Date fromDate) {

        Date currentDate = new Date();

        Long diff = currentDate.getTime() - fromDate.getTime();

        return diff.intValue();

    }

    

    /**
     * s Convertie une chaine de caratère au format JJ/MM/AAAA en objet Date
     * 
     * @param aDate
     *            = Date à convertir
     * 
     * @return Un object Date correspondant à la chaine passé en paramètre. Si le format est incorrect, retourne null
     */
    public static final Date convertToDateJJMMAAA(String aDate) {
        Matcher lMatcher;
        Date lResult = null;
        if (aDate != null) {
            lMatcher = PATTERN_DATE_5.matcher(aDate);
            if (lMatcher.matches()) {
                SimpleDateFormat lSimpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    lResult = lSimpleDateFormat.parse(aDate);
                } catch (ParseException e) {
                    logger.error("FormatUtil.convertToDateJJMMAAA() : impossible de convertir " + aDate + " en Date.");
                }
            }
        }
        return lResult;
    }

    public static Timestamp convertJavaDateToSqlTimestamp(Date dateToBeConverted) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateToBeConverted);
        cal.set(Calendar.MILLISECOND, 0);

        return new java.sql.Timestamp(cal.getTimeInMillis());
    }

    public static final Boolean isDateJJMMAAA(String aDate) {
        Matcher lMatcher;
        Boolean lResult = Boolean.FALSE;
        if (aDate != null) {
            lMatcher = PATTERN_DATE_5.matcher(aDate);
            if (lMatcher.matches()) {
                lResult = Boolean.TRUE;
            }
        }
        return lResult;
    }

    /**
     * 
     * Retourne la date à partir d'une String
     * 
     * @param aDate
     *            = Date
     * @param aDateFormat
     *            = DateFormat du SimpleDateFormat
     * @return la date en objet Date
     */
    public static Date getDateFromString(String aDate, String aDateFormat) {
        SimpleDateFormat lDateFormat = new SimpleDateFormat(aDateFormat);
        if (aDate != null) {
            try {
                return lDateFormat.parse(aDate);
            } catch (ParseException e) {
                return null;
            }
        }
        return null;
    }

    /**
     * Indique si la date passée en paramètre est égale a la date du jour sans tenir compte des heures.
     * 
     * @param date1
     * @param date2
     * 
     * @return 0 si la date est égale à la date du jour 1 si la date du jour est supérieur à la date en paramètre -1 si la date du jour est inférieur à la date en paramètre
     */
    public Integer compareDate(Date date1, Date date2) {
        Calendar lCalendar = Calendar.getInstance();
        lCalendar.setTime(date1);
        lCalendar.set(Calendar.MILLISECOND, 0);
        lCalendar.set(Calendar.SECOND, 0);
        lCalendar.set(Calendar.MINUTE, 0);
        lCalendar.set(Calendar.HOUR_OF_DAY, 0);

        return date2.compareTo(lCalendar.getTime());
    }

    /**
     * @param date
     * @param nbrDays
     * @return
     */
    public static Date addDaysToDate(Date date, Integer nbrDays) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int daysToDecrement = nbrDays;
        cal.add(Calendar.DATE, daysToDecrement);
        return cal.getTime();

    }

    /**
     * Renvoi l'année en String de la date passée en paramètre sur 2 positions (2014 => 14) et le mois
     * 
     * @param aDate
     *            La date dont il faut extraire l'année et le mois
     * @return
     */
    public static String get2CharYearAndMonthFromDateAsString(Date aDate) {
        if (aDate != null) {
            SimpleDateFormat lSimpleDateFormat = new SimpleDateFormat(DATE_FORMAT_YEAR_2CHAR_MONTH);
            return lSimpleDateFormat.format(aDate);
        } else {
            return "";
        }
    }

    /**
     * Renvoi en String de la date passée en paramètre sur 10 positions (YYYY-MM-DD)
     * 
     * @param aDate
     *            La date dont il faut extraire l'année, le mois et le jour
     * @return
     */
    public static String get2CharDB2IGFromDateAsString(Date aDate) {
        if (aDate != null) {
            SimpleDateFormat lSimpleDateFormat = new SimpleDateFormat(DATE_FORMAT_DB2_IG);
            return lSimpleDateFormat.format(aDate);
        } else {
            return "";
        }
    }

    /**
     * Renvoie la date vide 01/01/0001
     * 
     * @return Date 01/01/001
     */
    public static Date getDateVide() {
        Calendar lCalendar = Calendar.getInstance();
        lCalendar.clear();
        // La value pour setter le mois est 0-based. Ce qui signifie que 0 signifie Janvier, 1, Février...
        lCalendar.set(0001, 0, 01);
        return lCalendar.getTime();
    }

}
