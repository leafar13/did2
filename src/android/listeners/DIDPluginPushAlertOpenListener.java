//
// Disclaimer
// © 2019, Cyxtera Cybersecurity, Inc. d/b/a AppGate.  All rights reserved.  

// Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met: 
// (a) redistributions of source code must retain the above copyright notice, this list of conditions and the disclaimer below, and (b) 
// redistributions in binary form must reproduce the above copyright notice, this list of conditions and the disclaimer below in the documentation
// and/or other materials provided with the distribution. 

// THE CODE AND SCRIPTS POSTED ON THIS WEBSITE ARE PROVIDED ON AN “AS IS” BASIS AND YOUR USE OF SUCH CODE AND/OR SCRIPTS IS AT YOUR OWN RISK.  
// APPGATE DISCLAIMS ALL EXPRESS AND IMPLIED WARRANTIES, EITHER IN FACT OR BY OPERATION OF LAW, STATUTORY OR OTHERWISE, INCLUDING, BUT NOT LIMITED TO, 
// ALL WARRANTIES OF MERCHANTABILITY, TITLE, FITNESS FOR A PARTICULAR PURPOSE, NON-INFRINGEMENT, ACCURACY, COMPLETENESS, COMPATABILITY OF SOFTWARE OR 
// EQUIPMENT OR ANY RESULTS TO BE ACHIEVED THEREFROM.  APPGATE DOES NOT WARRANT THAT SUCH CODE AND/OR SCRIPTS ARE OR WILL BE ERROR-FREE.  
// IN NO EVENT SHALL APPGATE BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, RELIANCE, EXEMPLARY, PUNITIVE OR CONSEQUENTIAL DAMAGES, OR ANY LOSS 
// OF GOODWILL, LOSS OF ANTICIPATED SAVINGS, COST OF PURCHASING REPLACEMENT SERVICES, LOSS OF PROFITS, REVENUE, DATA OR DATA USE, ARISING IN ANY WAY OUT 
// OF THE USE AND/OR REDISTRIBUTION OF SUCH CODE AND/OR SCRIPTS, REGARDLESS OF THE LEGAL THEORY UNDER WHICH SUCH LIABILITY IS ASSERTED AND REGARDLESS 
// OF WHETHER APPGATE HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH LIABILITY.
//
package net.easysol.did.DetectIDCordovaPlugin.listeners;

import com.cyxtera.did.sdk.data.utils.LogMessagePriority;
import com.cyxtera.did.sdk.data.utils.LogUtils;
import com.google.gson.Gson;

import net.easysol.did.common.transaction.TransactionInfo;
import net.easysol.did.push_auth.alert.listener.PushAlertOpenListener;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;
import org.json.JSONException;
import org.json.JSONObject;

public class DIDPluginPushAlertOpenListener implements PushAlertOpenListener {

    private CallbackContext callbackContext;
    private TransactionInfo transactionInfo;

    private static DIDPluginPushAlertOpenListener instance;

    private DIDPluginPushAlertOpenListener() {
    }

    public static DIDPluginPushAlertOpenListener getInstance() {
        if (instance == null) {
            instance = new DIDPluginPushAlertOpenListener();
        }
        return instance;
    }

    public void setCallbackContext(CallbackContext callbackContext) {
        this.callbackContext = callbackContext;
        if (transactionInfo != null) {
            onPushAlertOpen(transactionInfo);
            transactionInfo = null;
        }
    }

    public void setTransactionInfo(TransactionInfo transactionInfo) {
        this.transactionInfo = transactionInfo;
    }

    @Override
    public void onPushAlertOpen(TransactionInfo transactionInfo) {
        try {
            String jsonTransactionInfo = new Gson().toJson(transactionInfo);
            JSONObject jsonObject = new JSONObject(jsonTransactionInfo);
            PluginResult result = new PluginResult(PluginResult.Status.OK, jsonObject);
            result.setKeepCallback(true);
            callbackContext.sendPluginResult(result);
        } catch (JSONException e) {
            LogUtils.logErrorWithPriority(getClass().getSimpleName(), "onPushTransactionOpen", e.getMessage(), LogMessagePriority.NOT_IMPORTANT_NOT_URGENT);
        }
    }
}
