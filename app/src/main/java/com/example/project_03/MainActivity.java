package com.example.project_03;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button button1, button2;
    ArrayList<String> bankNameList  =  new ArrayList<String>();
    ArrayList<String> bankServiceNameList  =  new ArrayList<String>();
    ArrayList<String> bankInterestList  =  new ArrayList<String>();
    ArrayList<String> urlList  =  new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("금융상품 비교");

        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);

        final Bundle bun = new Bundle();

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Thread 로 웹서버에 접속
                new Thread() {
                    @Override
                    public void run() {
                        //super.run();
                        bun.clear();
                        bankNameList.clear();
                        bankServiceNameList.clear();
                        bankInterestList.clear();
                        Installment saving = new Installment("http://m.finlife.fss.or.kr/mobile/deposit/selectDeposit.do?menuId=3000101");
                        bankNameList = saving.GetBankInfo("div.listInfo_tit h3");
                        bankServiceNameList = saving.GetBankInfo("div.listInfo_tit p");
                        bankInterestList = saving.rate2("span.txt_intrRat");

                        GetCompanySummary test1 = new GetCompanySummary();

                        for(String str : bankNameList)
                        {
                            urlList.add(test1.getSiteUrl(str));
                        }

                        bun.putString("servicetype","saving");
                        bun.putStringArrayList("bankname",bankNameList);
                        bun.putStringArrayList("bankservice",bankServiceNameList);
                        bun.putStringArrayList("interest",bankInterestList);
                        bun.putStringArrayList("siteUrl", urlList);
                        Message msg = handler.obtainMessage();
                        msg.setData(bun);
                        handler.sendMessage(msg);

                    }
                }.start();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Thread 로 웹서버에 접속
                new Thread() {
                    @Override
                    public void run() {
                        //super.run();
                        bun.clear();
                        bankNameList.clear();
                        bankServiceNameList.clear();
                        bankInterestList.clear();
                        Installment install = new Installment("http://m.finlife.fss.or.kr/mobile/installment/selectinstallment.do?menuId=3000102");
                        bankNameList = install.GetBankInfo("div.listInfo_tit h3");
                        bankServiceNameList = install.GetBankInfo("div.listInfo_tit p");
                        bankInterestList = install.rate2("span.txt_intrRat");

                        GetCompanySummary test1 = new GetCompanySummary();

                        for(String str : bankNameList)
                        {
                            urlList.add(test1.getSiteUrl(str));
                        }
                        //arrayList = b.getNumber();

                        bun.putString("servicetype","installment");
                        bun.putStringArrayList("bankname",bankNameList);
                        bun.putStringArrayList("bankservice",bankServiceNameList);
                        bun.putStringArrayList("interest",bankInterestList);
                        bun.putStringArrayList("siteUrl", urlList);
                        Message msg = handler.obtainMessage();
                        msg.setData(bun);
                        handler.sendMessage(msg);

                    }
                }.start();
            }
        });
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //super.handleMessage(msg);
            String str = "";
            ArrayList<String> bName = new ArrayList<>();
            ArrayList<String> sName = new ArrayList<>();
            ArrayList<String> rate = new ArrayList<>();
            ArrayList<String> urlstring = new ArrayList<>();
            AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);
            GetCompanySummary test = new GetCompanySummary();
            dlg.setIcon(R.mipmap.ic_launcher);
            Bundle bun = msg.getData();
            switch (bun.getString("servicetype")){
                case "saving" :
                    bName = bun.getStringArrayList("bankname");
                    sName = bun.getStringArrayList("bankservice");
                    rate = bun.getStringArrayList("interest");
                    urlstring = bun.getStringArrayList("siteUrl");
                    str = "";
                    dlg.setTitle("정기예금");
                    str = "저축금액:1000만원, 저축기간: 12개월 기준\n\n";
                    for (int i=0;i<10;i++) {
                        str += "은행명: " + bName.get(i)+"\n";
                        str += "상품명: " + sName.get(i)+"\n";
                        str += "이자율: " + rate.get(i)+"% (세전)\n";
                        str += "기업개요 링크:\n" + urlstring.get(i)+"\n--------------------------------------------------\n";
                    }
                    dlg.setMessage(str);
                    dlg.setPositiveButton("확인", null);
                    dlg.show();

                    break;
                case "installment":
                    bName = bun.getStringArrayList("bankname");
                    sName = bun.getStringArrayList("bankservice");
                    rate = bun.getStringArrayList("interest");
                    urlstring = bun.getStringArrayList("siteUrl");
                    str = "";
                    dlg.setTitle("적금");
                    str = "월 저축금액:10만원, 저축기간: 12개월 기준\n\n";
                    for (int i=0;i<10;i++) {
                        str += "은행명: " + bName.get(i)+"\n";
                        str += "상품명: " + sName.get(i)+"\n";
                        str += "이자율: " + rate.get(i)+"% (세전)\n";
                        str += "기업개요 링크:\n" + urlstring.get(i)+"\n--------------------------------------------------\n";

                    }
                    dlg.setMessage(str);
                    dlg.setPositiveButton("확인", null);
                    dlg.show();
                    break;
                default:
                    break;

            }
        }
    };
}