package org.dicio.numbers.lang.it;

import static org.dicio.numbers.test.TestUtils.niceDuration;
import static org.dicio.numbers.test.TestUtils.numberDeduceType;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.MONTHS;
import static java.time.temporal.ChronoUnit.SECONDS;
import static java.time.temporal.ChronoUnit.WEEKS;
import static java.time.temporal.ChronoUnit.YEARS;

import org.dicio.numbers.parser.lexer.TokenStream;
import org.dicio.numbers.test.WithTokenizerTestBase;
import org.dicio.numbers.unit.Duration;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.function.Function;

public class ExtractDateTimeTest extends WithTokenizerTestBase {

    // Tuesday the 10th of May, 2022, 19:38:36
    private static final LocalDateTime NOW = LocalDateTime.of(2022, 5, 10, 19, 38, 36, 295834726);

    @Override
    public String configFolder() {
        return "config/it-it";
    }


    private Duration t(final double number, final ChronoUnit chronoUnit) {
        return new Duration().plus(numberDeduceType(number), chronoUnit);
    }

    private void assertRelativeDurationFunction(final String s,
                                                final Duration expectedDuration,
                                                final int finalTokenStreamPosition,
                                                final Function<ItalianDateTimeExtractor, Duration> durationFunction) {
        final TokenStream ts = new TokenStream(tokenizer.tokenize(s));
        final Duration actualDuration = durationFunction.apply(new ItalianDateTimeExtractor(ts, NOW));
        assertNotNull("null relative duration for string \"" + s + "\"", actualDuration);
        assertEquals("wrong final token position for string \"" + s + "\"",
                finalTokenStreamPosition, ts.getPosition());
        assertTrue("wrong relative duration for string \"" + s + "\": expected \""
                        + niceDuration(expectedDuration) + "\" but got \""
                        + niceDuration(actualDuration) + "\"",
                expectedDuration.getNanos().equals(actualDuration.getNanos())
                        && expectedDuration.getDays().equals(actualDuration.getDays())
                        && expectedDuration.getMonths().equals(actualDuration.getMonths())
                        && expectedDuration.getYears().equals(actualDuration.getYears()));
    }

    private void assertRelativeDurationFunctionNull(final String s,
                                                    final Function<ItalianDateTimeExtractor, Duration> durationFunction) {
        final TokenStream ts = new TokenStream(tokenizer.tokenize(s));
        final Duration duration = durationFunction.apply(new ItalianDateTimeExtractor(ts, NOW));

        if (duration != null) {
            fail("expected no relative duration (null), but got \"" + niceDuration(duration)
                    + "\"");
        }
    }

    private <T> void assertFunction(final String s,
                                    final T expectedResult,
                                    int finalTokenStreamPosition,
                                    final Function<ItalianDateTimeExtractor, T> function) {
        final TokenStream ts = new TokenStream(tokenizer.tokenize(s));
        assertEquals("wrong result for string \"" + s + "\"",
                expectedResult, function.apply(new ItalianDateTimeExtractor(ts, NOW)));
        assertEquals("wrong final token position for string \"" + s + "\"",
                finalTokenStreamPosition, ts.getPosition());
    }

    private <T> void assertFunctionNull(final String s,
                                        final Function<ItalianDateTimeExtractor, T> numberFunction) {
        assertFunction(s, null, 0, numberFunction);
    }

    private void assertRelativeDuration(final String s, final Duration expectedDuration, int finalTokenStreamPosition) {
        assertRelativeDurationFunction(s, expectedDuration, finalTokenStreamPosition, ItalianDateTimeExtractor::relativeDuration);
    }

    private void assertRelativeDurationNull(final String s) {
        assertRelativeDurationFunctionNull(s, ItalianDateTimeExtractor::relativeDuration);
    }

    private void assertRelativeMonthDuration(final String s, final Duration expectedDuration, int finalTokenStreamPosition) {
        assertRelativeDurationFunction(s, expectedDuration, finalTokenStreamPosition, ItalianDateTimeExtractor::relativeMonthDuration);
    }

    private void assertRelativeMonthDurationNull(final String s) {
        assertRelativeDurationFunctionNull(s, ItalianDateTimeExtractor::relativeMonthDuration);
    }

    private void assertRelativeDayOfWeekDuration(final String s, final Duration expectedDuration, int finalTokenStreamPosition) {
        assertRelativeDurationFunction(s, expectedDuration, finalTokenStreamPosition, ItalianDateTimeExtractor::relativeDayOfWeekDuration);
    }

    private void assertRelativeDayOfWeekDurationNull(final String s) {
        assertRelativeDurationFunctionNull(s, ItalianDateTimeExtractor::relativeDayOfWeekDuration);
    }

    private void assertRelativeTomorrow(final String s, final Duration expectedDuration, int finalTokenStreamPosition) {
        assertRelativeDurationFunction(s, expectedDuration, finalTokenStreamPosition, ItalianDateTimeExtractor::relativeTomorrow);
    }

    private void assertRelativeTomorrowNull(final String s) {
        assertRelativeDurationFunctionNull(s, ItalianDateTimeExtractor::relativeTomorrow);
    }

    private void assertRelativeToday(final String s) {
        assertRelativeDurationFunction(s, new Duration(), 1, ItalianDateTimeExtractor::relativeToday);
    }

    private void assertRelativeTodayNull(final String s) {
        assertRelativeDurationFunctionNull(s, ItalianDateTimeExtractor::relativeToday);
    }

    private void assertRelativeYesterday(final String s, final Duration expectedDuration, int finalTokenStreamPosition) {
        assertRelativeDurationFunction(s, expectedDuration, finalTokenStreamPosition, ItalianDateTimeExtractor::relativeYesterday);
    }

    private void assertRelativeYesterdayNull(final String s) {
        assertRelativeDurationFunctionNull(s, ItalianDateTimeExtractor::relativeYesterday);
    }

    private void assertHour(final String s, final int expected, int finalTokenStreamPosition) {
        assertFunction(s, expected, finalTokenStreamPosition, ItalianDateTimeExtractor::hour);
    }

    private void assertHourNull(final String s) {
        assertFunctionNull(s, ItalianDateTimeExtractor::hour);
    }

    private void assertSpecialHour(final String s, final int expected, int finalTokenStreamPosition) {
        assertFunction(s, expected, finalTokenStreamPosition, ItalianDateTimeExtractor::specialHour);
    }

    private void assertSpecialHourNull(final String s) {
        assertFunctionNull(s, ItalianDateTimeExtractor::specialHour);
    }

    private void assertMinute(final String s, final int expected, int finalTokenStreamPosition) {
        assertFunction(s, expected, finalTokenStreamPosition, ItalianDateTimeExtractor::minute);
    }

    private void assertMinuteNull(final String s) {
        assertFunctionNull(s, ItalianDateTimeExtractor::minute);
    }

    private void assertSpecialMinute(final String s, final int expected, int finalTokenStreamPosition) {
        assertFunction(s, expected, finalTokenStreamPosition, ItalianDateTimeExtractor::specialMinute);
    }

    private void assertSpecialMinuteNull(final String s) {
        assertFunctionNull(s, ItalianDateTimeExtractor::specialMinute);
    }

    private void assertSecond(final String s, final int expected, int finalTokenStreamPosition) {
        assertFunction(s, expected, finalTokenStreamPosition, ItalianDateTimeExtractor::second);
    }

    private void assertSecondNull(final String s) {
        assertFunctionNull(s, ItalianDateTimeExtractor::second);
    }

    private void assertBcad(final String s, final Boolean expectedAd, int finalTokenStreamPosition) {
        assertFunction(s, expectedAd, finalTokenStreamPosition, ItalianDateTimeExtractor::bcad);
    }

    private void assertBcadNull(final String s) {
        assertFunctionNull(s, ItalianDateTimeExtractor::bcad);
    }

    private void assertAmpm(final String s, final Boolean expectedAd, int finalTokenStreamPosition) {
        assertFunction(s, expectedAd, finalTokenStreamPosition, ItalianDateTimeExtractor::ampm);
    }

    private void assertAmpmNull(final String s) {
        assertFunctionNull(s, ItalianDateTimeExtractor::ampm);
    }

    private void assertDayOfWeek(final String s, final int expected) {
        assertFunction(s, expected, 1, ItalianDateTimeExtractor::dayOfWeek);
    }

    private void assertDayOfWeekNull(final String s) {
        assertFunctionNull(s, ItalianDateTimeExtractor::dayOfWeek);
    }

    private void assertMonthName(final String s, final int expected) {
        assertFunction(s, expected, 1, ItalianDateTimeExtractor::monthName);
    }

    private void assertMonthNameNull(final String s) {
        assertFunctionNull(s, ItalianDateTimeExtractor::monthName);
    }


    @Test
    public void testRelativeDuration() {
        assertRelativeDuration("tra due settimane vado al mare", t(2, WEEKS),   3);
        assertRelativeDuration("in quattro mesi scorsi",         t(4, MONTHS),  3);
        assertRelativeDuration("dopo secondi è caduto",          t(1, SECONDS), 2);
        assertRelativeDuration("in un paio di decenni",          t(20, YEARS),  5);
        assertRelativeDuration("nove e giorni è fa e",           t(-9, DAYS),   5);
        assertRelativeDuration("settant'anni a questa parte",    t(-70, YEARS), 5);
        assertRelativeDuration("tre mesi e due giorni prima",    t(-3, MONTHS).plus(t(-2, DAYS)), 6);
        assertRelativeDuration("sessantadue secoli passati sono seimiladuecento anni fa", t(-6200, YEARS), 4);
    }

    @Test
    public void testRelativeDurationNull() {
        assertRelativeDurationNull("ciao come va");
        assertRelativeDurationNull("quattro semestri");
        assertRelativeDurationNull("sai che tra una settimana");
        assertRelativeDurationNull("e due mesi fa");
        assertRelativeDurationNull("la giornata scorsa");
    }

    @Test
    public void testRelativeMonthDuration() {
        assertRelativeMonthDuration("settembre che viene",   t(4, MONTHS),   3);
        assertRelativeMonthDuration("aprile e prossimo e a", t(11, MONTHS),  3);
        assertRelativeMonthDuration("scorso e aprile e a",   t(-1, MONTHS),  3);
        assertRelativeMonthDuration("maggio che verrà",      t(12, MONTHS),  3);
        assertRelativeMonthDuration("maggio è passato",      t(-12, MONTHS), 3);
        assertRelativeMonthDuration("in gennaio",            t(8, MONTHS),   2);
    }

    @Test
    public void testRelativeMonthDurationNull() {
        assertRelativeMonthDurationNull("ciao come va");
        assertRelativeMonthDurationNull("questo novembre fa");
        assertRelativeMonthDurationNull("ottobre");
        assertRelativeMonthDurationNull("tra due ottobre");
        assertRelativeMonthDurationNull("tra due mesi");
    }

    @Test
    public void testRelativeDayOfWeekDuration() {
        assertRelativeDayOfWeekDuration("giovedì prossimo",     t(2, DAYS),   2);
        assertRelativeDayOfWeekDuration("giovedi scorso",       t(-5, DAYS),  2);
        assertRelativeDayOfWeekDuration("tra due domeniche si", t(12, DAYS),  3);
        assertRelativeDayOfWeekDuration("due e domenica e fa",  t(-9, DAYS),  5);
        assertRelativeDayOfWeekDuration("tre lunedì e prima e", t(-15, DAYS), 4);
        assertRelativeDayOfWeekDuration("martedì prossimo",     t(7, DAYS),   2);
        assertRelativeDayOfWeekDuration("un martedì fa",        t(-7, DAYS),   3);
    }

    @Test
    public void testRelativeDayOfWeekDurationNull() {
        assertRelativeDayOfWeekDurationNull("ciao come va");
        assertRelativeDayOfWeekDurationNull("lunedi");
        assertRelativeDayOfWeekDurationNull("due venerdì");
        assertRelativeDayOfWeekDurationNull("tra due giorni");
        assertRelativeDayOfWeekDurationNull("e tra due domeniche");
        assertRelativeDayOfWeekDurationNull("ieri e domani");
    }

    @Test
    public void testRelativeTomorrow() {
        assertRelativeTomorrow("domani andiamo",            t(1, DAYS), 1);
        assertRelativeTomorrow("dopodomani e",              t(2, DAYS), 2);
        assertRelativeTomorrow("dopo l'domani test",        t(2, DAYS), 3);
        assertRelativeTomorrow("dopo e dopodomani e",       t(3, DAYS), 4);
        assertRelativeTomorrow("dopo dopo dopo dopodomani", t(5, DAYS), 5);
    }

    @Test
    public void testRelativeTomorrowNull() {
        assertRelativeTomorrowNull("ciao come va");
        assertRelativeTomorrowNull("e domani");
        assertRelativeTomorrowNull("il dopo domani");
        assertRelativeTomorrowNull("ieri");
        assertRelativeTomorrowNull("oggi");
    }

    @Test
    public void testRelativeToday() {
        assertRelativeToday("oggi");
        assertRelativeToday("oggi proprio oggi");
        assertRelativeToday("oggi test");
        assertRelativeToday("oggi e");
    }

    @Test
    public void testRelativeTodayNull() {
        assertRelativeTodayNull("ciao come va");
        assertRelativeTodayNull("proprio oggi");
        assertRelativeTodayNull("l'oggi");
        assertRelativeTodayNull("e oggi");
        assertRelativeTodayNull("ieri");
        assertRelativeTodayNull("domani");
    }

    @Test
    public void testRelativeYesterday() {
        assertRelativeYesterday("ieri sono stato",            t(-1, DAYS), 1);
        assertRelativeYesterday("altro l'ieri test",          t(-2, DAYS), 3);
        assertRelativeYesterday("ieri l'altro e",             t(-2, DAYS), 3);
        assertRelativeYesterday("ieri l'altro l'altro",       t(-2, DAYS), 3);
        assertRelativeYesterday("altro ieri altro",           t(-2, DAYS), 2);
        assertRelativeYesterday("altroieri",                  t(-2, DAYS), 2);
        assertRelativeYesterday("altro l'altro l'altro ieri", t(-4, DAYS), 6);
    }

    @Test
    public void testRelativeYesterdayNull() {
        assertRelativeYesterdayNull("ciao come va");
        assertRelativeYesterdayNull("e ieri");
        assertRelativeYesterdayNull("l'altro ieri");
        assertRelativeYesterdayNull("altri ieri");
        assertRelativeYesterdayNull("oggi");
        assertRelativeYesterdayNull("domani");
    }

    @Test
    public void testHour() {
        assertHour("ventuno test",     21, 2);
        assertHour("le zero e",        0,  2);
        assertHour("l'una e ventisei", 1,  2);
        assertHour("dodici in punto",  12, 1);
        assertHour("alle diciassette", 17, 2);
        assertHour("all'ora alle tre", 3,  4);
        assertHour("alle ore tredici", 13, 3);
    }

    @Test
    public void testHourNull() {
        assertHourNull("ciao come va");
        assertHourNull("venticinque");
        assertHourNull("le meno due");
        assertHourNull("alle cento cinquanta quattro");
        assertHourNull("il sette");
        assertHourNull("alle ore");
        assertHourNull("le e zero e");
        assertHourNull("e venti quattro");
        assertHourNull("l'un milione");
    }

    @Test
    public void testSpecialHour() {
        assertSpecialHour("a mezzanotte",      24, 2);
        assertSpecialHour("mezzo giorno",      12, 2);
        assertSpecialHour("queste mezze notti",24, 3);
        assertSpecialHour("questa sera e",     21, 2);
        assertSpecialHour("stanotte test",     3,  1);
    }

    @Test
    public void testSpecialHourNull() {
        assertSpecialHourNull("ciao come va");
        assertSpecialHourNull("e a mezzogiorno");
        assertSpecialHourNull("mezza è notte");
        assertSpecialHourNull("la cena");
        assertSpecialHourNull("alle ore cena");
    }

    @Test
    public void testMinute() {
        assertMinute("zero a b c",       0,  1);
        assertMinute("cinquantanove ore",59, 2);
        assertMinute("quindici e",       15, 1);
        assertMinute("venti e otto s",   28, 3);
        assertMinute("sei minuti test",  6,  2);
        assertMinute("trentasei e min",  36, 2);
        assertMinute("44m e",            44, 2);
    }

    @Test
    public void testMinuteNull() {
        assertMinuteNull("ciao come va");
        assertMinuteNull("sessanta minuti");
        assertMinuteNull("cento venti");
        assertMinuteNull("meno sedici");
        assertMinuteNull("12000 minuti");
        assertMinuteNull("e due e");
    }

    @Test
    public void testSpecialMinute() {
        assertSpecialMinute("un quarto e",              15, 2);
        assertSpecialMinute("mezza test",               30, 1);
        assertSpecialMinute("un mezzo",                 30, 2);
        assertSpecialMinute("zero virgola due",         12, 3);
        assertSpecialMinute("tredici ottantasettesimi", 9,  3); // 13/87*60 is 8.97 -> rounded to 9
    }

    @Test
    public void testSpecialMinuteNull() {
        assertSpecialMinuteNull("ciao come va");
        assertSpecialMinuteNull("due");
        assertSpecialMinuteNull("cento dodici");
        assertSpecialMinuteNull("meno un quarto");
        assertSpecialMinuteNull("quattro quarti");
        assertSpecialMinuteNull("zero mezzi");
        assertSpecialMinuteNull("zero e virgola due");
        assertSpecialMinuteNull("tredici e ottantasettesimi");
    }

    @Test
    public void testSecond() {
        assertSecond("zero a b c",       0,  1);
        assertSecond("cinquantanove ore",59, 2);
        assertSecond("quindici e",       15, 1);
        assertSecond("venti e otto m",   28, 3);
        assertSecond("sei secondo test", 6,  2);
        assertSecond("trentasei e sec",  36, 2);
        assertSecond("44s e",            44, 2);
    }

    @Test
    public void testSecondNull() {
        assertSecondNull("ciao come va");
        assertSecondNull("sessanta secondi");
        assertSecondNull("cento venti");
        assertSecondNull("meno sedici");
        assertSecondNull("12000 secondi");
        assertSecondNull("dodici mila");
        assertSecondNull("e due e");
    }

    @Test
    public void testBcad() {
        assertBcad("a.C. test",   false, 3);
        assertBcad("d.C. e",      true,  3);
        assertBcad("dopo Cristo", true,  2);
        assertBcad("c test",      false, 1);
        assertBcad("a e Cristo",  false, 3);
    }

    @Test
    public void testBcadNull() {
        assertBcadNull("a.m.");
        assertBcadNull("dopo test Cristo");
        assertBcadNull("e avanti Cristo");
        assertBcadNull("test c");
        assertBcadNull("m");
    }

    @Test
    public void testAmpm() {
        assertAmpm("a.m. test",      false, 3);
        assertAmpm("p.m. e",         true,  3);
        assertAmpm("post meridiano", true,  2);
        assertAmpm("meridian test",  false, 1);
        assertAmpm("p e meridiem",   true,  3);
    }

    @Test
    public void testAmpmNull() {
        assertAmpmNull("a.C.");
        assertAmpmNull("ante test meridiem");
        assertAmpmNull("e post m");
        assertAmpmNull("test m");
        assertAmpmNull("c");
    }

    @Test
    public void testDayOfWeek() {
        assertDayOfWeek("lunedì",         0);
        assertDayOfWeek("domeniche test", 6);
        assertDayOfWeek("sab e",          5);
        assertDayOfWeek("mar",            1);
    }

    @Test
    public void testDayOfWeekNull() {
        assertDayOfWeekNull("gennaio");
        assertDayOfWeekNull("vendro");
        assertDayOfWeekNull("ciao martedì");
        assertDayOfWeekNull("e ven to");
    }

    @Test
    public void testMonthName() {
        assertMonthName("gennaio",    0);
        assertMonthName("dic e",      11);
        assertMonthName("sett embre", 8);
        assertMonthName("mar",        2);
    }

    @Test
    public void testMonthNameNull() {
        assertMonthNameNull("lunedì");
        assertMonthNameNull("genner");
        assertMonthNameNull("ciao feb");
        assertMonthNameNull("e dic to");
    }
}
