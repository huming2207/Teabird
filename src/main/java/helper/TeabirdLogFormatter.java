package helper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class TeabirdLogFormatter extends Formatter
{
    @Override
    public String format(LogRecord record)
    {
        // Converts the record's unix timestamp to Date object,
        // then parse the Date object to string with specified format.
        String date = new SimpleDateFormat("dd/MM/yyyy hh:mm").format(new Date(record.getMillis()));

        // Return the format string, e.g. "06/03/2018 23:20 - INFO: bla bla bla"
        return String.format("\n%s - %s: %s", date, record.getLevel().getName(), formatMessage(record));
    }
}
