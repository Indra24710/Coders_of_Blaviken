package com.codersofblvkn.criminaltagging.Fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.codersofblvkn.criminaltagging.Activities.NotificationActivity;
import com.codersofblvkn.criminaltagging.R;
import com.codersofblvkn.criminaltagging.Utils.FilePath;
import com.codersofblvkn.criminaltagging.Utils.InternetConnection;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static android.content.Intent.ACTION_PICK;

public class DetectFragment extends Fragment {
    Button button_capture,button_choose, button_upload;
    ImageView imageView;
    String imageFilePath;
    Bitmap bitmap;
    String finalPath=null;
    private static final int PIC_ID=1001;
    private static final int CAMERA_PERMISSIONS =1002;
    private static final int PICK_IMAGE_REQUEST=1003;
    Context context;
    Uri filepath=null;
    Uri photoURI;
    ProgressDialog dialog;
    final String SERVER_URL="http://upload-dimg.herokuapp.com/upload/video";

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static final String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    public DetectFragment() {
        // Required empty public constructor
    }


    public static DetectFragment newInstance() {
        DetectFragment fragment = new DetectFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detect, container, false);
        button_capture=view.findViewById(R.id.button_capture);
        button_choose=view.findViewById(R.id.button_choose);
        button_upload=view.findViewById(R.id.upload_suspect);
        imageView=view.findViewById(R.id.criminal_image_iv);
        context=view.getContext();

        Intent intent = getActivity().getIntent();
        String action = intent.getAction();
        String type = intent.getType();


        int permission = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    getActivity(),
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }


        if (Intent.ACTION_SEND.equals(action) && type != null) {
                handleSendImage(intent);
        }



        button_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                    if(InternetConnection.checkConnection(getActivity().getApplicationContext()))
                    {
                        final String sendFilePath=finalPath;
                        if(sendFilePath!=null && imageView.getDrawable()!=null)
                        {
                            Log.d("Detect",sendFilePath);
                            dialog = ProgressDialog.show(getActivity(), "", getString(R.string.uploadingfile), true);

                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    //creating new thread to handle Http Operations
                                    uploadFile(sendFilePath);
                                }
                            }).start();
                        }
                        else
                        {
                            Toast.makeText(getContext(),getString(R.string.chooseimagetoupload),Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(getActivity().getApplicationContext(),getString(R.string.no_internet),Toast.LENGTH_SHORT).show();
                    }

            }
        });



        button_capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (ContextCompat.checkSelfPermission(
                        getActivity().getApplicationContext(), Manifest.permission.CAMERA) ==
                        PackageManager.PERMISSION_GRANTED) {
                    // You can use the API that requires the permission.
                    Intent pictureIntent = new Intent(
                            MediaStore.ACTION_IMAGE_CAPTURE);
                    if(pictureIntent.resolveActivity(Objects.requireNonNull(getContext()).getPackageManager()) != null){
                        //Create a file to store the image
                        File photoFile = null;
                        try {
                            photoFile = createImageFile();
                        } catch (IOException ex) {
                            // Error occurred while creating the File
                        }
                        if (photoFile != null) {
                            filepath = FileProvider.getUriForFile(getContext(),"com.codersofblvkn.criminaltagging.provider",photoFile);
                            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                                    filepath);
                            startActivityForResult(pictureIntent,
                                    PIC_ID);
                        }
                    }
                } else {
                    // You can directly ask for the permission.
                    // The registered ActivityResultCallback gets the result of this request.
                    requestPermissions(new String[]{Manifest.permission.CAMERA},CAMERA_PERMISSIONS);


                }


            }
        });


        button_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Picture"),PICK_IMAGE_REQUEST);
            }
        });
        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);


        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filepath = data.getData();
            imageView.setVisibility(View.VISIBLE);
            try {
                //getting image from gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), filepath);

//                Log.d("Detect", FilePath.getPath(getContext(),filepath).toString());
                finalPath= FilePath.getPath(getContext(),filepath);
                //Setting image to ImageView
                imageView.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("Detect",e.getMessage());
            }
        }
        else if(requestCode == PIC_ID && resultCode == RESULT_OK)
        {
            imageView.setVisibility(View.VISIBLE);
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), filepath);
                if(imageFilePath!=null)
                {
//                    Log.d("Detect",imageFilePath.toString());
                    finalPath= imageFilePath;
                }
                imageView.setImageBitmap(bitmap);
            } catch (Exception e) {
                Log.d("Detect",e.getMessage());
            }
        }
        else if(resultCode== RESULT_CANCELED)
        {
            finalPath=null;
            imageView.setVisibility(View.GONE);
            imageView.setImageBitmap(null);
        }
        Log.d("Detect","On Activity Result");
    }


    private File createImageFile() throws IOException {
        String timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir =
                getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        imageFilePath = image.getAbsolutePath();
        return image;
    }

    private void handleSendImage(Intent intent) {
        // Get the image URI from intent
        imageView.setVisibility(View.VISIBLE);
        Uri imageUri = intent.getParcelableExtra(Intent.EXTRA_STREAM);
        // When image URI is not null
        if (imageUri != null) {
            // Update UI to reflect image being shared
            imageView.setImageURI(imageUri);
            filepath=imageUri;
//            Log.d("Detect", FilePath.getPath(getContext(),filepath).toString());
            finalPath= FilePath.getPath(getContext(),filepath);
        } else{
            Toast.makeText(getActivity(), getString(R.string.urierror), Toast.LENGTH_LONG).show();
        }
    }
    public void uploadFile(final String selectedFilePath) {

        int serverResponseCode = 0;

        HttpURLConnection connection;
        DataOutputStream dataOutputStream;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";


        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File selectedFile = new File(selectedFilePath);
        String[] parts = selectedFilePath.split("/");
        final String fileName = parts[parts.length - 1];

        if (!selectedFile.isFile()) {
            dialog.dismiss();

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
//                    tvFileName.setText("Source File Doesn't Exist: " + selectedFilePath);
                }
            });
        } else {
            try {
                FileInputStream fileInputStream = null;
                try {
                    fileInputStream = new FileInputStream(selectedFilePath);
                }
                catch (Exception e)
                {
                    Log.d("Detect",e.getMessage());
                }
                URL url = new URL(SERVER_URL);
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);//Allow Inputs
                connection.setDoOutput(true);//Allow Outputs
                connection.setUseCaches(false);//Don't use a cached Copy
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Connection", "Keep-Alive");
                connection.setRequestProperty("ENCTYPE", "multipart/form-data");
                connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                connection.setRequestProperty("video", selectedFilePath);

                //creating new dataoutputstream
                dataOutputStream = new DataOutputStream(connection.getOutputStream());

                //writing bytes to data outputstream
                dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
                dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"video\";filename=\""
                        + selectedFilePath + "\"" + lineEnd);

                dataOutputStream.writeBytes(lineEnd);

                //returns no. of bytes present in fileInputStream
                bytesAvailable = fileInputStream.available();
                //selecting the buffer size as minimum of available bytes or 1 MB
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                //setting the buffer as byte array of size of bufferSize
                buffer = new byte[bufferSize];

                //reads bytes from FileInputStream(from 0th index of buffer to buffersize)
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                //loop repeats till bytesRead = -1, i.e., no bytes are left to read
                while (bytesRead > 0) {
                    //write the bytes read from inputstream
                    dataOutputStream.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                }

                dataOutputStream.writeBytes(lineEnd);
                dataOutputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                serverResponseCode = connection.getResponseCode();
                String serverResponseMessage = connection.getResponseMessage();
                BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
                StringBuilder sb = new StringBuilder();
                String output;
                Log.d("Detection","Server Response:");
                while ((output = br.readLine()) != null) {
                    sb.append(output);
                }
                String fo= String.valueOf(sb);

                Log.d("Detection", "Server Response is: " + fo + ": " + serverResponseCode);
                Log.d("Detection", "Server Response is: " + fo + ": " + serverResponseMessage);
                //response code of 200 indicates the server status OK
                if (serverResponseCode == 200) {
                    getDetectionData(fo);
                }

                //closing the input and output streams
                fileInputStream.close();
                dataOutputStream.flush();
                dataOutputStream.close();


            } catch (FileNotFoundException e) {
                e.printStackTrace();
                System.err.println(e.getMessage());
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), getString(R.string.filenotfound), Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (MalformedURLException e) {
                e.printStackTrace();
                System.err.println(e.getMessage());
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), getString(R.string.urlerror), Toast.LENGTH_SHORT).show();
                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
                System.err.println(e.getMessage());
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), getString(R.string.rwerror), Toast.LENGTH_SHORT).show();
                    }
                });
            }

        }

    }


    public void getDetectionData(String fo)
    {
        String url_image="";
        //creating new thread to handle Http Operations
        try {
            url_image=new JSONObject(fo).getString("url");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Request request1 = new Request.Builder()
                .url("https://codersofblaviken.blob.core.windows.net/detection/images/url.json")
                .build();
        try {
            OkHttpClient sHttpClient=new OkHttpClient();
            Response response = sHttpClient.newCall(request1).execute();
            if(response.isSuccessful())
            {
                String resp_forward=response.body().string();
                getDetectionID(resp_forward,url_image);
                Log.d("Detection","Successful "+resp_forward);
            }
            else
                {
                    Log.d("Detection","Error Ngrok Something else "+response.body().string());
                }
        } catch (IOException e) {
            Log.d("Detection","Error Ngrok Exception");
        }
    }


    @SuppressLint("CheckResult")
    public void getDetectionID(String urljson, String urlimage)
    {

        try {
            String CIDJSON="";
            String url=new JSONObject(urljson).getString("url")+"/image?url="+urlimage;
            Request request2 = new Request.Builder()
                    .url(url)
                    .build();
            OkHttpClient sHttpClient=new OkHttpClient();
            Log.d("Detection", "Final URL:"+url);
            Response response = sHttpClient.newCall(request2).execute();
            String resp=response.body().string();
            if(response.isSuccessful())
            {

                Log.d("Detection","Successful "+resp);
                finalDecisionMaking(resp);
            }
            else
                {
                    finalDecisionMaking("Error Server Disconnected");
                    Log.d("Detection","Error Ngrok Something else "+resp);

                }
        } catch (JSONException e) {
            finalDecisionMaking("Exception");
            Log.d("Detection","Error JSON Exception");
        } catch (IOException e) {
            finalDecisionMaking("Exception");
            Log.d("Detection","Unexpected IO Exception");
        }
        dialog.dismiss();
    }


    public void finalDecisionMaking(String output)
    {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if(output.equals("Error Server Disconnected")||output.equals("Exception")||output.equals("Error")||output.equals(""))
                {
                    Toast.makeText(getActivity(),"Error occured in the server",Toast.LENGTH_LONG).show();
                }
                else{
                    String message="";
                    try {
                        message=Integer.toString(new JSONObject(output).getInt("cid"));
                        Intent intent=new Intent(getActivity(), NotificationActivity.class);
                        intent.putExtra("cid",":"+message);
                        startActivity(intent);
                    } catch (JSONException e) {
                        Toast.makeText(getActivity(),"Improper CID Received",Toast.LENGTH_LONG).show();
                    }
                }

            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==CAMERA_PERMISSIONS)
        {
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {

            }
            else
            {
                Toast.makeText(getActivity().getApplicationContext(),getString(R.string.png),Toast.LENGTH_SHORT).show();
            }
        }

    }
}
