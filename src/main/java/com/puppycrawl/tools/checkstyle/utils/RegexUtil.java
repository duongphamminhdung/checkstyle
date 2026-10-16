package com.puppycrawl.tools.checkstyle.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public final class RegexUtil {
    private RegexUtil() {
    
    }

    /**
     * Helper method to create a regular expression.
     *
     * @param pattern
     *            the pattern to match
     * @return a created regexp object
     * @throws IllegalArgumentException
     *             if unable to create Pattern object.
     */
    public static Pattern createPattern(String pattern) {
        return createPattern(pattern, 0);
    }

    /**
     * Helper method to create a regular expression with a specific flags.
     *
     * @param pattern
     *            the pattern to match
     * @param flags
     *            the flags to set
     * @return a created regexp object
     * @throws IllegalArgumentException
     *             if unable to create Pattern object.
     */
    public static Pattern createPattern(String pattern, int flags) {
        try {
            return Pattern.compile(pattern, flags);
        }
        catch (final PatternSyntaxException exc) {
            throw new IllegalArgumentException(
                "Failed to initialise regular expression " + pattern, exc);
        }
    }

    /**
     * Validates whether passed string is a valid pattern or not.
     *
     * @param pattern
     *            string to validate
     * @return true if the pattern is valid false otherwise
     */
    public static boolean isPatternValid(String pattern) {
        boolean isValid = true;
        try {
            Pattern.compile(pattern);
        }
        catch (final PatternSyntaxException ignored) {
            isValid = false;
        }
        return isValid;
    }
        /**
     * Puts part of line, which matches regexp into given template
     * on positions $n where 'n' is number of matched part in line.
     *
     * @param template the string to expand.
     * @param lineToPlaceInTemplate contains expression which should be placed into string.
     * @param regexp expression to find in comment.
     * @return the string, based on template filled with given lines
     */
        public static String fillTemplateWithStringsByRegexp(
            String template, String lineToPlaceInTemplate, Pattern regexp) {
            final Matcher matcher = regexp.matcher(lineToPlaceInTemplate);
            String result = template;
            if (matcher.find()) {
                for (int index = 0; index <= matcher.groupCount(); index++) {
                    final String group = matcher.group(index);
                    if (group != null) {
                        // $n expands comment match like in Pattern.subst().
                        result = result.replaceAll("\\$" + index, Matcher.quoteReplacement(group));
                    }
                }
            }
            return result;
        }
}
