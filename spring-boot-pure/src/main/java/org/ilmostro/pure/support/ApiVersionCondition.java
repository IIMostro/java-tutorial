package org.ilmostro.pure.support;

import org.springframework.lang.NonNull;
import org.springframework.web.servlet.mvc.condition.RequestCondition;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author li.bowei
 */
public class ApiVersionCondition implements RequestCondition<ApiVersionCondition> {

    private static final Pattern VERSION_PREFIX_PATTERN = Pattern.compile("v(\\d+)/");
    private final int version;

    public ApiVersionCondition(int version) {
        this.version = version;
    }

    @Override
    @NonNull
    public ApiVersionCondition combine(ApiVersionCondition other) {
        return new ApiVersionCondition(other.getVersion());
    }

    @Override
    public ApiVersionCondition getMatchingCondition(HttpServletRequest request) {
        Matcher matcher = VERSION_PREFIX_PATTERN.matcher(request.getRequestURI());
        if (!matcher.find()) return null;
        int v = Integer.parseInt(matcher.group(1));
        if (v >= this.version) return this;
        return null;
    }

    @Override
    public int compareTo(ApiVersionCondition other, @NonNull HttpServletRequest request) {
        return other.getVersion() - this.version;
    }

    public int getVersion() {
        return version;
    }
}
