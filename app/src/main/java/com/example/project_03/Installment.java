package com.example.project_03;

import android.util.Log;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class Installment {

    Document doc;
    int cnt = 0;
    boolean flag = true;
    public Installment(String urlstring) {
        try{
            doc = Jsoup.connect(urlstring).get();
        }
        catch (IOException e) {
            //e.printStackTrace();
            Log.d("Installment의 getNumber()함수 에러 : ",e.getMessage());
        }
    }

    public ArrayList<String> GetBankInfo(String htmltag)
    {
        ArrayList<String>  arrayList = new ArrayList<String>();
        arrayList.clear();
        Iterator<Element> ie1 = doc.select(htmltag).iterator();
        for(int i = 0 ; i< 10; i++)
        {
            arrayList.add(ie1.next().text());
        }
        return arrayList;
    }


    public ArrayList<String> rate2(String urlstring)
    {
        ArrayList<String>  arrayList = new ArrayList<String>();
        ArrayList<String> temp =  new ArrayList<String>();
        arrayList.clear();
        temp.clear();
        Iterator<Element> ie1 = doc.select(urlstring).iterator();
        for(int i = 0 ; i< 20; i++)
        {
            if(i%2==0)
            {
                arrayList.add(ie1.next().text());
            }
            else
            {
                temp.add(ie1.next().text());
            }
        }
        return arrayList;
    }

}