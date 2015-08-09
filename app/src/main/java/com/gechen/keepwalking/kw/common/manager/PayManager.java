package com.gechen.keepwalking.kw.common.manager;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;

import com.gechen.keepwalking.kw.common.model.PayInfo;
import com.gechen.keepwalking.kw.constants.AppConfig;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * Created by Administrator on 2015/8/9.
 */
public class PayManager {
    private Activity mContext;
    private Handler mPayHandler;
    private static final int RESULT_OK = 0x0001;

    public PayManager(Activity context, Handler handler) {
        this.mContext = context;
        this.mPayHandler = handler;
        IntentFilter filter = new IntentFilter();
        filter.addAction(AppConfig.WXPAY_FINISH);
        mContext.registerReceiver(mHandleMessageReceiver, filter);
    }

    /**
     * 微信支付的appsecret/appkey/partnerkey/由服务端完成，支付前确定订单
     * 将订单参数给服务器，服务器返回响应的数据，在调用微信api支付
     */
    private void sendPayRequestByWechat(PayInfo payInfo) {
        if(payInfo == null) {
            return;
        }

        IWXAPI api = WXAPIFactory.createWXAPI(mContext, payInfo.getAppId());
        PayReq payReq = new PayReq();
        payReq.appId = payInfo.getAppId();
        payReq.partnerId = payInfo.getPartnerId();
        payReq.prepayId = payInfo.getPrepayId();
        payReq.nonceStr = payInfo.getNonceStr();
        payReq.timeStamp = payInfo.getTimeStamp();
        payReq.packageValue = payInfo.getPackageValue();
        payReq.sign = payInfo.getSign();
        api.sendReq(payReq);
    }

//    private void sendPayRequestByAlipay(PayInfo payInfo) {
//        final String alipayPayInfo = getAlipayPayInfo(payInfo);
//
//        Runnable payRunnable = new Runnable() {
//            @Override
//            public void run() {
//                PayTask alipay = new PayTask(TradePayActivity.this);
//                String result = alipay.pay(alipayPayInfo);
//                Message.obtain(mPayHandler, SDK_PAY_FLAG, result).sendToTarget();
//            }
//        };
//
//        Thread payThread = new Thread(payRunnable);
//        payThread.start();
//    }

//    private String getAlipayPayInfo(PayInfo payInfo) {
//        String orderInfo = "_input_charset=\"utf-8\"";
//        orderInfo += "&body=" + "\"" + payInfo.getBody() + "\"";
//        orderInfo += "&notify_url=" + "\"" + payInfo.getNotifyUrl() + "\"";
//        orderInfo += "&out_trade_no=" + "\"" + payInfo.getOutTradeNo() + "\"";
//        orderInfo += "&partner=" + "\"" + payInfo.getPartner() + "\"";
//        orderInfo += "&payment_type=\"1\"";
//        orderInfo += "&seller_id=" + "\"" + payInfo.getSellerEmail() + "\"";
//        orderInfo += "&service=\"mobile.securitypay.pay\"";
//        orderInfo += "&subject=" + "\"" + payInfo.getSubject() + "\"";
//        orderInfo += "&total_fee=" + "\"" + payInfo.getTotalFee() + "\"";
//        orderInfo += "&sign=" + "\"" + payInfo.getSign() + "\"";
//        orderInfo += "&sign_type=" + "\"" + payInfo.getSignType() + "\"";
//        return orderInfo;
//    }

//    private Handler.Callback mPayCallback = new Handler.Callback() {
//
//        @Override
//        public boolean handleMessage(Message msg) {
//            payFinish();
//            return false;
//        }
//
//    };

    private void payFinish() {
        Intent intent = new Intent(AppConfig.UPDATE_TRADE);
        mContext.sendBroadcast(intent);
        mContext.setResult(RESULT_OK);
        mContext.finish();
    }

    private BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            payFinish();
        }

    };
}
