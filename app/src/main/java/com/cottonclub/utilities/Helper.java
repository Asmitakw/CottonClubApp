package com.cottonclub.utilities;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListPopupWindow;
import android.widget.TextView;

import com.cottonclub.BuildConfig;
import com.cottonclub.R;
import com.cottonclub.interfaces.DialogListener;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;


public class Helper {
    public static void showOkDialog(Context context, String msg) {
        final Dialog dialog = new Dialog(context);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_ok_dialog);
        TextView tvOK, tvMsg;
        ImageView ivClose;
        tvMsg = (TextView) dialog.findViewById(R.id.tvMsg);
        tvOK = (TextView) dialog.findViewById(R.id.tvOK);
        tvMsg.setText(msg);
        ivClose = (ImageView) dialog.findViewById(R.id.ivClose);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tvOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public static void showOkClickDialog(Context context, String msg, final DialogListener dialogListener) {
        final Dialog dialog = new Dialog(context);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_ok_dialog);
        TextView tvOK, tvMsg;
        ImageView ivClose;
        tvMsg = (TextView) dialog.findViewById(R.id.tvMsg);
        tvOK = (TextView) dialog.findViewById(R.id.tvOK);
        tvMsg.setText(msg);
        ivClose = (ImageView) dialog.findViewById(R.id.ivClose);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        tvOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (dialogListener != null)
                    dialogListener.onButtonClicked(Dialog.BUTTON_POSITIVE);
            }
        });
        dialog.show();
    }


    public static void showOkCancelDialog(Context context, String message, String yesButtonMessage,
                                          String noButtonMessage,
                                          final DialogListener dialogListener) {
        AlertDialog.Builder build = new AlertDialog.Builder(context);
        final AlertDialog okCancelDialog;
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_yes_no, null);
        build.setView(view);
        okCancelDialog = build.create();
        okCancelDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        final TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        final TextView tvMessage = (TextView) view.findViewById(R.id.tvMessage);

        final TextView btnYes = (TextView) view.findViewById(R.id.btnYes);
        btnYes.setText(yesButtonMessage);
        final TextView btnNo = (TextView) view.findViewById(R.id.btnNo);
        btnNo.setText(noButtonMessage);

        final ImageView ivClose = (ImageView) view.findViewById(R.id.ivClose);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                okCancelDialog.dismiss();
            }
        });


        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                okCancelDialog.dismiss();
                if (dialogListener != null)
                    dialogListener.onButtonClicked(Dialog.BUTTON_POSITIVE);
            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                okCancelDialog.dismiss();
                if (dialogListener != null)
                    dialogListener.onButtonClicked(Dialog.BUTTON_NEGATIVE);
            }

        });
        tvTitle.setText(context.getString(R.string.app_name));
        if (TextUtils.isEmpty(message))
            tvMessage.setText("");
        else
            tvMessage.setText(message);

        okCancelDialog.show();
    }

    public static boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }


    public static boolean isValidPassword(String password) {
        String pattern = "((?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
        java.util.regex.Pattern mpattern = java.util.regex.Pattern.compile(pattern);
        java.util.regex.Matcher mMatcher = mpattern.matcher(password);
        return mMatcher.matches();
    }

    public static String formatDate(String dateInString) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.US);
        SimpleDateFormat formatedDate = new SimpleDateFormat("dd MMM yyyy", Locale.US);
        String newDate = "";
        try {
            Date date = formatter.parse(dateInString);
            newDate = formatedDate.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return newDate;
    }

    public static Date getDatePart(String date) {
        Date myDate = null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.US);
            //simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            myDate = simpleDateFormat.parse(date);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return myDate;
    }

    public static Bitmap retriveVideoFrameFromVideo(String videoPath)
            throws Throwable {
        Bitmap bitmap = null;
        MediaMetadataRetriever mediaMetadataRetriever = null;
        try {
            mediaMetadataRetriever = new MediaMetadataRetriever();
            if (Build.VERSION.SDK_INT >= 14)
                mediaMetadataRetriever.setDataSource(videoPath, new HashMap<String, String>());
            else
                mediaMetadataRetriever.setDataSource(videoPath);
            bitmap = mediaMetadataRetriever.getFrameAtTime();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Throwable(
                    "Exception in retriveVideoFrameFromVideo(String videoPath)"
                            + e.getMessage());

        } finally {
            if (mediaMetadataRetriever != null) {
                mediaMetadataRetriever.release();
            }
        }
        return bitmap;
    }


    public class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;

        @Override
        protected String doInBackground(String... params) {
            publishProgress(""); // Calls onProgressUpdate()
            try {
                // Do your long operations here and return the result

                retriveVideoFrameFromVideo("");

                int time = Integer.parseInt(params[0]);
                Thread.sleep(time);

            } catch (InterruptedException e) {
                e.printStackTrace();
                resp = e.getMessage();
            } catch (Exception e) {
                e.printStackTrace();
                resp = e.getMessage();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            return resp;
        }


        @Override
        protected void onPostExecute(String result) {


        }


        @Override
        protected void onPreExecute() {

        }


        @Override
        protected void onProgressUpdate(String... text) {

        }
    }

    public static String getCurrentVersionName(Context context) {
        PackageInfo pInfo = null;
        try {
            pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String version = pInfo.versionName;
        return version;
    }


    public static int getCurrentVersionCode(Context context) {
        PackageInfo pInfo = null;
        try {
            pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        int code = pInfo.versionCode;
        return code;
    }

    public static void Log(String tag, String msg) {
        if (msg == null)
            return;

        if (BuildConfig.DEBUG)
            android.util.Log.v(tag, msg);

    }

    public static void errorLog(String tag, String msg) {
        if (msg == null)
            return;

        if (BuildConfig.DEBUG)
            android.util.Log.e(tag, msg);

    }

    //Added for Image pick
    public static Uri getOutputMediaFileUri() {
        return Uri.fromFile(getOutputMediaFile());
    }

    private static File getOutputMediaFile() {
        // External sdcard location
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "cottonclub");
        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
        return mediaFile;
    }

    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getImagePathFromGalleryAboveKitkat(Context cntxt, Uri uri) {
        if (uri == null) {
            return null;
        }

        String[] projection = {MediaStore.Images.Media.DATA};

        Cursor cursor = null;
        if (Build.VERSION.SDK_INT > 19) {
            try {
                // Will return "image:x*"
                String wholeID = DocumentsContract.getDocumentId(uri);
                // Split at colon, use second item in the array
                if (!wholeID.contains(":"))
                    return null;

                String id = wholeID.split(":")[1];
                // where id is equal to
                String sel = MediaStore.Images.Media._ID + "=?";
                cursor = cntxt.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        projection, sel, new String[]{id}, null);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else {
            cursor = cntxt.getContentResolver().query(uri, projection, null, null, null);
        }
        String path = null;
        try {
            int column_index = cursor
                    .getColumnIndex(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            path = cursor.getString(column_index).toString();
            cursor.close();
        } catch (NullPointerException e) {
            return null;
        } catch (Exception e) {
            return null;
        }
        return path;
    }

    public static void showDropDown(View view, ListAdapter adapter, final AdapterView.OnItemClickListener clickListener) {
        final ListPopupWindow window = new ListPopupWindow(view.getContext());
        window.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                window.dismiss();
                clickListener.onItemClick(arg0, arg1, arg2, arg3);
            }
        });

        window.setAdapter(adapter);
        window.setModal(true);
        window.setAnchorView(view);
        window.setWidth(ListPopupWindow.WRAP_CONTENT);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        window.show();
        if (window.isShowing()) {
            window.getListView().setVerticalScrollBarEnabled(false);
            window.getListView().setFastScrollEnabled(true);
        }
    }

    public static Dialog showProgressDialog(Context context) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.vw_custom_progress_bar);
        dialog.setCancelable(false);
        dialog.show();
        return dialog;
    }

    public static String getCurrentTime() {
        //date output format
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());
    }
}



