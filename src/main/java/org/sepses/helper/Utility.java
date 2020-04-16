package org.sepses.helper;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Utility {

    public static final String TIME_ZONE = "Europe/Vienna";
    public static final String SECONDS = "SECONDS";
    public static final String XSD_DATETIME = "yyyy-MM-dd'T'HH:mm:ss";

    private static final Logger log = LoggerFactory.getLogger(Utility.class);

    /**
     * *** create hash out of content
     *
     * @param templateText
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String createHash(String templateText) throws NoSuchAlgorithmException {
        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
        final byte[] hashbytes = digest.digest(templateText.getBytes(StandardCharsets.UTF_8));
        return DigestUtils.sha256Hex(hashbytes);
    }

    public static String cleanContent(String inputContent) {

        String cleanContent = inputContent.replace("\\", "|"); // clean up cleanContent
        cleanContent = cleanContent.replaceAll("\"", "|");
        cleanContent = cleanContent.replaceAll("'", "|");

        return cleanContent;
    }

    public static String cleanUriContent(String inputContent) {

        String cleanContent = inputContent.replaceAll("[^a-zA-Z0-9._-]", "_");
        cleanContent = cleanContent.replaceAll("\\.+$", "");

        return cleanContent;
    }

    public static void writeToFile(String string, String filename) throws IOException {
        FileWriter writer = new FileWriter(filename);
        writer.write(string);
        writer.flush();
        writer.close();
    }

    public static String localTimeConversion(String timeParam, String timeFormat) {

        LocalDateTime dateTime = LocalDateTime.ofEpochSecond(0, 0, ZoneOffset.UTC);
        ZoneId zoneId = ZoneId.of(TIME_ZONE);
        ZoneOffset zoneOffSet = zoneId.getRules().getOffset(LocalDateTime.now());

        DateTimeFormatter fromFormatter;
        if (timeFormat.equalsIgnoreCase(SECONDS)) {
            dateTime = LocalDateTime.ofEpochSecond(Integer.parseInt(timeParam), 0, zoneOffSet);

        } else {
            try {
                fromFormatter = DateTimeFormatter.ofPattern(timeFormat);
                dateTime = LocalDateTime.parse(timeParam, fromFormatter);
            } catch (IllegalArgumentException e) {
                log.error(e.getMessage());
            } catch (DateTimeParseException e) {
                log.error(e.getMessage());
            }
        }

        return dateTime.format(DateTimeFormatter.ofPattern(XSD_DATETIME));
    }
}
