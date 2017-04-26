package io.ribot.app.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;


import java.util.List;

import io.ribot.app.constant.SchemeConstants;


public class SchemeUtils {

    public static boolean isMyUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return false;
        }
        Uri uri = Uri.parse(url);
        if (uri == null) {
            return false;
        }
        String scheme = uri.getScheme();
        if (SchemeConstants.MY_SCHEME.equalsIgnoreCase(scheme)) {
            return true;
        }
        String authority = uri.getAuthority();
        String path = uri.getPath();
        if (SchemeConstants.WEIBO_URI_SCHEME_HTTP.equalsIgnoreCase(scheme)
                && SchemeConstants.WEIBO_URI_AUTHORITY_HTTP
                .equalsIgnoreCase(authority) && path != null) {
            return path.toLowerCase().startsWith(
                    SchemeConstants.PRE_PATH_HTTP);
        }
        return false;
    }

    public static boolean openScheme(Context ctx, String scheme) {
        return openScheme(ctx, scheme, null);
    }

    public static boolean openScheme(Context ctx, String scheme, Bundle extras) {
        return openScheme(ctx, scheme, extras, -1);
    }

    public static boolean openScheme(Context ctx, String scheme, Bundle extras, int requestCode) {
        return openScheme(ctx, scheme, extras, null, requestCode);
    }

    public static boolean openScheme(Context ctx, String scheme, Bundle extras, Bundle optionBundle,
                                     int requestCode) {
        if (ctx == null || TextUtils.isEmpty(scheme)) {
            return false;
        }

        final Uri uri = Uri.parse(scheme);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(uri);
        intent.addCategory(Intent.CATEGORY_DEFAULT);

        if (isMyUrl(scheme)) {
            intent.setPackage(ctx.getPackageName());
        }

        if (!(ctx instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }

        if (extras != null) {
            intent.putExtras(extras);
        }

        if (queryActivityIntent(ctx, intent)) {
            if (ctx instanceof Activity && requestCode >= 0) {
                if (optionBundle != null) {
                    ActivityCompat.startActivityForResult((Activity) ctx, intent, requestCode, optionBundle);
                } else {
                    ((Activity) ctx).startActivityForResult(intent, requestCode);
                }
            } else {
                if (optionBundle != null && (ctx instanceof Activity)) {
                    ActivityCompat.startActivity((Activity) ctx, intent, optionBundle);
                } else {
                    ctx.startActivity(intent);
                }
            }
            return true;
        } else {
            return false;
        }
    }


    private static boolean queryActivityIntent(Context context, Intent intent) {
        final PackageManager packageManager = context.getPackageManager();
        final List<ResolveInfo> queryIntentActivities = packageManager
                .queryIntentActivities(intent, 0);
        return queryIntentActivities != null
                && queryIntentActivities.size() > 0;
    }

    /**
     * 获取scheme里指定key的value
     *
     * @param scheme
     * @return
     */
    public static String getValue(String scheme, String key) {
        if (!TextUtils.isEmpty(scheme)) {
            Uri uri = Uri.parse(scheme);

            if (uri != null && uri.isHierarchical()) {
                return uri.getQueryParameter(key);
            }
        }
        return null;
    }

}

