package app.aosp.web;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import jakarta.servlet.http.HttpServletRequest;

public class PageConfig {
    private static final String ATTR_NAME = PageConfig.class.getCanonicalName();
    private HttpServletRequest req;

    private PageConfig(HttpServletRequest req) {
        this.req = req;
    }

    public static PageConfig get(HttpServletRequest request) {
        Object cfg = request.getAttribute(ATTR_NAME);
        if (cfg != null) {
            return (PageConfig) cfg;
        }
        PageConfig pcfg = new PageConfig(request);
        request.setAttribute(ATTR_NAME, pcfg);
        return pcfg;
    }

    private long nameToNumber(String name) {
        long num = 0;
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(name);
        while (matcher.find()) {
            num += (num << 8) + (Integer.valueOf(matcher.group()).intValue() & 255);
        }
        return num;
    }

    public List<IndexEntry> getIndexList() {
        String indexRoot = this.req.getServletContext().getInitParameter("aospapp-index-root");
        try {
            DateFormat df = new SimpleDateFormat("yyy-MM-dd");
            return Files.list(Paths.get(indexRoot)).sorted((o1, o2) -> {
                long t1 = nameToNumber(o1.getFileName().toString());
                long t2 = nameToNumber(o2.getFileName().toString());
                return (int) (t2 - t1);
            }).filter(path -> path.startsWith("android-")).map(path -> {
                IndexEntry entry = new IndexEntry();
                entry.setName(path.getFileName().toString());
                entry.setPath(path.getFileName().toString());
                try {
                    BasicFileAttributes attr = Files.readAttributes(path.resolve("timestamp"), BasicFileAttributes.class);
                    entry.setDate(df.format(attr.lastModifiedTime().toMillis()));
                } catch (IOException e) {
                    entry.setDate("Unknown");
                }
                return entry;
            }).collect(Collectors.toList());
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }
}