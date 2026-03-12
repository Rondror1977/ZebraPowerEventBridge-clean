
package com.newlog.powerbridge;
import android.content.*;import android.os.*;import android.util.Log;
public class PowerEventBridgeReceiver extends BroadcastReceiver{
 @Override public void onReceive(Context c,Intent i){ if(i==null)return;String a=i.getAction(),s=null;
 if(Intent.ACTION_POWER_CONNECTED.equals(a))s="CHARGING";else if(Intent.ACTION_POWER_DISCONNECTED.equals(a))s="NOT_CHARGING";
 else if(Intent.ACTION_BOOT_COMPLETED.equals(a))s=getState(c);
 if(s!=null)send(c,s);
 }
 private String getState(Context c){Intent b=c.registerReceiver(null,new IntentFilter(Intent.ACTION_BATTERY_CHANGED));if(b!=null){int p=b.getIntExtra(BatteryManager.EXTRA_PLUGGED,-1);boolean ch=p==BatteryManager.BATTERY_PLUGGED_AC||p==BatteryManager.BATTERY_PLUGGED_USB||p==BatteryManager.BATTERY_PLUGGED_WIRELESS;return ch?"CHARGING":"NOT_CHARGING";}return "UNKNOWN";}
 private void send(Context c,String s){Intent v=new Intent("com.wavelink.intent.action.BARCODE");v.addCategory(Intent.CATEGORY_DEFAULT);
 v.putExtra("com.wavelink.extra.symbology_type","POWER_EVENT");v.putExtra("com.wavelink.extra.data_string",s);
 v.setPackage("com.wavelink.velocity");c.sendBroadcast(v);} }
