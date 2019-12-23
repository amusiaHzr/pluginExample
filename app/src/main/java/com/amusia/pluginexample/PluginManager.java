package com.amusia.pluginexample;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Environment;
import android.widget.Toast;
import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import dalvik.system.DexClassLoader;

public class PluginManager {
    private final String TAG = PluginManager.class.getSimpleName();

    private static PluginManager instance = null;
    private Context mContext;
    private DexClassLoader mDexClassLoader;
    private Resources mResources;
    private String apkPath;

    public static PluginManager getInstance(Context context) {
        if (instance == null) {
            synchronized (PluginManager.class) {
                if (instance == null) {
                    instance = new PluginManager(context);
                }
            }
        }
        return instance;
    }

    public void loadPlugin() {
        try {
            //插件apk地址
            File file = new File(Environment.getExternalStorageDirectory() + File.separator + "plugin-debug.apk");
            if (!file.exists()) {
                Toast.makeText(mContext, "plugin不存在", Toast.LENGTH_SHORT).show();
//                Log.e(TAG, "plugin不存在");
                return;
            }
            apkPath = file.getAbsolutePath();
            //插件apk的缓存目录  "data/data/包名/"
            File plugin = mContext.getDir("plugin", Context.MODE_PRIVATE);
            //类加载器
            mDexClassLoader = new DexClassLoader(apkPath, plugin.getAbsolutePath(), null, mContext.getClassLoader());

            //拿到资源加载器Class
            Class<AssetManager> assetManagerClass = AssetManager.class;
            //通过反射得到addAssetPath方法
            Method addAssetPath = assetManagerClass.getMethod("addAssetPath", String.class);
            //通过newInstance实例化AssetManager
            AssetManager assetManager = assetManagerClass.newInstance();
            //执行addAssetPath方法，设置资源路径
            addAssetPath.invoke(assetManager, apkPath);
            //获取到当前app环境的Resources
            Resources r = mContext.getResources();
            //资源加载 assetManager：资源管理器（pluginAPK的路径）  DisplayMetrics：系统的Resources配置  Configuration：系统的Resources配置
            mResources = new Resources(assetManager, r.getDisplayMetrics(), r.getConfiguration());

            parserPluginApk(file);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private PluginManager(Context context) {
        mContext = context;
    }


    DexClassLoader getDexClassLoader() {
        return mDexClassLoader;
    }

    String getApkPath() {
        return apkPath;
    }

    Resources getResources() {
        return mResources;
    }

    private void parserPluginApk(File file) throws Exception {

        //得到PackageParser（package解析器）
        @SuppressLint("PrivateApi")
        Class<?> mPackageParserClass = Class.forName("android.content.pm.PackageParser");
        // 得到parsePackage方法
        Method mParsePackageMethod = mPackageParserClass.getMethod("parsePackage", File.class, int.class);
        //实例化PackageParser
        Object mPackageParser = mPackageParserClass.newInstance();
        //执行 parsePackage 方法 返回一个package
        Object mPackage = mParsePackageMethod.invoke(mPackageParser, file, PackageManager.GET_ACTIVITIES);
        //获取receivers属性
        Object mReceivers = mPackage.getClass().getField("receivers").get(mPackage);
        //强转成ArrayList
        ArrayList mArrayList = (ArrayList) mReceivers;
        //对获取到的 mActivity进行解析
        for (Object mActivity : mArrayList) {
            //获取 PackageParser的内部类Component
            Class<?> mComponent = Class.forName("android.content.pm.PackageParser$Component");
            //获取Component中的intents属性
            Object mIntentsField = mComponent.getField("intents").get(mActivity);
            //强转成ArrayList
            ArrayList<IntentFilter> mIntents = (ArrayList<IntentFilter>) mIntentsField;


            //获取state
            Object mState = Class.forName("android.content.pm.PackageUserState").newInstance();

            //获取userID
            Class<?> mUserHandleClass = Class.forName("android.os.UserHandle");
            Method getCallingUserIdMethod = mUserHandleClass.getMethod("getCallingUserId");
            int userID = (int) getCallingUserIdMethod.invoke(null);

            //得到 generateActivityInfo 方法
            Method generateActivityInfoMethod = mPackageParserClass.getMethod("generateActivityInfo", mActivity.getClass(), int.class, mState.getClass(), int.class);

            //执行generateActivityInfo得到ActivityInfo
            ActivityInfo mActivityInfo = (ActivityInfo) generateActivityInfoMethod.invoke(mPackageParser, mActivity, 0, mState, userID);
            //得到静态广播名字
            String broadcastName = mActivityInfo.name;
            //加载静态广播类
            Class<?> staticReceiverClass = getDexClassLoader().loadClass(broadcastName);
            BroadcastReceiver staticReceiver =(BroadcastReceiver) staticReceiverClass.newInstance();
            //对所有IntentFilter进行注册
            for (IntentFilter mIntent : mIntents) {
                mContext.registerReceiver(staticReceiver, mIntent);
            }
        }
    }


}
