package SimpleTest;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import com.android.bluetoothtest.R;

/**
 * This class echoes a string called from JavaScript.
 */
public class MyTest extends CordovaPlugin {

    private SimpleTest.BluetoothTestActivity bluetoothTestActivity = null;
    private BluetoothAdapter bluetoothAdapter = null;
    /////////////////////////////////////////////////////////////////////////////////////////////
    private Spinner mSpinner=null;
    private List<String> mpairedDeviceList=new ArrayList<String>();
    private ArrayAdapter<String> mArrayAdapter;

    private BluetoothAdapter mBluetoothAdapter = null; //创建蓝牙适配器
    private BluetoothDevice mBluetoothDevice=null; //蓝牙驱动
    private BluetoothSocket mBluetoothSocket=null;
    OutputStream mOutputStream=null;
    /*Hint: If you are connecting to a Bluetooth serial board then try using
     * the well-known SPP UUID 00001101-0000-1000-8000-00805F9B34FB. However
     * if you are connecting to an Android peer then please generate your own unique UUID.*/
    private static final UUID SPP_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    Set<BluetoothDevice> pairedDevices=null;
    /////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("coolMethod")) {
            String message = args.getString(0);
            this.coolMethod(message, callbackContext);
            return true;
        }else if(action.equals("printData")){
            this.printData(args.getString(0),callbackContext);
            return true;
        }else if(action.equals("connect")){
            this.connect(args.getString(0),callbackContext);
            return true;
        }else if(action.equals("cutpaper")){
            this.cutPaper(args.getString(0),callbackContext);
            return true;
        }else if(action.equals("opencash")){
            this.openCash(args.getString(0),callbackContext);
            return true;
        }else if (action.equals("list")) {
            this.list(callbackContext);
            return true;
        }
        return false;
    }

    /**
     * 函数初始化
     */
//    private void init(){
//        mBluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
//        mArrayAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,mpairedDeviceList);
//        mSpinner.setAdapter(mArrayAdapter);
//        try {
//            if (mBluetoothAdapter==null) {
//                System.out.println(R.string.NotBluetoothAdapter);
//            } else if (mBluetoothAdapter.isEnabled()) {
//                String getName=mBluetoothAdapter.getName();
//                pairedDevices=mBluetoothAdapter.getBondedDevices();
//                while (mpairedDeviceList.size()>1) {
//                    mpairedDeviceList.remove(1);
//                }
//                if (pairedDevices.size()==0) {
//
//                }
//                for (BluetoothDevice device : pairedDevices) {
//                    // Add the name and address to an array adapter to show in a ListView
//                    getName=device.getName() + "#" + device.getAddress();
//                    mpairedDeviceList.add(getName);
//                }
//            }
//            else {
//                System.out.println("BluetoothAdapter not open...");
//            }
//        }
//        catch (Exception e) {
//            System.out.println(e.toString());
//        }
//    }

    /**
     * 获取匹配列表
     * @param callbackContext
     */
    private void list(CallbackContext callbackContext) throws JSONException{

        JSONArray deviceList = new JSONArray();
        Set<BluetoothDevice> bondedDevices = bluetoothAdapter.getBondedDevices();

        for (BluetoothDevice device : bondedDevices) {
            deviceList.put(this.deviceToJSON(device));
        }
        callbackContext.success(deviceList);
    }

    /**
     * 测试函数
     * @param message
     * @param callbackContext
     */
    private void coolMethod(String message, CallbackContext callbackContext) {
        if (message != null && message.length() > 0) {
            callbackContext.success(message);
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }

    /**
     * 例子
     * @param callbackContext
     */
    private void example(CallbackContext callbackContext) {
        if(bluetoothTestActivity == null){
            bluetoothTestActivity = new SimpleTest.BluetoothTestActivity();
        }
        callbackContext.success("ok");
    }

    /***
     * 连接
     * @param data
     * @param callbackContext
     */
    private void connect(String data,CallbackContext callbackContext){
        System.out.println("_______________________________");
        String temString=(String) mSpinner.getSelectedItem();
        if (mSpinner.getSelectedItemId()!=0) {
//            if (buttonConnect.getText()!=getString(R.string.Disconnected)) {
                try {
                    mOutputStream.close();
                    mBluetoothSocket.close();
                    //callbackContext.error(R.string.Disconnected);
                } catch (Exception e) {
                    callbackContext.error(e.toString());
                    return;
                }
//            }
            temString=temString.substring(temString.length()-17);
            try {
                System.out.println("连接中......");
                mBluetoothDevice=mBluetoothAdapter.getRemoteDevice(temString);
                mBluetoothSocket=mBluetoothDevice.createRfcommSocketToServiceRecord(SPP_UUID);
                mBluetoothSocket.connect();
                System.out.println("已连接。");
            } catch (Exception e) {
                callbackContext.error(R.string.ConnectFailed+e.toString());
            }
        }
        else {
            callbackContext.error("Pls select a bluetooth device...");
        }

    }

    /**
     * 打印数据
     * @param data
     * @param callbackContext
     */
    private void printData(String data,CallbackContext callbackContext) {
        if (data != null && data.length() > 0) {
            try {
                mOutputStream=mBluetoothSocket.getOutputStream();
                mOutputStream.write((data+"--->"+"\n").getBytes("GBK"));
                mOutputStream.flush();
                System.out.println("Data sent successfully...");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("打印失败:"+e.getMessage());
                callbackContext.error("打印失败:"+e.getMessage());
            }
        }else{
            callbackContext.error("Data is not null");
        }
    }

    /**
     * 切纸
     * @param data
     * @param callbackContext
     */
    private void cutPaper(String data,CallbackContext callbackContext){
        try {
            if (data != null && data.length() > 0) {
            }else{
                callbackContext.error("Pls input print data...");
                return;
            }
            mOutputStream=mBluetoothSocket.getOutputStream();
            mOutputStream.write(new byte[]{0x0a,0x0a,0x1d,0x56,0x01});
            mOutputStream.flush();
            callbackContext.success("Data sent successfully...");
        } catch (IOException e) {
            e.printStackTrace();
            callbackContext.error(R.string.CutPaperFaild+e.getMessage());
        }
    }

    /**
     * 开钱箱
     * @param data
     * @param callbackContext
     */
    private void openCash(String data,CallbackContext callbackContext){
        try {
            if (data != null && data.length() > 0) {

            }else{
                callbackContext.error("Pls input print data...");
                return;
            }
            mOutputStream=mBluetoothSocket.getOutputStream();
            mOutputStream.write(new byte[]{0x1b,0x70,0x00,0x1e,(byte)0xff,0x00});
            mOutputStream.flush();
            callbackContext.success("Data sent successfully...");
        } catch (IOException e) {
            e.printStackTrace();
            callbackContext.error(R.string.OpenCashFaild+e.getMessage());
        }
    }

    /**
     * 将获取的数据转换成json
     * @param device
     * @return
     * @throws JSONException
     */
    private JSONObject deviceToJSON(BluetoothDevice device) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("name", device.getName());
        json.put("address", device.getAddress());
        json.put("id", device.getAddress());
        if (device.getBluetoothClass() != null) {
            json.put("class", device.getBluetoothClass().getDeviceClass());
        }
        return json;
    }

}
