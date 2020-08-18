package com.cottonclub.utilities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentUris;
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
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListPopupWindow;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cottonclub.BuildConfig;
import com.cottonclub.R;
import com.cottonclub.interfaces.DialogListener;
import com.cottonclub.interfaces.ImageDialogActionListener;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


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
                if (dialogListener != null)
                    dialogListener.onButtonClicked(Dialog.BUTTON_POSITIVE);
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
            if (Build.VERSION.SDK_INT >= 21)
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

    @TargetApi(Build.VERSION_CODES.P)
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.P;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }



    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getImagePathFromGalleryAboveKitkat(Context cntxt, Uri uri) {
        if (uri == null) {
            return null;
        }

        String[] projection = {MediaStore.Images.Media.DATA};

        Cursor cursor = null;
        if (Build.VERSION.SDK_INT > 21) {
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

    public static String getFileName(String path) {
        File file = new File(path);
        return file.getName();
    }

    //Method to upload image
    public static void showImagePickDialog(final Context context, String title, final ImageDialogActionListener listener) {
        final Dialog dialog= new Dialog(context, R.style.CustomMediumText);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_add_photo_option, null);
        dialog.setContentView(view);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height =     WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);

        final TextView tvCamera= (TextView) view.findViewById(R.id.tvCamera);
        final TextView tvGallery = (TextView) view.findViewById(R.id.tvGallery);
        final ImageView ivCamera = (ImageView)view.findViewById(R.id.ivCamera);
        final ImageView ivGallery = (ImageView)view.findViewById(R.id.ivGallery);
        final TextView tvUploadImage = (TextView)view.findViewById(R.id.tvUploadImage);

        tvUploadImage.setText(title);

        //Adding animation effects to the view
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(context, R.anim.animation_rotate);
        Animation zoom_in = AnimationUtils.loadAnimation(context, R.anim.animation_zoom_in);
        Animation zoom_animation = AnimationUtils.loadAnimation(context, R.anim.animation_zoom_in);

        tvUploadImage.startAnimation(zoom_animation);
        tvCamera.startAnimation(zoom_in);
        tvGallery.startAnimation(zoom_in);

        ivCamera.startAnimation(hyperspaceJumpAnimation);
        ivGallery.startAnimation(hyperspaceJumpAnimation);

        ivCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (listener != null)
                    listener.onCameraOptionClicked();
            }
        });

        ivGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if (listener != null)
                    listener.onGalleryOptionClicked();
            }
        });

        dialog.setCancelable(true);
        dialog.show();
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void sendNotification(Context context, String message) {
        RequestQueue requestQueue;
        String firebaseURl = "https://fcm.googleapis.com/fcm/send";
        requestQueue = Volley.newRequestQueue(context);
        FirebaseMessaging.getInstance().subscribeToTopic("news");
        JSONObject mainObject = new JSONObject();
        try {
            mainObject.put("to", "/topics/" + "news");
            JSONObject notificationObject = new JSONObject();
            notificationObject.put("title", "Cotton Club");
            notificationObject.put("body", message);
            mainObject.put("notification", notificationObject);

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, firebaseURl,
                    mainObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> header = new HashMap<>();
                    header.put("content-type", "application/json");
                    header.put("authorization", "key=AAAA0How4PA:APA91bExSt4jGOv5jIdb0OR0P2lv13qVZHN6Wo_TqwUdKLcs_rL_a8taAe5rBCfE1Ko3GrN6F9trOBYCxXWByYhjkbXb2b8Ta9VmTOcg2bRBaBrgKvHOfPTlgnEQz99R9uf85b_OFug1");
                    return header;
                }
            };

            requestQueue.add(request);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}



