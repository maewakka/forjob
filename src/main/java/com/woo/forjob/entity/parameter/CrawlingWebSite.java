package com.woo.forjob.entity.parameter;

public enum CrawlingWebSite {
    INCRUIT("인크루트", "https://www.incruit.com/"),
    SARAMIN("사람인", "https://www.saramin.co.kr/");

    String name;
    String url;
    CrawlingWebSite(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}
