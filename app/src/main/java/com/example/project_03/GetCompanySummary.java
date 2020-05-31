package com.example.project_03;

import android.util.Log;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.IOException;
import java.util.ArrayList;

public class GetCompanySummary {
    ArrayList<String> arrayList;

    public GetCompanySummary() {
        arrayList = new ArrayList<String>();
    }


    public String getSiteUrl(String bankName) {

        try {

            String baseurl = "https://m.catch.co.kr";
            String urlstr = "https://m.catch.co.kr/Search/SearchList?Keyword="+bankName;
            String strUrl;
            Document doc = Jsoup.connect(urlstr).get();
                if(doc.select("ul.list_company a").hasText())
                {
                    Element url = doc.select("ul.list_company a").get(0);
                    strUrl = baseurl +url.attr("href");
                }
                else
                {
                    strUrl = "url 정보가 없습니다";
                }

                return strUrl;

        } catch (IOException e) {
            //e.printStackTrace();
            Log.d("GetCompanySummary의 getNumber()함수 에러 : ",e.getMessage());
            return null;
        }
    }
}